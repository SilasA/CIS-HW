#!/usr/bin python3

# Citations:
# https://stackabuse.com/creating-a-neural-network-from-scratch-in-python-adding-hidden-layers/
# https://towardsdatascience.com/how-to-build-your-own-neural-network-from-scratch-in-python-68998a08e4f6
# https://towardsdatascience.com/coding-a-2-layer-neural-network-from-scratch-in-python-4dd022d19fd2

# Use numpy for exp and dot on matrices
import numpy as np

def sigmoid(x):
    ''' Sigmoid activation function 
    '''
    return 1 / ( 1 + np.exp(-x))

def dsigmoid(x):
    ''' actual derivative is sigmoid(x) * (1 - sigmoid(x))
        but the inputs are already assumed to be passed through the sigmoid function
    '''
    return x * (1 - x)
    
class Network:
''' Neural network with 2d input layer, 2d hidden layer, and scalar output.  
    This class is adapted to run on a set of data rather than one segment of data at a time
'''
    def __init__(self, data_in, actual):
        self.input = data_in
        
        # Weights between input and hidden layer
        self.hidden_weights = np.random.rand(self.input.shape[1], 2)
        # Weights between hidden and output layer
        self.output_weights = np.random.rand(2, 1)
        self.actual_results = actual
        
        # Bias between input and hidden layer
        self.hidden_bias = np.random.rand(1)
        # Bias between hidden and output layer
        self.output_bias = np.random.rand(1)
        self.output = np.zeros(self.actual_results.shape)
    
    def feedforward(self):
        self.hidden = sigmoid(np.dot(self.input, self.hidden_weights) + self.hidden_bias)
        self.output = sigmoid(np.dot(self.hidden, self.output_weights) + self.output_bias)
    
    def backprop(self):
        # Mean sum-of-squares used to calculate error.
        # Derivative of the output error with respect to weight
        d_output_weights = np.dot(self.hidden.T, 2 * np.subtract(self.actual_results, self.output) * dsigmoid(self.output))
        # Derivative of the output error with respect to weight fed back to hidden layer weights
        temp = np.dot(2 * np.subtract(self.actual_results, self.output) * dsigmoid(self.output), self.output_weights.T)
        self.hidden_weights += np.dot(self.input.T, temp * dsigmoid(self.hidden))
        self.output_weights += new_output_weights
        
        # Derivatives of the output error with respect to bias for output and hidden bias
        self.output_bias += .1 * np.sum(2 * np.subtract(self.actual_results, self.output) * dsigmoid(self.output))
        self.hidden_bias += .1 * np.sum(temp)
        
    def test(self, inputs):
        # Run feedforward code on custom inputs
        self.hidden = sigmoid(np.dot(inputs, self.hidden_weights) + self.hidden_bias)
        self.output = sigmoid(np.dot(self.hidden, self.output_weights) + self.output_bias)
        return self.output

# AND Gate
nn = Network(np.array([[0,1],[0,0],[1,0],[1,1]]), np.array([[0],[0],[0],[1]]))

for i in range(15000):
    nn.feedforward()
    nn.backprop()

print("AND Gate approximation after 15000 iterations:")
print("(0,0) -> " + str(nn.test([[0,0]])).strip("[]"))
print("(0,1) -> " + str(nn.test([[0,1]])).strip("[]"))
print("(1,0) -> " + str(nn.test([[1,0]])).strip("[]"))
print("(1,1) -> " + str(nn.test([[1,1]])).strip("[]"))

# OR Gate
nn = Network(np.array([[0,1],[0,0],[1,0],[1,1]]), np.array([[1],[0],[1],[1]]))

for i in range(15000):
    nn.feedforward()
    nn.backprop()

print("OR Gate approximation after 15000 iterations:")
print("(0,0) -> " + str(nn.test([[0,0]])).strip("[]"))
print("(0,1) -> " + str(nn.test([[0,1]])).strip("[]"))
print("(1,0) -> " + str(nn.test([[1,0]])).strip("[]"))
print("(1,1) -> " + str(nn.test([[1,1]])).strip("[]"))

# XOR Gate
nn = Network(np.array([[0,1],[0,0],[1,0],[1,1]]), np.array([[1],[0],[1],[0]]))

for i in range(15000):
    nn.feedforward()
    nn.backprop()

print("XOR Gate approximation after 15000 iterations:")
print("(0,0) -> " + str(nn.test([[0,0]])).strip("[]"))
print("(0,1) -> " + str(nn.test([[0,1]])).strip("[]"))
print("(1,0) -> " + str(nn.test([[1,0]])).strip("[]"))
print("(1,1) -> " + str(nn.test([[1,1]])).strip("[]"))
