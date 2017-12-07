class Node:
	name = ''
	children = []
	weight = ''
	parent = ''

	def __init__(self, line):
		if '->' in line:
			nodeAndChildren = line.split(' -> ')
			self.children = nodeAndChildren[1].split(', ')
			line = nodeAndChildren[0]
		nameAndWeight = line.split()
		self.name = nameAndWeight[0]
		self.weight = nameAndWeight[1]
		self.parent = ''

	def __str__(self):
		return self.name + ' ' + self.weight + ' ' + str(self.children) + ' ' + self.parent


with open('input.txt') as inFile:
	lines = inFile.read().splitlines()

nodes = {}

for line in lines:
	node = Node(line)
	nodes[node.name] = node

for parentName in nodes:
	parent = nodes[parentName]
	for childName in parent.children:
		child = nodes[childName]
		child.parent = parentName

current = nodes[nodes.keys()[0]]
while current.parent != '':
	current = nodes[current.parent]
	
print current.name

