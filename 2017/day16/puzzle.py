import sys
import string

def spin(x, programs):
    return programs[-x:] + programs[:len(programs)-x]

def exchange(a, b, programs):
    tmp = programs[a]
    programs[a] = programs[b]
    programs[b] = tmp
    return programs

def partner(a, b, programs):
    return exchange(programs.index(a), programs.index(b), programs)

def doInstruction(instruction, programs):
    if instruction[0] == 's':
        return spin(int(instruction[1:]), programs)
    elif instruction[0] == 'x':
        aAndB = instruction[1:].split('/')
        return exchange(int(aAndB[0]), int(int(aAndB[1])), programs)
    elif instruction[0] == 'p':
        aAndB = instruction[1:].split('/')
        return partner(aAndB[0], aAndB[1], programs)

def run(programs, instructions):
    iterations = 0
    startState = list(programs)

    while True:
        iterations += 1
        for instruction in instructions:
            programs = doInstruction(instruction, programs)
        if iterations == 1:
            part1 = ''.join(programs)
        if programs == startState:
            break

    iterationsToReachSameState = iterations
    while iterations + iterationsToReachSameState < 1000000000:
        iterations += iterationsToReachSameState

    while True:
        iterations += 1
        for instruction in instructions:
            programs = doInstruction(instruction, programs)
        if iterations == 1000000000:
            break
    
    part2 = ''.join(programs)
    return part1, part2


def test():
    instructions = 's1,x3/4,pe/b'.split(',')
    programs = list(string.ascii_lowercase)[:5]
    expectedPart1 = 'baedc'
    actualPart1, actualPart2 = run(programs, instructions)
    if actualPart1 != 'baedc':
        print 'Test failed, expected ' + expectedPart1 + ' but got ' + actualPart1
        sys.exit()

test()

with open('input.txt') as inFile:
    instructions = inFile.read().split(',')
programs = list(string.ascii_lowercase)[:16]
part1, part2 = run(programs, instructions)
print 'Part 1: ' + part1
print 'Part 2: ' + part2