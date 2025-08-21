import sys
from collections import deque

input = sys.stdin.readline

def solve_optimizied():
    board = [input().strip() for _ in range(8)]
    queue = deque([(7, 0)])

    time = 0
    while queue:
        # 8초 이상 지나면 모든 벽이 사라져서 자유롭게 이동 가능
        if time > 8:
            print(1)
            return
        # 같은 시간대에 방문할 수 있는 모든 위치를 한번에 처리
        # visited_in_level: 이번 1초 동안 새로 방문한 위치 (중복 제거용)
        visited_in_level = set()

        for _ in range(len(queue)):
            r, c = queue.popleft()

            # # 현재 머무는 위치로 벽이 내려온 경우 - '이동(1초가 지난)'한 욱제가 벽과 충돌하는지
            if r - time >= 0 and board[r - time][c] == '#':
                continue

            # 목적지에 도달하면 성공
            if r == 0 and c == 7:
                print(1)
                return

            # 9방향 탐색
            for dr, dc in [(0, 0), (0, 1), (0, -1), (1, 0), (-1, 0), (1, 1), (1, -1), (-1, 1), (-1, -1)]:
                nr, nc = r + dr, c + dc
                if 0<= nr < 8 and 0 <= nc < 8:
                    # 이동할 위치(nr, nc)가 현재 시간에 안전한지 확인 - '현재시간'이 중요
                    # (nr - time)은 현재 시간 기준으로 벽의 원래 위치를 역산하는 것
                    is_safe_now = (nr - time < 0) or (board[nr-time][nc] == '.') # nr-time < 0은 어차피 8초가 다 지나서 벽이 없음을 의미
                    if is_safe_now:
                        visited_in_level.add((nr, nc))
        # 이번 1초 동안 새로 방문한 곳들을 큐에 추가
        for pos in visited_in_level:
            queue.append(pos)
        # 시간 경과 (벽이 한칸 내려옴)
        time += 1
    print(0)

solve_optimizied()