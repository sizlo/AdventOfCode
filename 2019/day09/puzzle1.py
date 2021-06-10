def getDigit(number, n):
    return number // 10**n % 10

def LOG(msg):
    if True:
        print(msg)

class Computer:
    def __init__(self, program):
        self.pc = 0
        self.modes = []
        self.relativeBase = 0
        self.memory = {}
        for i in range(0, len(program)):
            self.memory[i] = program[i]

    def execute(self):
        while self.get(self.pc) != 99:
            instruction = self.get(self.pc)
            opcode = (10 * getDigit(instruction, 1)) + getDigit(instruction, 0)
            self.modes = [getDigit(instruction, 2), getDigit(instruction, 3), getDigit(instruction, 4)]
            LOG(f'\nPC: {self.pc}, OPCODE: {opcode}, MODES: {self.modes}, REL BASE: {self.relativeBase}')
            if opcode == 1:
                dest = self.get(self.pc + 3)
                self.set(dest, self.getParam(0) + self.getParam(1))
                self.pc += 4
            elif opcode == 2:
                dest = self.get(self.pc + 3)
                self.set(dest, self.getParam(0) * self.getParam(1))
                self.pc += 4
            elif opcode == 3:
                dest = self.get(self.pc + 1)
                self.set(dest, self.getInput())
                self.pc += 2
            elif opcode == 4:
                output = self.getParam(0)
                self.doOutput(output)
                self.pc += 2
            elif opcode == 5:
                if self.getParam(0) != 0:
                    self.pc = self.getParam(1)
                else:
                    self.pc += 3
            elif opcode == 6:
                if self.getParam(0) == 0:
                    self.pc = self.getParam(1)
                else:
                    self.pc += 3
            elif opcode == 7:
                dest = self.get(self.pc + 3)
                if self.getParam(0) < self.getParam(1):
                    self.set(dest, 1)
                else:
                    self.set(dest, 0)
                self.pc += 4
            elif opcode == 8:
                dest = self.get(self.pc + 3)
                if self.getParam(0) == self.getParam(1):
                    self.set(dest, 1)
                else:
                    self.set(dest, 0)
                self.pc += 4
            elif opcode == 9:
                self.relativeBase += self.getParam(0)
                LOG(f'NEW REL BASE: {self.relativeBase}')
                self.pc += 2
            LOG('')

    def get(self, address):
        if address not in self.memory.keys():
            self.memory[address] = 0
        value = self.memory[address]
        LOG(f'READ address: {address}, value: {value}')
        return value

    def set(self, address, value):
        LOG(f'WRITE address: {address}, value: {value}')
        self.memory[address] = value

    def getParam(self, index):
        paramFromProgram = self.get(self.pc + index + 1)
        if self.modes[index] == 1:
            LOG(f'PARAM index: {index}, mode: {self.modes[index]}, value: {paramFromProgram}')
            return paramFromProgram
        elif self.modes[index] == 2:
            LOG(f'PARAM index: {index}, mode: {self.modes[index]}, offset: {paramFromProgram}, address: {paramFromProgram + self.relativeBase}, value: {self.get(paramFromProgram + self.relativeBase)}')
            return self.get(paramFromProgram + self.relativeBase)
        else:
            LOG(f'PARAM index: {index}, mode: {self.modes[index]}, address: {paramFromProgram}, value: {self.get(paramFromProgram)}')
            return self.get(paramFromProgram)

    def getInput(self):
        return 1

    def doOutput(self, value):
        print(f'OUTPUT {value}')

with open('input1.txt') as file:
    program = list(map(int, file.read().split(',')))

computer = Computer(program)
computer.execute()
