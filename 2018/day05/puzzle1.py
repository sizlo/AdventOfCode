def solve(filename):
	with open(filename) as file:
		polymer = file.read()

	while True:
		startLength = len(polymer)
		for i in range(0, len(polymer) - 1):
			thisLetter = polymer[i]
			nextLetter = polymer[i + 1]
			if thisLetter != nextLetter and thisLetter.upper() == nextLetter.upper():
				polymer = polymer[:i] + polymer[i+2:]
				break
		endLength = len(polymer)
		if startLength == endLength:
			break

	print(filename + ': ' + str(len(polymer)))

solve('exampleinput1.txt')
solve('input1.txt')
