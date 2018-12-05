def fromString(stringSubGrid):
	rowStrings = stringSubGrid.split('/')
	rows = []
	for rowString in rowStrings:
		rows.append(list(rowString))
	return rows

def toString(subGrid):
	return '/'.join([''.join(row) for row in subGrid])