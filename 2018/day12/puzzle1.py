def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	state = lines[0][15:]
	
	plantProducingPatterns = []

	lines = lines[2:]
	for line in lines:
		parts = line.split(' => ')
		if parts[1] == '#':
			plantProducingPatterns.append(parts[0])

	startIndex = 0
	endIndex = len(state)
	for generation in range(0, 20):
		
		state = '...' + state + '...'
		startIndex -= 3
		endIndex += 3

		while state[:5] not in plantProducingPatterns:
			state = state[1:]
			startIndex += 1

		while state[-5:] not in plantProducingPatterns:
			state = state[:-1]
			endIndex -= 1

		newState = state
		for currentPot in range(0, len(state)):
			currentPattern = state[currentPot - 2 : currentPot + 3]
			if currentPattern in plantProducingPatterns:
				newState = newState[:currentPot] + '#' + newState[currentPot + 1:]
			else:
				newState = newState[:currentPot] + '.' + newState[currentPot + 1:]
		state = newState

	totalOfPotIndexesWithPlant = 0
	stringIndex = 0
	for potIndex in range(startIndex, endIndex):
		if state[stringIndex] == '#':
			totalOfPotIndexesWithPlant += potIndex
		stringIndex += 1

	print(filename + ': ' + str(totalOfPotIndexesWithPlant))


solve('exampleinput1.txt')
solve('input1.txt')