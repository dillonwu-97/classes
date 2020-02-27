
'''
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu 
'''
import argparse
import time
import random
import math
import sys
import re
from collections import Counter
from statistics import mean

# For testing the correctness of my implementation
# from sklearn.metrics import mean_squared_error
# from sklearn.metrics import silhouette_score
# import numpy as np
# import pandas as pd

# The program only works for numerical data
# Assumption is that the last column is the column with the labels
# Lab computers dont have pandas, so I didnt use that
class kmeans():

	def __init__ (self, file, clusters, output):
		self.clusters = int(clusters)
		self.file = file
		self.output = output
		self.centroids = [0]*self.clusters


	def file_to_list(self):
		dataset = []
		yset = []
		for i in self.file.read().splitlines():
			temp = re.split("[,\s]\s*",i)
			dataset.append(temp)
		return_dataset = [[] for i in range(len(dataset))]
		# Check for columns with categorical values
		drop_col = []
		for i in range(len(dataset[0])):
			try: 
				float(dataset[0][i])
			except ValueError:
				if i!= len(dataset[0])-1:
					drop_col.append(i)
		# Check for columns with categorical values; if exists, drop it; also, remove last column
		for i in range(len(dataset)):
			# Append y labels
			yset.append(dataset[i][-1])
			# Convert string to float
			for j in range(0,len(dataset[i])-1):
				if j not in drop_col:
					dataset[i][j] = float(dataset[i][j])
					return_dataset[i].append(dataset[i][j])
		# Check for \n characters that may have created a mismatch in size of array
		for i in range(len(return_dataset)):
			if len(return_dataset[i])==0: 
				return_dataset.pop(i)
				i-=1 # In case there are two adjacent empty lists
		return return_dataset, yset
	
	# def file_to_df(self):
	# 	dataset, yset = self.file_to_list()
	# 	df = pd.DataFrame(dataset)
	# 	y = pd.DataFrame(yset)
	# 	return df, y

	# Calculates the euclidean distance between two arrays
	def euclidean_distance(self, array1, array2):
		total = [(array1[i]-array2[i])**2 for i in range(len(array1))]
		return math.sqrt(sum(total))

	# Finds the nearest neighbor for each row in the dataset
	def nearest_neighbor(self, dataset):
		ypredict = [] # final predictions for dataset
		centroid_dic = {i:[] for i,j in enumerate(self.centroids)} # dictionary to store values for centroid calculation
		for row in dataset:
			min_val = sys.maxsize
			min_index = 0
			for center in range(len(self.centroids)):
				dis = self.euclidean_distance(self.centroids[center], row)
				if dis < min_val:
					min_val = dis
					min_index = center
			ypredict.append(min_index)
			centroid_dic[min_index].append(row)
		return ypredict, centroid_dic
		
	# Recalculates the centroids 
	def centroid_recalculate(self, centroid_dic):
		for i in centroid_dic.keys():
			self.centroids[i] = list(map(mean,zip(*centroid_dic[i])))
			
	# Make the predictions
	def fit_predict(self, dataset, yset):
		# print(set(yset))
		# Random selection of initial centroids
		ypredict = []
		while(len(set(ypredict))!=self.clusters): # in case there is a point that leads to nothing
			# print(len(set(ypredict)))
			for i in range(len(self.centroids)):
				self.centroids[i] = random.choice(dataset)
			ypredict, centroid_dic = self.nearest_neighbor(dataset)
		self.centroid_recalculate(centroid_dic)
		yfinal = []
		# looping until equilibrium
		while (ypredict != yfinal):
			yfinal = ypredict
			ypredict, centroid_dic = self.nearest_neighbor(dataset)
			self.centroid_recalculate(centroid_dic)
		# print(yfinal)
		return dataset, yfinal

	# error sum of squares (SSE)
	# Formula: sum of (x - xmean)^2 for all x
	def sse(self, dataset, yfinal):
		#self.centroids contain the averages
		total = 0
		for i in range(len(dataset)):
			temp = [(self.centroids[ yfinal[i] ][j] - dataset[i][j])**2 for j in range(len(dataset[i]))]
			total+=sum(temp)
		# print(self.centroids)
		# print(total)
		#total = total / (len(dataset)*len(dataset[0]))
		return total

	# Silhouette coefficient
	# Using formula from the textbook
	def silhouette(self, dataset, yfinal):
		silhouette_table = [] # Silhouette scores for each point
		# Find average distance between a point and all other points in the same cluster
		# Calculating a(o)
		for i in range(len(dataset)):
			a = 0
			count = 0
			for j in range(len(dataset)):
				if i!=j and yfinal[i] == yfinal[j]:
					a+=self.euclidean_distance(dataset[i], dataset[j])
					count+=1
			if count !=0:
				a/=count
			silhouette_table.append(a)
		# print(silhouette_table)
		# Average distance between a point and all other points not in that cluster
		# Calculating b(o)
		labels = set(yfinal)
		silhouette_support = [[] for i in range (len(silhouette_table))]
		for i in range(len(dataset)):
			for label in labels:
				if label == yfinal[i]:
					continue
				b = 0
				count = 0
				for j in range(len(dataset)):
					if yfinal[j] == label:
						b+=self.euclidean_distance(dataset[i], dataset[j])
						count+=1
				if count!= 0: 
					b/= count
					silhouette_support[i].append(b)
		# print(silhouette_support)
		# Calculating coefficient using formula: (b - a) / max(a,b)
		for i in range(len(silhouette_table)):
			b = min(silhouette_support[i])
			a = silhouette_table[i]
			silhouette_table[i] = (b-a)/max(a,b)
		# print(silhouette_table)
		total = 0
		for i in silhouette_table:
			total += i
		total /= len(silhouette_table)
		return total, silhouette_table

	def texify(self, file, yfinal, end_values):
		for i in yfinal:
			file.write(str(i) + "\n")
		file.write("SSE Value: " + str(end_values[0]) + " ")
		file.write("Silhouette coefficient: " + str(end_values[1]) + " ")
		file.write("\n")
		return file


def main():
	# Crashes at 0 probably
	parser = argparse.ArgumentParser()
	parser.add_argument("input", help = "name of dataset file")
	parser.add_argument("support", help = "k value")
	parser.add_argument("output", help = "output file name")
	args = parser.parse_args()

	file = open(args.input)
	output = open(args.output, "w")

	test = kmeans(file, args.support, output)
	dataset, yset = test.file_to_list() # The original dataset
	dataset, yfinal = test.fit_predict(dataset, yset)
	sse = test.sse(dataset, yfinal)
	# sksse = mean_squared_error()
	print('SSE is: ' + str(sse))
	# print('Sklearn silhouette score is: ' + str(silhouette_score(dataset,yfinal)))
	sil, sil_table = test.silhouette(dataset,yfinal)
	print('Silhouette score is: ' + str(sil))
	end_vals = []
	end_vals.append(sse)
	end_vals.append(sil)
	test.texify(output, yfinal, end_vals)
	
	file.close()
	output.close()



if __name__ == '__main__':
	main()