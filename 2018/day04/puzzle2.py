import re

def solve(filename):
	with open(filename) as file:
		lines = file.read().splitlines()

	lines.sort()

	guards = {}
	currentGuard = None
	sleepStart = None
	sleepEnd = None

	for line in lines:

		matches = re.search('Guard #(\\d*) begins shift', line)
		if matches:
			currentGuard = int(matches.group(1))
			if currentGuard not in guards.keys():
				guards[currentGuard] = [0] * 60
			continue

		matches = re.search('(\\d*)\\] falls asleep', line)
		if matches:
			sleepStart = int(matches.group(1))
			continue

		matches = re.search('(\\d*)\\] wakes up', line)
		if matches:
			sleepEnd = int(matches.group(1))
			for minute in range(sleepStart, sleepEnd):
				guards[currentGuard][minute] += 1
			continue



	minuteWithMostSleepInstances = 0
	mostSleepInstances = 0
	guardWithMinuteWithMostSleepInstances = None

	for guardId, minutes in guards.items():
		for minute in range(0, 60):
			sleepInstances = minutes[minute]
			if sleepInstances > mostSleepInstances:
				mostSleepInstances = sleepInstances
				minuteWithMostSleepInstances = minute
				guardWithMinuteWithMostSleepInstances = guardId


	print(filename + ': ' + str(guardWithMinuteWithMostSleepInstances * minuteWithMostSleepInstances))


solve('exampleinput1.txt')
solve('input1.txt')