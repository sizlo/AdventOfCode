def is_valid(possibility):
    str_possibility = str(possibility)
    has_adjacent_digits = False
    for i in range(1, len(str_possibility)):
        if str_possibility[i] < str_possibility[i-1]:
            return False
        if str_possibility[i] == str_possibility[i-1]:
            has_adjacent_digits = True
    return has_adjacent_digits

lowest_input = 231832
highest_input = 767346

num_valid = 0
for possibility in range(lowest_input, highest_input+1):
    if is_valid(possibility):
        num_valid += 1
print(num_valid)