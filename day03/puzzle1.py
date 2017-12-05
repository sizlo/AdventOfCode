import sys

def getRingNumber(inputNumber):
	oddNumber = 1
	while (inputNumber > oddNumber*oddNumber):
		oddNumber += 2
	return oddNumber/2

def getSideLengthForRingNumber(ringNumber):
	sideLength = (2*ringNumber) + 1
	return max(sideLength, 0)

def getRingRange(ringNumber):
	sideLength = getSideLengthForRingNumber(ringNumber)
	sideLengthForPreviousRing = getSideLengthForRingNumber(ringNumber-1)
	minInRing = (sideLengthForPreviousRing*sideLengthForPreviousRing) + 1
	maxInRing = sideLength * sideLength
	return range(minInRing, maxInRing+1)

def getMidpoints(ringNumber, numbersInRing):
	ringLength = len(numbersInRing)
	sideLength = getSideLengthForRingNumber(ringNumber)
	firstMid = (sideLength / 2) - 1
	secondMid = firstMid + sideLength - 1
	thirdMid = secondMid + sideLength - 1
	fourthMid = thirdMid + sideLength - 1
	return [numbersInRing[firstMid], numbersInRing[secondMid], numbersInRing[thirdMid], numbersInRing[fourthMid]]

def findClosest(inputNumber, midpoints):
	closestDistance = abs(midpoints[0] - inputNumber)
	closestMidpoint = midpoints[0]
	for midpoint in midpoints:
		distance = abs(midpoint - inputNumber)
		if distance < closestDistance:
			closestDistance = distance
			closestMidpoint = midpoint
	return closestMidpoint

inputNumber = int(sys.argv[1])
ringNumber = getRingNumber(inputNumber)
numbersInRing = getRingRange(ringNumber)
midPointsOfRingSides = getMidpoints(ringNumber, numbersInRing)
closestMidpoint = findClosest(inputNumber, midPointsOfRingSides)
shortestPath = abs(closestMidpoint - inputNumber) + ringNumber
print(shortestPath)