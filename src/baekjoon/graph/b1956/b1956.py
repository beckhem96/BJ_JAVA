import sys

input = sys.stdin.readline
INF = float('inf')
V, E = map(int, input().split())

board = [[INF for _ in range(V + 1)] for _ in range(V + 1)]
for i in range(1, V + 1):
    board[i][i] = 0

for _ in range(E):
    a, b, c = map(int, input().split())
    board[a][b] = c

for k in range(1, V + 1):
    for i in range(1, V + 1):
        for j in range(1, V + 1):
            board[i][j] = min(board[i][j], board[i][k] + board[k][j])

min_cycle = INF
for i in range(1, V + 1):
    for j in range(1, V + 1):
        if i == j:
            continue
        # i -> j 경로와 j -> i 결로가 모두 존재해야 사이클
        if board[i][j] != INF and board[j][i] != INF:
            min_cycle = min(min_cycle, board[i][j] + board[j][i])
if min_cycle == INF:
    print(-1)
else:
    print(min_cycle)
