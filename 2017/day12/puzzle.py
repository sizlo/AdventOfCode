from sets import Set

class Program:
	def __init__(self, initString):
		indexAndNeighbours = initString.split(' <-> ')
		self.index = int(indexAndNeighbours[0])
		self.neighbours = map(int, indexAndNeighbours[1].split(', '))

	def __str__(self):
		return str(self.index) + ': ' + ', '.join(map(str, self.neighbours))

programs = []
with open('input.txt') as inFile:
	lines = inFile.read().splitlines()
for line in lines:
	programs.append(Program(line))
	
def visit(programIndex, programs, visitedPrograms=Set()):
	visitedPrograms.add(programIndex)
	for neighbourIndex in programs[programIndex].neighbours:
		if neighbourIndex not in visitedPrograms:
			visitedPrograms.update(visit(neighbourIndex, programs))
	return visitedPrograms

print 'Part 1: ' + str(len(visit(0, programs)))

groupCount = 0
visitedPrograms = Set()
for program in programs:
	if program.index not in visitedPrograms:
		groupCount += 1
		visit(program.index, programs, visitedPrograms)

print 'Part 2: ' + str(groupCount)
