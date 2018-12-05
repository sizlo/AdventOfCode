with open('input.txt') as inFile:
	lines = inFile.read().splitlines()

def countLetters(word):
	counts = [0] * 26
	for char in word:
		index = ord(char) - ord('a')
		counts[index] += 1
	return counts

totalValid = 0
for line in lines:
	valid = True
	words = line.split()
	for index in range(0, len(words)):
		thisWord = words[index]
		letterCount = countLetters(thisWord)
		otherWords = [x for i,x in enumerate(words) if i!=index]
		for otherWord in otherWords:
			otherLetterCount = countLetters(otherWord)
			if letterCount == otherLetterCount:
				valid = False
	if valid:
		totalValid += 1

print(totalValid)