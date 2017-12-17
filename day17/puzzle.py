circularBuffer = [0]

steps = 354
currentPos = 0

for value in range(1, 2018):
    currentPos = (currentPos + steps) % len(circularBuffer)
    currentPos += 1
    circularBuffer.insert(currentPos, value)
    
print 'Part 1: ' + str(circularBuffer[currentPos + 1])

currentPos = 0
for value in range(1, 50000001):
    currentPos = (currentPos + steps) % value
    currentPos += 1
    if currentPos == 1:
        valueAfterZero = value

print 'Part 2: ' + str(valueAfterZero)