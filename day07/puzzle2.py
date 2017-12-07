class Node:
	name = ''
	children = []
	weight = 0
	parent = ''

	def __init__(self, line):
		if '->' in line:
			nodeAndChildren = line.split(' -> ')
			self.children = nodeAndChildren[1].split(', ')
			line = nodeAndChildren[0]
		nameAndWeight = line.split()
		self.name = nameAndWeight[0]
		self.weight = int(nameAndWeight[1][1:-1])
		self.parent = ''

	def __str__(self):
		return self.name + ' ' + str(self.weight) + ' ' + str(self.children) + ' ' + self.parent

	def printTowerInfo(self, nodes):
		print '\t' + str(self.isBalanced(nodes))
		print '\t' + str(self.getTowerWeight(nodes))
		if len(self.children) > 0:
			print '\t',
			for childName in self.children:
				print childName + ' ' + str(nodes[childName].getTowerWeight(nodes)),
			print ''

	def getTowerWeight(self, nodes):
		towerWeight = self.weight
		if len(self.children) == 0:
			return towerWeight
		for childName in self.children:
			towerWeight += nodes[childName].getTowerWeight(nodes)
		return towerWeight

	def isBalanced(self, nodes):
		if len(self.children) > 0:
			childTowerWeights = []
			for childName in self.children:
				childTowerWeights.append(nodes[childName].getTowerWeight(nodes))
			return len(set(childTowerWeights)) == 1
		else:
			return True

	def getBalanceWeight(self, nodes):
		childTowerWeights = []
		for childName in self.children:
			childTowerWeights.append(nodes[childName].getTowerWeight(nodes))
		mostCommonWeight = max(set(childTowerWeights), key=childTowerWeights.count)
		leastCommonWeight = min(set(childTowerWeights), key=childTowerWeights.count)
		for childName in self.children:
			childNode = nodes[childName]
			if childNode.getTowerWeight(nodes) == leastCommonWeight:
				difference = mostCommonWeight - leastCommonWeight
				return childNode.weight + difference



with open('input.txt') as inFile:
	lines = inFile.read().splitlines()

nodes = {}

for line in lines:
	node = Node(line)
	nodes[node.name] = node


unbalancedNodes = []

for name in nodes:
	node = nodes[name]
	if not node.isBalanced(nodes):
		unbalancedNodes.append(name)

deepestUnbalancedNode = None
for parentName in unbalancedNodes:
	parentNode = nodes[parentName]
	hasUnbalancedChild = False
	for childName in parentNode.children:
		if childName in unbalancedNodes:
			hasUnbalancedChild = True
	if not hasUnbalancedChild:
		deepestUnbalancedNode = parentNode

print deepestUnbalancedNode.getBalanceWeight(nodes)


