import time
import sys
INFINITY = 9999999

class Coordinate:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __str__(self):
        return '(' + str(self.x) + ',' + str(self.y) + ')'

    def __hash__(self):
        return self.y * 1000 + self.x

    def above(self):
        return Coordinate(self.x, self.y-1)

    def below(self):
        return Coordinate(self.x, self.y+1)

    def left(self):
        return Coordinate(self.x-1, self.y)

    def right(self):
        return Coordinate(self.x+1, self.y)

    def adjacent(self):
        return [self.above(), self.below(), self.left(), self.right()]

class GraphNode:
    def __init__(self, coordinate):
        self.coordinate = coordinate
        self.reachable = []
        self.previous = None
        self.distance = INFINITY

    def addReachable(self, node):
        if node not in self.reachable:
            self.reachable.append(node)

def currentTime():
    return int(round(time.time() * 1000))

def buildGraph(regex):
    branches = []
    here = Coordinate(0, 0)
    graph = {}
    graph[here] = GraphNode(here)

    lastPrint = None

    for char in regex[1:-1]:
        if char == '(':
            branches.append(here)
        elif char == ')':
            branches = branches[:-1]
        elif char == '|':
            here = branches[-1]
        elif char in 'NSEW':
            theres = {'N': here.above(), 'S': here.below(), 'W': here.left(), 'E': here.right()}
            there = theres[char]
            if there not in graph.keys():
                graph[there] = GraphNode(there)
            hereNode = graph[here]
            thereNode = graph[there]
            hereNode.addReachable(thereNode)
            thereNode.addReachable(hereNode)
            here = there

            printGraph(graph, here)
            now = currentTime()
            if lastPrint:
                frameTime = now - lastPrint
                desiredFrameTime = 16
                sleepTime = max(min(desiredFrameTime - frameTime, desiredFrameTime), 0)
                time.sleep(sleepTime / 1000.0)
            lastPrint = now
        else:
            print('ERROR: Unkown char ' + char)

    return graph

def printGraph(graph, here):
    minX = -49
    maxX = 50
    minY = -51
    maxY = 48

    height = (maxY - minY) + 1
    width = (maxX - minX) + 1

    grid = []
    for y in range(0, (2 * height) + 1):
        grid.append([])
        for x in range(0, (2 * width) + 1):
            grid[y].append('#')

    for graphCoordinate, node in graph.items():
        zeroStartX = graphCoordinate.x - minX
        zeroStartY = graphCoordinate.y - minY
        gridCoordinate = Coordinate((2 * zeroStartX) + 1, (2 * zeroStartY) + 1)

        grid[gridCoordinate.y][gridCoordinate.x] = '.'
        if graphCoordinate == Coordinate(0, 0):
            grid[gridCoordinate.y][gridCoordinate.x] = 'X'
        if graphCoordinate == here:
            grid[gridCoordinate.y][gridCoordinate.x] = 'x'

        for otherGraphCoordinate, otherGridCoordinate, symbol in zip(graphCoordinate.adjacent(), gridCoordinate.adjacent(), ['-', '-', '|', '|']):
            if otherGraphCoordinate in graph.keys():
                otherNode = graph[otherGraphCoordinate]
                if otherNode in node.reachable:
                    grid[otherGridCoordinate.y][otherGridCoordinate.x] = symbol

    toPrint = '\n\n\n\n\n\n\n\n'
    for y in range(0, len(grid)):
        for x in range(0, len(grid[0])):
            toPrint += grid[y][x]
        toPrint += '\n'
    print(toPrint)

def visualize(filename):
    with open(filename) as file:
        regex = file.read()

    graph = buildGraph(regex)

visualize('input1.txt')