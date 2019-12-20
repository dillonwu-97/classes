import argparse
import numpy as np
import pandas as pd
from sklearn import preprocessing
from sklearn.feature_extraction import DictVectorizer as dv
from sklearn.feature_extraction.text import TfidfVectorizer as tfv
from sklearn.feature_extraction.text import CountVectorizer as cv
from sklearn.feature_extraction.text import TfidfTransformer as tft
from scipy import sparse
from sklearn.model_selection import KFold, train_test_split
from perceptron import Perceptron, file_to_numpy, calc_mistakes
from q3 import bayes, linreg 
from sklearn.metrics import accuracy_score



def model_assessment(input):
    """
    Given the entire data, decide how
    you want to assess your different models
    to compare perceptron, logistic regression,
    and naive bayes, the different parameters, 
    and the different datasets.
    """
    #evaluating 9 different models -> how evaluate it on same 
    #how plan to assess all 9 of the models are doing 
    #not calling in assessment; can be doing train test split consistent across three data sets 
    #k fold, montecarlo etc. 

    spam = open(input, "r")

    contents = spam.readlines()
    vmap, size = build_vocab_map(contents)
    #print(contents)
    binary = construct_binary(contents, vmap, size)
    count = construct_count(contents, vmap, size)
    tfidf = construct_tfidf(count, vmap)
    
    #This section was just testing exporting the full files 
    #exportbin = binary.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/binary.csv')
    #exportcount = count.to_csv("/Users/Kvothe/Desktop/cs334/hw4/homework4_template/count.csv")
    #why doesn't export to excel work?
    #exportft = sparse.save_npz('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/tft.npz', tfidf)
    #exporttft = tfidf.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/tfidf.csv')
    #numpy zip 

    yarray = ymake(contents)
    #yarray.to_frame().to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/y.csv')
    #print(binary)
    #print(yarray)
    #holdout split 
    #using holdout since the dataset is big; .7 is arbitrary
    #Three times, total of 12 outputs 
    
    xTrainB, xTestB, yTrainB, yTestB = train_test_split(binary, yarray, test_size = .3)

    bin_xTrain = xTrainB.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/binaryTrain.csv', header = False)
    bin_xTest = xTestB.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/binaryTest.csv', header = False)
    bin_yTrain = yTrainB.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/bin_yTrain.csv', header = False)
    bin_yTest = yTestB.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/bin_yTest.csv', header=False)
    
    xTrainC, xTestC, yTrainC, yTestC = train_test_split(count, yarray, test_size = .3)
    count_xTrain = xTrainC.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/countTrain.csv', header = False)
    count_xTest = xTestC.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/countTest.csv', header = False)
    count_yTrain = yTrainC.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/count_yTrain.csv', header = False)
    count_yTest = yTestC.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/count_yTest.csv', header = False)

    xTrainT, xTestT, yTrainT, yTestT = train_test_split(tfidf, yarray, test_size = .3)
    tf_xTrain = xTrainT.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/tfTrain.csv' , header = False)
    tf_xTest = xTestT.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/tfTest.csv', header = False)
    tf_yTrain = yTrainT.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/tf_yTrain.csv', header = False)
    tf_yTest = yTestT.to_csv('/Users/Kvothe/Desktop/cs334/hw4/homework4_template/tf_yTest.csv', header = False)
    
    
    return None


def build_vocab_map(contents):

    words = {}
    size = 0
    for x in contents:
        size +=1 
        for i in x.split():
            if i not in words:
                words[i] = 1
            else: words[i] += 1

    wordsFinal = {}
    for i in words:
        if words[i] >= 30:
            wordsFinal[i] = words[i]
    wordsFinal.pop('1')
    wordsFinal.pop('0')
    print("TESTING FINAL VOCAB MAP---------------------------------")
    #print(wordsFinal)
    return wordsFinal, size
    #getting rid of 0 and 1 in the dictionary

def construct_binary(contents, vmap, size):
    """
    Construct the email datasets based on
    the binary representation of the email.
    For each e-mail, transform it into a
    feature vector where the ith entry,
    $x_i$, is 1 if the ith word in the 
    vocabulary occurs in the email,
    or 0 otherwise
    """
    #How do I speed up this algorithm? Am I understanding it correctly?
    words = vmap.keys()
    #print(words)
    zero = np.zeros(shape=(size, len(words)))
    binary = pd.DataFrame(zero, columns = words)
    
    #filling in the dataframe
    row = 0
    for line in contents:
        for word in line.split():
            if word in words: #if the word in the line is in the 30+ word bag, then modify cell in dataframe
                binary.iloc[row][word] = 1
        print(row)
        row+=1
        if (row == 1000): print("Please have patience")
        if (row == 3000): print("Just a little longer")
        if (row == 5000): print("Almost there")
        #if row == 5: break
    print(binary)

    return binary


def construct_count(contents, vmap, size):
    """
    Construct the email datasets based on
    the count representation of the email.
    For each e-mail, transform it into a
    feature vector where the ith entry,
    $x_i$, is the number of times the ith word in the 
    vocabulary occurs in the email,
    or 0 otherwise
    """
    words = vmap.keys()
    #print(words)
    zero = np.zeros(shape=(size, len(words)))
    count = pd.DataFrame(zero, columns = words)
    
    #filling in the dataframe
    row = 0
    for line in contents:
        for word in line.split():
            if word in words: #if the word in the line is in the 30+ word bag, then modify cell in dataframe
                count.iloc[row][word] += 1
        print(row)
        row+=1
        #if row == 5: break
    print(count)

    return count
        

            

def construct_tfidf(count, vmap):
    """
    Construct the email datasets based on
    the TF-IDF representation of the email.
    """
    
    optimus = tft()
    prime =optimus.fit_transform(count)
    #convert to df -> call pandas on it and column names from first part; for purpose -> numpy shape 
    print("Shape is: " + str(prime.shape))
    #print(optimus.get_params)
    #test = optimus.transform(X=count)
    #print(prime)
    #print(type(prime))

    #transforming into dataframe
    megatron = prime.toarray()
    print(megatron)
    print(len(megatron))
    print(len(megatron[0]))
    bee = pd.DataFrame(megatron, columns = vmap.keys())
    print(bee)

    return bee

def ymake (contents):
    temp = []
    for x in contents:
        temp.append(x.split()[0])
    y = pd.Series(temp)
    print(y)
    return y



def main():
    """
    Main file to run from the command line.
    """
    # set up the program to take in arguments from the command line
    parser = argparse.ArgumentParser()
    parser.add_argument("--data",
                        default="spamAssassin.data",
                        help="filename of the input data")
    args = parser.parse_args()
    model_assessment(args.data)


    
    




if __name__ == "__main__":
    main()
