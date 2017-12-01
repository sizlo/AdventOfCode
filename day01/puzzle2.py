from captcha import calculateSum
import sys

def runTests():
	runTest('1212', 6)
	runTest('1221', 0)
	runTest('123425', 4)
	runTest('123123', 12)
	runTest('12131415', 4)

def runTest(inputString, expected):
	actual = calculateSum(inputString, len(inputString) / 2)
	if actual != expected:
		print('Failed on ' + inputString)
		print('Expected ' + str(expected) + ' but got ' + str(actual))
		sys.exit(1)

if (sys.argv[1] == 'tests'):
	runTests()
else:
	print calculateSum(sys.argv[1], len(sys.argv[1]) / 2)