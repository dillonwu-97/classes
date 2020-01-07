from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn import metrics 
from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
import argparse
import numpy as np
import pandas as pd 
from sklearn.tree import DecisionTreeClassifier 
from sklearn.metrics import accuracy_score as ass



def hyperknn(x, y):
	knn = KNeighborsClassifier(n_neighbors = 5)
	knn.fit(x, y.values.ravel())
	#y_pred = knn.predict(x)
	#scores = cross_val_score(knn, x, y, cv = 10, scoring = 'accuracy')
	#print(y_pred)
	print(y)
	yNew = y.values.ravel()
	#print(scores)
	#print(scores.mean())
	#trying from range 1 -> 30 

	#DONE WITH SCRATCH WORK
	avgs = []
	for i in range(1,31):
		knn = KNeighborsClassifier(n_neighbors = i)
		scores = cross_val_score(knn, x, yNew, cv = 5, scoring = 'accuracy')
		avgs.append(scores.mean())
	#print(avgs)
	#print(max(avgs))
	print(avgs.index(max(avgs))+1)

	return avgs.index(max(avgs))+1

#tuning for max depth
def hyperdt(x, y):
	dt = DecisionTreeClassifier(max_depth=15, min_samples_leaf=10)
	yNew = y.values.ravel()
	print(dt.fit(x, yNew))

	avgs = []
	for i in range (1, 31): 
		dt = DecisionTreeClassifier(max_depth = i, min_samples_leaf=10)
		scores = cross_val_score(dt, x, yNew, cv=5, scoring = 'accuracy')
		avgs.append(scores.mean())

	print(avgs)
	print(avgs.index(max(avgs))+1)

	return avgs.index(max(avgs))+1

def knnEval(x, y, hyper, xTest, yTest):
	knn = KNeighborsClassifier(n_neighbors = hyper)
	yNew = y.values.ravel()
	knn.fit (x, yNew)
	y_pred = knn.predict(x)
	#predicting accuracy
	y_pred_new = knn.predict(xTest)
	score_test = ass(y_pred_new, yTest)
	#calculating AUC curve 
	trainAuc, testAuc = sktree_train_test(knn, x, y, xTest, yTest)
	return score_test, trainAuc, testAuc

def dtEval(x, y, hyper, xTest, yTest):
	dt = DecisionTreeClassifier(max_depth = hyper, min_samples_leaf=10)
	yNew = y.values.ravel()
	dt.fit (x, yNew)
	y_pred = dt.predict(x)
	y_pred_test = dt.predict(xTest)
	#predicting accuracy
	score_test = ass(y_pred_test, yTest)
	#print(score_train)
	#calculating AUC curve 
	trainAuc, testAuc = sktree_train_test(dt, x, y, xTest, yTest)
	return score_test, trainAuc, testAuc

def sktree_train_test(model, xTrain, yTrain, xTest, yTest):
    """
    Given a sklearn tree model, train the model using
    the training dataset, and evaluate the model on the
    test dataset.

    Parameters
    ----------
    model : DecisionTreeClassifier object
        An instance of the decision tree classifier 
    xTrain : nd-array with shape nxd
        Training data
    yTrain : 1d array with shape n
        Array of labels associated with training data
    xTest : nd-array with shape mxd
        Test data
    yTest : 1d array with shape m
        Array of labels associated with test data.

    Returns
    -------
    trainAUC : float
        The AUC of the model evaluated on the training data.
    testAuc : float
        The AUC of the model evaluated on the test data.
    """
    # fit the data to the training dataset
    model.fit(xTrain, yTrain.values.ravel())
    # predict training and testing probabilties
    yHatTrain = model.predict_proba(xTrain)
    yHatTest = model.predict_proba(xTest)
    # calculate auc for training
    fpr, tpr, thresholds = metrics.roc_curve(yTrain['label'],
                                             yHatTrain[:, 1])
    trainAuc = metrics.auc(fpr, tpr)
    # calculate auc for test dataset
    fpr, tpr, thresholds = metrics.roc_curve(yTest['label'],
                                             yHatTest[:, 1])
    testAuc = metrics.auc(fpr, tpr)
    return trainAuc, testAuc


def main():
    # set up the program to take in arguments from the command line
    parser = argparse.ArgumentParser()
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

    khyper = hyperknn(xTrain, yTrain)
    dhyper = hyperdt(xTrain,yTrain)
    print ("Optimal hyperparameter for knn algorithm is: %s" % khyper)
    print ("Optimal hyperparameter for dt algorithm is: %s" % dhyper)


    # use the holdout set with a validation size of 30 of training
    acc1, aucTrain1, aucTest1 = knnEval(xTrain, yTrain, khyper, xTest, yTest)

    xTrain2, xTest2, yTrain2, yTest2 = train_test_split(xTrain, yTrain, test_size = .01)
    acc2, aucTrain2, aucTest2 = knnEval(xTrain2, yTrain2, khyper, xTest, yTest)

    xTrain3, xTest3, yTrain3, yTest3 = train_test_split(xTrain, yTrain, test_size = .05)
    acc3, aucTrain3, aucTest3 = knnEval(xTrain3, yTrain3, khyper, xTest, yTest)
    
    xTrain4, xTest4, yTrain4, yTest4 = train_test_split(xTrain, yTrain, test_size = .1)
    acc4, aucTrain4, aucTest4 = knnEval(xTrain4, yTrain4, khyper, xTest, yTest)
    
    acc5, aucTrain5, aucTest5 = dtEval(xTrain, yTrain, khyper, xTest, yTest)
    acc6, aucTrain6, aucTest6 = dtEval(xTrain2, yTrain2, khyper, xTest, yTest)

    acc7, aucTrain7, aucTest7 = dtEval(xTrain3, yTrain3, khyper, xTest, yTest)
    acc8, aucTrain8, aucTest8 = dtEval(xTrain4, yTrain4, khyper, xTest, yTest)
    
    perfDF = pd.DataFrame([['knn Reg', acc1, aucTrain1, aucTest1],
                           ['knn 1%', acc2, aucTrain2, aucTest2],
                           ['knn 5%', acc3, aucTrain3, aucTest3],
                           ['knn 10%', acc4, aucTrain4, aucTest4],
                           ['df Reg', acc5, aucTrain5, aucTest5],
                           ['df 1', acc6, aucTrain6, aucTest6],
                           ['df 5', acc7, aucTrain7, aucTest7],
                           ['df 10', acc8, aucTrain8, aucTest8]],
                           columns=['Type', 'Accuracy', 'TrainAUC', 'TestAUC'])
    print(perfDF)

if __name__ == "__main__":
    main()
