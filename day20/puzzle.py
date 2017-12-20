import sys
from sets import Set

class Vector:
	def __init__(self, x, y, z):
		self.x = x
		self.y = y
		self.z = z

	def add(self, other):
		x = self.x + other.x
		y = self.y + other.y
		z = self.z + other.z
		return Vector(x, y, z)

	def mul(self, factor):
		x = self.x * factor
		y = self.y * factor
		z = self.z * factor
		return Vector(x, y, z)

	def manhattan(self):
		return abs(self.x) + abs(self.y) + abs(self.z)

class Particle:
	def __init__(self, particleString, particleId):
		pva = particleString.split(', ')
		px, py, pz = pva[0][3:-1].split(',')
		vx, vy, vz = pva[1][3:-1].split(',')
		ax, ay, az = pva[2][3:-1].split(',')
		self.position = Vector(long(px), long(py), long(pz))
		self.velocity = Vector(long(vx), long(vy), long(vz))
		self.acceleration = Vector(long(ax), long(ay), long(az))
		self.id = particleId

	def positionAtTime(self, time):
		# v = u + at
		u = self.velocity
		t = time
		at = self.acceleration.mul(t)
		v = u.add(at)
		# s = 1/2(u + v)t
		uplusv = u.add(v)
		halft = 0.5 * t
		s = uplusv.mul(halft)
		# Add displacement to start position
		return self.position.add(s)

	def distanceAtTime(self, time):
		positionAtTime = self.positionAtTime(time)
		return positionAtTime.manhattan()

	def tick(self):
		self.velocity = self.velocity.add(self.acceleration)
		self.position = self.position.add(self.velocity)

with open('input.txt') as inFile:
	lines = inFile.read().splitlines()

particles = []
nextId = 0
for line in lines:
	particles.append(Particle(line, nextId))
	nextId += 1

time = 10000
lowestDistance = float('inf')
lowestId = 0
for particle in particles:
	thisDistance = particle.distanceAtTime(time)
	if thisDistance < lowestDistance:
		lowestDistance = thisDistance
		lowestId = particle.id

print 'Part 1: ' + str(lowestId)

def colliding(particle, otherParticle):
	if particle.id == otherParticle.id:
		return False
	return (particle.position.x == otherParticle.position.x
			and particle.position.y == otherParticle.position.y
			and particle.position.z == otherParticle.position.z)

numCollidedParticlesInLast10Frames = [1] * 10
while True:
	for particle in particles:
		particle.tick()
	collidedParticles = Set()
	for particle in particles:
		for otherParticle in particles:
			if colliding(particle, otherParticle):
				collidedParticles.add(particle)
	for particle in collidedParticles:
		particles.remove(particle)
	numCollidedParticlesInLast10Frames = numCollidedParticlesInLast10Frames[1:]
	numCollidedParticlesInLast10Frames.append(len(collidedParticles))
	if numCollidedParticlesInLast10Frames == [0] * 10:
		break

print 'Part 2: ' + str(len(particles))


