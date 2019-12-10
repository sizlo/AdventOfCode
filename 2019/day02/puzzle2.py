import sys

def execute(cleanProgram, noun, verb):
    program = cleanProgram.copy()

    program[1] = noun
    program[2] = verb

    pc = 0
    while program[pc] != 99:
        opcode = program[pc]
        lhs = program[pc + 1]
        rhs = program[pc + 2]
        dest = program[pc + 3]
        if opcode == 1:
            program[dest] = program[lhs] + program[rhs]
        elif opcode == 2:
            program[dest] = program[lhs] * program[rhs]
        else:
            print(f'Unhandled opcode, {opcode}')
        pc += 4

    return program[0]

with open('input1.txt') as file:
    program = list(map(int, file.read().split(',')))

for noun in range(0, 100):
    for verb in range(0, 100):
        result = execute(program, noun, verb)
        print(f'{noun} {verb} {result} {100 * noun + verb}')
        if result == 19690720:
            sys.exit()
