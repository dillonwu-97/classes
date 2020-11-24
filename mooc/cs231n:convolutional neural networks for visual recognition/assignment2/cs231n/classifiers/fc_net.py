from builtins import range
from builtins import object
import numpy as np

from cs231n.layers import *
from cs231n.layer_utils import *


class TwoLayerNet(object):
	"""
	A two-layer fully-connected neural network with ReLU nonlinearity and
	softmax loss that uses a modular layer design. We assume an input dimension
	of D, a hidden dimension of H, and perform classification over C classes.

	The architecure should be affine - relu - affine - softmax.

	Note that this class does not implement gradient descent; instead, it
	will interact with a separate Solver object that is responsible for running
	optimization.

	The learnable parameters of the model are stored in the dictionary
	self.params that maps parameter names to numpy arrays.
	"""
	def __init__(self, input_dim=3*32*32, hidden_dim=100, num_classes=10,
				 weight_scale=1e-3, reg=0.0):
		"""
		Initialize a new network.

		Inputs:
		- input_dim: An integer giving the size of the input
		- hidden_dim: An integer giving the size of the hidden layer
		- num_classes: An integer giving the number of classes to classify
		- weight_scale: Scalar giving the standard deviation for random
		  initialization of the weights.
		- reg: Scalar giving L2 regularization strength.
		"""
		self.params = {}
		self.reg = reg

		############################################################################
		# TODO: Initialize the weights and biases of the two-layer net. Weights    #
		# should be initialized from a Gaussian centered at 0.0 with               #
		# standard deviation equal to weight_scale, and biases should be           #
		# initialized to zero. All weights and biases should be stored in the      #
		# dictionary self.params, with first layer weights                         #
		# and biases using the keys 'W1' and 'b1' and second layer                 #
		# weights and biases using the keys 'W2' and 'b2'.                         #
		############################################################################
		# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		self.params['W1'] = np.random.normal(scale = weight_scale, size=(input_dim,hidden_dim))
		self.params['W2'] = np.random.normal(scale = weight_scale, size=(hidden_dim, num_classes))
		self.params['b1'] = np.zeros(shape = hidden_dim)
		self.params['b2'] = np.zeros(shape = num_classes)
		# print(self.params['W1'].shape)
		# print(self.params['b1'].shape)
		# print(self.params['W2'].shape)
		# print(self.params['b2'].shape)

		# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		############################################################################
		#                             END OF YOUR CODE                             #
		############################################################################


	def loss(self, X, y=None):
		"""
		Compute loss and gradient for a minibatch of data.

		Inputs:
		- X: Array of input data of shape (N, d_1, ..., d_k)
		- y: Array of labels, of shape (N,). y[i] gives the label for X[i].

		Returns:
		If y is None, then run a test-time forward pass of the model and return:
		- scores: Array of shape (N, C) giving classification scores, where
		  scores[i, c] is the classification score for X[i] and class c.

		If y is not None, then run a training-time forward and backward pass and
		return a tuple of:
		- loss: Scalar value giving the loss
		- grads: Dictionary with the same keys as self.params, mapping parameter
		  names to gradients of the loss with respect to those parameters.
		"""
		scores = None
		############################################################################
		# TODO: Implement the forward pass for the two-layer net, computing the    #
		# class scores for X and storing them in the scores variable.              #
		############################################################################
		# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		W1 = self.params['W1']
		W2 = self.params['W2']
		b1 = self.params['b1']
		b2 = self.params['b2']
		
		# print(W1.shape)
		# print(b1.shape)
		A1 = X.dot(W1) + b1 # affine layer 1
		A1[A1 < 0] = 0 # apply non linear function RELU
		scores = A1.dot(W2) + b2 # affine layer 2

		# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		############################################################################
		#                             END OF YOUR CODE                             #
		############################################################################

		# If y is None then we are in test mode so just return scores
		if y is None:
			return scores

		loss, grads = 0, {}
		############################################################################
		# TODO: Implement the backward pass for the two-layer net. Store the loss  #
		# in the loss variable and gradients in the grads dictionary. Compute data #
		# loss using softmax, and make sure that grads[k] holds the gradients for  #
		# self.params[k]. Don't forget to add L2 regularization!                   #
		#                                                                          #
		# NOTE: To ensure that your implementation matches ours and you pass the   #
		# automated tests, make sure that your L2 regularization includes a factor #
		# of 0.5 to simplify the expression for the gradient.                      #
		############################################################################
		# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		scores -= np.max(scores, keepdims= True)
		scores = np.exp(scores)
		scores /= np.sum(scores, axis = 1, keepdims=True) # Applying nonlinear function softmax
		loss += np.sum(-np.log(scores[np.arange(scores.shape[0]), y]))
		loss/=scores.shape[0]
		loss += 0.5 * (np.sum(W1 * W1) * self.reg + np.sum(W2 * W2) * self.reg)
		# Finding the gradients 

		scores [np.arange(scores.shape[0]), y] -=1 # first, calculate the gradient of the softmax layer
		grads['W2'] = A1.T.dot(scores)# hidden layer multiplied by scores
		grads['W2'] /= A1.shape[0]
		grads['W2'] += self.reg * W2
		grads['b2'] = np.sum(scores, axis = 0) / A1.shape[0]
		# backpropagation layer
		backprop_layer = scores.dot(W2.T) * A1

		A1 [A1 > 0] = 1
		grads['W1'] = X.T.dot(backprop_layer)
		grads['W1'] /= X.shape[0]
		grads['W1'] += self.reg * W1 # not sure why even though it is L2 regularization, we multiply by W1 only once
		grads['b1'] = np.sum(backprop_layer, axis = 0) / X.shape[0]

		# Using the code from layers.py and layer_utils.py
		# out1, cache1 = affine_relu_forward(X, grads['W1'], grads['b1'])
		# out2, cache2 = affine_relu_forward(out1, grads['W2'], grads['b2'])
		# loss, dx = softmax_loss(out2, y)
		# loss += .5 * reg * (np.sum(W1 * W1) + np.sum(W2 * W2))
		
		# dx, dw, db = affine_relu_backward(dx, cache2)
		# # number 1/2 is used to simplify derivative calculations
		# grads['W2'] = self.reg * grads['W2'] + dw
		# grads['b2'] = db
		# _, dw, db = affine_relu_backward(dx, cache1)
		# grads['W1'] += self.reg * grads['W1'] + dw
		# grads['b1'] = db

		# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		############################################################################
		#                             END OF YOUR CODE                             #
		############################################################################

		return loss, grads


