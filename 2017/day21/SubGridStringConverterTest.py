import SubGridStringConverter

def fromStringShouldMakeCorrectSubGrid():
	# Given
	inputString = '123/456/789'
	# When
	result = SubGridStringConverter.fromString(inputString)
	# Then
	assert(result == [['1', '2', '3'],['4', '5', '6'],['7', '8', '9']])

def toStringShouldMakeCorrectString():
	# Given
	inputSubGrid = [['a', 'b', 'c', 'd'], ['e', 'f', 'g', 'h'], ['i', 'j', 'k', 'l'], ['m', 'n', 'o', 'p']]
	# When
	result = SubGridStringConverter.toString(inputSubGrid)
	# Then
	assert(result == 'abcd/efgh/ijkl/mnop')

fromStringShouldMakeCorrectSubGrid()
toStringShouldMakeCorrectString()