#include <iostream>
#include <fstream>
#include <unordered_set>
#include <functional>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;
using std::cout;            using std::endl;
using std::string;          using std::unordered_set;

template<typename T> void printV(vector<T> v) {
    for (const auto & i: v) {
        cout << i << " ";
    }
    cout << endl;
}

/*
 * Note that you have to pass in the file as a single string
 * into the findWikiLinks function!
 * So we have to turn the passed-in file into a single string...
 * does that sound familiar at all?
 */
set<string> findWikiLinks(const string& page_html) {
    // TODO: delete this return statement and implement the
    //       function!
    // Your solution must use the following algorithms to do the following things:
    // std::search    // to find the start of a link // use this to find <a href=
    // std::find      // to find the end of a link // use this to find </a>
    // std::all_of    // to check whether the link contains an invalid character
    set<string> d;
    string link_start = "<a href=\"/wiki/";
    string link_finish = "</a>";

    // don't go past this point
    // several candidates for end indication
    int end_index = max(page_html.find("NewPP limit report"), page_html.find("id=\"References\""));
    end_index = max(end_index, (int)page_html.find("printfooter"));
    // cout << end_index << endl;
    /****************************** Looking for all the starts with string::search ***********************************/
    // cout << *search(page_html.begin(), page_html.end(), start.begin(), start.end()) << endl;
    // cout << page_html.find(start) << endl;
    // cout << *page_html.begin() << endl;
    std::string::const_iterator it = page_html.begin(); 
    // starting place
    string start_index = "mw-content-text";
    search(it, page_html.end(), start_index.begin(), start_index.end());
    cout << it - page_html.begin() << endl;
    if (it - page_html.begin() == 0) {
        it = page_html.begin();
    }
    vector<int> v_start;
    while (it <= page_html.end()) {
        // Notes / Observations
        // cout << *it << endl; // prints first character of what it found
        // cout << &start_it << endl; // prints the pointer
        // cout << start_it - page_html.begin() << endl; // prints the index
        // cout << start_it << endl; // results in error
        // cout << it - page_html.begin() << endl;
        it = search(it, page_html.end(), link_start.begin(), link_start.end()) + 1; // need to add 1 else it will keep including previously found item
        v_start.push_back(it - page_html.begin() - 1);
        // cout << it - page_html.begin() << endl;
    }   

    printV(v_start);
    /****************************** looking for all the finishes with string::find ****************************************/
    vector<int> v_end;
    int offset=page_html.find(start_index);
    if (offset == -1) { offset = 0; }
    // int offset = end_index;
    if (end_index != -1) {
        while (offset <= end_index) {
            // cout << std::string::npos << endl;
            v_end.push_back(offset);
            offset = page_html.find(link_finish, offset + link_finish.size());
            // cout << offset << endl;
        }
    } else {
        while (offset != string::npos) {
            v_end.push_back(offset);
            offset = page_html.find(link_finish, offset + link_finish.size());
        }
    }
    
    // cout << "\n" << endl;
    // cout << page_html.substr(v_start[v_start.size() - 2], 100) << endl;
    printV(v_end);

    /*********************************** printing out all of the unique links *********************************************/
    int i = 0, j = 0, temp, temp2;
    string sandwich, link;
    while (i < v_start.size() && j < v_end.size()) {
        cout << i << " " << j << endl;
        if (v_start[i] + link_start.size() < page_html.size() && v_end[j] > v_start[i]) {
            // find the word that is in between > and <
            sandwich = page_html.substr(v_start[i] + link_start.size(), v_end[j] - v_start[i]);
            temp = sandwich.find("title=\"") +7;
            temp2 = sandwich.find("\">");
            // cout << temp << " " << temp2 << endl;
            link = sandwich.substr(temp,temp2 - temp);
            // cout << link << endl;
            // continue if found ":" inside the link
            if (link.find(":") != string::npos || link.find("class=\"image") != string::npos) {
                // cout << link.find(":") << endl;
                i++;
                continue;
            }
            // cout << link << endl;
            replace(link.begin(), link.end(), ' ', '_');
            link[0] = toupper(link[0]);
            // cout << link << endl;
            if (temp2 > temp) {
                d.insert(link);
            }
            i++;
            // cout << sandwich.substr(temp, sandwich.find("<") - temp) << endl;
        }
        j++;
        // cout << i << " j " << j << endl;
    }

    // filter map to avoid special items like ones with Category: whatever or Help: whatever

    return d;
}

int main() {
    /* Note if your file reading isn't working, please go to the
     * projects tab on the panel on the left, and in the run section,
     * uncheck the "Run in terminal" box and re-check it. This
     * should fix things.
     */

    // use this later
    string filename;
    string page_html;
    string temp;
    ifstream file;
    cout << "Enter a file name: ";
    while(1) {
        cout << 1 << endl;
        getline(std::cin, filename);
        page_html = "";
        temp="";

        cout << "filename is " << filename << endl;

        // TODO: Create a filestream from the filename, and convert it into
        //       a single string of data called page_html (declared above)

        // Write code here
        // filename = "../res/ksound.txt";
        file.open(filename);
        if (!file.is_open()) {
            // cout << filename << endl;
            perror("Error with opening file");
            exit(1);
        }
        while(getline(file, temp)) { 
            // cout << temp << endl;
            page_html += temp;
        }
        // cout << page_html << endl;
        

        set<string> validLinks = findWikiLinks(page_html);

        // TODO: Print out the validLinks set above.
        //       You can print it out in any format; this is just for you
        //       to be able to compare your results with the [output] results
        //       under the /res folder.

        // Write code here
        // cout << filename.substr(11, filename.size() - 11 - 4) << endl;
        string write_to = "./my-" + filename.substr(11, filename.size() - 11 - 4) + ".txt";
        ofstream out_file;
        out_file.open(write_to);
        for (auto i: validLinks) {
            out_file << i << endl;
        }

        file.close();
        out_file.close();

    }

    return 0;
}
