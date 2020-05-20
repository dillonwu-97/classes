import argparse
import numpy as np
import pandas as pd
import time

from lr import LinearRegression, file_to_numpy
from numpy.linalg import inv


class StandardLR(LinearRegression):

    def train_predict(self, xTrain, yTrain, xTest, yTest):
        """
        See definition in LinearRegression class
        """
        trainStats = {}
        # TODO: DO SOMETHING
        # Calculating beta
        # train the model first on xTrain
        # Beta = ([xTrainT][xTrain])^-1(xTrainT)(yTrain)
        xTransp = xTrain.transpose()
        inverse = inv(np.matmul(xTransp, xTrain))
        multWY = np.matmul(inverse, xTransp)
        self.beta = np.matmul(multWY, yTrain)
        #print("self.beta is: " + str(self.beta))

        # Calculate mse
        error_Train = LinearRegression.mse(self, xTrain, yTrain)
        error_Test = LinearRegression.mse(self, xTest, yTest)
        return trainStats


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

    args = parser.parse_args()
    # load the train and test data
    xTrain = file_to_numpy(args.xTrain)
    yTrain = file_to_numpy(args.yTrain)
    xTest = file_to_numpy(args.xTest)
    yTest = file_to_numpy(args.yTest)

    model = StandardLR()
    trainStats = model.train_predict(xTrain, yTrain, xTest, yTest)
    print(trainStats)


if __name__ == "__main__":
    main()
