OPEN_GROUND = 1
TREES = 2
LUMBERYARD = 3

SYMBOLS = {OPEN_GROUND: '.', TREES: '|', LUMBERYARD: '#'}
MATERIALS = {'.': OPEN_GROUND, '|': TREES, '#': LUMBERYARD}

class Coordinate:
	def __init__(self, x, y):
		self.x = x
		self.y = y

	def __eq__(self, other):
		return self.x == other.x and self.y == other.y

	def __str__(self):
		return '(' + str(self.x) + ',' + str(self.y) + ')'

	def above(self):
		return Coordinate(self.x, self.y-1)

	def below(self):
		return Coordinate(self.x, self.y+1)

	def left(self):
		return Coordinate(self.x-1, self.y)

	def right(self):
		return Coordinate(self.x+1, self.y)

	def adjacent(self):
		return [self.above().left(), self.above(), self.above().right(), self.left(), self.right(), self.below().left(), self.below(), self.below().right()]


class Map:
	def __init__(self, mapString):
		mapLines = mapString.splitlines()
		
		self.height = len(mapLines)
		self.width = len(mapLines[0])

		self.grid = []
		for y in range(0, self.height):
			self.grid.append([])
			for x in range(0, self.width):
				self.grid[y].append(MATERIALS[mapLines[y][x]])

	def __str__(self):
		result = ''
		for y in range(0, self.height):
			for x in range(0, self.width):
				result += SYMBOLS[self.grid[y][x]]
			result += '\n'
		return result

	def materialAt(self, coordinate):
		if coordinate.x < 0 or coordinate.x >= self.width or coordinate.y < 0 or coordinate.y >= self.height:
			return None
		return self.grid[coordinate.y][coordinate.x]

	def surroundings(self, coordinate):
		counts = {OPEN_GROUND: 0, TREES: 0, LUMBERYARD: 0}
		for adjacentSpot in coordinate.adjacent():
			material = self.materialAt(adjacentSpot)
			if material:
				counts[material] += 1
		return counts

	def tick(self):
		newGrid = []
		for y in range(0, self.height):
			newGrid.append([])
			for x in range(0, self.width):
				newGrid[y].append(OPEN_GROUND)

		for y in range(0, self.height):
			for x in range(0, self.width):
				here = Coordinate(x, y)
				materialHere = self.materialAt(here)
				surroundingCounts = self.surroundings(here)

				if materialHere == OPEN_GROUND:
					if surroundingCounts[TREES] >= 3:
						newGrid[y][x] = TREES
					else:
						newGrid[y][x] = OPEN_GROUND
				elif materialHere == TREES:
					if surroundingCounts[LUMBERYARD] >= 3:
						newGrid[y][x] = LUMBERYARD
					else:
						newGrid[y][x] = TREES
				elif materialHere == LUMBERYARD:
					if surroundingCounts[LUMBERYARD] >= 1 and surroundingCounts[TREES] >= 1:
						newGrid[y][x] = LUMBERYARD
					else:
						newGrid[y][x] = OPEN_GROUND

		self.grid = newGrid

	def resourceValue(self):
		counts = {OPEN_GROUND: 0, TREES: 0, LUMBERYARD: 0}
		for y in range(0, self.height):
			for x in range(0, self.width):
				counts[self.grid[y][x]] += 1
		return counts[TREES] * counts[LUMBERYARD]

def solve(filename):
	with open(filename) as file:
		mapString = file.read()

	theMap = Map(mapString)

	for minute in range(0, 10):
		theMap.tick()

	print(filename + ': ' + str(theMap.resourceValue()))

solve('exampleinput1.txt')
solve('input1.txt')