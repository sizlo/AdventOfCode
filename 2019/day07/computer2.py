def getDigit(number, n):
    return number // 10**n % 10

class Computer:
    def __init__(self, program):
        self.pc = 0
        self.program = program
        self.modes = []
        self.input = None
        self.output = None
        self.halted = False

    def execute(self, pauseAfterInput=False):
        while self.program[self.pc] != 99:
            opcode = (10 * getDigit(self.program[self.pc], 1)) + getDigit(self.program[self.pc], 0)
            self.modes = [getDigit(self.program[self.pc], 2), getDigit(self.program[self.pc], 3), getDigit(self.program[self.pc], 4)]
            if opcode == 1:
                dest = self.program[self.pc + 3]
                self.program[dest] = self.getParam(0) + self.getParam(1)
                self.pc += 4
            elif opcode == 2:
                dest = self.program[self.pc + 3]
                self.program[dest] = self.getParam(0) * self.getParam(1)
                self.pc += 4
            elif opcode == 3:
                dest = self.program[self.pc + 1]
                self.program[dest] = self.getInput()
                self.pc += 2
                if pauseAfterInput:
                    return
            elif opcode == 4:
                output = self.getParam(0)
                self.doOutput(output)
                self.pc += 2
                return
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
                dest = self.program[self.pc + 3]
                if self.getParam(0) < self.getParam(1):
                    self.program[dest] = 1
                else:
                    self.program[dest] = 0
                self.pc += 4
            elif opcode == 8:
                dest = self.program[self.pc + 3]
                if self.getParam(0) == self.getParam(1):
                    self.program[dest] = 1
                else:
                    self.program[dest] = 0
                self.pc += 4

        self.halted = True

    def getParam(self, index):
        paramFromProgram = self.program[self.pc + index + 1]
        if self.modes[index] == 1:
            return paramFromProgram
        else:
            return self.program[paramFromProgram]

    def getInput(self):
        return self.input

    def setInput(self, value):
        self.input = value

    def doOutput(self, value):
        self.output = value

    def getOutput(self):
        return self.output

    def isHalted(self):
        return self.halted
