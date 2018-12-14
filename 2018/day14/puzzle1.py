def solve(numRecipesBeforeGood):
	numRecipesToUse = 10

	scores = [3, 7]
	elves = [0, 1]

	while len(scores) < numRecipesBeforeGood + numRecipesToUse:
		totalElfScore = 0
		for elf in elves:
			totalElfScore += scores[elf]
		newScores = [int(digit) for digit in str(totalElfScore)]
		scores += newScores

		newElves = []
		for elf in elves:
			newElves.append((elf + scores[elf] + 1) % len(scores))
		elves = newElves


	recipesToUse = scores[numRecipesBeforeGood : numRecipesBeforeGood + numRecipesToUse]
	print(str(numRecipesBeforeGood) + ': ' + ''.join([str(recipe) for recipe in recipesToUse]))


solve(9)
solve(5)
solve(18)
solve(2018)
solve(509671)