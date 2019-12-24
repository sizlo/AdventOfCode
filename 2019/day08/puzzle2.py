with open ('input1.txt') as file:
    pixels = list(map(int, list(file.read())))

width = 25
height = 6
image = [[], [], [], [], [], []]
x = 0
y = 0
for pixel in pixels:
    row = image[y]

    if x >= len(row):
        row.append(pixel)
    else:
        if row[x] == 2:
            row[x] = pixel

    x += 1
    if x >= width:
        x = 0
        y += 1
        if y >= height:
            y = 0

for row in image:
    print(''.join(map(str, row)))
