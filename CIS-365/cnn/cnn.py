#!/usr/bin python3
# Code used throughout as guidline:
# https://machinelearningmastery.com/how-to-develop-a-convolutional-neural-network-from-scratch-for-mnist-handwritten-digit-classification/

from keras.datasets import mnist
from keras.utils import to_categorical
from keras.models import Sequential, load_model
from keras.layers import Conv2D, MaxPooling2D, Dense, Flatten, BatchNormalization
from keras.optimizers import SGD
from numpy import mean, std
from matplotlib import pyplot
from sklearn.model_selection import KFold
import sys

def prep_pixels(train, test):
    ''' Normalize the dataset for the cnn
    '''
    train_norm = train.astype("float32")
    test_norm = test.astype("float32")
    train_norm /= 255.0
    test_norm /= 255.0
    return train_norm, test_norm

def define_model():
    ''' Create model
    '''
    model = Sequential()
    model.add(Conv2D(32, (3, 3), activation="relu", kernel_initializer="he_uniform", input_shape=(28, 28, 1)))
    model.add(BatchNormalization())
    model.add(MaxPooling2D((2, 2)))
    model.add(Conv2D(64, (3, 3), activation="relu", kernel_initializer="he_uniform", input_shape=(28, 28, 1)))
    model.add(Conv2D(64, (3, 3), activation="relu", kernel_initializer="he_uniform", input_shape=(28, 28, 1)))
    model.add(MaxPooling2D((2, 2)))
    model.add(Flatten())
    model.add(Dense(100, activation="relu", kernel_initializer="he_uniform"))
    model.add(Dense(10, activation="softmax"))
    opt = SGD(lr=0.01, momentum=.9)
    model.compile(optimizer=opt, loss="categorical_crossentropy", metrics=["accuracy"])
    return model

def evaluate_model(dataX, dataY, n_folds=5):
    ''' Train model on data set and test accuracy and loss across folds
    '''
    scores, histories = list(), list()
    kfold = KFold(n_folds, shuffle=True, random_state=1)
    models = []
    for train_ix, test_ix in kfold.split(dataX):
        model = define_model()
        trainX, trainY, testX, testY = dataX[train_ix], dataY[train_ix], dataX[test_ix], dataY[test_ix]
        history = model.fit(trainX, trainY, epochs=10, batch_size=32, validation_data=(testX, testY), verbose=0)
        losses, accuracy = model.evaluate(testX, testY, verbose=0)
        print("Accuracy: %.3f" % (accuracy * 100.0))
        scores.append(accuracy)
        histories.append(history)
        models.append(model)
    return scores, histories, models[scores.index(max(scores))]

def summarize_diag(histories):
    ''' Create plots of accuracy and losses.
    '''
    for i in range(len(histories)):
        pyplot.subplot(2, 1, 1)
        pyplot.title("Cross Entropy Loss")
        pyplot.plot(histories[i].history["loss"], color="blue", label="train")
        pyplot.plot(histories[i].history["val_loss"], color="orange", label="test")
        pyplot.subplot(2, 1, 2)
        pyplot.title("Classification Accuracy")
        pyplot.plot(histories[i].history["accuracy"], color="blue", label="train")
        pyplot.plot(histories[i].history["val_accuracy"], color="orange", label="test")
    pyplot.show()

def summarize_acc(scores):
    ''' Compute and display the mean scores and std dev.
    '''
    print("Accuracy: mean=%.3f std=%.3f, n=%d" % (mean(scores) * 100, std(scores) * 100, len(scores)))
    pyplot.boxplot(scores)
    pyplot.show()

# Check args
if (len(sys.argv) < 3):
    print("Improper args: cnn [options [info]]\n\t-t [model file name] train the model\n\t-e [model file name] evaluate and use the model")
    exit()

# Load in dataset
(trainX, trainY), (testX, testY) = mnist.load_data()
trainX = trainX.reshape((trainX.shape[0], 28, 28, 1))
trainY = to_categorical(trainY)
testX = testX.reshape((testX.shape[0], 28, 28, 1))
testY = to_categorical(testY)

trainX, testX = prep_pixels(trainX, testX)

if (sys.argv[1] == "-t"):
    scores, histories, max_perf_model = evaluate_model(trainX, trainY)
    summarize_diag(histories)
    summarize_acc(scores)
    max_perf_model.save(sys.argv[2])
elif (sys.argv[1] == "-e"):
    model = load_model(sys.argv[2])
    loss, acc = model.evaluate(testX, testY, verbose=0)
    print("Accuracy: %.3f" % (acc * 100.0))
else:
    print("Improper arguments")
