import argparse
import numpy as np
import pandas as pd
from sklearn.tree import tree
import random
from sklearn.metrics import accuracy_score


class RandomForest(object):
    nest = 0           # number of trees
    maxFeat = 0        # maximum number of features
    maxDepth = 0       # maximum depth of the decision tree
    minLeafSample = 0  # minimum number of samples in a leaf
    criterion = None   # splitting criterion

    def __init__(self, nest, maxFeat, criterion, maxDepth, minLeafSample):
        """
        Decision tree constructor

        Parameters
        ----------
        nest: int
            Number of trees to have in the forest
        maxFeat: int
            Maximum number of features to consider in each tree
        criterion : String
            The function to measure the quality of a split.
            Supported criteria are "gini" for the Gini impurity
            and "entropy" for the information gain.
        maxDepth : int 
            Maximum depth of the decision tree
        minLeafSample : int 
            Minimum number of samples in the decision tree
        """
        self.nest = nest
        self.criterion = criterion
        self.maxDepth = maxDepth
        self.minLeafSample = minLeafSample
        self.forest = []

    def train(self, xFeat, y):
        """
        Train the random forest using the data

        Parameters
        ----------
        xFeat : nd-array with shape n x d
            Training data 
        y : 1d array with shape n
            Array of responses associated with training data.

        Returns
        -------
        stats : object
            Keys represent the number of trees and
            the values are the out of bag errors
        """
        #Idea for oob is that at each iteration, you use the previous + current decision trees to predict
        #for the samples that were not used at that iteration, and if the error rate stops going down, 
        #you can find the optimal self.nest parameter

        treeSize = int(.632 * len(xFeat))
        oobSize = len(xFeat) - treeSize
        #Create a decision tree for each bag
        oob_accuracy = 0
        nest_stop = 0
        flag = 0
        for B in range(self.nest):
            #Put b number of samples into nest # of bags
            #What is b? b should be equal to .632 of the training samples 
            #contains the indices
            temp = np.random.choice(xFeat.shape[0], treeSize, replace = True)
            
            #fill in the indices
            zeroX = np.empty(shape=(treeSize, len(xFeat[0])))
            count = 0

            #fill in new x bag
            for i in temp:
                zeroX[count] = xFeat[i]
                count+=1
            #fill in new y bag
            zeroY = np.empty(shape=(treeSize, 1))
            count = 0
            for i in temp:
                zeroY[count] = y[i]
                count+=1

            #filling in the x bag of the stuff that was NOT used
            not_in_bag = []
            for i in range(len(xFeat)):
                if i not in temp:
                    not_in_bag.append(i)
            #print(temp)
            #print(not_in_bag)

            #filling in the not_in_bag feature stuff
            zero_NIB = np.empty(shape=(len(not_in_bag),len(xFeat[0])))
            count = 0
            for i in not_in_bag:
                zero_NIB[count] = xFeat[i]
                count+=1

            zero_NIB_y = np.empty(shape=(len(not_in_bag), 1))
            count = 0
            for i in not_in_bag:
                zero_NIB_y[count] = y[i]
                count+=1
            #print(zero_NIB)
            #print(zero_NIB_y.ravel())

             
            #predict using the new bags
            oakwood = tree.DecisionTreeClassifier(criterion = self.criterion, min_samples_leaf = self.minLeafSample,
                                                    max_depth = self.maxDepth, splitter="random")
            elm = oakwood.fit(zeroX, zeroY)
            self.forest.append(elm)

            #Calculating OOB error
            #First, make the predictions of y using each tree 
            predictions = self.predict(zero_NIB)
            acc = accuracy_score(predictions, zero_NIB_y)
            #print(acc)
            if (acc > oob_accuracy):
                oob_accuracy = acc
                flag = 0
            else:
                nest_stop = B
                flag+=1
                #print(flag)
                if (flag > 1):
                    break






        #Voting system for the predictions of each decision tree
        self.nest = nest_stop - flag+1
        print("Nest_stop: " + str(self.nest)) #the +1 is because B starts at 0, but it has to start at at least 1
        return 1 - oob_accuracy, self.nest

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
            Predicted response per sample
        """
        predictions = [] #keeps track of the predictions made by each decision tree
        for i in range(len(self.forest)):
            temp = self.forest[i].predict(xFeat)
            predictions.append(temp)
        true = [0] * len(xFeat) #keeps track of the count of each prediction for y
        for i in range(len(predictions)):
            for j in range(len(xFeat)):
                if (predictions[i][j] == 1):
                    true[j] += 1
                else: true[j] -=1
        #counting the y values
        yHat = [0] * len(xFeat)
        for i in range(len(true)):
            if true[i] > 0:
                yHat[i] = 1
            elif true[i] < 0:
                yHat[i] = 0
            else:
                yHat[i] = random.randint(0,1)
        #print(yHat)
        return yHat


def file_to_numpy(filename):
    """
    Read an input file and convert it to numpy
    """
    df = pd.read_csv(filename)
    return df.to_numpy()

def optimize(nest, criterion, xTrain, yTrain, xTest, yTest):
    leaf_range = list(range(1,10))
    depth_range = list(range(1,10))
    feature_range = list (range(1,10))
    best_accuracy = 0
    best_leaf = 0
    best_depth = 0
    best_feature = 0
    for i in leaf_range: 
        print('i is : ' + str(i))
        for j in depth_range:
            for f in feature_range:
                model = RandomForest(nest, f, criterion, j, i)
                model.train(xTrain, yTrain)
                yHat = model.predict(xTest)
                acc = accuracy_score(yTest, yHat)
                if (acc > best_accuracy):
                    best_accuracy = acc
                    best_leaf = i
                    best_depth = j
                    best_feature = f

    return best_accuracy, best_leaf, best_depth, best_feature


def main():
    """
    Main file to run from the command line.
    """
    # set up the program to take in arguments from the command line
    parser = argparse.ArgumentParser()
    parser.add_argument("xTrain",
                        help="filename for features of the training data")
    parser.add_argument("yTrain",
                        help="filename for labels associated with training data")
    parser.add_argument("xTest",
                        help="filename for features of the test data")
    parser.add_argument("yTest",
                        help="filename for labels associated with the test data")
    parser.add_argument("nest", type=int, help="number of nests")
    parser.add_argument("maxFeat", type=int, help="maxFeat")
    parser.add_argument("criterion", type=str, help="criterion")
    parser.add_argument("maxDepth", type=int, help="maxDepth")
    parser.add_argument("minLeafSample", type=int, help="minLeafSample")
    parser.add_argument("--seed", default=334, 
                        type=int, help="default seed number")
    
    args = parser.parse_args()
    # load the train and test data assumes you'll use numpy
    xTrain = file_to_numpy(args.xTrain)
    yTrain = file_to_numpy(args.yTrain)
    xTest = file_to_numpy(args.xTest)
    yTest = file_to_numpy(args.yTest)
    np.random.seed(args.seed)   
    model = RandomForest(args.nest, args.maxFeat, args.criterion, args.maxDepth, args.minLeafSample)
    trainStats, best_nest = model.train(xTrain, yTrain)
    print("OOB Error is: " + str(trainStats))
    yHat = model.predict(xTest)
    acc = accuracy_score(yTest, yHat)
    print("Accuracy is: " + str(acc))

    best_accuracy, best_leaf, best_depth, best_feature = optimize(args.nest, args.criterion, xTrain, yTrain, xTest, yTest)
    temp = [best_accuracy, best_leaf, best_depth, best_feature]
    print("Optimal features are " + str(temp))
    


if __name__ == "__main__":
    main()