'''
/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */
'''
import argparse
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sb
import random as random


class Knn(object):
    k = 0    # number of neighbors to use

    def __init__(self, k):
        """
        Knn constructor

        Parameters
        ----------
        k : int 
            Number of neighbors to use.
        """
        self.k = k
        self.store = 0 #this stores the new table
        self.columns=[]
        self.misc = 0

    def train(self, xFeat, y):
        """
        Train the k-nn model.

        Parameters
        ----------
        xFeat : nd-array with shape n x d
            Training data 
        y : 1d array with shape n
            Array of labels associated with training data.

        Returns
        -------
        self : object
        """
        # TODO 
        #creating the column names to be used later
        columns =[]
        for c in xFeat.columns:
            columns.append(c)
        #print(columns)
        #Scratch
        #test = xFeat.plot.scatter(x= columns[0], y=columns[1],)
        #print(test)
        #plt.show()
        #creating the index for test2 from 0 -> size of test2

        table = xFeat.copy()
        #inserting the y values as a new column
        table["real values"] = y
        #inserting the indices of each row to be used in the prediction part
        index = []
        for i in range(0,len(xFeat)):
            index.append(i)
        #finalizing the table
        table["Index"] = index
        #looking at a graph of the output
        #graph2 = sb.pairplot(x_vars = [columns[0]], y_vars=[columns[1]], data=table, hue="real values", height=5)

        #plt.show()
        self.store = table
        self.columns = columns
        #print(self.store)     
        return self


    def predict(self, xFeat):
        """
        Given the feature set xFeat, predict 
        what class the values will have.

        Parameters
        ----------
        xFeat : nd-array with shape m x d
            The data to predict.  

        Returns
        -------
        yHat : 1d array or list with shape m
            Predicted class label per sample
        """
        trainsize = len(self.store["Index"])
        #print(trainsize)
        yHat = [] # variable to store the estimated class label
        # TODO
        
        #Scratch

        #This keeps track of all the numbers of each iteration; it will be sorted at each iteration
        temp = {}
        distance = 0
        counter = 0
        #to store the j values; to be used to look at their corresponding colors
        inter = []

        
        #Naming the columns
        columns =[]
        for c in xFeat.columns:
            columns.append(c)
        testsize = len(xFeat[columns[0]])
        #print(testsize)
        #print ('HIIIIIIIIIIII')
        #print (self.columns)
        #print(len(self.columns))
        
        #testsize
        #print(len(self.columns))
        for i in range(0,testsize):
            for j in range(0,trainsize):
                #calculate the euclidean distance between point i and every point j
                #print(self.store[ self.columns[1] ][i])
                #print(self.store [ self.columns[1] ][j])
                
                #problem is here
                #xFeat's x value - self's x Value
                #this needs to be n instead of just [0] and [1]
                totalsum = 0
                for n in range(0, len(self.columns)):
                    totaldiff = 0
                    totaldiff = xFeat [ columns[n] ] [i] - self.store [self.columns [n] ][j] 
                    totalsum = totaldiff**2 + totalsum
                distance = totalsum**(1.0/ len(columns))

                #xdiff = xFeat[ columns[0] ] [i] - self.store [ self.columns[0] ][j]
                #xFeat's y value - self's y value
                #ydiff = xFeat[ columns[1] ] [i] - self.store [ self.columns[1] ][j]
                #distance = np.sqrt(xdiff**2 + ydiff**2)
                #print(distance)
                temp[distance]= j
            #sort the keys 
            #mistake is that i'm not looking at k; should only be counting k times...
            for key in sorted(temp.keys()):
                #print(key)
                #print(temp[key])
                inter.append(self.store ["real values"][ temp[key] ])
                counter+=1
                if counter == self.k:
                    break
            #print(inter)
            #finding the majority
            yHat.append(majority (inter))
            #reset
            temp = {}
            inter = []
            counter = 0
            #print(i)
        #yHat contains all the prediction values for the data set you are trying to predict for, whether
        #it be training or test

        #print(self.store)
        #print(self)

        #Looking at graph
        #only works for q3
        #self.store["yHat"] = yHat
        #table = xFeat
        #table ["yHat"] = yHat
        #graph3 = sb.pairplot(x_vars = [columns[0]], y_vars=[columns[1]], data=table, hue="yHat", height=5)
        #plt.show()
        return yHat

def majority (array):
    #count
    track = {}
    for i in array:
        if i not in track:
            track[i]=1
        else:
            track[i]+=1
    #find the maximum
    high = max(track.values())
    #print (high)
    #find all the maximums
    temp = []
    for i in track:
        if track[i] == high:
            temp.append(i)
    maximum = random.choice(temp)

    return maximum


def accuracy(yHat, yTrue):
    """
    Calculate the accuracy of the prediction

    Parameters
    ----------
    yHat : 1d-array with shape n
        Predicted class label for n samples
    yTrue : 1d-array with shape n
        True labels associated with the n samples

    Returns
    -------
    acc : float between [0,1]
        The accuracy of the model
    """
    # TODO calculate the accuracy
    #columns =[]
    #for c in yTrue.columns:
    #    columns.append(c) 
    yT = yTrue.tolist()

    acc = 0
    pos = 0
    neg = 0

    for i in range(0, len(yHat)):
        if yHat[i] != yT[i]:
            neg+=1
        else: pos+=1
    acc = pos / (pos + neg)
    #print(acc)

    return acc


def main():
    """
    Main file to run from the command line.
    """
    # set up the program to take in arguments from the command line
    parser = argparse.ArgumentParser()
    parser.add_argument("k",
                        type=int,
                        help="the number of neighbors")
    parser.add_argument("--xTrain",
                        default="q3xTrain.csv",
                        help="filename for features of the training data")
    parser.add_argument("--yTrain",
                        default="q3yTrain.csv",
                        help="filename for labels associated with training data")
    parser.add_argument("--xTest",
                        default="q3xTest.csv",
                        help="filename for features of the test data")
    parser.add_argument("--yTest",
                        default="q3yTest.csv",
                        help="filename for labels associated with the test data")

    args = parser.parse_args()
    # load the train and test data
    xTrain = pd.read_csv(args.xTrain)
    yTrain = pd.read_csv(args.yTrain)
    xTest = pd.read_csv(args.xTest)
    yTest = pd.read_csv(args.yTest)
    # create an instance of the model
    knn = Knn(args.k)
    knn.train(xTrain, yTrain['label'])
    # predict the training dataset
    yHatTrain = knn.predict(xTrain)
    trainAcc = accuracy(yHatTrain, yTrain['label'])
    # predict the test dataset
    yHatTest = knn.predict(xTest)
    testAcc = accuracy(yHatTest, yTest['label'])
    print("Training Acc:", trainAcc)
    print("Test Acc:", testAcc)


if __name__ == "__main__":
    main()
