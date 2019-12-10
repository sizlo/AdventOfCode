with open('input1.txt') as file:
    program = list(map(int, file.read().split(',')))

program[1] = 12
program[2] = 2

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

print(program[0])
