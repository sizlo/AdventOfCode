import GridBuilder
import SubGridPermuter
import SubGridStringConverter
import sys

def parseRules(rulesString):
	rules = {}
	for line in rulesString.splitlines():
		parts = line.split(' => ')
		rules[parts[0]] = parts[1]
	return rules

def getEnhancedSubGridForSubGrid(subGrid, rules):
	for numRotations in range(4):
		rotated = list(subGrid)
		for rotation in range(numRotations):
			rotated = SubGridPermuter.rotate(rotated)
		rotatedString = SubGridStringConverter.toString(rotated)
		if rotatedString in rules.keys():
			return SubGridStringConverter.fromString(rules[rotatedString])

		flippedVertically = SubGridPermuter.flipVertically(rotated)
		flippedVerticallyString = SubGridStringConverter.toString(flippedVertically)
		if flippedVerticallyString in rules.keys():
			return SubGridStringConverter.fromString(rules[flippedVerticallyString])

		flippedHorizontally = SubGridPermuter.flipHorizontally(rotated)
		flippedHorizontallyString = SubGridStringConverter.toString(flippedHorizontally)
		if flippedHorizontallyString in rules.keys():
			return SubGridStringConverter.fromString(rules[flippedHorizontallyString])

	print 'Could not find rule for sub grid: ' + str(subGrid)
	sys.exit()
	

def runWithRulesAndIterations(rulesString, iterations):
	rules = parseRules(rulesString)
	grid = [['.', '#', '.'], ['.', '.', '#'], ['#', '#', '#']]

	for i in range(iterations):
		size = len(grid)
		if size % 2 == 0:
			subGrids = GridBuilder.splitIntoSubGridsOfSize(grid, 2)
		elif size % 3 == 0:
			subGrids = GridBuilder.splitIntoSubGridsOfSize(grid, 3)
		else:
			print 'Unhandled grid size: ' + str(size)

		enhancedSubGrids = []
		for subGrid in subGrids:
			enhancedSubGrids.append(getEnhancedSubGridForSubGrid(subGrid, rules))

		grid = GridBuilder.build(enhancedSubGrids)

	return grid

def countOnCells(grid):
	onCells = 0
	for row in grid:
		for cell in row:
			if cell == '#':
				onCells += 1
	return onCells

def test():
	result = runWithRulesAndIterations('../.# => ##./#../...\n.#./..#/### => #..#/..../..../#..#', 2)
	assert(result == [['#', '#', '.', '#', '#', '.'], 
					  ['#', '.', '.', '#', '.', '.'], 
					  ['.', '.', '.', '.', '.', '.'], 
					  ['#', '#', '.', '#', '#', '.'], 
					  ['#', '.', '.', '#', '.', '.'], 
					  ['.', '.', '.', '.', '.', '.']])


test()
with open('input.txt') as inFile:
	rulesString = inFile.read()
print 'Part 1: ' + str(countOnCells(runWithRulesAndIterations(rulesString, 5)))
print 'Part 2: ' + str(countOnCells(runWithRulesAndIterations(rulesString, 18)))
