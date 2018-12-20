import re

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

PRINT = False

class Program:
	def __init__(self, programString):
		programLines = programString.splitlines()

		matches = re.search('#ip (\\d+)', programLines[0])

		self.instructionRegister = int(matches.group(1))
		self.instructionPointer = 0
		self.instructions = programLines[1:]
		self.registers = [0] * 6

	def __str__(self):
		return 'ip=' + str(self.instructionPointer) + ' ' + str(self.registers)

	def bumpInstructionPointer(self):
		self.instructionPointer += 1

	def readInstructionPointer(self):
		self.registers[self.instructionRegister] = self.instructionPointer

	def writeInstructionPointer(self):
		self.instructionPointer = self.registers[self.instructionRegister]

	def instruction(self, opcode, a, b, c):
		if opcode == 'addr':
			self.registers[c] = self.registers[a] + self.registers[b]
		elif opcode == 'addi':
			self.registers[c] = self.registers[a] + b
		elif opcode == 'mulr':
			self.registers[c] = self.registers[a] * self.registers[b]
		elif opcode == 'muli':
			self.registers[c] = self.registers[a] * b
		elif opcode == 'banr':
			self.registers[c] = self.registers[a] & self.registers[b]
		elif opcode == 'bani':
			self.registers[c] = self.registers[a] & b
		elif opcode == 'borr':
			self.registers[c] = self.registers[a] | self.registers[b]
		elif opcode == 'bori':
			self.registers[c] = self.registers[a] | b
		elif opcode == 'setr':
			self.registers[c] = self.registers[a]
		elif opcode == 'seti':
			self.registers[c] = a
		elif opcode == 'gtir':
			if a > self.registers[b]:
				self.registers[c] = 1
			else:
				self.registers[c] = 0
		elif opcode == 'gtri':
			if self.registers[a] > b:
				self.registers[c] = 1
			else:
				self.registers[c] = 0
		elif opcode == 'gtrr':
			if self.registers[a] > self.registers[b]:
				self.registers[c] = 1
			else:
				self.registers[c] = 0
		elif opcode == 'eqir':
			if a == self.registers[b]:
				self.registers[c] = 1
			else:
				self.registers[c] = 0
		elif opcode == 'eqri':
			if self.registers[a] == b:
				self.registers[c] = 1
			else:
				self.registers[c] = 0
		elif opcode == 'eqrr':
			if self.registers[a] == self.registers[b]:
				self.registers[c] = 1
			else:
				self.registers[c] = 0
		else:
			raise Exception('Unknown opcode ' + opcode)

	def run(self):
		while self.instructionPointer >= 0 and self.instructionPointer < len(self.instructions):
			thisInstruction = self.instructions[self.instructionPointer]
			parts = thisInstruction.split(' ')
			opcode, a, b, c = parts[0], int(parts[1]), int(parts[2]), int(parts[3])
			
			self.readInstructionPointer()
			toPrint = str(self)
			self.instruction(opcode, a, b, c)
			self.writeInstructionPointer()

			toPrint += ' ' + thisInstruction + ' ' + str(self.registers)
			if PRINT:
				print(toPrint)
			
			self.bumpInstructionPointer()

def solve(filename):
	with open(filename) as file:
		programString = file.read()

	program = Program(programString)
	program.run()

	print(filename + ': ' + str(program.registers[0]))

solve('exampleinput1.txt')
solve('input1.txt')