class FullyConnectedNet(object):
	"""
	A fully-connected neural network with an arbitrary number of hidden layers,
	ReLU nonlinearities, and a softmax loss function. This will also implement
	dropout and batch/layer normalization as options. For a network with L layers,
	the architecture will be

	{affine - [batch/layer norm] - relu - [dropout]} x (L - 1) - affine - softmax

	where batch/layer normalization and dropout are optional, and the {...} block is
	repeated L - 1 times.

	Similar to the TwoLayerNet above, learnable parameters are stored in the
	self.params dictionary and will be learned using the Solver class.
	"""

	def __init__(self, hidden_dims, input_dim=3*32*32, num_classes=10,
				 dropout=1, normalization=None, reg=0.0,
				 weight_scale=1e-2, dtype=np.float32, seed=None):
		"""
		Initialize a new FullyConnectedNet.

		Inputs:
		- hidden_dims: A list of integers giving the size of each hidden layer.
		- input_dim: An integer giving the size of the input.
		- num_classes: An integer giving the number of classes to classify.
		- dropout: Scalar between 0 and 1 giving dropout strength. If dropout=1 then
		  the network should not use dropout at all.
		- normalization: What type of normalization the network should use. Valid values
		  are "batchnorm", "layernorm", or None for no normalization (the default).
		- reg: Scalar giving L2 regularization strength.
		- weight_scale: Scalar giving the standard deviation for random
		  initialization of the weights.
		- dtype: A numpy datatype object; all computations will be performed using
		  this datatype. float32 is faster but less accurate, so you should use
		  float64 for numeric gradient checking.
		- seed: If not None, then pass this random seed to the dropout layers. This
		  will make the dropout layers deteriminstic so we can gradient check the
		  model.
		"""
		self.normalization = normalization
		self.use_dropout = dropout != 1
		self.reg = reg
		self.num_layers = 1 + len(hidden_dims)
		self.dtype = dtype
		self.params = {}

		############################################################################
		# TODO: Initialize the parameters of the network, storing all values in    #
		# the self.params dictionary. Store weights and biases for the first layer #
		# in W1 and b1; for the second layer use W2 and b2, etc. Weights should be #
		# initialized from a normal distribution centered at 0 with standard       #
		# deviation equal to weight_scale. Biases should be initialized to zero.   #
		#                                                                          #
		# When using batch normalization, store scale and shift parameters for the #
		# first layer in gamma1 and beta1; for the second layer use gamma2 and     #
		# beta2, etc. Scale parameters should be initialized to ones and shift     #
		# parameters should be initialized to zeros.                               #
		############################################################################
		# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

		# batchnorm_forward (x, gamma, beta, bn_param)
		# batchnorm_backward_alt (dout, cache)

		#####
		# initializing the parameters of the network in self.params
		row_dim = input_dim
		col_dim = hidden_dims[0]
		for i in range(0,self.num_layers):
			s_w = 'W' + str(i+1)
			s_b = 'b' + str(i+1)
			self.params[s_w] = np.random.normal(scale=weight_scale, size = (row_dim, col_dim))
			self.params[s_b] = np.zeros(shape=col_dim)

			if (self.normalization == 'batchnorm' or self.normalization=='layernorm') and i < self.num_layers-1 :
				# initializing shift parameters
				s_gamma = 'gamma' + str(i+1) # scale parameter
				s_beta = 'beta' + str(i+1) # shift parameter
				self.params[s_gamma] = np.ones(col_dim)
				self.params[s_beta] = np.zeros(col_dim)

			row_dim = col_dim

			if (i + 1 < self.num_layers-1): 
				col_dim = hidden_dims[i+1]
			else:
				col_dim = num_classes
		# done initializing parameters
		#####

		# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		############################################################################
		#                             END OF YOUR CODE                             #
		############################################################################

		# When using dropout we need to pass a dropout_param dictionary to each
		# dropout layer so that the layer knows the dropout probability and the mode
		# (train / test). You can pass the same dropout_param to each dropout layer.
		self.dropout_param = {}
		if self.use_dropout:
			self.dropout_param = {'mode': 'train', 'p': dropout}
			if seed is not None:
				self.dropout_param['seed'] = seed

		# With batch normalization we need to keep track of running means and
		# variances, so we need to pass a special bn_param object to each batch
		# normalization layer. You should pass self.bn_params[0] to the forward pass
		# of the first batch normalization layer, self.bn_params[1] to the forward
		# pass of the second batch normalization layer, etc.
		self.bn_params = []
		if self.normalization=='batchnorm':
			self.bn_params = [{'mode': 'train'} for i in range(self.num_layers - 1)]
		if self.normalization=='layernorm':
			self.bn_params = [{} for i in range(self.num_layers - 1)]

		# Cast all parameters to the correct datatype
		for k, v in self.params.items():
			self.params[k] = v.astype(dtype)


	def loss(self, X, y=None):
		"""
		Compute loss and gradient for the fully-connected net.

		Input / output: Same as TwoLayerNet above.
		"""
		X = X.astype(self.dtype)
		mode = 'test' if y is None else 'train'

		# Set train/test mode for batchnorm params and dropout param since they
		# behave differently during training and testing.
		if self.use_dropout:
			self.dropout_param['mode'] = mode
		if self.normalization=='batchnorm':
			for bn_param in self.bn_params:
				bn_param['mode'] = mode
		scores = None
		############################################################################
		# TODO: Implement the forward pass for the fully-connected net, computing  #
		# the class scores for X and storing them in the scores variable.          #
		#                                                                          #
		# When using dropout, you'll need to pass self.dropout_param to each       #
		# dropout forward pass.                                                    #
		#                                                                          #
		# When using batch normalization, you'll need to pass self.bn_params[0] to #
		# the forward pass for the first batch normalization layer, pass           #
		# self.bn_params[1] to the forward pass for the second batch normalization #
		# layer, etc.                                                              #
		############################################################################
		# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

		#####
		# Starting forward pass implementation
		cache_dict = {}
		start = X.copy()
		for i in range(1, self.num_layers):
			s_w = 'W' + str(i)
			s_b = 'b' + str(i)
			# print(start, self.params[s_w], self.params[s_b])
			if self.normalization == None:
				start, cache = affine_relu_forward(start, self.params[s_w], self.params[s_b])
			elif self.normalization == "batchnorm" or self.normalization == "layernorm":
				s_gamma = 'gamma' + str(i)
				s_beta = 'beta' + str(i)
				# print(s_gamma)
				start, cache = affine_batchnorm_relu_forward(start, self.params[s_w], self.params[s_b],\
				 self.params[s_gamma], self.params[s_beta], self.bn_params[i-1], self.normalization)
			if self.use_dropout:
				start, dropout_cache = dropout_forward(start, self.dropout_param)
				s_d = 'dropout' + str(i)
				cache_dict[s_d] = dropout_cache
			s_c = 'cache' + str(i)
			cache_dict[s_c] = cache
		# one more forward pass
		start, cache = affine_forward(start, self.params['W' + str(self.num_layers)], self.params['b' + str(self.num_layers)])
		cache_dict['cache' + str(self.num_layers)] = cache
		# if we are using dropout, one more dropout layer
		if self.use_dropout:
			start, dropout_cache = dropout_forward(start, self.dropout_param)
			cache_dict['dropout' + str(self.num_layers)] = dropout_cache

		scores = start
		#####

		# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		############################################################################
		#                             END OF YOUR CODE                             #
		############################################################################

		# If test mode return early
		if mode == 'test':
			return scores

		loss, grads = 0.0, {}
		############################################################################
		# TODO: Implement the backward pass for the fully-connected net. Store the #
		# loss in the loss variable and gradients in the grads dictionary. Compute #
		# data loss using softmax, and make sure that grads[k] holds the gradients #
		# for self.params[k]. Don't forget to add L2 regularization!               #
		#                                                                          #
		# When using batch/layer normalization, you don't need to regularize the scale   #
		# and shift parameters.                                                    #
		#                                                                          #
		# NOTE: To ensure that your implementation matches ours and you pass the   #
		# automated tests, make sure that your L2 regularization includes a factor #
		# of 0.5 to simplify the expression for the gradient.                      #
		############################################################################
		# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

		# Applying softmax
		loss, dx = softmax_loss(scores, y)
		total = 0
		for i in range(1, self.num_layers):
			s_w = "W" + str(i)
			total += self.reg * np.sum(self.params[s_w] * self.params[s_w])
		loss += .5 * total
		# loss += .5 * self.reg * sum([ np.sum(self.params["W" + str(x)] * self.params["W" + str(x)] for x in range (1, self.num_layers-1)) ])
		# self.num_layers = hidden dims + 1
		# print(self.params) 

		# if we are using dropout, then do a dropout backward pass first
		if self.use_dropout:
			dropout_cache = cache_dict["dropout" + str(self.num_layers)]
			dx = dropout_backward(dx, dropout_cache)
		# print(cache_dict["cache" + str(self.num_layers-1)])
		# first affine backward pass
		affine_cache = cache_dict["cache" + str(self.num_layers)]
		dx,dw,db = affine_backward(dx, affine_cache)
		s_w = "W" + str(self.num_layers)
		s_b = "b" + str(self.num_layers)
		grads[s_w] = dw
		grads[s_b] = db

		for i in range(self.num_layers-1, 0, -1):
			s_c = "cache" + str(i)
			s_w = "W" + str(i)
			s_b = "b" + str(i)
			if self.use_dropout:
				dropout_cache = cache_dict["dropout" + str(i)]
				dx = dropout_backward(dx, dropout_cache)
			if self.normalization == None:
				dx, dw, db = affine_relu_backward(dx, cache_dict[s_c])
			else:
				s_gamma = "gamma" + str(i)
				s_beta = "beta" + str(i)
				dx, dw, db, dgamma, dbeta = affine_batchnorm_relu_backward(dx, cache_dict[s_c], self.normalization)
				grads[s_gamma] = dgamma 
				grads[s_beta] = dbeta
			grads[s_w] = self.params[s_w] * self.reg + dw
			grads[s_b] = db 

		# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
		############################################################################
		#                             END OF YOUR CODE                             #
		############################################################################

		return loss, grads


