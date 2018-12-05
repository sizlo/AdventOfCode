import binascii

def reverse(theList, start, length):
	end = start + length - 1
	for i in range(0, length/2):
		lhs = (start + i) % len(theList)
		rhs = (end - i) % len(theList)
		tmp = theList[lhs]
		theList[lhs] = theList[rhs]
		theList[rhs] = tmp
	return theList

def knotHash(inputString):
	lengths = []
	for char in inputString:
		lengths.append(ord(char))
	lengths.append(17)
	lengths.append(31)
	lengths.append(73)
	lengths.append(47)
	lengths.append(23)

	currentPosition = 0
	skipSize = 0
	theList = range(0, 256)

	for i in range(0, 64):
		for length in lengths:
			reverse(theList, currentPosition, length)
			currentPosition += length + skipSize
			skipSize += 1

	denseHash = []
	blockStart = 0
	while blockStart < len(theList):
		blockValue = theList[blockStart]
		for i in range(1, 16):
			blockValue = blockValue ^ theList[blockStart + i]
		denseHash.append(blockValue)
		blockStart += 16

	hexHash = ''
	for blockValue in denseHash:
		hexStr = hex(blockValue)[2:]
		if len(hexStr) == 1:
			hexStr = '0' + hexStr
		hexHash += hexStr

	return hexHash

def hexStringToBinaryString(hexStr):
	binary = bin(int(hexStr, 16))[2:]
	while len(binary) < 128:
		binary = '0' + binary
	return binary

def isOn(grid, row, col):
	if row < 0 or row > 127 or col < 0 or col > 127:
		return False
	return grid[row][col] == '1'

def clearConnectedGroup(grid, row, col):
	grid[row][col] = '0'
	if isOn(grid, row-1, col):
		clearConnectedGroup(grid, row-1, col)
	if isOn(grid, row+1, col):
		clearConnectedGroup(grid, row+1, col)
	if isOn(grid, row, col-1):
		clearConnectedGroup(grid, row, col-1)
	if isOn(grid, row, col+1):
		clearConnectedGroup(grid, row, col+1)


key = 'xlqgujun'
usedSquares = 0
bitGrid = [['0' for x in range(0,128)] for y in range(0,128)]
for row in range (0, 128):
	keyWithIndex = key + '-' + str(row)
	hexHash = knotHash(keyWithIndex)
	binaryString = hexStringToBinaryString(hexHash)
	col = 0
	for bit in binaryString:
		if bit == '1':
			usedSquares += 1
		bitGrid[row][col] = bit
		col += 1

groupCount = 0
for row in range(0, 128):
	for col in range(0, 128):
		if isOn(bitGrid, row, col):
			groupCount += 1
			clearConnectedGroup(bitGrid, row, col)


print 'Part 1: ' + str(usedSquares)
print 'Part 2: ' + str(groupCount)
