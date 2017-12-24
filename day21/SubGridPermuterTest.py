import SubGridPermuter

def flipVerticallyShouldFlipSubGridVertically():
	# Given
	inputSubGrid = [['1', '2', '3'], ['4', '5', '6'], ['7', '8', '9']]
	# When
	result = SubGridPermuter.flipVertically(inputSubGrid)
	# Then
	assert(result == [['7', '8', '9'], ['4', '5', '6'], ['1', '2', '3']])

def flipHorizontallyShouldFlipSubGridHorizontally():
	# Given
	inputSubGrid = [['1', '2', '3'], ['4', '5', '6'], ['7', '8', '9']]
	# When
	result = SubGridPermuter.flipHorizontally(inputSubGrid)
	# Then
	assert(result == [['3', '2', '1'], ['6', '5', '4'], ['9', '8', '7']])

def rotateShouldRotateSubGridClockwise():
	# Given
	inputSubGrid = [['1', '2', '3', '4'], ['5', '6', '7', '8'], ['9', '10', '11', '12'], ['13', '14', '15', '16']]
	# When
	result = SubGridPermuter.rotate(inputSubGrid)
	# Then
	assert(result == [['13', '9', '5', '1'], ['14', '10', '6', '2'], ['15', '11', '7', '3'], ['16', '12', '8', '4']])

flipVerticallyShouldFlipSubGridVertically()
flipHorizontallyShouldFlipSubGridHorizontally()
rotateShouldRotateSubGridClockwise()