import re

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

	def addReachable(self, node):
		if node not in self.reachable:
			self.reachable.append(node)

def generateRoutes(regex):
	toReplace = regex[1:-1]
	token = 0
	replacements = []

	while ('(') in toReplace:
		print('Finding replacement for token ' + str(token))
		matches = re.search('(\\([NSEWT|\\d]*\\))', toReplace)
		firstMatch = matches.group(1)
		options = firstMatch[1:-1].split('|')
		replacements.append(options)
		toReplace = toReplace.replace(firstMatch, 'T' + str(token), 1)
		token += 1

	routes = {toReplace}
	while token  > 0:
		token -= 1
		print('Performing replacement for token ' + str(token))
		newRoutes = set()
		for route in routes:
			for replacement in replacements[token]:
				newRoutes.add(route.replace('T' + str(token), replacement))
		routes = newRoutes

	for route in routes:
		print(route)

	return list(routes)

def buildGraph(routes):
	graph = {}

	for route in routes:
		here = Coordinate(0, 0)
		if here not in graph.keys():
			graph[here] = GraphNode(here)

		for direction in route:
			if direction == 'N':
				there = here.above()
			elif direction == 'S':
				there = here.below()
			elif direction == 'W':
				there = here.left()
			elif direction == 'E':
				there = here.right()
			
			if there not in graph.keys():
				graph[there] = GraphNode(there)

			hereNode = graph[here]
			thereNode = graph[there]
			hereNode.addReachable(thereNode)
			thereNode.addReachable(hereNode)

			here = there

	return graph

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

def solve(filename):
	with open(filename) as file:
		regex = file.read()

	print(filename)

	graph = buildGraph(regex)

	if not verifyGraph(graph):
		return

	printGraph(graph)

solve('exampleinput1.txt')
solve('exampleinput2.txt')
solve('exampleinput3.txt')
solve('exampleinput4.txt')
solve('exampleinput5.txt')
solve('input1.txt')