def getLargestBlock(blocks):
	largestIndex = 0
	largestBlock = blocks[0]
	for index in range(0, len(blocks)):
		if blocks[index] > largestBlock:
			largestBlock = blocks[index]
			largestIndex = index
	return largestIndex, largestBlock

def getIndexOfBlockInPreviousBlocks(blocks, previousBlocks):
	index = 0
	while blocks != previousBlocks[index]:
		index += 1
	return index

with open('input.txt') as inFile:
	strBlocks = inFile.read().split()
blocks = []
for strBlock in strBlocks:
	blocks.append(int(strBlock))

previousBlocks = []
previousBlocks.append(list(blocks))

numSteps = 0
while True:
	numSteps += 1
	index, value = getLargestBlock(blocks)
	blocks[index] = 0
	while value > 0:
		index = (index + 1) % len(blocks)
		blocks[index] += 1
		value -= 1
	if blocks in previousBlocks:
		stepNumberOfPreviousBlock = getIndexOfBlockInPreviousBlocks(blocks, previousBlocks)
		print(numSteps - stepNumberOfPreviousBlock)
		break
	previousBlocks.append(list(blocks))
