class Group:
	def __init__(self, parent):
		self.childGroups = []
		self.parent = parent
		if self.parent == None:
			self.score = 1
		else:
			self.score = parent.score + 1
		self.garbage = ''

	def countScores(self):
		scores = self.score
		if len(self.childGroups) == 0:
			return scores
		for child in self.childGroups:
			scores += child.countScores()
		return scores

	def countGarbage(self):
		garbageLength = len(self.garbage)
		if len(self.childGroups) == 0:
			return garbageLength
		for child in self.childGroups:
			garbageLength += child.countGarbage()
		return garbageLength


def handleGarbage(thisChar, index, currentGroup):
	if thisChar == '>':
		return False, index
	elif thisChar == '!':
		return True, index + 1
	else:
		currentGroup.garbage += thisChar
		return True, index

def handleNotGarbage(thisChar, currentGroup):
	if thisChar == '{':
		newChild = Group(currentGroup)
		currentGroup.childGroups.append(newChild)
		return False, newChild
	elif thisChar == '}':
		return False, currentGroup.parent
	elif thisChar == ',':
		return False, currentGroup
	elif thisChar == '<':
		return True, currentGroup
	else:
		print 'Unhandled none garbage char: ' + thisChar
		return currentGroup

def run(inputString):
	inGarbage = False
	index = 1
	root = Group(None)
	currentGroup = root
	while index < len(inputString):
		thisChar = inputString[index]
		if (inGarbage):
			inGarbage, index = handleGarbage(thisChar, index, currentGroup)
		else:
			inGarbage, currentGroup = handleNotGarbage(thisChar, currentGroup)
		index += 1
	return root.countScores(), root.countGarbage()

with open('input.txt') as inFile:
	inputString = inFile.read()
part1, part2 = run(inputString)
print 'Part 1: ' + str(part1)
print 'Part 2: ' + str(part2)