import argparse
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA
from sklearn.preprocessing import MinMaxScaler 



def extract_features(df):
    """
    Given a pandas dataframe, extract the relevant features
    from the date column

    Parameters
    ----------
    df : pandas dataframe
        Training or test data 
    Returns
    -------
    df : pandas dataframe
        The updated dataframe with the new features
    """
    # TODO do more than this
    
    #print(df['date'])
    df['date'] = pd.to_datetime(df['date'])
    # Extracting info from date and converting all the values to string 
    df['hour'] = df.date.dt.hour.apply(str)
    df['day'] = df.date.dt.day.apply(str)
    df['month'] = df.date.dt.month.apply(str)
    df.drop(columns='date', inplace=True)
    return df


def select_features(df):
    """
    Select the features to keep

    Parameters
    ----------
    df : pandas dataframe
        Training or test data 
    Returns
    -------
    df : pandas dataframe
        The updated dataframe with a subset of the columns
    """
    # TODO

    # Producing the Pearson correlation heatmap
    sns.heatmap(df.corr())
    plt.show()

    # Using min max scaler
    mm_scaler = MinMaxScaler()
    cols = df.columns.tolist()
    cols = cols[:-3]
    #print(cols)
    df[cols] = mm_scaler.fit_transform(df[cols])

    return df


def preprocess_data(trainDF, testDF):
    """
    Preprocess the training data and testing data

    Parameters
    ----------
    trainDF : pandas dataframe
        Training data 
    testDF : pandas dataframe
        Test data 
    Returns
    -------
    trainDF : pandas dataframe
        The preprocessed training data
    testDF : pandas dataframe
        The preprocessed testing data
    """
    # TODO do something

    # Finding the components that cover 95% of variance
    pca = PCA(n_components = .99)
    train = pca.fit_transform(trainDF)
    test = pca.fit_transform(testDF)

    trainDF = pd.DataFrame({'C1': train[:, 0], 'C2': train[:, 1], 'C3': train[:, 2]})
    testDF = pd.DataFrame({'C1': test[:, 0], 'C2': test[:, 1], 'C3': test[:, 2]})

    mm_scaler = MinMaxScaler()
    train = mm_scaler.fit_transform(trainDF)
    test = mm_scaler.fit_transform(testDF)

    trainDF = pd.DataFrame({'C1': train[:, 0], 'C2': train[:, 1], 'C3': train[:, 2]})
    testDF = pd.DataFrame({'C1': test[:, 0], 'C2': test[:, 1], 'C3': test[:, 2]})

    print(trainDF)

    return trainDF, testDF


def main():
    """
    Main file to run from the command line.
    """
    # set up the program to take in arguments from the command line
    parser = argparse.ArgumentParser()
    parser.add_argument("outTrain",
                        help="filename of the updated training data")
    parser.add_argument("outTest",
                        help="filename of the updated test data")
    parser.add_argument("--trainFile",
                        default="eng_xTrain.csv",
                        help="filename of the training data")
    parser.add_argument("--testFile",
                        default="eng_xTest.csv",
                        help="filename of the test data")
    args = parser.parse_args()
    # load the train and test data
    xTrain = pd.read_csv(args.trainFile)
    xTest = pd.read_csv(args.testFile)
    # extract the new features
    xNewTrain = extract_features(xTrain)
    xNewTest = extract_features(xTest)
    # select the features
    xNewTrain = select_features(xNewTrain)
    xNewTest = select_features(xNewTest)
    # preprocess the data
    xTrainTr, xTestTr = preprocess_data(xNewTrain, xNewTest)
    # save it to csv
    xTrainTr.to_csv(args.outTrain, index=False)
    xTestTr.to_csv(args.outTest, index=False)


if __name__ == "__main__":
    main()
