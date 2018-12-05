with open('input.txt') as inFile:
	lines = inFile.read().splitlines()

totalValid = 0
for line in lines:
	valid = True
	words = line.split()
	for index in range(0, len(words)):
		thisWord = words[index]
		if thisWord in words[index+1:]:
			valid = False
	if valid:
		totalValid += 1

print(totalValid)