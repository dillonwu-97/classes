import sys
import numpy as np




# Calculate (non-normalized) edge betweenness for every edge in graph G
# Since the graph is undirected, assume that each shortest path from a to b is 
# the same as from b to a, and so we only count one direction
def edge_betweenness(G):
	# Betweenness score for each edge, to be added up and returned
	bw = { (a,b) : 0 for (a,b) in tuple(G.edges()) }


	# Do something for each source...
	for s in G.nodes():
		#print("hello")
		#######################
		# ... XXX: Implement me
		#######################
		#print("hello")
		Nodes, Parents, paths, track = bfs(G, s)
		#print("s is: ")
		#print (s)
		#print(paths)
		bwcalc(G, bw, Nodes, Parents, paths, track)
		
	# Normalize back since we looked at every directed pair of nodes
	#norm(bw)
	norm(bw)
	return bw

def bfs(G, s):
    Parents = {}
    Nodes = []
    #for all the elements in the parents dictionary, assign them as having no parents currently
    for v in G:
        Parents[v] = []
    #paths creates a dictionary of all the nodes in G and stores their respective number of shortest paths
    #in each entry
    #Note: shortest path from a node to itself is 1
    paths = dict.fromkeys(G, 0.0)    # sigma[v]=0 for v in G
    paths[s] = 1.0

    D = {}
    D[s] = 0
    
    #Q is a queue, starting with s. 
    Q = [s]

    #stores the last child and returns it
    track = []

    #While the queue is not empty
    while Q:
        #print(Q)   
        v = Q.pop(0)
        path = paths[v]
        Nodes.append(v)
        Dv = D[v]
        #print ("Dv: " + str(Dv))
        for w in G[v]:
            #print(G[v])
            if w not in D:
            	#print(w)
            	#print("paths")
            	#print("w: " + str(w))
                Q.append(w)
                D[w] = Dv + 1
                #print("paths")
                #print(D)
            if D[w] == Dv + 1:   
                #print("paths2")
                paths[w] += path
                Parents[w].append(v) 
                #print(D)
        #need to append all of the final children to this 
        #print("--------")
    

    #to find the leaf nodes
    leaf = {i:0 for i in range (0, len(Nodes))}
    #for x in leaf:
    #	print(x)
    for x in Parents:
    	for y in Parents[x]:
    		leaf[y] = leaf[y]+1

    for x in leaf:
    	#print(leaf[x])
    	if (leaf[x]==0):
    		track.append(x)
    		#print(x)

    return Nodes, Parents, paths, track


#define bwcalc as a function of btweeness called bw
#bw graph should be modified after this program runs
def bwcalc(G, bw, Nodes, Parents, paths, track):
    
    #sort according the path size to get the node with the highest path count
    bwtrack = track
    #print(bwtrack)

    # stores coefficient you use for each betweenness
    coeff = dict.fromkeys(G,1.0)
    print(coeff)

    # appends all the nodes of Nodes by backwards 
    #for x in range(start, -1, -1):
    	#bwtrack.append(x)
    	#print(x)

    while bwtrack:
    	# x node is the parent, y node is the child
    	y = bwtrack.pop(0)
    	#print("y: " + str(y))
    	#print(Parents[y])
    	for x in Parents[y]:
    		bwtrack.append(x)
    		#print (x)
    		if (y, x) not in bw:
    			#print("enter")
    			bw[(x, y)] = bw[(x, y)] + coeff[y] * paths[x]/paths[y]
	    		coeff[x] = coeff [x] + coeff[y] * paths[x]/paths[y]
	    		#print(str(x) + ": " + str(coeff[x]))
    		else:
    			#print ("y:")
    			#print (y)
	    		bw[(y,x)] = bw[(y,x)] + coeff[y] * paths[x]/paths[y]
	    		coeff[x] = coeff [x] + coeff[y] * paths[x]/paths[y]
	    		#print(str(x) + ": " + str(coeff[x]))
	    		#print(str(x) + " " + str(y) + " " + str(bw[(y,x)]))


#Normalization
def norm (bw):
	for x in bw:
		bw[x] = bw[x]/2

#helper function to find max betweenness
def max(bw):
	high = 0.0
	for x in bw: 
		#print x
		#print "bw[x]: " + str(bw[x])
		if (bw[x] > high):
			high = bw[x]
			#print "entered"
			
	
	return high

#returns node with highest paths
def max2(paths):
	high = 0
	for x in paths:
		if (paths[x] > high):
			high = x

	return high


# Girvan Newman hierarchical clustering method
def girvan_newman(Gorig):
	# Ensure we don't mess up the original graph, take a copy
	G = Gorig.copy()
	res = []

	#find the betweennness of the graph
	bw = edge_betweenness(G)

	# Repeatedly remove the highest valued note w.r.t. edge betweenness.
	while bw:
		#######################
		# ... XXX: Implement me
		#######################
		m = max(bw) #returns the bw with the max value
		#print(m)
		for x in bw:
			if bw[x] == m:
				#need to find a way to extract the node values from the dictionary key
				#after extracting the nodes, have to put it into the tuple
				#u represents one of the nodes, v represents the second one that forms the edge
				temp = str(x)
				temp = temp.replace(",", "")
				temp2 = temp.split()
				temp2[0] = temp2[0].replace("(", "")
				temp2[1] = temp2[1].replace(")", "")
				
				u = int(temp2[0])
				v = int(temp2[1])

				tup = (u,v, bw[x])
				tup = (u,v, bw[x])

				#print(tup)
				res.append(tup)
				bw.pop(x)
				break


	# Edge list of the decomposition
	# res = [ (v1,v2,bw1), (v3,v4,bw2), ... ] means the edge (v1,v2) was removed 
	# first with edge betweenness value of bw1, followed by the edge (v3,v4), and so forth.
	return res



if __name__ == "__main__":
	print ("Run: python3 eval.py instead.")


