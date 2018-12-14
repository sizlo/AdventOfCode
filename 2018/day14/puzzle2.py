def solve(recipeStringToLookFor):
	recipesToLookFor = [int(digit) for digit in recipeStringToLookFor]
	windowSize = len(recipesToLookFor)

	scores = [3, 7]
	elves = [0, 1]

	found = False
	while not found:
		lastScoresLength = len(scores)

		totalElfScore = 0
		for elf in elves:
			totalElfScore += scores[elf]
		newScores = [int(digit) for digit in str(totalElfScore)]
		scores += newScores

		newElves = []
		for elf in elves:
			newElves.append((elf + scores[elf] + 1) % len(scores))
		elves = newElves

		startIndex = lastScoresLength - windowSize
		for index in range(startIndex, len(scores)):
			endIndex = min(index + windowSize, len(scores))
			window = scores[index : endIndex]
			if window == recipesToLookFor:
				found = True
				break


	print(recipeStringToLookFor + ': ' + str(index))


solve('51589')
solve('01245')
solve('92510')
solve('59414')
solve('509671')