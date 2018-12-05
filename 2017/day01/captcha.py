def calculateSum(inputString, distanceToNextDigit):
	digits = list(inputString)
	length = len(digits)
	matchingDigits = 0

	for thisIndex in range(0, length):
		nextIndex = (thisIndex + distanceToNextDigit) % length
		thisDigit = digits[thisIndex]
		nextDigit = digits[nextIndex]
		if thisDigit == nextDigit:
			matchingDigits += int(thisDigit)

	return matchingDigits