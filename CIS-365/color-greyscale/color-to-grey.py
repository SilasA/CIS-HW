# !/usr/bin
# This script generates the greyscale historgram of a color image
# this should work on all common image formats (jpg, png, etc...)
# Author: Silas Agnew

# uses OpenCV install with: pip install opencv-python
# uses matlibplot install with: pip install matlibplot

# References
# https://www.geeksforgeeks.org/opencv-python-program-analyze-image-using-histogram/
# https://docs.opencv.org/master/

import cv2
import sys
from matplotlib import pyplot as plot

if (len(sys.argv) < 2):
    print("Input an image filepath")
    exit(1)

# Convert to greyscale
image = cv2.imread(sys.argv[1])
greyscale = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# Display original and greyscale image
cv2.imshow("Color", image)
cv2.imshow("Grey", greyscale)

# calculate the historgram of the greyscale intensity and display it
hist = cv2.calcHist([greyscale], [0], None, [256], [0, 256])
plot.plot(hist)
plot.show()

# Wait for input to close images
cv2.waitKey()
cv2.destroyAllWindows()
