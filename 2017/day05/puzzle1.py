instructions = []

with open('input.txt') as inFile:
	lines = inFile.read().splitlines()
	for line in lines:
		instructions.append(int(line))

currentInstruction = 0
steps = 0
while currentInstruction >= 0 and currentInstruction < len(instructions):
	nextInstruction = currentInstruction + instructions[currentInstruction]
	instructions[currentInstruction] += 1
	currentInstruction = nextInstruction
	steps += 1

print(steps)