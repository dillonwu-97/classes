import argparse
import numpy as np
import pandas as pd 
from sklearn import preprocessing as pp
from sklearn.linear_model import LogisticRegression as LR
from sklearn.metrics import accuracy_score
from sklearn.decomposition import PCA
from sklearn.decomposition import NMF
from sklearn.metrics import roc_curve

def parta(xTrain, yTrain, xTest, yTest):
	"""
	1. This part is for normalizing the features of the wine dataset
	2. It trains an unregularized logistic model on normalized dataset
	3. Predicts the probabilities on the normalized test data
	"""
	#Trying min-max normalization
	#why are these so different
	mm_scaler=pp.MinMaxScaler()
	xTrainNorm = mm_scaler.fit_transform(xTrain)
	xTestNorm = mm_scaler.fit_transform(xTest)
	logistic_mod = LR(solver = 'lbfgs').fit(xTrainNorm, yTrain.ravel())
	prediction = logistic_mod.predict(xTestNorm)
	#print(prediction)

	#calculating accuracy
	#print(accuracy_score(prediction, yTest.ravel()))

	#predicting probability estimates
	prob_pred = logistic_mod.predict_proba(xTestNorm)
	#print(prob_pred) #1st column represents probability of 0, and the second a probability of 1

	return prob_pred

	'''
	#Unregularized Linear Regression, normalized
	linear_mod = LR(fit_intercept = True, normalize = True).fit(xTrain, xTrain)
	#print(linear_mod)
	for i in store:
		print(np.shape(i))
	prediction = linear_mod.predict(xTest)
	print(prediction)
	'''

	#Maybe standardize data instead?

def partb(xTrain, yTrain, xTest, yTest):
	"""
	1. Run PCA on normalized training dataset 
	How many components were needed to capture at least 95% of the variance in the original data. 
	Answer: 2
	Discuss what characterizes the first 3 principal components 
	Answer:
	(i.e., which original features are important).
	"""
	#Normalization
	mm_scaler=pp.MinMaxScaler()
	xTrainNorm = mm_scaler.fit_transform(xTrain)
	xTestNorm = mm_scaler.transform(xTest)
	#identifying min number of components
	pca = PCA(n_components=3) #for 95% variance capture
	#pca = PCA(n_components = 3) #for 3 components
	# components = pca.fit(xTrain)
	#print (components)
	#print(pca.explained_variance_ratio_)
	#print(pca.components_)
	#print("hi" + str(pca.components_))
	pca_train = pca.fit_transform(xTrain)
	pca_test = pca.transform(xTest)
	#print(pca_train)
	#print(pca_test)

	coeff = pd.DataFrame(pca.components_)
	#print(coeff.idxmax(axis=1))
	#print(coeff.max(axis=1))
	#print(coeff)
	return pca_train, pca_test


	
def partc(xTrain, yTrain, xTest, yTest):
	mm_scaler=pp.MinMaxScaler()
	xTrainNorm = mm_scaler.fit_transform(xTrain)
	xTestNorm = mm_scaler.transform(xTest)
	
	nmf = NMF(n_components = 3)
	# components = nmf.fit(xTrain)
	nmf_train = nmf.fit_transform(xTrain)
	nmf_test = nmf.transform(xTest)

	return nmf_train, nmf_test

def partd(xTrain, yTrain, xTest, yTest):
	pca_train, pca_test = partb(xTrain, yTrain, xTest, yTest)
	nmf_train, nmf_test = partc(xTrain, yTrain, xTest, yTest)
	print(pca_train)
	print(nmf_train)

	logistic_mod = LR(penalty = 'none', solver = 'lbfgs').fit(pca_train, yTrain.ravel())
	print(pca_test.shape)
	prediction = logistic_mod.predict(pca_test)
	proba = logistic_mod.predict_proba(pca_test)
	#calculating accuracy
	print(accuracy_score(prediction, yTest.ravel()))

	logistic_mod2 = LR(penalty = 'none',solver = 'lbfgs').fit(nmf_train, yTrain.ravel())
	prediction2 = logistic_mod2.predict(nmf_test)
	proba_2 = logistic_mod2.predict_proba(nmf_test)
	#calculating accuracy
	print(accuracy_score(prediction2, yTest.ravel()))
	#print(proba)
	#print(proba[:, 1]) #prints out column[1] i think
	fpr, tpr, thresholds = roc_curve(yTest.ravel(), proba[:, 1])
	#print(fpr)
	#print(tpr)
	#print(thresholds)

	fpr2, tpr2, thresholds2 = roc_curve(yTest.ravel(), proba_2[:, 1])


def file_to_numpy (filename):
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

	xTrain = file_to_numpy(args.xTrain)
	yTrain = file_to_numpy(args.yTrain)
	xTest = file_to_numpy(args.xTest)
	yTest = file_to_numpy(args.yTest)
	
	answer_a = parta(xTrain, yTrain, xTest, yTest)
	answer_b = partb(xTrain, yTrain, xTest, yTest)
	answer_c = partc(xTrain, yTrain, xTest, yTest)
	#print(answer_a)
	#print(answer_b)
	#print(answer_c)
	partd(xTrain, yTrain, xTest, yTest)

if __name__ == "__main__":
	main()