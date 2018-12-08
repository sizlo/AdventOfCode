import re

class Node:
	def __init__(self):
		self.children = []
		self.metadatas = []

def consume(treeDefinition):
	result = treeDefinition[0]
	treeDefinition.remove(result)
	return int(result)

def readNode(treeDefinition):
	numChildren = consume(treeDefinition)
	numMetadata = consume(treeDefinition)
	node = Node()
	for i in range(0, numChildren):
		node.children.append(readNode(treeDefinition))
	for i in range(0, numMetadata):
		node.metadatas.append(consume(treeDefinition))
	return node

def findValue(node):
	if len(node.children) == 0:
		result = 0
		for metadata in node.metadatas:
			result += metadata
		return result

	result = 0
	for metadata in node.metadatas:
		childIndex = metadata - 1
		if childIndex < len(node.children):
			result += findValue(node.children[childIndex])
	return result

def solve(filename):
	with open(filename) as file:
		treeString = file.read()

	treeDefinition = treeString.split(' ')

	root = readNode(treeDefinition)

	print(filename + ': ' + str(findValue(root)))

solve('exampleinput1.txt')
solve('input1.txt')