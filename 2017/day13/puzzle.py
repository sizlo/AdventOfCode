import copy
import sys

class Scanner:
	def __init__(self, inputString):
		self.depth = int(inputString.split(': ')[0])
		self.range = int(inputString.split(': ')[1])
		self.currentPos = 1
		self.speed = -1

	def update(self):
		if self.currentPos == 1 or self.currentPos == self.range:
			self.speed = self.speed * -1
		self.currentPos += self.speed

	def caughtMe(self):
		return self.currentPos == 1

	def __str__(self):
		return str(self.depth) + ' ' + str(self.range) + ' ' + str(self.currentPos) + ' ' + str(self.speed)

class Player:
	def __init__(self, startTick):
		self.startTick = startTick
		self.pos = 0
		self.severity = 0
		self.caught = False

with open('input.txt') as inFile:
	lines = inFile.read().splitlines()
scanners = {}
lastScanner = 0
for line in lines:
	scanner = Scanner(line)
	scanners[scanner.depth] = scanner
	lastScanner = max(lastScanner, scanner.depth)

activePlayers = []
currentTick = 0
while True:
	activePlayers.append(Player(currentTick))
	currentTick += 1
	for player in activePlayers:
		if player.pos in scanners.keys() and scanners[player.pos].caughtMe():
			player.caught = True
			player.severity += scanners[player.pos].depth * scanners[player.pos].range
	for scanner in scanners.values():
		scanner.update()
	for player in activePlayers:
		player.pos += 1
		if player.pos > lastScanner:
			activePlayers.remove(player)
			if player.startTick == 0:
				print 'Part 1: ' + str(player.severity)
			if player.caught == False:
				print 'Part 2: ' + str(player.startTick)
				sys.exit()

