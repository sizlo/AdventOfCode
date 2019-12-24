from computer2 import Computer
import itertools

class Amplifier:
    def __init__(self, program, phase):
        self.computer = Computer(program)
        self.computer.setInput(phase)
        self.computer.execute(pauseAfterInput=True)

    def execute(self, inputSignal):
        if not self.computer.isHalted():
            self.computer.setInput(inputSignal)
            self.computer.execute()

    def getOutputSignal(self):
        return self.computer.getOutput()

    def isHalted(self):
        return self.computer.isHalted()

with open('input1.txt') as file:
    program = list(map(int, file.read().split(',')))

phasePermutations = itertools.permutations([5, 6, 7, 8, 9])
maxThrust = None
bestPhases = None

def allAmplifiersAreHalted(amplifiers):
    for amplifier in amplifiers:
        if not amplifier.isHalted():
            return False
    return True

for phases in phasePermutations:
    amplifiers = []
    for phase in phases:
        amplifiers.append(Amplifier(program.copy(), phase))

    nextInputSignal = 0
    amplifierIndex = 0
    while not allAmplifiersAreHalted(amplifiers):
        for amplifier in amplifiers:
            amplifier.execute(nextInputSignal)
            nextInputSignal = amplifier.getOutputSignal()

    if maxThrust is None or nextInputSignal > maxThrust:
        maxThrust = nextInputSignal
        bestPhases = phases

print(f'{bestPhases} -> {maxThrust}')
