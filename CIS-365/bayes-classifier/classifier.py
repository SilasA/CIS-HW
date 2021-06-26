#!/usr/bin python3
# Using petal length to classify iris species

# Usage:
# use the following to train the classifier
# classifier.py <training data csv> -t
#
# use the following to classify data with training results
# classifier.py <data csv> -c

import sys
import csv
import math

def read_data(filename):
    data = []
    with open(filename, newline='\n') as file:
        filedata = csv.reader(file, delimiter=',')
        for row in filedata:
            if (row != []):
                data.append(row)
    return data # cut off empty element for some reason

def write_training_results(results):
    with open("results.data", "w") as out:
        for row in results:
            out.write(", ".join(map(str, row)))
            out.write("\n")

def gaussian_density(x, mean, stddev):
    return 1 / (stddev * math.sqrt(2 * math.pi)) * math.exp(-(((float(x) - mean) ** 2) / (2 * stddev ** 2)))

def average(data, col):
    sum = 0
    for row in data:
        sum += float(row[col])
    return sum / len(data)

def standard_deviation(data, col, average):
    sumsq = 0
    for row in data:
        sumsq += (float(row[col]) - average) ** 2
    return math.sqrt(sumsq / (len(data) - 1))

def calc_stats(data):
    # divide into class
    cl = {}
    for row in data:
        if (row[-1] not in  cl.keys()):
            cl[row[-1]] = []
        cl[row[-1]].append(row)
    
    stats = []
    for key in cl:
        stat = []
        avg = average(cl[key], 2)
        stat.append(avg)
        stat.append(standard_deviation(cl[key], 2, avg))
        stat.append(key)
        stats.append(stat)
    return stats

# Classifying
def get_probabilities(entry, stats):
    probabilities = {}
    for row in stats:
        probabilities[row[-1]] = gaussian_density(entry[2], float(row[0]), float(row[1]))
    # Sort greatest to least prob
    # this python-ism is from https://stackoverflow.com/questions/613183/how-do-i-sort-a-dictionary-by-value
    return { k: v for k, v in sorted(probabilities.items(), key=lambda item: item[1], reverse=True) }

if (len(sys.argv) < 3):
    print("Expects mode argument")
    exit()

data = read_data(sys.argv[1])

if (sys.argv[2] == "-c"):
    result = {}
    total_correct = 0
    total_incorrect = 0

    training_data = read_data("results.data")
    
    for entry in data:
        probs = get_probabilities(entry, training_data)
        if (entry[-1] not in result):
            result[entry[-1]] = [0, 0]
        if (list(probs.keys())[0].strip(" ") == entry[-1]):
            result[entry[-1]][0] += 1
            total_correct += 1
        else:
            result[entry[-1]][1] += 1
            total_incorrect += 1
        
    for r in result:
        print(r + ":")
        print("Correct: " + str(result[r][0]) + " Incorrect: " + str(result[r][1]))
        print("Accuracy: " + str(result[r][0] / (result[r][0] + result[r][1])))
        print()
        
    accuracy = total_correct / len(data)
    print("Overall Accuracy: " + str(accuracy))
    
elif (sys.argv[2] == "-t"):
    stats = calc_stats(data)
    write_training_results(stats)
