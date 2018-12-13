import copy

UP = 1
DOWN = 2
LEFT = 3
RIGHT = 4

TURN_LEFT = 0
GO_STRAIGHT = 1
TURN_RIGHT = 2

NUM_CHOICES = 3

class Cart:
	def __init__(self, x, y, direction):
		self.x = x
		self.y = y
		self.direction = direction
		self.lastIntersection = -1
		self.crashed = False

	def __str__(self):
		return '(' + str(self.x) + ',' + str(self.y) + ') ' + self.symbol()

	def __lt__(self, other):
		if self.y == other.y:
			return self.x < other.x
		return self.y < other.y

	def symbol(self):
		symbols = {UP: '^', DOWN: 'v', LEFT: '<', RIGHT: '>'}
		return symbols[self.direction]

	def moveForward(self):
		moves = {UP: [-1, 0], DOWN: [1, 0], LEFT: [0, -1], RIGHT: [0, 1]}
		self.x += moves[self.direction][1]
		self.y += moves[self.direction][0]

	def doCorner(self, cornerType):
		if self.direction == UP and cornerType == '/':
			self.direction = RIGHT
		elif self.direction == UP and cornerType == '\\':
			self.direction = LEFT

		elif self.direction == DOWN and cornerType == '/':
			self.direction = LEFT
		elif self.direction == DOWN and cornerType == '\\':
			self.direction = RIGHT

		elif self.direction == LEFT and cornerType == '/':
			self.direction = DOWN
		elif self.direction == LEFT and cornerType == '\\':
			self.direction = UP

		elif self.direction == RIGHT and cornerType == '/':
			self.direction = UP
		elif self.direction == RIGHT and cornerType == '\\':
			self.direction = DOWN

	def doIntersection(self):
		intersectionChoice = (self.lastIntersection + 1) % NUM_CHOICES

		if self.direction == UP and intersectionChoice == TURN_LEFT:
			self.direction = LEFT
		elif self.direction == UP and intersectionChoice == TURN_RIGHT:
			self.direction = RIGHT

		elif self.direction == DOWN and intersectionChoice == TURN_LEFT:
			self.direction = RIGHT
		elif self.direction == DOWN and intersectionChoice == TURN_RIGHT:
			self.direction = LEFT

		elif self.direction == LEFT and intersectionChoice == TURN_LEFT:
			self.direction = DOWN
		elif self.direction == LEFT and intersectionChoice == TURN_RIGHT:
			self.direction = UP

		elif self.direction == RIGHT and intersectionChoice == TURN_LEFT:
			self.direction = UP
		elif self.direction == RIGHT and intersectionChoice == TURN_RIGHT:
			self.direction = DOWN

		self.lastIntersection = intersectionChoice


class Track:
	def __init__(self, mapString):
		mapLines = mapString.splitlines()

		self.map = []
		self.carts = []

		self.height = len(mapLines)
		self.width = len(mapLines[0])

		for y in range(0, self.height):
			self.map.append([])
			for x in range(0, self.width):
				trackPiece = mapLines[y][x]

				if trackPiece == '<':
					self.carts.append(Cart(x, y, LEFT))
					trackPiece = '-'
				elif trackPiece == '>':
					self.carts.append(Cart(x, y, RIGHT))
					trackPiece = '-'
				elif trackPiece == '^':
					self.carts.append(Cart(x, y, UP))
					trackPiece = '|'
				elif trackPiece == 'v':
					self.carts.append(Cart(x, y, DOWN))
					trackPiece = '|'

				self.map[y].append(trackPiece)

	def __str__(self):
		mapToPrint = copy.deepcopy(self.map)
		for cart in self.carts:
			mapToPrint[cart.y][cart.x] = cart.symbol()

		result = ''
		for y in range(self.height):
			for x in range(self.width):
				result += mapToPrint[y][x]
			result += '\n'

		return result

	def tick(self):
		self.carts.sort()
		for cart in self.carts:
			cart.moveForward()

			for otherCart in self.carts:
				if otherCart.crashed or otherCart == cart:
					continue
				if cart.x == otherCart.x and cart.y == otherCart.y:
					cart.crashed = True
					otherCart.crashed = True

			trackType = self.map[cart.y][cart.x]
			if trackType == '+':
				cart.doIntersection()
			elif trackType in ['/', '\\']:
				cart.doCorner(trackType)


		self.carts = [cart for cart in self.carts if not cart.crashed]

		if len(self.carts) == 1:
			return (self.carts[0].x, self.carts[0].y)
		return None

def solve(filename):
	with open(filename) as file:
		mapString = file.read()

	track = Track(mapString)

	lastCart = None
	while lastCart is None:
		lastCart = track.tick()

	print(filename + ': ' + str(lastCart))

solve('exampleinput2.txt')
solve('input1.txt')