import sys

def calculateSum(inputString, distanceToNextDigit):
	digits = list(inputString)
	length = len(digits)
	matchingDigits = 0

	for thisIndex in range(0, length):
		nextIndex = (thisIndex + distanceToNextDigit) % length
		thisDigit = digits[thisIndex]
		nextDigit = digits[nextIndex]
		if thisDigit == nextDigit:
			matchingDigits += int(thisDigit)

	return matchingDigits

def runTests():
	runTest('1122', 1, 3)
	runTest('1111', 1, 4)
	runTest('1234', 1, 0)
	runTest('91212129', 1, 9)

def runTest(inputString, distanceToNextDigit, expected):
	actual = calculateSum(inputString, distanceToNextDigit)
	if actual != expected:
		print('Failed on ' + inputString)
		print('Expected ' + str(expected) + ' but got ' + str(actual))
		sys.exit(1)

if (sys.argv[1] == 'tests'):
	runTests()
else:
	print calculateSum(sys.argv[1], 1)