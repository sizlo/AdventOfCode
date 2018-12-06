def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	fabric = []
	for x in range(0, 1500):
		fabric.append([])
		for y in range(0, 1500):
			fabric[x].append([])

	for line in lines:
		parts = line.split(' ')
		areaId = int(parts[0][1:])
		originX = int(parts[2][:-1].split(',')[0]) + 1
		originY = int(parts[2][:-1].split(',')[1]) + 1
		width = int(parts[3].split('x')[0])
		height = int(parts[3].split('x')[1])

		for x in range(0, width):
			for y in range(0, height):
				fabric[originX + x][originY + y].append(areaId)

	for line in lines:
		parts = line.split(' ')
		areaId = int(parts[0][1:])
		originX = int(parts[2][:-1].split(',')[0]) + 1
		originY = int(parts[2][:-1].split(',')[1]) + 1
		width = int(parts[3].split('x')[0])
		height = int(parts[3].split('x')[1])

		hasCollision = False
		for x in range(0, width):
			for y in range(0, height):
				if len(fabric[originX + x][originY + y]) > 1:
					hasCollision = True
		if not hasCollision:
			print(filename + ': ' + str(areaId))


solve('exampleinput1.txt')
solve('input1.txt')