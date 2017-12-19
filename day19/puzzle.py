import string

class Walker:
	def __init__(self, mapLines):
		self.map = []
		for mapLine in mapLines:
			self.map.append(list(mapLine))
		self.currentRow = 0
		self.currentCol = 0
		self.findFirstPathPiece()
		self.direction = 'down'
		self.collectedLetters = []
		self.steps = 1

	def __str__(self):
		return '(' + str(self.currentRow) + ',' + str(self.currentCol) + ')\t' + self.direction + '\t' + ''.join(self.collectedLetters)

	def currentMapPiece(self):
		return self.map[self.currentRow][self.currentCol]

	def nextMapPiece(self):
		try:
			if self.direction == 'down':
				return self.map[self.currentRow+1][self.currentCol]
			elif self.direction == 'up':
				return self.map[self.currentRow-1][self.currentCol]
			elif self.direction == 'left':
				return self.map[self.currentRow][self.currentCol-1]
			elif self.direction == 'right':
				return self.map[self.currentRow][self.currentCol+1]
			else:
				print 'Unhandled direction ' + direction
		except IndexError:
			return ' '

	def findFirstPathPiece(self):
		self.currentRow = 0
		for self.currentCol in range(0, len(self.map[self.currentRow])):
			if self.currentMapPiece() == '|':
				break

	def move(self):
		if self.direction == 'down':
			self.currentRow += 1
		elif self.direction == 'up':
			self.currentRow -= 1
		elif self.direction == 'left':
			self.currentCol -= 1
		elif self.direction == 'right':
			self.currentCol += 1
		else:
			print 'Unhandled direction ' + direction

	def collectLetter(self):
		if self.currentMapPiece() in string.ascii_uppercase:
			self.collectedLetters.append(self.currentMapPiece())

	def turnLeftIfPossible(self):
		try:
			if self.map[self.currentRow][self.currentCol-1] in string.ascii_uppercase + '+-':
				self.direction = 'left'
				return True
		except IndexError:
			return False
		return False

	def turnRightIfPossible(self):
		try:
			if self.map[self.currentRow][self.currentCol+1] in string.ascii_uppercase + '+-':
				self.direction = 'right'
				return True
		except IndexError:
			return False
		return False

	def turnUpIfPossible(self):
		try:
			if self.map[self.currentRow-1][self.currentCol] in string.ascii_uppercase + '+|':
				self.direction = 'up'
				return True
		except IndexError:
			return False
		return False

	def turnDownIfPossible(self):
		try:
			if self.map[self.currentRow+1][self.currentCol] in string.ascii_uppercase + '+|':
				self.direction = 'down'
				return True
		except IndexError:
			return False
		return False


	def turnIfNeeded(self):
		if self.currentMapPiece() in string.ascii_uppercase + '+':
			if self.direction in ['up', 'down']:
				if self.turnLeftIfPossible():
					return
				if self.turnRightIfPossible():
					return
			elif self.direction in ['left', 'right']:
				if self.turnUpIfPossible():
					return
				if self.turnDownIfPossible():
					return
			else:
				print 'Unhandled direction ' + direction

	def tick(self):
		self.move()
		self.collectLetter()
		self.turnIfNeeded()
		self.steps += 1

	def walk(self):
		while self.nextMapPiece() != ' ':
			self.tick()

with open('input.txt') as inFile:
	lines = inFile.read().splitlines()
walker = Walker(lines)
walker.walk()

print 'Part 1: ' + ''.join(walker.collectedLetters)
print 'Part 2: ' + str(walker.steps)

