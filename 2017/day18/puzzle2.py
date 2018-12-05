with open('input.txt') as inFile:
	instructions = inFile.read().splitlines()

class Program:
	def __init__(self, programId):
		self.registers = {}
		self.registers['p'] = programId
		self.programCounter = 0
		self.queue = []
		self.waiting = False
		self.otherProgram = None
		self.numberOfSends = 0

	def getRegisterValue(self, registerName):
		if registerName not in self.registers.keys():
			self.registers[registerName] = 0
		return self.registers[registerName]

	def setRegisterValue(self, registerName, value):
		self.registers[registerName] = value

	def getValue(self, valueOrRegisterName):
		try:
			return int(valueOrRegisterName)
		except ValueError:
			return self.getRegisterValue(valueOrRegisterName)

	def snd(self, X):
		self.queue.append(self.getValue(X))
		self.numberOfSends += 1

	def set(self, X, Y):
		self.setRegisterValue(X, self.getValue(Y))

	def add(self, X, Y):
		self.setRegisterValue(X, self.getRegisterValue(X) + self.getValue(Y))

	def mul(self, X, Y):
		self.setRegisterValue(X, self.getRegisterValue(X) * self.getValue(Y))

	def mod(self, X, Y):
		self.setRegisterValue(X, self.getRegisterValue(X) % self.getValue(Y))

	def rcv(self, X):
		if len(self.otherProgram.queue) > 0:
			self.setRegisterValue(X, self.otherProgram.queue[0])
			self.otherProgram.queue = self.otherProgram.queue[1:]
			self.waiting = False
		else:
			self.programCounter -= 1
			self.waiting = True

	def jgz(self, X, Y):
		if self.getValue(X) > 0:
			self.programCounter += self.getValue(Y) - 1

	def doInstruction(self, instruction):
		parts = instruction.split(' ')
		instruction = parts[0]
		X = Y = None
		if len(parts) > 1:
			X = parts[1]
		if len(parts) > 2:
			Y = parts[2]
		if instruction == 'snd':
			return self.snd(X)
		elif instruction == 'set':
			return self.set(X, Y)
		elif instruction == 'mul':
			return self.mul(X, Y)
		elif instruction == 'add':
			return self.add(X, Y)
		elif instruction == 'mod':
			return self.mod(X, Y)
		elif instruction == 'rcv':
			return self.rcv(X)
		elif instruction == 'jgz':
			return self.jgz(X, Y)
		else:
			print 'Unhandled instruction: ' + instruction

	def tick(self):
		self.doInstruction(instructions[self.programCounter])
		self.programCounter += 1

def hasTerminated(program):
	return program.programCounter < 0 or program.programCounter >= len(instructions)

def deadlock(program0, program1):
	return program0.waiting and program1.waiting

program0 = Program(0)
program1 = Program(1)
program0.otherProgram = program1
program1.otherProgram = program0

while True:
	if hasTerminated(program0) and hasTerminated(program1):
		print 'Both terminated'
		break
	if deadlock(program0, program1):
		print 'Deadlock'
		break
	if not hasTerminated(program0):
		program0.tick()
	if not hasTerminated(program1):
		program1.tick()

print program1.numberOfSends

