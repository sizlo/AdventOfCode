class Coordinate:
	def __init__(self, x, y):
		self.x = x
		self.y = y

def findClosestPoint(here, points):
	closestDistance = 100000
	numAtClosestDistance = 0
	closestId = None
	for pointId in range(0, len(points)):
		point = points[pointId]
		distance = abs(here.x - point.x) + abs(here.y - point.y)
		if distance == closestDistance:
			numAtClosestDistance += 1
		elif distance < closestDistance:
			closestDistance = distance
			closestId = pointId
			numAtClosestDistance = 1
	if numAtClosestDistance == 1:
		return closestId
	else:
		return None

def solve(filename):
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

	numClosestToPoint = [0] * len(points)
	disallowedPoints = []
	for y in range(0, bounds.y):
		for x in range(0, bounds.x):
			thisCoordinate = Coordinate(x, y)
			closestPoint = findClosestPoint(thisCoordinate, points)
			if closestPoint is not None:
				numClosestToPoint[closestPoint] += 1
			if x == 0 or y == 0 or x == bounds.x or y == bounds.y:
				if closestPoint not in disallowedPoints:
					disallowedPoints.append(closestPoint)

	highestNumClosestToPoint = 0
	highestPointId = None
	for pointId in range(0, len(points)):
		if numClosestToPoint[pointId] > highestNumClosestToPoint and pointId not in disallowedPoints:
			highestNumClosestToPoint = numClosestToPoint[pointId]
			highestPointId = pointId

	print(filename + ': ' + str(highestPointId) + ' - ' + str(highestNumClosestToPoint))

solve('exampleinput1.txt')
solve('input1.txt')