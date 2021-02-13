#include <bits/stdc++.h>
#include <algorithm>
#include <vector>

int main() {
	std::vector<int> v= {10,2,3,4,1,91};
	sort(v.begin(), v.end(), [](int a, int b){
		if (a == 1) {
			return false;
		} 
		if (b == 1) {
			return true;
		}
		return a < b;
	});
	for (auto i: v) {
		cout << i << endl;
	}
}
