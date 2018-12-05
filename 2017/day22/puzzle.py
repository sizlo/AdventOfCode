def readGrid(gridString):
	grid = []
	for line in gridString.splitlines():
		grid.append(list(line))
	return grid

def addRowTop(grid):
	gridWidth = len(grid[0])
	newRow = ['.' for x in range(gridWidth)]
	grid.insert(0, newRow)

def addRowToBottom(grid):
	gridWidth = len(grid[0])
	newRow = ['.' for x in range(gridWidth)]
	grid.append(newRow)

def addColToLeft(grid):
	for row in grid:
		row.insert(0, '.')

def addColToRight(grid):
	for row in grid:
		row.append('.')

class Virus:
	def __init__(self, grid):
		self.grid = grid
		self.x = len(grid[0]) / 2
		self.y = len(grid) / 2
		self.direction = 'up'
		self.burstsThatInfected = 0

	def __str__(self):
		result = '(' + str(self.x) + ',' + str(self.y) + ') ' + self.direction + ' ' + str(self.burstsThatInfected) + '\n '
		for y in range(len(self.grid)):
			for x in range(len(self.grid[y])):
				if y == self.y and x == self.x:
					result = result[:-1] + '[' + self.grid[y][x] + ']'
				else:
					result += self.grid[y][x] + ' '
			result += '\n '
		return result

	def move(self):
		if self.direction == 'up':
			self.y -= 1
		elif self.direction == 'down':
			self.y += 1
		elif self.direction == 'left':
			self.x -= 1
		elif self.direction == 'right':
			self.x += 1
		self.expandGridIfNeeded()

	def expandGridIfNeeded(self):
		if self.x < 0:
			addColToLeft(self.grid)
			self.x = 0
		if self.x >= len(self.grid[0]):
			addColToRight(self.grid)
			self.x = len(self.grid[0]) - 1
		if self.y < 0:
			addRowTop(self.grid)
			self.y = 0
		if self.y >= len(self.grid):
			addRowToBottom(self.grid)
			self.y = len(self.grid) - 1

	def turnLeft(self):
		if self.direction == 'up':
			self.direction = 'left'
		elif self.direction == 'down':
			self.direction = 'right'
		elif self.direction == 'left':
			self.direction = 'down'
		elif self.direction == 'right':
			self.direction = 'up'

	def turnRight(self):
		if self.direction == 'up':
			self.direction = 'right'
		elif self.direction == 'down':
			self.direction = 'left'
		elif self.direction == 'left':
			self.direction = 'up'
		elif self.direction == 'right':
			self.direction = 'down'

	def chooseDirection(self):
		if self.grid[self.y][self.x] == '#':
			self.turnRight()
		else:
			self.turnLeft()

	def applyNewState(self):
		if self.grid[self.y][self.x] == '#':
			self.grid[self.y][self.x] = '.'
		else:
			self.grid[self.y][self.x] = '#'
			self.burstsThatInfected += 1

	def burst(self):
		self.chooseDirection()
		self.applyNewState()
		self.move()

class Part2Virus(Virus):
	def reverse(self):
		if self.direction == 'up':
			self.direction = 'down'
		elif self.direction == 'down':
			self.direction = 'up'
		elif self.direction == 'left':
			self.direction = 'right'
		elif self.direction == 'right':
			self.direction = 'left'

	def chooseDirection(self):
		currentNode = self.grid[self.y][self.x]
		if currentNode == '.':
			self.turnLeft()
		elif currentNode == '#':
			self.turnRight()
		elif currentNode == 'F':
			self.reverse()

	def applyNewState(self):
		if self.grid[self.y][self.x] == '.':
			self.grid[self.y][self.x] = 'W'
		elif self.grid[self.y][self.x] == 'W':
			self.grid[self.y][self.x] = '#'
			self.burstsThatInfected += 1
		elif self.grid[self.y][self.x] == '#':
			self.grid[self.y][self.x] = 'F'
		elif self.grid[self.y][self.x] == 'F':
			self.grid[self.y][self.x] = '.'

def testPart1():
	grid = readGrid('..#\n#..\n...')
	virus = Virus(grid)
	for i in range(10000):
		virus.burst()
	assert(virus.burstsThatInfected == 5587)

def testPart2():
	grid = readGrid('..#\n#..\n...')
	virus = Part2Virus(grid)
	for i in range(10000000):
		virus.burst()
	assert(virus.burstsThatInfected == 2511944)

def part1():
	with open('input.txt') as inFile:
		grid = readGrid(inFile.read())
	virus = Virus(grid)
	for i in range(10000):
		virus.burst()
	print 'Part 1: ' + str(virus.burstsThatInfected)

def part2():
	with open('input.txt') as inFile:
		grid = readGrid(inFile.read())
	virus = Part2Virus(grid)
	for i in range(10000000):
		virus.burst()
	print 'Part 2: ' + str(virus.burstsThatInfected)

testPart1()
part1()
testPart2()
part2()