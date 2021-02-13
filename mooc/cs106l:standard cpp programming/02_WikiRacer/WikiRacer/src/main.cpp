#include <iostream>     // for cout, cin
#include <fstream>      // for ifstream
#include <sstream>      // for stringstream
#include <unordered_set>
#include <vector>
#include <queue>
#include <unordered_map>
#include "wikiscraper.h"
#include <stdlib.h>
#include <stdio.h>

using std::cout;            using std::endl;
using std::ifstream;        using std::stringstream;
using std::string;          using std::vector;
using std::priority_queue;  using std::unordered_map;
using std::unordered_set;   using std::cin;

using namespace std;

#define pii pair<string, int>

/*
 * This is the function you will be implementing. It takes
 * two string representing the names of a start_page and
 * end_page and is supposed to return a ladder, represented
 * as a vector<string>, of links that can be followed from
 * start_page to get to the end_page.
 *
 * For the purposes of this algorithm, the "name" of a Wikipedia
 * page is what shows at the end of the URL when you visit that page
 * in your web browser. For ex. the name of the Stanford University
 * Wikipedia page is "Stanford_University" since the URL that shows
 * in your browser when you visit this page is:
 *
 *       https://en.wikipedia.org/wiki/Stanford_University
 */

int countSharedLinks(unordered_set<string> a, unordered_set<string> b, const string& end_page) {
    int count = 0;
    for (auto it = a.begin(); it != a.end(); it++) {
        if (*it == end_page) {
            return -1;
        }
        if (b.find(*it) != b.end()) {
            count ++;
        }
    }
    return count;
}

vector<string> findWikiLadder(const string& start_page, const string& end_page) {
    // TODO: 1. Once your file reading is working, replace the below
    //          return statement with "return {};".
    //       2. Make sure that you've copied over your findWikiLinks method
    //          from Part A into wikiscraper.cpp.
    //       3. Finally, implement this function per Part B in the handout!
    //
    //                Best of luck!
    // cout << start_page << " " << end_page << endl;

    // uninitialized variables
    WikiScraper ws;
    vector<string> ret;
    pii out;
    string cur_page;
    unordered_set<string> finish;
    unordered_set<string> current;
    unordered_set<string> iterpage;
    unordered_map<string, string> parents;
    int count;

    // setting up pq
    auto cmpFn = [](pii a, pii b){
        return a.second > b.second;
    }; // lambda comparator function
    priority_queue<pii, vector<pii>, decltype(cmpFn)> pq (cmpFn);

    // starting value
    // string end_page = "Strawberry";
    // cout << start_page << endl;
    // cout << end_page << endl;
    // return ret;
    finish = ws.getLinkSet(end_page);
    parents[start_page] = "";
    current = ws.getLinkSet(start_page);
    count = countSharedLinks(current, finish, end_page);
    // cout << count << endl;
    // for (auto it = finish.begin(); it != finish.end(); it++) {
    //     cout << *it << endl;
    // }
    if (count == -1) {
        // finished
        ret.push_back(start_page);
        ret.push_back(end_page);
        // for (auto i: ret) {
        //     cout << i << " ";
        // }
        cout << endl;
        return ret;
    }
    pq.push(make_pair(start_page, count));

    // while not empty
    while (pq.size()) {
        out = pq.top(); pq.pop();
        cur_page = out.first;
        // cout << "CURRENT PAGE IS " << cur_page << endl;
        current.clear();
        current = ws.getLinkSet(cur_page);
        for (auto it = current.begin(); it != current.end(); it++) {
            // cout << "inside current " << *it << endl;
            if (parents.find(*it) == parents.end()) {
                iterpage.clear();
                iterpage = ws.getLinkSet(*it);
                parents[*it] = cur_page;
                count = countSharedLinks(iterpage, finish, end_page);
                // cout << "count is " << count << endl;
                if (count == -1) {
                    // finished
                    for (auto it = parents.begin(); it != parents.end(); it++) {
                        // cout << " this is " << it->first << " " << it->second << endl;
                    }
                    ret.push_back(end_page);
                    string start = *it;
                    while (start != start_page) {
                        ret.push_back(start);
                        start = parents[start];
                    }
                    ret.push_back(start);
                    reverse(ret.begin(), ret.end());
                    return ret;
                } else {
                    pq.push(make_pair(*it, count));
                }
            }
        }

    }
    // for (auto it = start.begin(); it != start.end(); it++) {
    //     cout << *it << endl;
    // }
    return {"No sequence found"};
}

int main() {
    /* Container to store the found ladders in */
    vector<vector<string>> outputLadders;
    ifstream file;
    cout << "Enter a file name: ";
    string filename;
    getline(cin, filename);

    // TODO: Create a filestream from the filename.
    //       For each pair {start_page, end_page} in the input file,
    //       retrieve the result of findWikiLadder(start_page, end_page)
    //       and append that vector to outputLadders.

    // Write code here
    int numFiles;
    string startStr, endStr;

    file.open(filename);
    if (!file.is_open()) {
        // cout << filename << endl;
        perror("Error with opening file");
        exit(1);
    }
    string temp="";
    getline(file, temp);
    numFiles = stoi(temp);
    while (numFiles--) {
        getline(file, temp);
        cout << temp << endl;
        startStr = temp.substr(0, temp.find(" "));
        endStr = temp.substr(temp.find(" ")+1, temp.size());
        stringstream ss(endStr);
        ss >> endStr;
        // cout << endStr << endl;
        cout << startStr << " " << endStr << endl;
        outputLadders.push_back(findWikiLadder(startStr, endStr));
    }

    /*
     * Print out all ladders in outputLadders.
     * We've already implemented this for you!
     */

    // output to a file
    string write_to = "myresults.txt";
    ofstream out_file;
    out_file.open(write_to);

    for (auto& ladder : outputLadders) {
        if(ladder.empty()) {
            cout << "No ladder found!" << endl;
        } else {
            cout << "Ladder found:" << endl;
            cout << "\t" << "{";

            std::copy(ladder.begin(), ladder.end() - 1,
                      std::ostream_iterator<string>(cout, ", "));
            /*
             * The above is an alternate way to print to cout using the
             * STL algorithms library and iterators. This is equivalent to:
             *    for (size_t i = 0; i < ladder.size() - 1; ++i) {
             *        cout << ladder[i] << ", ";
             *    }
             */
            cout << ladder.back() << "}" << endl;

            out_file << "Ladder found: " << endl;
            copy(ladder.begin(), ladder.end(), ostream_iterator<string>(out_file, ", "));
            out_file << endl;
        }
    }

    out_file.close();
    return 0;
}




