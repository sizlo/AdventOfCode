class SpaceObject:
    def __init__(self, name):
        self.name = name
        self.orbitingObjects = []
        self.directOrbits = 0
        self.indirectOrbits = 0
        self.parent = None

    def addOrbitingObject(self, other):
        self.orbitingObjects.append(other)

    def setParent(self, parent):
        self.parent = parent

    def calculateOrbits(self, parents=0):
        orbits = parents
        for orbitingObject in self.orbitingObjects:
            orbits += orbitingObject.calculateOrbits(parents=parents+1)
        return orbits

    def lookForSantaInChildren(self, path):
        path.append(self.name)

        if self.name == 'SAN':
            return True, path

        for orbitingObject in self.orbitingObjects:
            found, santapath = orbitingObject.lookForSantaInChildren(path.copy())
            if found:
                return True, santapath

        return False, path

    def findSanta(self, path=[]):
        found, santapath = self.lookForSantaInChildren(path.copy())
        if found:
            return santapath

        path.append(self.name)
        return self.parent.findSanta(path=path)

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
    spaceObjects[orbitingObjectName].setParent(spaceObjects[orbitedObjectName])

path = spaceObjects['YOU'].findSanta()
print(' -> '.join(path))
print(len(path) - 3)
