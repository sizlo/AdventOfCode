import sys

def getFuelRequired(mass):
    return int(mass / 3) - 2

with open('input1.txt') as file:
    lines = file.read().splitlines()

masses = []
for line in lines:
    masses.append(int(line))

totalFuelRequired = 0
for mass in masses:
    lastMass = mass
    while True:
        fuelRequired = getFuelRequired(lastMass)
        if fuelRequired <= 0:
            break
        totalFuelRequired += fuelRequired
        lastMass = fuelRequired

print(totalFuelRequired)