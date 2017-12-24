import math

def build(subGrids):
	subGridSize = len(subGrids[0])
	numItemsInGrid = len(subGrids) * subGridSize * subGridSize
	gridSize = int(math.sqrt(numItemsInGrid))
	grid = [[None for x in range(gridSize)] for y in range(gridSize)]

	topLeftX = 0
	topLeftY = 0
	for subGrid in subGrids:
		for y in range(len(subGrid)):
			row = subGrid[y]
			for x in range(len(row)):
				item = row[x]
				grid[topLeftY + y][topLeftX + x] = item
		
		topLeftX += subGridSize
		if topLeftX >= gridSize:
			topLeftX = 0
			topLeftY += subGridSize

	return grid

def splitIntoSubGridsOfSize(grid, subGridSize):
	gridSize = len(grid)
	subGrids = []
	topLeftX = 0
	topLeftY = 0
	while topLeftY < gridSize:
		subGrid = []
		for y in range(subGridSize):
			subGridRow = []
			for x in range(subGridSize):
				subGridRow.append(grid[topLeftY + y][topLeftX + x])
			subGrid.append(subGridRow)
		subGrids.append(subGrid)

		topLeftX += subGridSize
		if topLeftX >= gridSize:
			topLeftX = 0
			topLeftY += subGridSize

	return subGrids
