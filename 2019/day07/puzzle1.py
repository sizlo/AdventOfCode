from computer import Computer
import itertools

class Amplifier:
    def __init__(self, program, phase, inputSignal):
        self.computer = Computer(program, [phase, inputSignal])

    def execute(self):
        self.computer.execute()

    def getOutputSignal(self):
        return self.computer.getOutput()

with open('input1.txt') as file:
    program = list(map(int, file.read().split(',')))

phasePermutations = itertools.permutations([0, 1, 2, 3, 4])
maxThrust = None
bestPhases = None
for phases in phasePermutations:
    nextInputSignal = 0
    for phase in phases:
        amplifier = Amplifier(program.copy(), phase, nextInputSignal)
        amplifier.execute()
        nextInputSignal = amplifier.getOutputSignal()
    if maxThrust is None or nextInputSignal > maxThrust:
        maxThrust = nextInputSignal
        bestPhases = phases

print(f'{bestPhases} -> {maxThrust}')
