import argparse
import numpy as np
import pandas as pd
from sklearn.metrics import accuracy_score


class DecisionTree(object):
    maxDepth = 0       # maximum depth of the decision tree
    minLeafSample = 0  # minimum number of samples in a leaf
    criterion = None   # splitting criterion

    def __init__(self, criterion, maxDepth, minLeafSample):
        """
        Decision tree constructor

        Parameters
        ----------
        criterion : String
            The function to measure the quality of a split.
            Supported criteria are "gini" for the Gini impurity
            and "entropy" for the information gain.
        maxDepth : int 
            Maximum depth of the decision tree
        minLeafSample : int 
            Minimum number of samples in the decision tree
        """
        self.criterion = criterion
        self.maxDepth = maxDepth
        self.minLeafSample = minLeafSample
        self.root = None

    def train(self, xFeat, y):
        """
        Train the decision tree model.

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
        # TODO do whatever you need
        print("Criterion shows: " + str(self.criterion))
        print("Max Depth shows: " + str(self.maxDepth))
        print("Min Leaf shows: " + str(self.minLeafSample))
        #recursively splitting
        print("STARTING WHILE LOOP NOW")
        count = 0
        xRun = xFeat    #for reuse
        yRun = y
        root = node() #initializing tree
        self.root = root

        while (count != self.maxDepth):
            print("COUNT " + str(count))
            count+=1
            #column returns the column to split on, splitter returns the best value to split on
            col,splitter = Split(xRun, yRun, self.minLeafSample)

            #iterate through the dataframe and series and adding the values to the new df / series to split
            xL = pd.DataFrame()
            xR = pd.DataFrame()
            temp1 = [] #left
            temp2 = [] #right
            for i in range(0,len(xFeat)):
                root.column = col #storing the column
                root.value = xFeat.loc[splitter,col] #storing the value at that column
                if (xFeat.loc[i,col] > xFeat.loc[splitter,col]):
                    xL = xL.append(xFeat.iloc[i])
                    temp1.append(y[i])
                else:  
                    xR = xR.append(xFeat.iloc[i])
                    temp2.append(y[i])
            yL = pd.Series(temp1)
            yR= pd.Series(temp2)

            #calculate the y column with the greater gini impurity; the one with the greater impurity gets operated on
            #print("flag")
            #print(gini(yL))
            #print(gini(yR))

            #storing information in the tree
            root.left = node()
            root.right = node()
            if (gini (yL) > gini(yR)):
                root.right.isLeaf = max(set(temp2), key=temp2.count) #right is true since the left side is more impure, i.e. split on left, keep right
                #this stores the most common value in temp2
                #print("Pre: " + str(root))
                root = root.left #go to the next leaf
                #print("Post: " + str(root))
                xRun = xL
                yRun = yL
                xRun = xRun.reset_index(drop=True)
                if (count == self.maxDepth):
                    a, b = count2(temp1)
                    #test = np.random.choice(a,p=b)
                    #print("test: " + str(test))
                    root.isLeaf= [a, b]

            else: 
                root.left.isLeaf = max(set(temp1), key=temp1.count) #left is true since the right side is more impure
                root = root.right #go to the next leaf (the right one)
                xRun = xR
                yRun = yR
                xRun = xRun.reset_index(drop=True)
                if (count == self.maxDepth):
                    a, b = count2(temp2)
                    #test = np.random.choice(a,p=b)
                    #print("test: " + str(test))
                    root.isLeaf= [a, b]


            #putting information into a node

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

        yHat = [] # variable to store the estimated class label
        #print(self.root.column)
        print(self.root.value)
        troot = self.root
        #print(self.root.left.isLeaf) #None
        print("right column value: " + str(self.root.right.column))
        print(self.root.right.isLeaf) #0
        #print("temp is ok?" + str(temp.value))
        for i in range(0, len(xFeat)):
            temp = troot
            while (temp.isLeaf == None):
                string = str(temp.column)
                #print(string)
                #print(i)
                #print(temp.value)
                #print(xFeat.loc[i,string])
                if (xFeat.loc[i,string] < temp.value):
                    #print("less")
                    temp = temp.left
                elif (xFeat.loc[i,string] > temp.value): 
                    #print("greater")
                    temp = temp.right
                else: 
                    #print("equal")
                    temp = temp.right
            if isinstance(temp.isLeaf, list) == False:
                yHat.append(temp.isLeaf)
            else:
                #print(type(temp.isLeaf))
                test = np.random.choice(temp.isLeaf[0],p=temp.isLeaf[1])
                #print(test)
                yHat.append(test)
        print(yHat)


        #Need to create a base for the tree, i.e. find when it stops
        # TODO
        return yHat

#***
#This contains a list of the functions I will use for the program
#***

class node(object):
    def __init__(self):
        self.left = None
        self.right = None
        self.isLeaf = None #stores value if it is a leaf, else store None
        self.column = None
        self.value = None

#This function counts the number of y values
def count(classes):
    total = {}
    for x in classes:
        if x not in total:
            total[x] = 1
        else: total[x]+=1
    return total
def count2(classes):
    total = {}
    length = len(classes)
    variables = []
    result = []
    for x in classes:
        if x not in total:
            total[x] = 1
        else: total[x]+=1
    for x in total:
        variables.append(x)
        print(total[x]/(float(length)))
        result.append(total[x]/(float(length)))
    print("TEMP IS: ") 
    print(classes)

    return variables,result

#find all the distinct values in a certain feature
#use the rounding function for this
def distinct (column):
    temp = []
    #print(column)
    for i in range(0, len(column)):
        temp.append ((int)(column[i]))
    result = set(temp)
    #print(result)
    return (result)

#this does the actual splitting
#col = column being split
#crit = criteria being split on
#returns the dataframes to calculate the gini coefficients on
def Split(xFeat, y, minLeafSample):
    #new dataframe or new array?
    #I think it has to be a new dataframe
    #Initializing the return values
    hi = 0
    original = gini(y)
    #print(original)

    #calculating the original info gain of the higher node? 

    for i in xFeat.columns:
        print(i)
        temp = np.argsort(xFeat[i]) #argsort returns original indices of each row, and assigns them with the sorted order they should be in
        #print(temp)
        tempY = y[temp]
        tempX = xFeat.iloc[temp]
        #print(tempX)
        #print(tempY)

    #print("At: " + str(xFeat.at[0,col]))
    #print(xFeat.iloc[0])
    #print(y[0])
    #remember to reset dataframes
        for j in range(minLeafSample,len(xFeat) - minLeafSample):
            #print((int)(xFeat.at[i,col]))
            if (y[j] == y[j+1]): continue
            #print(j)
            #If they are not equal, then you check the split and store the min value
            #Else, not equal so split
            #xR = tempX[j:len(xFeat)]
            yL = tempY[0:j] #left are those less than
            yR = tempY[j:len(xFeat)] #right are those greater than 

            #Here, implement the information gain and make sure it is weighted
            intermediate = gini(yL) * (len(yL)/len(y)) + gini (yR) * (len(yR)/len(y))
            infogain = original - intermediate
            #print(infogain)
            if infogain > hi:
                #print("test")
                hi = infogain
                #print(hi)
                col = i
                splitter = j
            original = gini(y)
    return col, splitter

def gini (y):
    total = 0
    yCount = count(y)
    #print(yCount)
    for i in yCount:
        temp = yCount[i] / ((float)(len(y)))
        total += temp*temp
    impurity = 1 - total
    return impurity

def entropy (y):
    total = 0
    yCount = count(y)
    for i in yCount:
        temp = yCount[i] / ((float)(len(y)))
        if temp!=0: 
            total += (math.log(temp))*temp
    impurity = total * -1
    return impurity




def dt_train_test(dt, xTrain, yTrain, xTest, yTest):
    """
    Given a decision tree model, train the model and predict
    the labels of the test data. Returns the accuracy of
    the resulting model.

    Parameters
    ----------
    dt : DecisionTree
        The decision tree with the model parameters
    xTrain : nd-array with shape n x d
        Training data 
    yTrain : 1d array with shape n
        Array of labels associated with training data.
    xTest : nd-array with shape m x d
        Test data 
    yTest : 1d array with shape m
        Array of labels associated with test data.

    Returns
    -------
    acc : float
        The accuracy of the trained knn model on the test data
    """
    # train the model
    dt.train(xTrain, yTrain['label'])
    # predict the training dataset
    yHatTrain = dt.predict(xTrain)
    trainAcc = accuracy_score(yTrain['label'], yHatTrain)
    # predict the test dataset
    yHatTest = dt.predict(xTest)
    testAcc = accuracy_score(yTest['label'], yHatTest)
    return trainAcc, testAcc


def main():
    """
    Main file to run from the command line.
    """
    # set up the program to take in arguments from the command line
    parser = argparse.ArgumentParser()
    parser.add_argument("md",
                        type=int,
                        help="maximum depth")
    parser.add_argument("mls",
                        type=int,
                        help="minimum leaf samples")
    parser.add_argument("--xTrain",
                        default="q4xTrain.csv",
                        help="filename for features of the training data")
    parser.add_argument("--yTrain",
                        default="q4yTrain.csv",
                        help="filename for labels associated with training data")
    parser.add_argument("--xTest",
                        default="q4xTest.csv",
                        help="filename for features of the test data")
    parser.add_argument("--yTest",
                        default="q4yTest.csv",
                        help="filename for labels associated with the test data")

    args = parser.parse_args()
    # load the train and test data
    xTrain = pd.read_csv(args.xTrain)
    yTrain = pd.read_csv(args.yTrain)
    xTest = pd.read_csv(args.xTest)
    yTest = pd.read_csv(args.yTest)
    # create an instance of the decision tree using gini
    dt1 = DecisionTree('gini', args.md, args.mls)
    trainAcc1, testAcc1 = dt_train_test(dt1, xTrain, yTrain, xTest, yTest)
    print("GINI Criterion ---------------")
    print("Training Acc:", trainAcc1)
    print("Test Acc:", testAcc1)
    dt = DecisionTree('entropy', args.md, args.mls)
    trainAcc, testAcc = dt_train_test(dt, xTrain, yTrain, xTest, yTest)
    print("Entropy Criterion ---------------")
    print("Training Acc:", trainAcc)
    print("Test Acc:", testAcc)


if __name__ == "__main__":
    main()
