import argparse
import numpy as np
import pandas as pd
import time
from sklearn.metrics import accuracy_score

class Perceptron(object):
    mEpoch = 1000  # maximum epoch size
    w = None       # weights of the perceptron, should be an array
    errors = None
    enum = mEpoch

    def __init__(self, epoch):
        self.epoch = epoch


    def train(self, xFeat, y):
        """
        Train the perceptron using the data
        
        Parameters
        ----------
        xFeat : nd-array with shape n x d
            Training data 
        y : 1d array with shape n
            Array of responses associated with training data.

        Returns
        -------
        stats : object
            Keys represent the epochs and values the number of mistakes
        """
        self.w = np.zeros(xFeat.shape[1]) #ignore the first element because it is for the index
        self.w[0] = 1 #assigning the weight for the bias to be 1 
        self.errors = 0
        stats = {}
        # TODO implement this
        threshold = 0 #Is this the case?
        #print(len(self.w))
        print("self.w" + str(self.w))
        #print(xFeat.shape)
        #print(len(xFeat))
        #print(self.mEpoch)
         #replacing the ith row, 0th column with all 0's to initialize the bias
        #print(xFeat)
        #print(y)
        if (len(y[0]) > 1):
            y = np.delete(y, 0, 1)
        for i in range (0, len(xFeat)):
            xFeat[i][0] = 0
            #y[i] = int(y[i])
            if (y[i] == 0):
                y[i] = -1
        print(y)
        for enum in range(self.epoch): #number of iterations, should be self.mEpoch
            #multiply the weight by each of the rows 
            #add them all up, see if they are above or below the threshold 
            #if they are above the threshold, then classify them as 1, else classify them as 0 
            #instead of updating them all at once, do one row at a team
            print(enum)
            for i in range(len(xFeat)):
                print(i)
                #compute dot product of row first
                val = np.dot(xFeat[i], self.w)
                #print("val is " + str(val))
                if val * y[i] <= 0:
                    for j in range(1, len(self.w)): #ignore self.w[0] and xFeat[i][0] because that is for bias
                        #print("j is: " +str(j))
                        
                        #print("xFeat[j][i] is " + str(xFeat[j][i]))
                        self.w[j] = self.w[j] + y[i] * xFeat[i][j]
                        #print("self.w[j] is " + str(self.w[j]))
                        #print("xFeat[j][i] is " + str(xFeat[j][i]))
                        #print("Combo breaker: " + str(y[i][1] * xFeat[i][j]))
                        xFeat[i][0] = xFeat[i][0] + y[i] #updating bias
                #print(self.w)
            #testing if all correct
            print(self.w)
            self.errors = 0
            val = np.dot(xFeat, self.w)
            print( "VAL IS: " + str(val))
            for i in range(len(val)):
                print(val[i])
                print(y[i])
                if (val[i] > threshold and y[i] < threshold) or (val[i] < threshold and y[i] > threshold):
                    print(val[i])
                    print(y[i])
                    self.errors += 1
            print("error is " + str(self.errors))
            if self.errors == 0: 
                self.enum = enum
                break
            


            
        #print (self.w)
        #print(self.enum)
        val = np.dot(xFeat, self.w) 
        for i in val:
            print(i)

        print("PRINTING WEIGHTS")

        for i in self.w:
            print(i)
        #one last time
        trainStats = self.errors
        
        pred = []
        #print(val)
        for i in range(len(val)):
            if (val[i] > threshold):
                pred.append(1)
            else: pred.append(-1)    
        #print(pred)

        for i in range(len(pred)):
            if pred[i] != y[i]:
                trainStats += 1
        print("tstats" + str(trainStats))
        
        
        return trainStats, self.enum
       
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
        yHat = []
        for i in range (0, len(xFeat)): #for adding the bias
            xFeat[i][0] = 1
            #y[i] = int(y[i])
        yHat = np.dot(xFeat, self.w)
        for i in range(len(yHat)): 
            print("yHat is: " + str(yHat[i]))
            print("weight is: " + str(self.w[i]))
            print("xFeat is: " + str(xFeat[i]))
            if yHat[i] < 0: 
                yHat[i] = 0
            else: yHat[i] = 1
        #print("yhat is: " + str(yhat))
        #print("yhat is appearing? " + str(yhat))
        print(yHat)
        return yHat


def calc_mistakes(yHat, yTrue):
    """
    Calculate the number of mistakes
    that the algorithm makes based on the prediction.

    Parameters
    ----------
    yHat : 1-d array or list with shape n
        The predicted label.
    yTrue : 1-d array or list with shape n
        The true label.      

    Returns
    -------
    err : int
        The number of mistakes that are made
    """
    
    errors = 0
    yTrue = np.delete(yTrue, 0, 1)
    for i in range(len(yHat)):
        if yHat [i] != yTrue [i]:
            errors += 1
    
    return 0


def file_to_numpy(filename):
    """
    Read an input file and convert it to numpy
    """
    df = pd.read_csv(filename)
    return df.to_numpy()


def main():
    """
    Main file to run from the command line.
    """
    # set up the program to take in arguments from the command line
    parser = argparse.ArgumentParser()
    parser.add_argument("xTrain",
                        default="binary.csv",
                        help="filename for features of the training data")
    parser.add_argument("yTrain",
                        default = "y.csv",
                        help="filename for labels associated with training data")
    parser.add_argument("xTest",
                        default = "binary.csv",
                        help="filename for features of the test data")
    parser.add_argument("yTest",
                        default = "y.csv",
                        help="filename for labels associated with the test data")
    parser.add_argument("epoch", default=1000, type=int, help="max number of epochs")
    parser.add_argument("--seed", default=334, 
                        type=int, help="default seed number")
    
    args = parser.parse_args()
    # load the train and test data assumes you'll use numpy
    try:
        xTrain = file_to_numpy(args.xTrain)
        xTest = file_to_numpy(args.xTest)
    except: 
        xTrain = np.load(args.xTrain, allow_pickle=True)
        xTest = np.load(args.xTest, allow_pickle=True)
    yTrain = file_to_numpy(args.yTrain)
    yTest = file_to_numpy(args.yTest)

    #xTrain = pd.read_csv(args.xTrain, index_col=0)
    #yTrain = pd.read_csv(args.yTrain, index_col=0)
    #xTest = pd.read_csv(args.xTest, index_col=0)
    #yTest = pd.read_csv(args.yTest, index_col=0)

    np.random.seed(args.seed)   
    model = Perceptron(args.epoch)
    trainStats, enum = model.train(xTrain, yTrain)
    print("Trainstats is: " + str(trainStats))
    yHat = model.predict(xTest)
    #print("nothing is appearing? " + str(yHat))
    # print out the number of mistakes
    #print("Number of mistakes on the test dataset")
    #print(calc_mistakes(yHat, yTest))
    print("The epoch is: " + str(enum))
    print(yHat)
    print(yTest)
    yTest = np.delete(yTest, 0, 1)
    print(accuracy_score(yHat, yTest))




if __name__ == "__main__":
    main()