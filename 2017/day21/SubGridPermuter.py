def flipVertically(inputSubGrid):
	return list(reversed(inputSubGrid))

def flipHorizontally(inputSubGrid):
	return [list(reversed(row)) for row in inputSubGrid]

def rotate(inputSubGrid):
	rotatedWithTuples = zip(*reversed(inputSubGrid))
	return [list(tupleRow) for tupleRow in rotatedWithTuples]