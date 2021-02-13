#include <iostream>
#include <algorithm>
#include <unordered_set>
#include <unordered_map>
#include "wikiscraper.h"
#include "error.h"
#include <set>


using namespace std;
using std::cout;            using std::endl;
using std::cerr;            using std::string;
using std::unordered_map;   using std::unordered_set;

/*
 * You should delete the code in this function and
 * fill it in with your code from part A of the assignment.
 *
 * If you used any helper functions, just put them above this function.
 */

template<typename T> void printV(vector<T> v) {
    for (const auto & i: v) {
        cout << i << " ";
    }
    cout << endl;
}

unordered_set<string> findWikiLinks(const string& page_html) {
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
    // cout << it - page_html.begin() << endl;
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

    // printV(v_start);
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
    // printV(v_end);

    /*********************************** printing out all of the unique links *********************************************/
    int i = 0, j = 0, temp, temp2;
    string sandwich, link;
    while (i < v_start.size() && j < v_end.size()) {
        // cout << i << " " << j << endl;
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
            // cout << "link is " << link << endl;
            if (temp2 > temp) {
                d.insert(link);
            }
            i++;
            // cout << sandwich.substr(temp, sandwich.find("<") - temp) << endl;
        }
        j++;
        // cout << i << " j " << j << endl;
    }
    // cout << " end " << endl;
    // filter map to avoid special items like ones with Category: whatever or Help: whatever


    unordered_set<string>d2(d.begin(), d.end());

    return d2;
}


/*
 * ==================================================================================
 * |                           DON"T EDIT ANYTHING BELOW HERE                       |
 * ==================================================================================
 */
unordered_set<string> WikiScraper::getLinkSet(const string& page_name) {
    if(linkset_cache.find(page_name) == linkset_cache.end()) {
        // note: links NEEDS to be an unordered_set
        auto links = findWikiLinks(getPageSource(page_name));
        linkset_cache[page_name] = links;
    }
    return linkset_cache[page_name];
}


WikiScraper::WikiScraper() {
    (void)getPageSource("Main_Page");
#ifdef _WIN32
    // define something for Windows
    system("cls");
#else
    // define it for a Unix machine
    system("clear");
#endif
}


string createPageUrl(const string& page_name) {
    return "https://en.wikipedia.org/wiki/" + page_name;
}

void notFoundError(const string& msg, const string& page_name, const string& url) {
    const string title = "    AN ERROR OCCURED DURING EXECUTION.    ";
    const string border(title.size() + 4, '*');
    cerr << endl;
    errorPrint(border);
    errorPrint("* " + title + " *");
    errorPrint(border);
    errorPrint();
    errorPrint("Reason: " + msg);
    errorPrint();
    errorPrint("Debug Information:");
    errorPrint();
    errorPrint("\t- Input parameter: " + page_name);
    errorPrint("\t- Attempted url: " + url);
    errorPrint();
}

string WikiScraper::getPageSource(const string &page_name) {
    const static string not_found = "Wikipedia does not have an article with this exact name.";
    if(page_cache.find(page_name) == page_cache.end()) {
        QUrl url(createPageUrl(page_name).c_str()); // need c string to convert to QString

        QNetworkRequest request(url);

        QNetworkReply *reply(manager.get(request));

        QEventLoop loop;
        QObject::connect(reply, SIGNAL(finished()) , &loop, SLOT(quit()));
        loop.exec();

        string ret = reply->readAll().toStdString();
        if(std::search(ret.begin(), ret.end(), not_found.begin(), not_found.end()) != ret.end()){
            notFoundError("Page does not exist!", page_name, url.toString().toStdString());
            return "";
        }
        size_t indx = ret.find("plainlinks hlist navbar mini");
        if(indx != string::npos) {
            return ret.substr(0, indx);
        }
        page_cache[page_name] = ret;
    }
    return page_cache[page_name];
}





