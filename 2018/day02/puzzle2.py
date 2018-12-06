def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	for lhs in lines:
		for rhs in lines:
			if len(lhs) is not len(rhs):
				break
			differentIndexes = []
			for index in range(0, len(lhs)):
				if lhs[index] is not rhs[index]:
					differentIndexes.append(index)
			if len(differentIndexes) is 1:
				differentIndex = differentIndexes[0]
				removedDifference = lhs[:differentIndex] + lhs[differentIndex+1:]
				print(filename + ': ' + removedDifference)
				return



solve('exampleinput2.txt')
solve('input1.txt')