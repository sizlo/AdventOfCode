def getHundreds(num):
	if num < 100:
		return 0
	return int(num / 100) % 10

def solve(serialNumber):
	grid = []
	for y in range(0, 301):
		grid.append([])
		for x in range(0, 301):
			grid[y].append(None)

	for y in range(1, 301):
		for x in range(1, 301):
			rackId = x + 10
			powerLevel = rackId * y
			powerLevel += serialNumber
			powerLevel *= rackId
			powerLevel = getHundreds(powerLevel)
			powerLevel -= 5
			grid[y][x] = powerLevel

	largestWindowPowerVal = -9999
	largestWindowPowerX = 0
	largestWindowPowerY = 0
	windowSize = 3
	for y in range(1, 301 - (windowSize - 1)):
		for x in range(1, 301 - (windowSize - 1)):
			windowPower = 0
			for yOffset in range(0, windowSize):
				for xOffset in range(0, windowSize):
					windowPower += grid[y + yOffset][x + xOffset]
			if windowPower > largestWindowPowerVal:
				largestWindowPowerVal = windowPower
				largestWindowPowerX = x
				largestWindowPowerY = y

	print(str(serialNumber) + ': ' + str(largestWindowPowerX) + ',' + str(largestWindowPowerY))

solve(18)
solve(42)
solve(5235)