def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	total = 0
	index = 0
	history = []

	while True:
		history.append(total)
		line = lines[index]
	
		operation = line[0]
		operand = int(line[1:])
		if operation is '+':
			total += operand
		elif operation is '-':
			total -= operand
		else:
			print('Unkown operation: ' + operation)

		if total in history:
			print(filename + ': ' + str(total))
			break

		index = (index + 1) % len(lines)

solve('exampleinput5.txt')
solve('exampleinput6.txt')
solve('exampleinput7.txt')
solve('exampleinput8.txt')
solve('exampleinput9.txt')
solve('input1.txt')