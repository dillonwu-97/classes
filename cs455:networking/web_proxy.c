/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu 
Web Proxy
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>

#define BUFSIZE 2056

int main(int argc, char **argv){
	// port, listening socket, receiver socket, send socket
	int port, lsocket, rsocket, ssocket, bsocket, received_bytes;
	struct sockaddr_in proxylisten, proxyreceive, proxysend, proxybrowse;
	struct hostent* hostname;
	FILE* stream;
	char buf[BUFSIZE], method[BUFSIZE], uri[BUFSIZE], version[BUFSIZE], sendline[BUFSIZE], recvline[BUFSIZE];

	// Checking to see if there are enough arguments
	if (argc!=2) {
		perror("Not enough arguments babe");
	}

	port = atoi(argv[1]);

	lsocket = socket(AF_INET, SOCK_STREAM, 0);

	// listener for the proxy
	memset(&proxylisten, 0, sizeof(struct sockaddr_in));
	proxylisten.sin_family = AF_INET;
	proxylisten.sin_port = htons(port);
	proxylisten.sin_addr.s_addr = INADDR_ANY;

	// binding
	if (bind(lsocket, (struct sockaddr*) &proxylisten, sizeof(struct sockaddr)) < 0) 
		perror("Issue with the binding of isaac");

	// listener
	if (listen(lsocket, 1) < 0)
		perror("Can't hear nothin'");

	// wait until accept something from client
	while (1){
		printf("%s\n", "Starting");
		socklen_t prsize = sizeof(proxyreceive);
		rsocket = accept(lsocket, (struct sockaddr*) &proxyreceive, &prsize);
		if (rsocket < 0) 
					perror("ERROR on accept");

				if ((stream = fdopen(rsocket, "r+")) == NULL)
					perror("Won't open");

			// printf("%s", buf);
				// recv(rsocket, &buf, BUFSIZE, 0); 
		// printf("%s\n", buf);
				
				// Getting the information from stream
				fgets(buf, BUFSIZE, stream);
				printf("%s\n", buf);
				// Extracting website information
				sscanf(buf, "%s %s %s\n", method, uri, version);

		if (strcasecmp(method, "GET")) {
					perror("Not implemented");
				}
				// ignore '/' character in uri
				memmove(uri, &(uri[1]), strlen(&(uri[1])));
				uri[strlen(uri)-1] = '\0';

				if((hostname = gethostbyname(uri)) == NULL) {
					perror("no host");
				}
				// printf("%s\n", hostname->h_name);

				snprintf(sendline, BUFSIZE, "GET / HTTP/1.0\r\nHost: %s\r\nConnection:close\r\n\r\n", uri);
				printf("%s\n", sendline);
			ssocket = socket(AF_INET, SOCK_STREAM, 0);

			memset(&proxysend, 0, sizeof(struct sockaddr_in));
			proxysend.sin_family = AF_INET;
			proxysend.sin_port = htons(80);
			memcpy(&proxysend.sin_addr, hostname->h_addr_list[0], hostname->h_length);

			if (connect(ssocket, (struct sockaddr*)&proxysend, sizeof(struct sockaddr)) < 0)
				perror("Connection failed what is this at&t?");

			// if (write(ssocket, sendline, strlen(sendline)) < 0)
			// 	perror("nothing was written");

			if (send(ssocket, sendline, strlen(sendline), 0) < 0)
				perror("nothing was sent");

			memset(recvline, 0, BUFSIZE);
			while ((received_bytes = recv(ssocket, recvline, BUFSIZE,0)) > 0) {
				recvline[BUFSIZE-1] = '\0';
			}

			break;
	}

		printf("%s\n", recvline);

		// char* testline = "HTTP/1.1 200 OK\nContent-length: 46\nContent-Type: text/html\n\n<html><body><H1>Hello world</H1></body></html>\0";
		// printf("%s\n", testline);

		// this not needed
		// if (connect(rsocket, (struct sockaddr*)&proxybrowse, sizeof(struct sockaddr)) < 0) 
		// 	perror ("Connection failed verizon sucks");

		if((send(rsocket, recvline, strlen(recvline), 0)) < 0)
			perror ("nothing was sent to the browser");

		close(lsocket);
		close(rsocket);
		close(ssocket);

}
