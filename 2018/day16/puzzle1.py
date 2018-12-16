OPCODES = [
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
]

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

	matchingOpcodes = []
	for opcode in OPCODES:
		if instruction(opcode, a, b, c, beforeRegisters) == afterRegisters:
			matchingOpcodes.append(opcode)

	return matchingOpcodes

def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	samplesWithThreeOrMoreOpcodes = 0

	for lineNum in range(0, len(lines), 4):
		matchingOpcodes = doSample(lines[lineNum: lineNum + 3])
		if len(matchingOpcodes) >= 3:
			samplesWithThreeOrMoreOpcodes += 1

	print(filename + ': ' + str(samplesWithThreeOrMoreOpcodes))

solve('input1.txt')
