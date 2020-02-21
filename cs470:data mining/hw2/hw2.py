'''
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu 
'''

# This function creates several data structures
# item_bag
# Array of all transactions and their respective unique items
# item_count 
# An array of integers size 0-999, each of which keeps track of the number of items for each unique item
# transaction_index
# array of integers 0-999 where each element contains a list of transaction ids from 0-99 999 containing that int
import argparse
import time

def construct(file):

	item_all = []

	for i in file:
		temp_int = [int(x) for x in i.split()]
		item_all.append(set(temp_int))
	unique_vals = max(set([z for y in item_all for z in y])) + 1
	
	item_count = [0] * unique_vals
	transaction_id = [[] for i in range(unique_vals)]
	for i in range(len(item_all)):
		for j in item_all[i]:
			item_count[j]+=1
			transaction_id[j].append(i)
	return item_count, item_all, transaction_id
	
def texify(dic, file):


	for i in sorted(dic.keys()):
		for j in i:
			if j!=-99999:
				file.write(str(j) + " ")
		file.write("("+str(dic[i])+")" + "\n")
	return file

def apriori(input, support, output):
	
	# timer
	start = time.time()

	item_count, item_all, transaction = construct(input)
	transaction_id = [set(i) for i in transaction]
	return_dic = {} # store all return values / tuples
	
	support_sat = [] #support condition satisfied list
	for i in range(len(item_count)):
		if item_count[i] >= support:
			#-99999 is a dummy value to make sorting easier
			return_dic.update( {(i,-99999):item_count[i]} )
			support_sat.append([i])

	while(len(support_sat) != 0):
		print('NEW SUPPORT CALCULATION...')
		support_temp = []
		# join the pairs of items in support_sat
		for i in range(len(support_sat)):
			for j in range(i+1, len(support_sat)):
				pair = tuple(sorted(set(support_sat[i] + support_sat[j])))
				if pair not in return_dic:
					return_dic[pair] = 0
					support_temp.append(pair)
		
		support_sat = [] #fill with new info one tier up
		for i in range(len(support_temp)):
			# iterate through the pairs and find matches from transaction_ids
			temp_inter = transaction_id[ support_temp[i][0] ]
			for j in range(1, len(support_temp[i])):
				temp_inter = temp_inter.intersection(transaction_id[ support_temp[i][j] ])
			if len(temp_inter) >= support:
				support_sat.append(support_temp[i])
				return_dic[support_temp[i]] = len(temp_inter)
		return_dic = {x:y for x,y in return_dic.items() if y!=0}
	texify(return_dic, output)
	end = time.time()
	return return_dic, end-start




def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("input", help = "file to be read in")
	parser.add_argument("support", help = "support value")
	parser.add_argument("output", help = "output name")
	args = parser.parse_args()

	file = open(args.input)
	output = open(args.output, "w")
	dic_return, time_elapse = apriori(file, int(args.support), output)
	print("Time elapsed: " + str(time_elapse))
	file.close()
	output.close()




if __name__ == "__main__":
	main()