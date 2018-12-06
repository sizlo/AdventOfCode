def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	withExactlyTwo = 0
	withExactlyThree = 0

	for line in lines:
		removedTwo = False
		removedThree = False
		processedString = line
		while len(processedString) > 0:
			removed = processedString.replace(processedString[0], '')
			numRemoved = len(processedString) - len(removed)
			processedString = removed
			if numRemoved is 2:
				removedTwo = True
			if numRemoved is 3:
				removedThree = True
		if removedTwo:
			withExactlyTwo += 1
		if removedThree:
			withExactlyThree += 1

	print(filename + ': ' + str(withExactlyTwo * withExactlyThree))

solve('exampleinput1.txt')
solve('input1.txt')