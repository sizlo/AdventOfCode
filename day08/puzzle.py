def doCondition(lhs, operator, rhs):
	if operator == '>':
		return lhs > rhs
	elif operator == '<':
		return lhs < rhs
	elif operator == '>=':
		return lhs >= rhs
	elif operator == '<=':
		return lhs <= rhs
	elif operator == '==':
		return lhs == rhs
	elif operator == '!=':
		return lhs != rhs
	else:
		print 'Unhandled operator'


with open('input.txt') as inFile:
	lines = inFile.read().splitlines()

registers = {}

for line in lines:
	name = line.split()[0]
	registers[name] = 0

largestDuringExecution = 0
for line in lines:
	parts = line.split()
	registerToAlter = parts[0]
	direction = parts[1]
	amount = int(parts[2])
	registerToCheck = parts[4]
	operator = parts[5]
	rhs = int(parts[6])
	lhs = registers[registerToCheck]
	if doCondition(lhs, operator, rhs):
		if direction == 'inc':
			registers[registerToAlter] += amount
		elif direction == 'dec':
			registers[registerToAlter] -= amount
		else:
			print 'Unhandled direction'
		if registers[registerToAlter] > largestDuringExecution:
			largestDuringExecution = registers[registerToAlter]

largestAtEnd = -9999999
for name in registers.keys():
	if registers[name] > largestAtEnd:
		largestAtEnd = registers[name]

print 'part 1: ' + str(largestAtEnd)
print 'part 2: ' + str(largestDuringExecution)