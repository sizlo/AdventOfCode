import copy

INFINITY = 9999999

COLOURS = [
    '\033[31m',
    '\033[32m',
    '\033[33m',
    '\033[34m',
    '\033[35m',
    '\033[36m',
    '\033[37m',
    '\033[90m',
    '\033[91m',
    '\033[92m'   
]
END_COLOUR = '\033[39m'
nextColour = 0

PRINT = False
INTERACTIVE = False

class Coordinate:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __lt__(self, other):
        if self.y == other.y:
            return self.x < other.x
        return self.y < other.y

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __hash__(self):
        return self.y * 1000 + self.x

    def __str__(self):
        return '(' + str(self.x) + ', ' + str(self.y) + ')'

class TargetSpace:
    def __init__(self, coordinate, colour):
        self.coordinate = coordinate
        self.colour = colour

class Unit:
    def __init__(self, x, y, attackPower, enemies):
        global nextColour
        self.position = Coordinate(x, y)
        self.enemies = enemies
        self.attackPower = attackPower
        self.health = 200
        self.colour = COLOURS[nextColour]
        nextColour = (nextColour + 1) % len(COLOURS)

    def __lt__(self, other):
        return self.position < other.position

    def __str__(self):
        return colour(str(self.position) + ' ' + str(self.health), self.colour)

    def tick(self, spaces):
        if len(self.aliveEnemies()) == 0:
            return None, True
        
        targetSpaces = set()
        for enemy in self.aliveEnemies():
            for coordinate in adjacentCoordinates(enemy.position):
                if isEmpty(spaces, coordinate) or coordinate == self.position:
                    targetSpaces.add(TargetSpace(coordinate, enemy.colour))

        if len(targetSpaces) == 0:
            return None, None

        if self.position not in [targetSpace.coordinate for targetSpace in targetSpaces]:
            self.move(targetSpaces, spaces)
        
        return self.attack(), None

    def attack(self):
        lowestHealthEnemy = None
        for coordinate in adjacentCoordinates(self.position):
            for enemy in self.aliveEnemies():
                if enemy.position == coordinate:
                    if lowestHealthEnemy == None or enemy.health < lowestHealthEnemy.health or (enemy.health == lowestHealthEnemy.health and enemy < lowestHealthEnemy):
                        lowestHealthEnemy = enemy
        if lowestHealthEnemy:
            lowestHealthEnemy.health -= self.attackPower

        return lowestHealthEnemy

    def move(self, targetSpaces, spaces):
        lowestDistanceToTarget = INFINITY
        bestNextSpace = None
        bestNextSpaceColour = None
        for targetSpace in targetSpaces:
            distanceToTarget, nextSpace = findDistanceToTargetAndNextSpace(self.position, targetSpace.coordinate, spaces)
            if distanceToTarget < lowestDistanceToTarget or (distanceToTarget == lowestDistanceToTarget and nextSpace < bestNextSpace):
                lowestDistanceToTarget = distanceToTarget
                bestNextSpace = nextSpace
                bestNextSpaceColour = targetSpace.colour

        if bestNextSpace:
            unitChar = spaces[self.position.y][self.position.x]
            spaces[self.position.y][self.position.x] = '.'
            self.position.y = bestNextSpace.y
            self.position.x = bestNextSpace.x
            spaces[self.position.y][self.position.x] = unitChar

    def aliveEnemies(self):
        return [enemy for enemy in self.enemies if enemy.health > 0]

class Map:
    def __init__(self, mapString, elfAttackPower):
        mapLines = mapString.splitlines()

        self.height = len(mapLines)
        self.width = len(mapLines[0])

        self.units = []
        self.goblins = []
        self.elves = []

        self.spaces = []
        for y in range(0, self.height):
            self.spaces.append([])
            for x in range(0, self.width):
                mapChar = mapLines[y][x]
                if mapChar == 'G':
                    goblin = Unit(x, y, 3, self.elves)
                    self.units.append(goblin)
                    self.goblins.append(goblin)
                if mapChar == 'E':
                    elf = Unit(x, y, elfAttackPower, self.goblins)
                    self.units.append(elf)
                    self.elves.append(elf)
                self.spaces[y].append(mapChar)

    def __str__(self):
        result = ''
        for y in range(0, self.height):
            for x in range(0, self.width):
                spaceString = self.spaces[y][x]
                
                if spaceString == 'G' or spaceString == 'E':
                    for unit in self.aliveUnits():
                        if unit.position == Coordinate(x, y):
                            spaceString = colour(spaceString, unit.colour)

                result += spaceString
            result += '\n'

        for unit in self.aliveUnits():
            result += str(unit) + '\n'

        return result

    def tick(self):
        unitQueue = self.units.copy()
        while len(unitQueue) > 0:
            unitQueue.sort()
            unit = unitQueue[0]
            unitQueue = unitQueue[1:]
            
            if unit.health <= 0:
                continue

            damagedUnit, combatEnded = unit.tick(self.spaces)

            if damagedUnit and damagedUnit.health <= 0:
                self.spaces[damagedUnit.position.y][damagedUnit.position.x] = '.'

            if combatEnded:
                return True

        return False

    def aliveUnits(self):
        return [unit for unit in self.units if unit.health > 0]

