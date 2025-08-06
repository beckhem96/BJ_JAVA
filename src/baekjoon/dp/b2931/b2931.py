import sys

def solve():
    R, C = map(int, sys.stdin.readline().split())
    grid = [list(sys.stdin.readline().strip()) for _ in range(R)]

    # 출발지 찾기
    start_r, start_c = -1, -1
    for r in range(R):
        for c in range(C):
            if grid[r][c] == 'M':
                start_r, start_c = r, c
                break
        if start_r != -1:
            break

    # 방향: 0:상, 1:하, 2:좌, 3:우
    dr = [-1, 1, 0, 0]
    dc = [0, 0, -1, 1]

    # 1. 첫 흐름 찾기
    r, c, direction = -1, -1, -1
    for i in range(4):
        nr, nc = start_r + dr[i], start_c + dc[i]
        if 0 <= nr < R and 0 <= nc < C and grid[nr][nc] not in ['.', 'Z']:
            r, c, direction = nr, nc, i
            break

    # 2. 시물레이션
    while grid[r][c] != '.':
        block = grid[r][c]

        if block == '1': # └ (아래, 오른쪽)
            # 진입: 위(dir=0) 또는 왼쪽(dir=2)
            if direction == 0: direction = 3 # 위->오른쪽
            else: direction = 1 # 오른쪽->아래
        elif block == '2': # ┌ (위, 오른쪽)
            # 진입: 아래(dir=1) 또는 왼쪽(dir=2)
            if direction == 1: direction = 3 # 아래->오른쪽
            else: direction = 0 # 오른쪽->위
        elif block == '3': # ┐ (위, 왼쪽)
            # 진입: 아래(dir=1) 또는 오른쪽(dir=3)
            if direction == 1: direction = 2 # 아래->왼쪽
            else: direction = 0 # 왼쪽->위
        elif block == '4': # ┘ (아래, 왼쪽)
            # 진입: 위(dir=0) 또는 오른쪽(dir=3)
            if direction == 0: direction = 2 # 위->왼쪽
            else: direction = 1 # 왼쪽->아래

        # '|', '-', '+'는 방향이 바뀌지 않음

        r += dr[direction]
        c += dc[direction]

    # 3.누락된 파이프 결정
    # r, c가 누락된 위치
    connected = [False] * 4 # 상, 하, 좌, 우

    for i in range(4):
        nr, nc = r + dr[i], c + dc[i]
        if not (0 <= nr < R and 0 <= nc < C and grid[nr][nc] != '.'):
            continue

        pipe = grid[nr][nc]
        # i 방향에 있는 파이프가 (r, c) 쪽으로 연결되는지 확인, i 방향으로 흐르고 있다고 생각하면 됨
        if i == 0 and pipe in ['|', '+', '1', '4']: connected[0] = True # 위에서 옴 -> 온다는 것 보다는 연결로 이해하는게 나을듯
        elif i == 1 and pipe in ['|', '+', '2', '3']: connected[1] = True # 아래서 옴
        elif i == 2 and pipe in ['-', '+', '1', '2']: connected[2] = True # 좌측서 옴
        elif i == 3 and pipe in ['-', '+', '3', '4']: connected[3] = True # 우측서 옴

    print(r + 1, c + 1, end=' ')

    if connected == [True, True, True, True]: print('+')
    elif connected == [True, True, False, False]: print('|')
    elif connected == [False, False, True, True]: print('-')
    elif connected == [False, True, False, True]: print('1')
    elif connected == [True, False, False, True]: print('2')
    elif connected == [True, False, True, False]: print('3')
    elif connected == [False, True, True, False]: print('4')

solve()