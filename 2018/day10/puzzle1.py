import re

class Vector:
	def __init__(self, x, y):
		self.x = x
		self.y = y

class Point:
	def __init__(self, position, velocity):
		self.position = position
		self.velocity = velocity

def printPoints(points):
	minX = min([point.position.x for point in points])
	minY = min([point.position.y for point in points])
	maxX = max([point.position.x for point in points])
	maxY = max([point.position.y for point in points])

	width = (maxX - minX) + 1
	height = (maxY - minY) + 1

	grid = []
	for y in range(0, height):
		grid.append([])
		for x in range(0, width):
			grid[y].append('.')

	for point in points:
		correctedX = point.position.x - minX
		correctedY = point.position.y - minY
		grid[correctedY][correctedX] = '#'

	for y in range(0, height):
		for x in range(0, width):
			print(grid[y][x], end='')
		print('')

def areaAtTime(points, time):
	newPositions = []
	for point in points:
		x = point.position.x + (time * point.velocity.x)
		y = point.position.y + (time * point.velocity.y)
		newPositions.append(Vector(x, y))
	minX = min([position.x for position in newPositions])
	minY = min([position.y for position in newPositions])
	maxX = max([position.x for position in newPositions])
	maxY = max([position.y for position in newPositions])
	return (maxX - minX) * (maxY - minY)

def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	points = []
	for line in lines:
		matches = re.search('position=<\\s*(\\-*\\d+),\\s*(\\-*\\d+)> velocity=<\\s*(\\-*\\d+),\\s*(\\-*\\d+)>', line)
		position = Vector(int(matches.group(1)), int(matches.group(2)))
		velocity = Vector(int(matches.group(3)), int(matches.group(4)))
		points.append(Point(position, velocity))

	lastArea = areaAtTime(points, 0)
	time = 1
	while True:
		area = areaAtTime(points, time)
		if area > lastArea:
			time -= 1
			break
		lastArea = area
		time += 1

	for point in points:
		point.position.x += time * point.velocity.x
		point.position.y += time * point.velocity.y

	print(filename + ': ')
	print(str(time) + ' seconds')
	printPoints(points)
	print('')


solve('exampleinput1.txt')
solve('input1.txt')