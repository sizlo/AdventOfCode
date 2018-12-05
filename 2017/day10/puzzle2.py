def reverse(theList, start, length):
	end = start + length - 1
	for i in range(0, length/2):
		lhs = (start + i) % len(theList)
		rhs = (end - i) % len(theList)
		tmp = theList[lhs]
		theList[lhs] = theList[rhs]
		theList[rhs] = tmp
	return theList

with open('input.txt') as inFile:
	chars = inFile.read()
lengths = []
for char in chars:
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

print hexHash