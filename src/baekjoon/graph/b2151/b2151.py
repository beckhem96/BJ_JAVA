import sys
from collections import deque

input = sys.stdin.readline

def solve():
    N = int(input())
    grid = []
    doors = []
    for r in range(N):
        row = list(input().strip())
        for c, val in enumerate(row):
            if val == '#':
                doors.append((r, c))
        grid.append(row)

    # mirrors[r][c][dir]: (r, c)에 dir 방향으로 도달하기 위한 최소 거울 수
    mirrors = [[[float('inf')] * 4 for _ in range(N)] for _ in range(N)]

    # 덱(deque) 초기화. (거울수, 행, 열, 방향)
    # 거울 수를 맨 앞에 두면 우선순위 큐처럼 동작하지만, 0-1 BFS에서는 앞에 넣고 뒤에 넣는 것으로 구분
    dq = deque()

    # 방향: 0:상, 1:하, 2:좌, 3:우
    dr = [-1, 1, 0, 0]
    dc = [0, 0, -1, 1]

    start_r, start_c = doors[0]

    # 시작점에서 네 방향으로 출발하는 초기 상태 설정
    for i in range(4):
        mirrors[start_r][start_c][i] = 0
        dq.append((0, start_r, start_c, i))

    while dq:
        m_count, r, c, direction = dq.popleft()

        if m_count > mirrors[r][c][direction]:
            continue

        # 1. 직진 (비용 0)
        nr, nc = r + dr[direction], c + dc[direction]
        if 0 <= nr < N and 0 <= nc < N and grid[nr][nc] != '*':
            if mirrors[nr][nc][direction] > m_count:
                mirrors[nr][nc][direction] = m_count
                dq.appendleft((m_count, nr, nc, direction))

        # 2. 회전 (비용 1) - 거울 설치 가능 장소에서만
        if grid[r][c] == '!':
            # 현재 방향이 상(0)/하(1)이면 좌(2)/우(3)로 회전
            if direction <= 1:
                turn_dirs = [2, 3]
            # 현재 방향이 좌(2)/우(3)이면 상(0)/하(1)로 회전
            else:
                turn_dirs = [0, 1]

            for next_dir in turn_dirs:
                nr, nc = r + dr[next_dir], c + dc[next_dir]
                if 0 <= nr < N and 0 <= nc < N and grid[nr][nc] != '*':
                    if mirrors[nr][nc][next_dir] > m_count + 1:
                        mirrors[nr][nc][next_dir] = m_count + 1
                        dq.append((m_count + 1, nr, nc, next_dir))

    end_r, end_c = doors[1]
    print(min(mirrors[end_r][end_c]))

solve()