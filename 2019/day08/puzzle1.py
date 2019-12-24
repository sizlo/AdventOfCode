with open ('input1.txt') as file:
    pixels = list(map(int, list(file.read())))

width = 25
height = 6
layers = []
x = 0
y = 0
for pixel in pixels:
    if x == 0 and y == 0:
        layers.append([])
    layer = layers[-1]

    if x == 0:
        layer.append([])
    row = layer[y]

    row.append(pixel)

    x += 1
    if x >= width:
        x = 0
        y += 1
        if y >= height:
            y = 0

leastZeroes = None
onesTimesTwos = None
for layer in layers:
    digitCounts = [0, 0, 0]
    for row in layer:
        for pixel in row:
            digitCounts[pixel] += 1
    if leastZeroes is None or digitCounts[0] < leastZeroes:
        leastZeroes = digitCounts[0]
        onesTimesTwos = digitCounts[1] * digitCounts[2]

print(onesTimesTwos)
