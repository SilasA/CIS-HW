#!/usr/bin python3

# Maze represented by dictionary with key: Node; value: Array of children
# The way I implemented the maze turns out to make the graph a binary tree.
graph = {
        "Start" : ["A", "B"],
        "A": ["C", "D"],
        "B": [],
        "C": [],
        "D": ["E", "F"],
        "E": ["G", "H"],
        "F": [],
        "G": [],
        "H": ["I", "J"],
        "I": [],
        "J": ["K", "Goal"],
        "K": [],
        "Goal": []
        }

def bfs(graph, node, target):
    '''Breadth-First Search implementation.
    This implementation can traverse any graph even though the maze is a tree.
    '''
    queue = [node]
    visited = []
    while queue:
        n = queue.pop(0)
        print(n, end=" ")
        if (n == target):
            return n 

        for child in graph[n]:
            if (child not in visited):
                queue.append(child)
                visited.append(child)
    return 0

def dfs(graph, node, target, visited=[]):
    '''Depth-First Search implementation.
    This implementation can traverse any graph even though the maze is a tree
    '''
    print(node, end=" ")
    if (node == target):
        return [node]
    elif (len(graph[node]) > 0):
        for n in graph[node]:
            if (n not in visited):
                visited.append(n)
                ret = dfs(graph, n, target, visited)
                if (ret != 0):
                    ret.append(node)
                    return ret
    return 0


print("BFS Search: ", end="")
l = bfs(graph, "Start", "Goal")
print()
print("-------------------------------------------------------------")
print("DFS Search: ", end="")
l = dfs(graph, "Start", "Goal")
l.reverse()
print("\nResult Path: ", end="")
for n in l:
    print(n + " ", end="")
print()

