with open('input1.txt') as inFile:
	lines = inFile.read().splitlines()

lineSum = 0
for line in lines:
	digits = line.split()
	maxDigit = -1
	minDigit = 999999999
	for digit in digits:
		if int(digit) > maxDigit:
			maxDigit = int(digit)
		if int(digit) < minDigit:
			minDigit = int(digit)
	lineSum += maxDigit - minDigit

print(lineSum)