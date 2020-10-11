from builtins import range
import numpy as np
from random import shuffle
from past.builtins import xrange

def svm_loss_naive(W, X, y, reg):
    """
    Structured SVM loss function, naive implementation (with loops).

    Inputs have dimension D, there are C classes, and we operate on minibatches
    of N examples.

    Inputs:
    - W: A numpy array of shape (D, C) containing weights.
    - X: A numpy array of shape (N, D) containing a minibatch of data.
    - y: A numpy array of shape (N,) containing training labels; y[i] = c means
      that X[i] has label c, where 0 <= c < C.
    - reg: (float) regularization strength

    Returns a tuple of:
    - loss as single float
    - gradient with respect to weights W; an array of same shape as W
    """
    dW = np.zeros(W.shape) # initialize the gradient as zero
    # compute the loss and the gradient
    num_classes = W.shape[1]
    num_train = X.shape[0]
    loss = 0.0
    count = 0
    temp = 0
    temp2 = 0
    for i in range(num_train):
        scores = X[i].dot(W)
        correct_class_score = scores[y[i]]
        for j in range(num_classes):
            if j == y[i]:
                continue
            margin = scores[j] - correct_class_score + 1 # note delta = 1
            if margin > 0:
                loss += margin
#############################################################################

    #############################################################################
    # TODO:                                                                     #
    # Compute the gradient of the loss function and store it dW.                #
    # Rather that first computing the loss and then computing the derivative,   #
    # it may be simpler to compute the derivative at the same time that the     #
    # loss is being computed. As a result you may need to modify some of the    #
    # code above to compute the gradient.                                       #
    #############################################################################
    # *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
                if j == 0: # debug
                    count +=1
                    temp+= X[i][0]
                dW[:,y[i]] -= X[i,:] 
                dW[:,j] += X[i,:] 
    # print(count)
    # print(temp)
    dW /= num_train
    dW += reg*W
    # *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
#############################################################################
    # Right now the loss is a sum over all training examples, but we want it
    # to be an average instead so we divide by num_train.
    loss /= num_train

    # Add regularization to the loss.
    loss += reg * np.sum(W * W)

    # print('nonvec loss ' + str(loss))
    # print('dW[0] is ', dW[0])
    return loss, dW

def svm_loss_vectorized(W, X, y, reg):
    """
    Structured SVM loss function, vectorized implementation.

    Inputs and outputs are the same as svm_loss_naive.
    """
    loss = 0.0
    dW = np.zeros(W.shape) # initialize the gradient as zero

    #############################################################################
    # TODO:                                                                     #
    # Implement a vectorized version of the structured SVM loss, storing the    #
    # result in loss.                                                           #
    #############################################################################
    # *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****


    scores = X.dot(W)
    correct = scores[np.arange(scores.shape[0]), y] # gives an npa of all the correct values
    correct = np.stack((correct,) * scores.shape[1], axis = 1) # all of the values we need to subtract
    # correct [np.arange(scores.shape[0]), y] = 0 # sets all the index of the correct one to 0 
    # Doing the above operation after calculating the loss matrix is better
    loss_m = scores - correct + 1
    loss_m [np.arange(scores.shape[0]), y] = 0
    loss_m[loss_m < 0] = 0
    loss = np.mean(np.sum(loss_m, axis = 1))  # do -1 because we added 1 for the correct label as well
    # print('vec los: ' + str(loss))


    # *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

    #############################################################################
    # TODO:                                                                     #
    # Implement a vectorized version of the gradient for the structured SVM     #
    # loss, storing /the result in dW.                                           #
    #                                                                           #
    # Hint: Instead of computing the gradient from scratch, it may be easier    #
    # to reuse some of the intermediate values that you used to compute the     #
    # loss.                                                                     #
    #############################################################################
    # *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

    loss_m[loss_m > 0] = 1 # the indicator function result; of shape row_count x classes
    temp = X.T.dot(y)
    to_subtract = np.sum(loss_m, axis = 1) # replace the loss_m 0 values with this and subtract
    loss_m[np.arange(loss_m.shape[0]), y] = -1*to_subtract
    dW = np.dot(X.T, loss_m)
    dW/= X.shape[0]
    # print("dW[0] is ", dW[0])

    dW += reg * W
    loss += reg * np.sum(W*W)
    # *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

    return loss, dW
