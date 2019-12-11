class Coordindate:
    def __init__(self):
        self.x = 0
        self.y = 0

    def __str__(self):
        return f'[{self.x},{self.y} {self.manhattan()}]'

    def __hash__(self):
        return self.x*10000000 + self.y

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __ne__(self, other):
        return not self == other

    def copy(self):
        result = Coordindate()
        result.x = self.x
        result.y = self.y
        return result

    def manhattan(self):
        return abs(self.x) + abs(self.y)

class WireStatus:
    def __init__(self):
        self.present_wires = []

    def __str__(self):
        return ','.join(self.present_wires)

def process_wire(wire, the_map, wire_name):
    current = Coordindate()
    for instruction in wire:
        direction = instruction[0]
        distance = int(instruction[1:])
        for step in range(0, distance):
            step_value = 1 if direction in ['R', 'D'] else -1
            if direction in ['L', 'R']:
                current.x += step_value
            if direction in ['U', 'D']:
                current.y += step_value
            key = current.copy()
            if key not in the_map.keys():
                the_map[key] = WireStatus()
            if wire_name not in the_map[key].present_wires:
                the_map[key].present_wires.append(wire_name)

def print_map(the_map):
    for coordinate, wire_status in the_map.items():
        print(f'{coordinate}: {wire_status}')

def find_closest_intersection(the_map):
    intersections = []
    for coordinate, wire_status in the_map.items():
        if len(wire_status.present_wires) > 1:
            intersections.append(coordinate)
    return min(intersections, key = lambda coordindate: coordindate.manhattan())


with open('input1.txt') as file:
    lines = file.read().splitlines()
wire_one = lines[0].split(',')
wire_two = lines[1].split(',')

the_map = {}
process_wire(wire_one, the_map, 'one')
process_wire(wire_two, the_map, 'two')
print(find_closest_intersection(the_map).manhattan())

