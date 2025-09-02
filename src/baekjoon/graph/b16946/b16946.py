import sys
from collections import deque

input = sys.stdin.readline

N, M = map(int, input().split())
arr = [list(map(int, input().strip())) for _ in range(N)]
group = [[0] * M for _ in range(N)]
groupId = 1
groupMap = {}
dis = [(0, 1), (1, 0), (-1, 0), (0, -1)]

for i in range(N):
    for j in range(M):
        if arr[i][j] == 0 and group[i][j] == 0:
            q = deque([(i, j)])
            group[i][j] = groupId
            count = 1
            while q:
                cur_i, cur_j = q.popleft()
                for di, dj in dis:
                    ni, nj = cur_i + di, cur_j + dj
                    if 0 <= ni < N and 0 <= nj < M and arr[ni][nj] == 0 and group[ni][nj] == 0:
                        group[ni][nj] = groupId
                        q.append((ni, nj))
                        count += 1
            groupMap[groupId] = count
            groupId += 1

result = [[0]*M for _ in range(N)]

for i in range(N):
    for j in range(M):
        if arr[i][j] == 1:
            adjacent_groups = set()
            for di, dj in dis:
                ni, nj = i + di, j + dj
                if 0 <= ni < N and 0 <= nj < M and group[ni][nj] != 0:
                    adjacent_groups.add(group[ni][nj])
            count = 1
            for gid in adjacent_groups:
                count += groupMap[gid]
            result[i][j] = count % 10

for row in result:
    print("".join(map(str, row)))