import re

class Marble:
	def __init__(self):
		self.previous = None
		self.next = None
		self.score = 0

def pad(inStr):
	spaces = 5 - len(inStr)
	for i in range(0, spaces):
		inStr += ' '
	return inStr

def printGame(activePlayer, startMarble, currentMarble):
	return
	gameString = pad('[' + str(activePlayer) + ']')
	thisMarble = startMarble
	while True:
		if thisMarble is currentMarble:
			gameString += pad('(' + str(thisMarble.score) + ')')
		else:
			gameString += pad(str(thisMarble.score))
		thisMarble = thisMarble.next
		if thisMarble is startMarble:
			break
	print(gameString)

def solve(filename, multiplier=1):
	with open(filename) as file:
		gameDefinition = file.read()

	matches = re.search('(\\d+) players; last marble is worth (\\d+) points', gameDefinition)
	numPlayers = int(matches.group(1))
	lastMarble = int(matches.group(2)) * multiplier

	playerScores = [0] * numPlayers

	currentMarble = Marble()
	currentMarble.previous = currentMarble
	currentMarble.next = currentMarble
	startMarble = currentMarble

	activePlayer = 0

	printGame(0, startMarble, currentMarble)

	for marbleScore in range(1, lastMarble + 1):
		if marbleScore % 23 == 0:
			for i in range(0, 7):
				currentMarble = currentMarble.previous
			playerScores[activePlayer] += marbleScore + currentMarble.score
			lhs = currentMarble.previous
			rhs = currentMarble.next
			lhs.next = rhs
			rhs.previous = lhs
			currentMarble = rhs
		else:
			lhs = currentMarble.next
			rhs = lhs.next
			currentMarble = Marble()
			currentMarble.score = marbleScore
			currentMarble.previous = lhs
			currentMarble.next = rhs
			lhs.next = currentMarble
			rhs.previous = currentMarble
		printGame(activePlayer, startMarble, currentMarble)

		activePlayer = (activePlayer + 1) % numPlayers

	highestScore = 0
	for score in playerScores:
		if score > highestScore:
			highestScore = score
	print(filename + ': ' + str(highestScore))



print('Part 1:')
solve('exampleinput1.txt')
solve('exampleinput2.txt')
solve('exampleinput3.txt')
solve('exampleinput4.txt')
solve('exampleinput5.txt')
solve('exampleinput6.txt')
solve('input1.txt')
print('')
print('Part 2:')
solve('input1.txt', multiplier=100)