def affine_batchnorm_relu_forward(start, weights, bias, gamma, beta, bn_params, norm_value):
#affine_batchnorm_relu_forward(start, self.params[s_w], self.params[s_b],\
#				 self.params[s_gamma], self.params[s_beta], self.bn_params[i-1])
	a_out, affine_cache = affine_forward(start, weights, bias)
	if norm_value == 'batchnorm':
		b_out, batch_cache = batchnorm_forward(a_out, gamma, beta, bn_params)
	elif norm_value == 'layernorm':
		b_out, batch_cache = layernorm_forward(a_out, gamma, beta, bn_params)
	out, relu_cache = relu_forward(b_out)
	cache = (affine_cache, batch_cache, relu_cache)
	return out, cache

def affine_batchnorm_relu_backward(dout, cache, norm_value):
	affine_cache, batch_cache, relu_cache = cache
	r_out = relu_backward(dout, relu_cache)
	if norm_value == 'batchnorm':
		batch_out, dgamma, dbeta = batchnorm_backward_alt(r_out, batch_cache)
	elif norm_value == 'layernorm':
		batch_out, dgamma, dbeta = layernorm_backward(r_out, batch_cache)
	dx, dw, db = affine_backward(batch_out, affine_cache)
	return dx, dw, db, dgamma, dbeta

