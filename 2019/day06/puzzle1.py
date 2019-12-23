class SpaceObject:
    def __init__(self, name):
        self.name = name
        self.orbitingObjects = []
        self.directOrbits = 0
        self.indirectOrbits = 0

    def addOrbitingObject(self, other):
        self.orbitingObjects.append(other)

    def calculateOrbits(self, parents=0):
        orbits = parents
        for orbitingObject in self.orbitingObjects:
            orbits += orbitingObject.calculateOrbits(parents=parents+1)
        return orbits

with open('input1.txt') as file:
    lines = file.read().splitlines()

spaceObjects = {}
for line in lines:
    objectNames = line.split(')')
    for name in objectNames:
        if name not in spaceObjects.keys():
            spaceObjects[name] = SpaceObject(name)
    orbitedObjectName = objectNames[0]
    orbitingObjectName = objectNames[1]
    spaceObjects[orbitedObjectName].addOrbitingObject(spaceObjects[orbitingObjectName])

print(spaceObjects['COM'].calculateOrbits())
