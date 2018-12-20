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

def buildGraph(regex):
    branches = []
    here = Coordinate(0, 0)
    graph = {}
    graph[here] = GraphNode(here)

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
        else:
            print('ERROR: Unkown char ' + char)

    return graph

def verifyGraph(graph):
    good = True

    for coordinate, node in graph.items():
        for otherCoordinate in coordinate.adjacent():
            if otherCoordinate not in graph.keys():
                continue
            otherNode = graph[otherCoordinate]
            if otherNode in node.reachable and node not in otherNode.reachable:
                print('ERROR: node with coordinate ' + str(node.coordinate) + ' can reach other node with coordinate ' + str(otherCoordinate) + ' but not vice versa')
                good = False

    return good

def printGraph(graph):
    minX = min([coordinate.x for coordinate in graph.keys()])
    maxX = max([coordinate.x for coordinate in graph.keys()])
    minY = min([coordinate.y for coordinate in graph.keys()])
    maxY = max([coordinate.y for coordinate in graph.keys()])

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

        for otherGraphCoordinate, otherGridCoordinate, symbol in zip(graphCoordinate.adjacent(), gridCoordinate.adjacent(), ['-', '-', '|', '|']):
            if otherGraphCoordinate in graph.keys():
                otherNode = graph[otherGraphCoordinate]
                if otherNode in node.reachable:
                    grid[otherGridCoordinate.y][otherGridCoordinate.x] = symbol

    for y in range(0, len(grid)):
        for x in range(0, len(grid[0])):
            print(grid[y][x], end='')
        print('')

def removeSmallestDistance(queue):
    smallestIndex = None
    smallestDistance = INFINITY
    for index in range(0, len(queue)):
        node = queue[index]
        if node.distance < smallestDistance:
            smallestDistance = node.distance
            smallestIndex = index
    node = queue[smallestIndex]
    return node, queue[:smallestIndex] + queue[smallestIndex + 1:]

def dijkstras(sourceCoordinate, graph):
    sourceNode = graph[sourceCoordinate]

    queue = []
    for coordinate, node in graph.items():
        queue.append(node)

    sourceNode.distance = 0
    while len(queue) > 0:
        u, queue = removeSmallestDistance(queue)

        for v in u.reachable:
            alt = u.distance + 1
            if alt < v.distance:
                v.distance = alt
                v.previous = u

    return graph

def findFurthestRoom(graph):
    furthestNode = graph[Coordinate(0, 0)]
    for coordinate, node in graph.items():
        if node.distance > furthestNode.distance:
            furthestNode = node
    return furthestNode

def findRoomsThatRequireAtLeastXDoors(graph, x):
    rooms = []
    for coordinate, node in graph.items():
        if node.distance >= x:
            rooms.append(node)
    return rooms

def solve(filename):
    with open(filename) as file:
        regex = file.read()

    print(filename)

    graph = buildGraph(regex)

    if not verifyGraph(graph):
        return

    graph = dijkstras(Coordinate(0, 0), graph)

    furthestRoom = findFurthestRoom(graph)
    roomsThatRequireAtLeast1000Doors = findRoomsThatRequireAtLeastXDoors(graph, 1000)

    print(filename + ': part 1 - ' + str(furthestRoom.distance) + ' part 2 - ' + str(len(roomsThatRequireAtLeast1000Doors)))

solve('exampleinput1.txt')
solve('exampleinput2.txt')
solve('exampleinput3.txt')
solve('exampleinput4.txt')
solve('exampleinput5.txt')
solve('input1.txt')