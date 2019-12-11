def is_valid(possibility):
    str_possibility = str(possibility)
    len_possibility = len(str_possibility)
    has_only_two_adjacent_digits = False
    for i in range(1, len_possibility):
        if str_possibility[i] < str_possibility[i-1]:
            return False
        if (str_possibility[i] == str_possibility[i-1]
            and (i-2 < 0 or str_possibility[i] != str_possibility[i-2])
            and (i+1 >= len_possibility or str_possibility[i] != str_possibility[i+1]) ):
            has_only_two_adjacent_digits = True
    return has_only_two_adjacent_digits

lowest_input = 231832
highest_input = 767346

num_valid = 0
for possibility in range(lowest_input, highest_input+1):
    if is_valid(possibility):
        num_valid += 1
print(num_valid)