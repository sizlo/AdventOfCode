import re

class Worker:
	def __init__(self):
		self.step = None
		self.timeLeft = 0

	def needsWork(self):
		return self.step == None

	def hasWork(self):
		return not self.needsWork()

	def work(self, nodes, stepsInProgress, stepTime, stepsDoneThisTick):
		if self.needsWork():
			step = findNextStep(nodes, stepsInProgress)
			if step != None:
				self.step = step
				self.timeLeft = (ord(step) - 64) + stepTime
				stepsInProgress.append(step)

		if (self.hasWork()):
			log(self.step)
		else:
			log('.')

		if self.hasWork():
			self.timeLeft -= 1

		if self.hasWork() and self.timeLeft == 0:
			stepsDoneThisTick.append(self.step)
			self.step = None


def findNextStep(nodes, stepsInProgress):
	possibleNextSteps = list(nodes.keys())
	for step, children in nodes.items():
		for childStep in children:
			if childStep in possibleNextSteps:
				possibleNextSteps.remove(childStep)
	possibleNextSteps.sort()
	for step in possibleNextSteps:
		if step not in stepsInProgress:
			return step
	return None

def log(msg, newline=False):
	if False:
		print(msg, end=' ')
		if newline:
			print('')

def solve(filename, numWorkers, stepTime):
	with open(filename) as file:
		lines = file.read().splitlines()

	nodes = {}

	for line in lines:
		matches = re.search('Step (\\S+) must be finished before step (\\S+) can begin.', line)
		parent = matches.group(1)
		child = matches.group(2)
		
		if parent not in nodes.keys():
			nodes[parent] = []

		if child not in nodes.keys():
			nodes[child] = []

		nodes[parent].append(child)

	time = 0
	hasWork = True
	workers = []
	stepsInProgress = []
	stepsDone = []
	for i in range(0, numWorkers):
		workers.append(Worker())

	while len(nodes.keys()) > 0 or hasWork:
		log(time)
		hasWork = False
		stepsDoneThisTick = []
		for worker in workers:
			worker.work(nodes, stepsInProgress, stepTime, stepsDoneThisTick)
			if worker.hasWork():
				hasWork = True
		log(''.join(stepsDone), newline=True)
		for step in stepsDoneThisTick:
			stepsDone.append(step)
			nodes.pop(step)
			stepsInProgress.remove(step)
		time += 1


	print(filename + ': ' + str(time))

solve('exampleinput1.txt', 2, 0)
solve('input1.txt', 5, 60)