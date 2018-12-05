with open('input2.txt') as inFile:
	lines = inFile.read().splitlines()

lineSum = 0
for line in lines:
	digits = line.split()
	for numeratorChar in digits:
		numerator = int(numeratorChar)
		for denominatorChar in digits:
			denominator = int(denominatorChar)
			if numerator == denominator:
				continue
			if numerator % denominator == 0:
				lineSum += numerator / denominator

print(lineSum)