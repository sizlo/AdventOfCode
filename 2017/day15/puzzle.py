class Generator:
	def __init__(self, startValue, factor, multiple=1):
		self.lastValue = startValue
		self.factor = factor
		self.divisor = 2147483647
		self.multiple = multiple

	def next(self):
		self.lastValue = (self.lastValue * self.factor) % self.divisor
		while self.lastValue % self.multiple != 0:
			self.lastValue = (self.lastValue * self.factor) % self.divisor
		return self.lastValue

def toBinaryString(num):
	return bin(num)[2:]

def lower16BitsMatch(lhs, rhs):
	return lhs % 65536 == rhs % 65536


def part1():
	genA = Generator(116, 16807)
	genB = Generator(299, 48271)

	matches = 0
	for i in range(0, 40000000):
		if lower16BitsMatch(genA.next(), genB.next()):
			matches += 1

	print 'Part 1: ' + str(matches)

def part2():
	genA = Generator(116, 16807, 4)
	genB = Generator(299, 48271, 8)

	matches = 0
	for i in range (0, 5000000):
		if lower16BitsMatch(genA.next(), genB.next()):
			matches += 1

	print 'Part 2: ' + str(matches)

part1()
part2()