def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	fabric = []
	for i in range(0, 1500):
		fabric.append([0] * 1500)

	for line in lines:
		parts = line.split(' ')
		areaId = int(parts[0][1:])
		originX = int(parts[2][:-1].split(',')[0]) + 1
		originY = int(parts[2][:-1].split(',')[1]) + 1
		width = int(parts[3].split('x')[0])
		height = int(parts[3].split('x')[1])

		for x in range(0, width):
			for y in range(0, height):
				fabric[originX + x][originY + y] += 1
				
	collisions = 0
	for x in range(0, 1500):
		for y in range(0, 1500):
			if fabric[x][y] > 1:
				collisions += 1
	print(filename + ': ' + str(collisions))


solve('exampleinput1.txt')
solve('input1.txt')