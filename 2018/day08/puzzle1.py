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

def sumMetadata(root):
	result = 0
	for metadata in root.metadatas:
		result += metadata
	for child in root.children:
		result += sumMetadata(child)
	return result

def solve(filename):
	with open(filename) as file:
		treeString = file.read()

	treeDefinition = treeString.split(' ')

	root = readNode(treeDefinition)

	print(filename + ': ' + str(sumMetadata(root)))

solve('exampleinput1.txt')
solve('input1.txt')