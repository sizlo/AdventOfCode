# Shamelesly stolen from https://www.redblobgames.com/grids/hexagons/#distances-axial
def distanceBetween(x1, y1, x2, y2):
	return (abs(x1 - x2) 
          + abs(x1 + y1 - x2 - y2)
          + abs(y1 - y2)) / 2


with open('input.txt') as inFile:
	steps = inFile.read().split(',')

startX = 0
startY = 0
endX = startX
endY = startY
furthestDistance = 0
for step in steps:
	if step == 'n':
		endY -= 1
	elif step == 's':
		endY +=1
	elif step == 'ne':
		endX +=1
		endY -= 1
	elif step == 'se':
		endX += 1
	elif step == 'nw':
		endX -= 1
	elif step == 'sw':
		endX -= 1
		endY += 1
	else:
		print 'Unhandled step ' + step
	furthestDistance = max(furthestDistance, distanceBetween(startX, startY, endX, endY))

print 'Part 1: ' + str(distanceBetween(startX, startY, endX, endY))
print 'Part 2: ' + str(furthestDistance)