import sys

def calculateSum(inputString):
	digits = list(inputString)
	length = len(digits)
	matchingDigits = 0

	for thisIndex in range(0, length):
		nextIndex = (thisIndex + 1) % length
		thisDigit = digits[thisIndex]
		nextDigit = digits[nextIndex]
		if thisDigit == nextDigit:
			matchingDigits += int(thisDigit)

	return matchingDigits

def runTests():
	runTest('1122', 3)
	runTest('1111', 4)
	runTest('1234', 0)
	runTest('91212129', 9)

def runTest(inputString, expected):
	actual = calculateSum(inputString)
	if actual != expected:
		print('Failed on ' + inputString)
		print('Expected ' + str(expected) + ' but got ' + str(actual))
		sys.exit(1)

if (sys.argv[1] == 'tests'):
	runTests()
else:
	print calculateSum(sys.argv[1])