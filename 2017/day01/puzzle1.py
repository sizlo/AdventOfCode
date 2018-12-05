from captcha import calculateSum
import sys

def runTests():
	runTest('1122', 3)
	runTest('1111', 4)
	runTest('1234', 0)
	runTest('91212129', 9)

def runTest(inputString, expected):
	actual = calculateSum(inputString, 1)
	if actual != expected:
		print('Failed on ' + inputString)
		print('Expected ' + str(expected) + ' but got ' + str(actual))
		sys.exit(1)

if (sys.argv[1] == 'tests'):
	runTests()
else:
	print calculateSum(sys.argv[1], 1)