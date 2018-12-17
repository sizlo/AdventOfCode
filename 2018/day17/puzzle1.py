import re
import resource
import sys

SAND = 1
CLAY = 2
FLOWING_WATER = 3
SETTLED_WATER = 4

FLOWABLE_ON_MATERIALS = [CLAY, SETTLED_WATER]

PRINT = False

class Coordinate:
	def __init__(self, x, y):
		self.x = x
		self.y = y

	def __eq__(self, other):
		return self.x == other.x and self.y == other.y

	def below(self):
		return Coordinate(self.x, self.y+1)

	def left(self):
		return Coordinate(self.x-1, self.y)

	def right(self):
		return Coordinate(self.x+1, self.y)

class Map:
	def __init__(self, clayPoints):
		minX = min(c.x for c in clayPoints)
		maxX = max(c.x for c in clayPoints)
		minY = min(c.y for c in clayPoints)
		maxY = max(c.y for c in clayPoints)

		self.width = (maxX - minX) + 3
		self.height = (maxY - minY) + 1

		self.grid = []
		for y in range(0, self.height):
			self.grid.append([])
			for x in range(0, self.width):
				self.grid[y].append(SAND)

		for point in clayPoints:
			adjustedX = (point.x - minX) + 1
			adjustedY = point.y - minY
			self.grid[adjustedY][adjustedX] = CLAY

		self.tapX = (500 - minX) + 1

	def __str__(self):
		symbols = {SAND: '.', CLAY: '#', FLOWING_WATER: '|', SETTLED_WATER: '~'}
		result = ''

		for x in range(0, self.width):
			if x == self.tapX:
				result += '+'
			else:
				result += ' '

		result += '\n'

		for y in range(0, self.height):
			for x in range(0, self.width):
				result += symbols[self.grid[y][x]]
			result += '\n'

		return result

	def outOfBounds(self, coordinate):
		return coordinate.x < 0 or coordinate.x >= self.width or coordinate.y < 0 or coordinate.y >= self.height

	def setMaterialAt(self, coordinate, material):
		self.grid[coordinate.y][coordinate.x] = material

	def materialAt(self, coordinate):
		if self.outOfBounds(coordinate):
			return SAND
		return self.grid[coordinate.y][coordinate.x]


	def flow(self, here):
		if here is None:
			here = Coordinate(self.tapX, 0)

		if self.outOfBounds(here):
			return

		self.setMaterialAt(here, FLOWING_WATER)

		if self.materialAt(here.below()) == SAND:
			self.flow(here.below())

		leftmostPoint = here
		while self.materialAt(leftmostPoint.below()) in FLOWABLE_ON_MATERIALS and self.materialAt(leftmostPoint.left()) == SAND:
			leftmostPoint = leftmostPoint.left()
			self.setMaterialAt(leftmostPoint, FLOWING_WATER)
			if self.materialAt(leftmostPoint.below()) == SAND:
				self.flow(leftmostPoint.below())

		rightMostPoint = here
		while self.materialAt(rightMostPoint.below()) in FLOWABLE_ON_MATERIALS and self.materialAt(rightMostPoint.right()) == SAND:
			rightMostPoint = rightMostPoint.right()
			self.setMaterialAt(rightMostPoint, FLOWING_WATER)
			if self.materialAt(rightMostPoint.below()) == SAND:
				self.flow(rightMostPoint.below())

		canSettle = self.materialAt(leftmostPoint.left()) == CLAY and self.materialAt(rightMostPoint.right()) == CLAY
		settleCheckPoint = leftmostPoint
		while settleCheckPoint != rightMostPoint.right() and canSettle:
			if self.materialAt(settleCheckPoint.below()) not in FLOWABLE_ON_MATERIALS:
				canSettle = False
			settleCheckPoint = settleCheckPoint.right()

		if canSettle:
			doSettlePoint = leftmostPoint
			while doSettlePoint != rightMostPoint.right():
				self.setMaterialAt(doSettlePoint, SETTLED_WATER)
				doSettlePoint = doSettlePoint.right()

	def countMaterials(self, materials):
		total = 0
		for y in range(0, self.height):
			for x in range(0, self.width):
				if self.materialAt(Coordinate(x, y)) in materials:
					total += 1
		return total

def getClayPoints(clayLines):
	clayPoints = []

	for line in clayLines:
		matches = re.search('([xy])=(\\d+), [xy]=(\\d+)\\.\\.(\\d+)', line)
		
		horizontal = matches.group(1) == 'y'
		fixedPoint = int(matches.group(2))
		rangeStart = int(matches.group(3))
		rangeEnd = int(matches.group(4))

		for rangePoint in range(rangeStart, rangeEnd+1):
			if horizontal:
				clayPoints.append(Coordinate(rangePoint, fixedPoint))
			else:
				clayPoints.append(Coordinate(fixedPoint, rangePoint))

	return clayPoints

def createMap(clayLines):
	clayPoints = getClayPoints(clayLines)
	return Map(clayPoints)

def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	theMap = createMap(lines)
	
	if PRINT:
		print(theMap)

	theMap.flow(None)

	if PRINT:
		print(theMap)

	print(filename + ' part 1 : ' + str(theMap.countMaterials([FLOWING_WATER, SETTLED_WATER])))
	print(filename + ' part 2 : ' + str(theMap.countMaterials([SETTLED_WATER])))

sys.setrecursionlimit(10**6)

solve('exampleinput1.txt')
solve('input1.txt')


