from collections import deque

def solution(storage, requests):
    # 세로, 가로
    n, m = len(storage), len(requests[0])
    board = [list(row) for row in storage]

    def build_outside():
        H, W = n + 2, m + 2 # 패딩, 이걸 써야 밖깥 경계 표현이 쉬워짐
        vis = [[False] * W for _ in range(H)]
        q = deque([(0, 0)])
        vis[0][0] = True

        while q:
            r, c = q.popleft()
            for dr, dc in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
                nr, nc = r + dr, c + dc
                if not (0 <= nr < H and 0 <= nc < W) or vis[nr][nc]:
                    continue
                # 패딩 내부가 실제 창고 좌표라면 빈칸만 통과
                if 1 <= nr < n and 1 <= nc < m:
                    if board[nr - 1][nc - 1] != '.':
                        continue
                vis[nr][nc] = True
                q.appendleft((nr, nc))
        return vis

    for req in requests:
        ch = req[0]
        if len(req) == 2:
            for i in range(n):
                for j in range(m):
                    if board[i][j] == req[0]:
                        board[i][j] = '.'

        else:
            outside = build_outside()
            to_del = []
            for i in range(n):
                for j in range(m):
                    if board[i][j] != ch:
                        continue
                    pi, pj = i + 1, j + 1
                    if (outside[pi-1][pj] != ch or outside[pi+1][pj] or outside[pi][pj-1] or outside[pi][pj+1]):
                        to_del.append((i, j))
            for i, j in to_del:
                board[i][j] = '.'
    return sum(1 for i in range(n) for j in range(m) if board[i][j] != '.')

print(solution(		["HAH", "HBH", "HHH", "HAH", "HBH"], ["C", "B", "B", "B", "B", "H"]))