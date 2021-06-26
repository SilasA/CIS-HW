#!/usr/bin python3

class Node:
    ''' Class for n-ary tree nodes
    '''
    def __init__(self, label, score, children):
        self.label = label
        self.score = score
        self.children = children


def minPath(paths):
    ''' Find the minimum scored path (current list of nodes)
        paths - list of paths evaluated so far in the minimax
    '''
    min = paths[0]
    for p in paths:
        if (p[-1].score < min[-1].score):
            min = p
    return min

def maxPath(paths):
    ''' Find the maximum scored path (current list of nodes)
        paths - list of paths evaluated so far in the minimax
    '''
    max = paths[0]
    for p in paths:
        if (p[-1].score > max[-1].score):
            max = p
    return max

def min_max(node, depth = 0):
    ''' Recursively evaluate game tree using the minimax algorithm
        Takes current "root" node and the optional depth (for internal use)
    '''
    # Base case
    if (len(node.children) <= 0):
        return [node]
    
    # Evaluate tree for each child of current node
    results = []
    for child in node.children:
        n = min_max(child, depth + 1)
        results.append(n)
    
    # Find max or min path based on depth level
    if (depth % 2 == 0):
        max = maxPath(results)
        node.score = max[-1].score
        max.append(node);
        return max
    else:
        min = minPath(results)
        node.score = min[-1].score
        min.append(node);
        return min

# Make tree from CH4
root = Node("A", -1, [
        Node("B", -1, [
            Node("E", -1, [
                Node("K", 2, []),
                Node("L", 1, []),
                Node("M", 3, [])
            ]),
            Node("F", -1, [
                Node("N", 5, []),
                Node("O", 4, [])
            ])
        ]),
        Node("C", -1, [
            Node("G", -1, [
                Node("P", 7, [])
            ]),
            Node("H", -1, [
                Node("Q", 6, []),
                Node("R", 8, [])
            ])
        ]),
        Node("D", -1, [
            Node("I", -1, [
                Node("S", 9, []),
                Node("T", 10, [])
            ]),
            Node("J", -1, [
                Node("U", 12, []),
                Node("V", 11, [])
            ])
        ])
    ])

path = min_max(root)

print("Game Tree Evaluation (top to bottom): ", end="")
path.reverse()
for step in path:
    print(step.label + " ", end="")
print()
print("Path score: " + str(path[0].score))
