import sys

UP = "UP"
DOWN = "DOWN"
LEFT = "LEFT"
RIGHT = "RIGHT"

gridSize = 100
grid = [[0 for x in range(gridSize)] for y in range(gridSize)]

def getAbove(row, col):
	return grid[row-1][col]
def getBelow(row, col):
	return grid[row+1][col]
def getLeft(row, col):
	return grid[row][col-1]
def getRight(row, col):
	return grid[row][col+1]
def getAboveLeft(row, col):
	return grid[row-1][col-1]
def getAboveRight(row, col):
	return grid[row-1][col+1]
def getBelowLeft(row, col):
	return grid[row+1][col-1]
def getBelowRight(row, col):
	return grid[row+1][col+1]

def canTurn(row, col, direction):
	if direction == UP:
		return getLeft(row, col) == 0
	if direction == DOWN:
		return getRight(row, col) == 0
	if direction == LEFT:
		return getBelow(row, col) == 0
	if direction == RIGHT:
		return getAbove(row, col) == 0

def turn(direction):
	if direction == UP:
		return LEFT
	if direction == DOWN:
		return RIGHT
	if direction == LEFT:
		return DOWN
	if direction == RIGHT:
		return UP

def goForward(row, col, direction):
	if direction == UP:
		row -= 1
	if direction == DOWN:
		row += 1
	if direction == LEFT:
		col -= 1
	if direction == RIGHT:
		col += 1
	return row, col, direction

def move(row, col, direction):
	if canTurn(row, col, direction):
		direction = turn(direction)
	return goForward(row, col, direction)

def calculateSum(row, col):
	above = getAbove(row,col)
	below = getBelow(row,col)
	left = getLeft(row,col)
	right = getRight(row,col)
	aboveLeft = getAboveLeft(row,col)
	aboveRight = getAboveRight(row,col)
	belowLeft = getBelowLeft(row,col)
	belowRight = getBelowRight(row,col)
	return above + below + left + right + aboveLeft + aboveRight + belowLeft + belowRight

row = col = gridSize / 2
grid[row][col] = 1
col += 1
direction = RIGHT

inputNumber = int(sys.argv[1])

while(True):
	thisSquare = calculateSum(row, col)
	if thisSquare > inputNumber:
		print(thisSquare)
		sys.exit()
	grid[row][col] = thisSquare
	row, col, direction = move(row, col, direction)