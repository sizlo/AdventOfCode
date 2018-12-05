def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	total = 0
	for line in lines:
		operation = line[0]
		operand = int(line[1:])
		if operation is '+':
			total += operand
		elif operation is '-':
			total -= operand
		else:
			print('Unkown operation: ' + operation)

	print(filename + ': ' + str(total))

solve('exampleinput1.txt')
solve('exampleinput2.txt')
solve('exampleinput3.txt')
solve('exampleinput4.txt')
solve('input1.txt')