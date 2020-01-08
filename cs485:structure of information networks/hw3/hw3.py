import numpy as np
from numpy.polynomial.polynomial import polyfit
import matplotlib.pyplot as plt

M = open('les-miserables.txt', 'r')
D = open('dracula.txt', 'r')

temp = M.read()
temp2 = D.read()
#print(temp2)


#This creates map1 which has the word frequency in the file les miserables
map1 = {}
count1 = 0

#word count x
for word in temp.split():
	count1+=1
	if word not in map1:
		map1[word] = 1
	else:
		map1[word]+=1

#for x in map1:
#	print(x + ":" + str(map1[x]))

#print("count1: " + str(count1))

#This creates map2 which has the word frequency in dracula 
map2 = {}
count2 = 0

for word in temp2.split():
	count2+=1
	if word not in map2:
		map2[word] = 1
	else:
		map2[word] += 1

#for x in map2:
	#print(x + ":" + str(map2[x]))

#print("count2: " + str(count2))

#This is to create a list of the number of words that have a specific frequency, where the index i is 
#the frequency and result[i] is the fraction of words that have that frequency 
result = {}
for x in map1:
	if map1[x] not in result:
		#if (map1[x] > 6000):
			#print("OOGA BOOGA") just looking for where the words with a lot of usage is 
		#	print(x)
		result[map1[x]] = (1.0/count1)
	else: 
		result[map1[x]]= result[map1[x]] + (1.0/count1)

resultnonfrac = {}
for x in map2:
	if map2[x] not in resultnonfrac:
		#if (map1[x] > 6000):
			#print("OOGA BOOGA") just looking for where the words with a lot of usage is 
		#	print(x)
		resultnonfrac[map2[x]] = 1
	else: 
		resultnonfrac[map2[x]]= resultnonfrac[map2[x]] + 1

#result2 dictionary for dracula
result2 = {}
for x in map2:
	if map2[x] not in result2:
		#if (map1[x] > 6000):
			#print("OOGA BOOGA") just looking for where the words with a lot of usage is 
		#	print(x)
		result2[map2[x]] = (1.0/count2)
	else: 
		result2[map2[x]]= result2[map2[x]] + (1.0/count2)

#why doesn't sorted (result.keys()) return the keys of the graph in sorted order?
#sorted(result.keys())
print(sorted(resultnonfrac))
print(resultnonfrac[1])

#for key in sorted(result.keys()) :
	
#plt.loglog(sorted(result.keys()), result.values(), basex = 10, basey = 10)
#plt.loglog(sorted(result2.keys()), result2.values(), basex = 10, basey = 10)
#plt.loglog(resultnonfrac.keys(), resultnonfrac.values(), basex = 10, basey = 10)
#plt.title("Les Miserables Loglog Graph")
#plt.title("Dracula Loglog Graph")
plt.xlabel("Frequency of word")
plt.ylabel("Number of distinct words with frequency x")
plt.scatter(result2.keys(), resultnonfrac.values())
plt.yscale("log")
plt.xscale("log")
plt.ylim(bottom = 10)






#plt.hist(sorted(resultnonfrac.keys()), bins = 30, weights = resultnonfrac.values())


plt.draw()
plt.show()

#find least square regressions fit, should be straight line and go through most of the data















