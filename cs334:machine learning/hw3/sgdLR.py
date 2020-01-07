import argparse
import numpy as np
import pandas as pd
import time
from numpy.linalg import inv
from lr import LinearRegression, file_to_numpy


class SgdLR(LinearRegression):
    lr = 1  # learning rate
    bs = 1  # batch size
    mEpoch = 1000 # maximum epoch size

    def __init__(self, lr, bs, epoch):
        self.lr = lr
        self.bs = bs
        self.mEpoch = epoch

    def train_predict(self, xTrain, yTrain, xTest, yTest):
        """
        See definition in LinearRegression class
        """
        trainStats = {}

        #Initializing beta
        xTransp = xTrain.transpose()
        inverse = inv(np.matmul(xTransp, xTrain))
        multWY = np.matmul(inverse, xTransp)
        self.beta = np.matmul(multWY, yTrain)
        print(xTrain.shape)
        print(self.beta.shape)
        # TODO: DO SGD
        for i in range(self.mEpoch):
            
            # array_split splits unevenly, but does the job for batch sizes
            # Shuffle the data and break into B = N/Batch size 
            xBatch = self.randomize(xTrain, i)
            yBatch = self.randomize(yTrain, i)
            #print(xBatch)
            #print(yBatch)
            #print(self.beta)

            # Compute the gradients for each batch, which means minimizing mse
            # Take the sum of all the gradients and then divide each by the total number of batches
            gradients = np.zeros((len(self.beta),1))
            for j in range(len(xBatch)):
                print(j)
                xT = np.transpose(xBatch[j])
                temp1 = yBatch[j] - np.matmul(xBatch[j], self.beta)
                temp2 = np.matmul(xT, temp1)
                temp2 = temp2 / len(xTrain)
                temp2 = temp2 * self.lr
                print(temp2)
                gradients += temp2
                print("gradients")
                print(gradients)
            print(len(xBatch))
            gradients = gradients / len(xBatch)
            print(gradients)
            self.beta += gradients
            #print(self.beta)
            #gradients = gradients * learningrate / N; N = len(xTrain)?


            # Update beta 
        error_Train = LinearRegression.mse(self, xTrain, yTrain)
        error_Test = LinearRegression.mse(self, xTest, yTest)
        print(error_Train)
        print(error_Test)
            

        return trainStats

    def randomize(self, df, seed):
        np.random.seed(seed) # Setting the seed and then shuffling
        shuffle = np.copy(df) #create a copy 
        np.random.shuffle(shuffle) #shuffle data
        batches = []
        # split into n bs number of batches
        for batch in np.array_split(shuffle, self.bs):
            batches.append(batch)
        return batches




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
    parser.add_argument("lr", type=float, help="learning rate")
    parser.add_argument("bs", type=int, help="batch size")
    parser.add_argument("epoch", type=int, help="max number of epochs")
    parser.add_argument("--seed", default=334, 
                        type=int, help="default seed number")

    args = parser.parse_args()
    # load the train and test data
    xTrain = file_to_numpy(args.xTrain)
    yTrain = file_to_numpy(args.yTrain)
    xTest = file_to_numpy(args.xTest)
    yTest = file_to_numpy(args.yTest)

    # setting the seed for deterministic behavior
    np.random.seed(args.seed)   
    model = SgdLR(args.lr, args.bs, args.epoch)
    trainStats = model.train_predict(xTrain, yTrain, xTest, yTest)
    print(trainStats)


if __name__ == "__main__":
    main()