class GraphNode:
    def __init__(self, coordinate):
        self.coordinate = coordinate
        self.reachable = []
        self.distance = INFINITY
        self.previous = None

def isEmpty(spaces, coordinate):
    return spaces[coordinate.y][coordinate.x] == '.'

def colour(string, colour):
    return colour + string + END_COLOUR

def adjacentCoordinates(coordinate):
    return [
        Coordinate(coordinate.x, coordinate.y-1),
        Coordinate(coordinate.x, coordinate.y+1),
        Coordinate(coordinate.x-1, coordinate.y),
        Coordinate(coordinate.x+1, coordinate.y)
    ]

def addNode(nodes, spaces, nodeCoordinate, currentCoordinate):
    if nodeCoordinate in nodes.keys():
        return nodes, nodes[nodeCoordinate]
    
    node = GraphNode(nodeCoordinate)
    nodes[nodeCoordinate] = node

    for coordinate in adjacentCoordinates(nodeCoordinate):
        if isEmpty(spaces, coordinate) or coordinate == currentCoordinate:
            nodes, addedNode = addNode(nodes, spaces, coordinate, currentCoordinate)
            node.reachable.append(addedNode)

    return nodes, node

def createGraph(spaces, currentCoordinate, targetCoordinate):
    height = len(spaces)
    width = len(spaces[0])
    nodes = {}
    nodes, targetNode = addNode(nodes, spaces, targetCoordinate, currentCoordinate)
    return nodes

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

def findDistanceToTargetAndNextSpace(currentCoordinate, targetCoordinate, spaces):
    graph = createGraph(spaces, currentCoordinate, targetCoordinate)
    graph = dijkstras(targetCoordinate, graph)

    adjacentNodes = []

    for coordinate in adjacentCoordinates(currentCoordinate):
        if coordinate in graph.keys():
            adjacentNodes.append(graph[coordinate])

    bestNode = None
    for node in adjacentNodes:
        if bestNode == None or node.distance < bestNode.distance or (node.distance == bestNode.distance and node.coordinate < bestNode.coordinate):
            bestNode = node

    if currentCoordinate in graph.keys():
        return graph[currentCoordinate].distance, bestNode.coordinate
    return INFINITY + 1, None

def tryAttackPower(mapString, attackPower):
    theMap = Map(mapString, attackPower)
    roundNumber = 0
    
    while True:
        if PRINT or INTERACTIVE: 
            print('Round: ' + str(roundNumber))
            print(theMap)
            if INTERACTIVE and input('next? > ') == 'n':
                break

        roundNumber += 1
        combatEnded = theMap.tick()
        
        for elf in theMap.elves:
            if elf.health <= 3:
                return False, 0

        if combatEnded:
            lastFullRound = roundNumber - 1
            healthSum = 0
            for unit in theMap.aliveUnits():
                healthSum += unit.health
            solution = lastFullRound * healthSum

            if PRINT:
                print('Final state. Last full round: ' + str(lastFullRound))
                print(theMap)

            return True, solution

def solve(filename):
    with open(filename) as file:
        mapString = file.read()

    currentAttackPower = 2
    highestFailedAttackPower = -1
    lowestSuccessfulAttackPower = INFINITY
    lowestSuccessfulSolution = INFINITY
    
    while True:

        if lowestSuccessfulAttackPower == INFINITY:
            currentAttackPower *= 2
        else:
            difference = lowestSuccessfulAttackPower - highestFailedAttackPower
            currentAttackPower = highestFailedAttackPower + (difference // 2)

        success, solution = tryAttackPower(mapString, currentAttackPower)
        
        if success:
            lowestSuccessfulAttackPower = currentAttackPower
            lowestSuccessfulSolution = solution
        else:
            highestFailedAttackPower = currentAttackPower

        if highestFailedAttackPower == lowestSuccessfulAttackPower - 1:
            print(filename + ': ' + str(lowestSuccessfulAttackPower) + ' ' + str(lowestSuccessfulSolution))
            return lowestSuccessfulSolution

def test(filename, expected):
    actual = solve(filename)
    if actual != expected:
        print('ERROR: expect ' + str(expected) + ' but got ' + str(actual))

test('examplecombatinput1.txt', 4988)
test('examplecombatinput3.txt', 31284)
test('examplecombatinput4.txt', 3478)
test('examplecombatinput5.txt', 6474)
test('examplecombatinput6.txt', 1140)

solve('input1.txt')
