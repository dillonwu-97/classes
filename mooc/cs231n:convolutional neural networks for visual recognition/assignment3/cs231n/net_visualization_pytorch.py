import torch
import random
import torchvision.transforms as T
import numpy as np
from .image_utils import SQUEEZENET_MEAN, SQUEEZENET_STD
from scipy.ndimage.filters import gaussian_filter1d
import torch.nn.functional as F
from torch.autograd import Variable

def compute_saliency_maps(X, y, model):
	"""
	Compute a class saliency map using the model for images X and labels y.

	Input:
	- X: Input images; Tensor of shape (N, 3, H, W)
	- y: Labels for X; LongTensor of shape (N,)
	- model: A pretrained CNN that will be used to compute the saliency map.

	Returns:
	- saliency: A Tensor of shape (N, H, W) giving the saliency maps for the input
	images.
	"""
	# Make sure the model is in "test" mode
	model.eval()

	# Make input tensor require gradient
	X.requires_grad_()

	saliency = None
	##############################################################################
	# TODO: Implement this function. Perform a forward and backward pass through #
	# the model to compute the gradient of the correct class score with respect  #
	# to each input image. You first want to compute the loss over the correct   #
	# scores (we'll combine losses across a batch by summing), and then compute  #
	# the gradients with a backward pass.                                        #
	##############################################################################
	# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

	# This results in something that makes the background extremely red (not sure why)
	# The reason is because I did not have the abs value function on before; if that is the case
	# the low activation areas become extremely red
	# Forward Pass
	scores = model(X)
	loss = F.cross_entropy(scores, y)
	# Backward Pass
	loss.backward() # get the gradients here
	# Returning the max using gather
	# Get the gradients using X.grad
	# Then get the saliency which is max value of each channel
	# https://stackoverflow.com/questions/42479902/how-does-the-view-method-work-in-pytorch
	saliency = X.grad.abs()
	saliency = torch.max(saliency, dim =1).values
	# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
	##############################################################################
	#                             END OF YOUR CODE                               #
	##############################################################################
	return saliency

def make_fooling_image(X, target_y, model):
	"""
	Generate a fooling image that is close to X, but that the model classifies
	as target_y.

	Inputs:
	- X: Input image; Tensor of shape (1, 3, 224, 224)
	- target_y: An integer in the range [0, 1000)
	- model: A pretrained CNN

	Returns:
	- X_fooling: An image that is close to X, but that is classifed as target_y
	by the model.
	"""
	# Initialize our fooling image to the input image, and make it require gradient
	X_fooling = X.clone()
	X_fooling = X_fooling.requires_grad_()

	learning_rate = 1
	##############################################################################
	# TODO: Generate a fooling image X_fooling that the model will classify as   #
	# the class target_y. You should perform gradient ascent on the score of the #
	# target class, stopping when the model is fooled.                           #
	# When computing an update step, first normalize the gradient:               #
	#   dX = learning_rate * g / ||g||_2                                         #
	#                                                                            #
	# You should write a training loop.                                          #
	#                                                                            #
	# HINT: For most examples, you should be able to generate a fooling image    #
	# in fewer than 100 iterations of gradient ascent.                           #
	# You can print your progress over iterations to check your algorithm.       #
	##############################################################################
	# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

	# while(not fooled)
		# gradient ascent on the score of target class
		# gradient ascent is just the opposite of descent so instead of adding the gradients
		# we just subtract?

	X_fooling_var = Variable(X_fooling, requires_grad=True)
	y = torch.LongTensor([target_y])
	X_fooling.requires_grad = True
	# Idea is to iterate until the scores produced by X_fooling is the same as target?
	best = -1
	for i in range(100):
		# print(best)
		scores = model(X_fooling_var)

		# this doesnt work
		# loss = F.cross_entropy(scores, y)
		# loss.backward()
		# print(loss)

		best = scores[0, target_y] # the score keeps increasing
		best.backward() # backward pass using the score?
		# gradients = X.grad
		# print(X_fooling_var.data)
		gradients = learning_rate * X_fooling_var.grad / X_fooling_var.grad.norm()
		X_fooling_var.data += gradients
		


	# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
	##############################################################################
	#                             END OF YOUR CODE                               #
	##############################################################################
	return X_fooling

def class_visualization_update_step(img, model, target_y, l2_reg, learning_rate):
	########################################################################
	# TODO: Use the model to compute the gradient of the score for the     #
	# class target_y with respect to the pixels of the image, and make a   #
	# gradient step on the image using the learning rate. Don't forget the #
	# L2 regularization term!                                              #
	# Be very careful about the signs of elements in your code.            #
	########################################################################
	# *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

	img_var = Variable(img, requires_grad = True)
	scores = model(img)
	best_val = scores[0, target_y] # sy(I)
	best_val.backward()
	regularizer = l2_reg * img.norm() ** 2 # R(I)
	diff = best_val - regularizer # sy(I) - R(I)

	print(img.shape, best_val, regularizer)
	# img_var.data += img_var.grad * learning_rate

	# *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
	########################################################################
	#                             END OF YOUR CODE                         #
	########################################################################


def preprocess(img, size=224):
	transform = T.Compose([
		T.Resize(size),
		T.ToTensor(),
		T.Normalize(mean=SQUEEZENET_MEAN.tolist(),
					std=SQUEEZENET_STD.tolist()),
		T.Lambda(lambda x: x[None]),
	])
	return transform(img)

def deprocess(img, should_rescale=True):
	transform = T.Compose([
		T.Lambda(lambda x: x[0]),
		T.Normalize(mean=[0, 0, 0], std=(1.0 / SQUEEZENET_STD).tolist()),
		T.Normalize(mean=(-SQUEEZENET_MEAN).tolist(), std=[1, 1, 1]),
		T.Lambda(rescale) if should_rescale else T.Lambda(lambda x: x),
		T.ToPILImage(),
	])
	return transform(img)

def rescale(x):
	low, high = x.min(), x.max()
	x_rescaled = (x - low) / (high - low)
	return x_rescaled

def blur_image(X, sigma=1):
	X_np = X.cpu().clone().numpy()
	X_np = gaussian_filter1d(X_np, sigma, axis=2)
	X_np = gaussian_filter1d(X_np, sigma, axis=3)
	X.copy_(torch.Tensor(X_np).type_as(X))
	return X

def jitter(X, ox, oy):
	"""
	Helper function to randomly jitter an image.

	Inputs
	- X: PyTorch Tensor of shape (N, C, H, W)
	- ox, oy: Integers giving number of pixels to jitter along W and H axes

	Returns: A new PyTorch Tensor of shape (N, C, H, W)
	"""
	if ox != 0:
		left = X[:, :, :, :-ox]
		right = X[:, :, :, -ox:]
		X = torch.cat([right, left], dim=3)
	if oy != 0:
		top = X[:, :, :-oy]
		bottom = X[:, :, -oy:]
		X = torch.cat([bottom, top], dim=2)
	return X
