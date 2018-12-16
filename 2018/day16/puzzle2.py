OPCODES = {
	'addr',
	'addi',
	'mulr',
	'muli',
	'banr',
	'bani',
	'borr',
	'bori',
	'setr',
	'seti',
	'gtir',
	'gtri',
	'gtrr',
	'eqir',
	'eqri',
	'eqrr'
}

def instruction(opcode, a, b, c, startRegisters):
	registers = startRegisters.copy()

	if opcode == 'addr':
		registers[c] = registers[a] + registers[b]
	elif opcode == 'addi':
		registers[c] = registers[a] + b
	elif opcode == 'mulr':
		registers[c] = registers[a] * registers[b]
	elif opcode == 'muli':
		registers[c] = registers[a] * b
	elif opcode == 'banr':
		registers[c] = registers[a] & registers[b]
	elif opcode == 'bani':
		registers[c] = registers[a] & b
	elif opcode == 'borr':
		registers[c] = registers[a] | registers[b]
	elif opcode == 'bori':
		registers[c] = registers[a] | b
	elif opcode == 'setr':
		registers[c] = registers[a]
	elif opcode == 'seti':
		registers[c] = a
	elif opcode == 'gtir':
		if a > registers[b]:
			registers[c] = 1
		else:
			registers[c] = 0
	elif opcode == 'gtri':
		if registers[a] > b:
			registers[c] = 1
		else:
			registers[c] = 0
	elif opcode == 'gtrr':
		if registers[a] > registers[b]:
			registers[c] = 1
		else:
			registers[c] = 0
	elif opcode == 'eqir':
		if a == registers[b]:
			registers[c] = 1
		else:
			registers[c] = 0
	elif opcode == 'eqri':
		if registers[a] == b:
			registers[c] = 1
		else:
			registers[c] = 0
	elif opcode == 'eqrr':
		if registers[a] == registers[b]:
			registers[c] = 1
		else:
			registers[c] = 0
	else:
		raise Exception('Unknown opcode ' + opcode)

	return registers

def doSample(sampleLines):
	beforeLine = sampleLines[0]
	operationLine = sampleLines[1]
	afterLine = sampleLines[2]

	beforeRegisters = [int(value) for value in beforeLine[9:-1].split(', ')]
	afterRegisters = [int(value) for value in afterLine[9:-1].split(', ')]

	operands = operationLine.split(' ')
	opcodeNum = int(operands[0])
	a = int(operands[1])
	b = int(operands[2])
	c = int(operands[3])

	matchingOpcodes = set()
	for opcode in OPCODES:
		if instruction(opcode, a, b, c, beforeRegisters) == afterRegisters:
			matchingOpcodes.add(opcode)

	return opcodeNum, matchingOpcodes

def findOpcodes(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	posibleOpcodes = []
	for i in range(0, len(OPCODES)):
		posibleOpcodes.append(OPCODES.copy())

	for lineNum in range(0, len(lines), 4):
		opcodeNum, matchingOpcodes = doSample(lines[lineNum: lineNum + 3])
		posibleOpcodes[opcodeNum] = matchingOpcodes.intersection(posibleOpcodes[opcodeNum])

		if len(posibleOpcodes[opcodeNum]) == 1:
			opcodeToRemove = list(posibleOpcodes[opcodeNum])[0]
			for removeFromNum in range(0, len(posibleOpcodes)):
				if removeFromNum != opcodeNum:
					posibleOpcodes[removeFromNum] = posibleOpcodes[removeFromNum] - {opcodeToRemove}

	opcodes = []
	for i in range(0, len(posibleOpcodes)):
		opcodes.append(list(posibleOpcodes[i])[0])
	return opcodes

def run(opcodes, filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	registers = [0, 0, 0, 0]
	for line in lines:
		operands = line.split(' ')
		opcode = opcodes[int(operands[0])]
		a = int(operands[1])
		b = int(operands[2])
		c = int(operands[3])
		registers = instruction(opcode, a, b, c, registers)

	return registers

def solve(sampleFilename, programFilename):
	opcodes = findOpcodes(sampleFilename)
	registers = run(opcodes, programFilename)
	print(registers[0])

solve('input1.txt', 'input2.txt')
