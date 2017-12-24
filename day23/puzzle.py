class Program:
	def __init__(self):
		self.registers = {}
		self.programCounter = 0
		self.numMulInvocations = 0

	def __str__(self):
		result = 'programCounter: ' + str(self.programCounter) + ' numMulInvocations: ' + str(self.numMulInvocations) + '\n'
		result += str(self.registers)
		return result

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

	def set(self, X, Y):
		self.setRegisterValue(X, self.getValue(Y))

	def sub(self, X, Y):
		self.setRegisterValue(X, self.getRegisterValue(X) - self.getValue(Y))

	def mul(self, X, Y):
		self.setRegisterValue(X, self.getRegisterValue(X) * self.getValue(Y))
		self.numMulInvocations += 1

	def jnz(self, X, Y):
		if self.getValue(X) != 0:
			self.programCounter += self.getValue(Y) - 1

	def doInstruction(self, instruction):
		parts = instruction.split(' ')
		instruction = parts[0]
		X = Y = None
		if len(parts) > 1:
			X = parts[1]
		if len(parts) > 2:
			Y = parts[2]
		if instruction == 'set':
			self.set(X, Y)
		elif instruction == 'mul':
			self.mul(X, Y)
		elif instruction == 'sub':
			self.sub(X, Y)
		elif instruction == 'jnz':
			self.jnz(X, Y)
		else:
			print 'Unhandled instruction: ' + instruction

	def run(self, instructions):
		while self.programCounter >= 0 and self.programCounter < len(instructions):
			self.doInstruction(instructions[self.programCounter])
			self.programCounter += 1

with open('input.txt') as inFile:
	instructions = inFile.read().splitlines()
program = Program()
program.run(instructions)
print 'Part 1: ' + str(program.numMulInvocations)

def isPrime(value):
	for i in range(2, value):
		if value % i == 0:
			return False
	return True

b = 106700
h = 0
while b <= 123700:
	if not isPrime(b):
		h += 1
	b += 17
print 'Part 2: ' + str(h)

