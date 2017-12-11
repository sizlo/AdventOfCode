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
	lengths = map(int, inFile.read().split(','))
currentPosition = 0
skipSize = 0

theList = range(0, 256)
for length in lengths:
	reverse(theList, currentPosition, length)
	currentPosition += length + skipSize
	skipSize += 1

print theList[0] * theList[1]