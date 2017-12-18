with open('input.txt') as inFile:
	instructions = inFile.read().splitlines()

registers = {}
lastPlayedSound = None
programCounter = 0

def getRegisterValue(registerName):
	if registerName not in registers.keys():
		registers[registerName] = 0
	return registers[registerName]

def setRegisterValue(registerName, value):
	registers[registerName] = value

def getValue(valueOrRegisterName):
	try:
		return int(valueOrRegisterName)
	except ValueError:
		return getRegisterValue(valueOrRegisterName)

def snd(X):
	global lastPlayedSound
	lastPlayedSound = getValue(X)

def set(X, Y):
	setRegisterValue(X, getValue(Y))

def add(X, Y):
	setRegisterValue(X, getRegisterValue(X) + getValue(Y))

def mul(X, Y):
	setRegisterValue(X, getRegisterValue(X) * getValue(Y))

def mod(X, Y):
	setRegisterValue(X, getRegisterValue(X) % getValue(Y))

def rcv(X):
	if getValue(X) != 0:
		return lastPlayedSound

def jgz(X, Y):
	global programCounter
	if getValue(X) > 0:
		programCounter += getValue(Y) - 1

def doInstruction(instruction):
	parts = instruction.split(' ')
	instruction = parts[0]
	X = Y = None
	if len(parts) > 1:
		X = parts[1]
	if len(parts) > 2:
		Y = parts[2]
	if instruction == 'snd':
		return snd(X)
	elif instruction == 'set':
		return set(X, Y)
	elif instruction == 'mul':
		return mul(X, Y)
	elif instruction == 'add':
		return add(X, Y)
	elif instruction == 'mod':
		return mod(X, Y)
	elif instruction == 'rcv':
		return rcv(X)
	elif instruction == 'jgz':
		return jgz(X, Y)
	else:
		print 'Unhandled instruction: ' + instruction

while programCounter >= 0 and programCounter < len(instructions):
	result = doInstruction(instructions[programCounter])
	if result:
		print result
		break
	programCounter += 1

