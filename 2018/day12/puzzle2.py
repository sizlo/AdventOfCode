def applyGeneration(startIndex, endIndex, state, plantProducingPatterns):
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
	
	return startIndex, endIndex, newState


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

	history = []
	history.append(state)

	generation = 0
	while True:
		generation += 1
		startIndex, endIndex, state = applyGeneration(startIndex, endIndex, state, plantProducingPatterns)
		
		if state in history:
			break
		history.append(state)


	# While debugging I noticed that at this point the pattern of plants is stable, but each plant moves along one index each generation.
	# So I need to find out the value of the current generation, the number of generations left, and the number of plants
	# The value at the final generation = currentGenValue + (numPlants * gensLeft)

	currentGenValue = 0
	stringIndex = 0
	for potIndex in range(startIndex, endIndex):
		if state[stringIndex] == '#':
			currentGenValue += potIndex
		stringIndex += 1

	gensLeft = 50000000000 - generation
	numPlants = state.count('#')

	finalGenValue = currentGenValue + (numPlants * gensLeft)

	print(filename + ': ' + str(finalGenValue))

solve('input1.txt')