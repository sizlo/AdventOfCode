def solve(filename):
	with open(filename) as file:
		polymer = file.read()

	alphabet = 'abcdefghijklmnopqrstuvwxyz'
	bestLength = 100000
	for unit in alphabet:
		removedPolymer = removeUnit(polymer, unit)
		reducedPolymer = reduce(removedPolymer)
		if len(reducedPolymer) < bestLength:
			bestLength = len(reducedPolymer)
	print(filename + ': ' + str(bestLength))

def reduce(polymer):
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
	return polymer

def removeUnit(polymer, unit):
	return polymer.replace(unit, '').replace(unit.upper(), '')



solve('exampleinput1.txt')
solve('input1.txt')
