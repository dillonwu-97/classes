import xgboost
import argparse
import numpy as np
import pandas as pd
from xgboost import XGBClassifier as xgb
from sklearn.metrics import accuracy_score
from sklearn.model_selection import GridSearchCV as gscv

#gradients not weights 
#learning rate lower -> # of rounds is more 

def parta (xTrain, yTrain, xTest, yTest):
	#optimize hyperparameters using gscv 
	booster = xgb()
	#booster.fit(xTrain, yTrain.ravel())
	#print(booster)

	#setting up the ranges 
	depth_range = list(range(1,10))
	learning_rate_range = list(range(1,10))
	nround_range = list (range(1,10))
	for i in range(len(learning_rate_range)):
		learning_rate_range[i] *= .1
	param_grid = {}
	param_grid['max_depth'] = depth_range
	param_grid['learning_rate'] = learning_rate_range
	param_grid['num_round'] = nround_range
	print(param_grid)
	grid = gscv(booster, param_grid, cv=10, scoring='accuracy', verbose = 10)
	grid.fit(xTrain, yTrain.ravel())
	print(grid.best_score_)
	print(grid.best_params_)

	#y_pred = booster.predict(xTest)
	#accuracy = accuracy_score(y_pred, yTest)
	#print(accuracy)


def file_to_numpy(filename):
    """
    Read an input file and convert it to numpy
    """
    df = pd.read_csv(filename)
    return df.to_numpy()


def main():

    parser = argparse.ArgumentParser()
    parser.add_argument("xTrain",
                        help="filename for features of the training data")
    parser.add_argument("yTrain",
                        help="filename for labels associated with training data")
    parser.add_argument("xTest",
                        help="filename for features of the test data")
    parser.add_argument("yTest",
                        help="filename for labels associated with the test data")
    parser.add_argument("--seed", default=334, 
                        type=int, help="default seed number")

    args = parser.parse_args()
    # load the train and test data assumes you'll use numpy
    xTrain = file_to_numpy(args.xTrain)
    yTrain = file_to_numpy(args.yTrain)
    xTest = file_to_numpy(args.xTest)
    yTest = file_to_numpy(args.yTest)

    parta(xTrain, yTrain, xTest, yTest)

if __name__ == "__main__":
	main()