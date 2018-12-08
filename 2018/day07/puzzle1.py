import re

def findNextStep(nodes):
	possibleNextSteps = list(nodes.keys())
	for step, children in nodes.items():
		for childStep in children:
			if childStep in possibleNextSteps:
				possibleNextSteps.remove(childStep)
	possibleNextSteps.sort()
	return possibleNextSteps[0]


def solve(filename):
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

	steps = ''
	while len(nodes.keys()) > 0:
		nextStep = findNextStep(nodes)
		steps += nextStep
		nodes.pop(nextStep)

	print(filename + ': ' + steps)

solve('exampleinput1.txt')
solve('input1.txt')