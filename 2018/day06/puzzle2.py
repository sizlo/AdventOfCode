class Coordinate:
	def __init__(self, x, y):
		self.x = x
		self.y = y

def findDistanceSum(here, points):
	distanceSum = 0
	for pointId in range(0, len(points)):
		point = points[pointId]
		distance = abs(here.x - point.x) + abs(here.y - point.y)
		distanceSum += distance
	return distanceSum

def solve(filename, threshold):
	with open(filename) as file:
		lines = file.read().splitlines()

	points = []
	bounds = Coordinate(0, 0)
	for line in lines:
		point = Coordinate(int(line.split(', ')[0]), int(line.split(', ')[1]))
		if point.x > bounds.x:
			bounds.x = point.x
		if point.y > bounds.y:
			bounds.y = point.y
		points.append(point)
	bounds.x += 1
	bounds.y += 1

	totalWithinThreshold = 0

	for y in range(0, bounds.y):
		for x in range(0, bounds.x):
			thisCoordinate = Coordinate(x, y)
			distanceSum = findDistanceSum(thisCoordinate, points)
			if distanceSum < threshold:
				totalWithinThreshold += 1

	print(filename + ': ' + str(totalWithinThreshold))

solve('exampleinput1.txt', 32)
solve('input1.txt', 10000)