import sys
from collections import deque

input = sys.stdin.readline

def solve():
    n = int(input())
    grid = [list(map(int, input().split())) for _ in range(n)]

    # grid에 있는 모든 숫자를 중복 없이, 오름차순으로 정렬하여 저장합니다.
    unique_nums = sorted(list(set(num for row in grid for num in row)))

    # BFS: [min_val, max_val] 범위로 (0, 0)에서 (n-1, n-1)까지 도달 가능한지 확인
    def bfs(min_val, max_val):
        # 시작점이 범위 밖이며 바로 실패
        if not (min_val <= grid[0][0] <= max_val):
            return False

        q = deque([(0, 0)])
        visited  = [[False] * n for _ in range(n)]
        visited[0][0] = True


        while q:
            r, c = q.popleft()
            if r == n - 1 and c == n - 1:
                return True

            for dr, dc in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
                nr, nc = r + dr, c + dc
                if 0 <= nr < n and 0 <= nc < n and not visited[nr][nc]:
                    if min_val <= grid[nr][nc] <= max_val:
                        visited[nr][nc] = True
                        q.append((nr, nc))
        return False

    # is_possible: (max-min) 차이가 diff 이하인 경로가 존재하는지 화인
    def is_possible(diff):
        # for min_v in range(201-diff):
        # 개선점 실제 배열에 있는 unique_nums 만 순회
        for min_v in unique_nums:
            max_v = min_v + diff
            # grid 안의 숫자는 최대 200이므로, max_v가 200을 넘으면 더 볼 필요 없음
            if max_v > 200:
                break # unique_nums가 정렬되어 있으니 break 사용
            if bfs(min_v, max_v):
                return True
        return False

    # # 이분 탐색 시작
    # low, high = 0, 200
    # result = 200
    #
    # while low <= high:
    #     mid = (low + high) // 2 # mid는 시도해볼 (max-min) 차이값
    #
    #     if is_possible(mid):
    #         # mid 차이로 성공했다면, 더 착은 차이를 시도
    #         result = mid
    #         high = mid - 1
    #     else:
    #         # mid 차이로 실패했다면, 더 큰 차이가 필요
    #         low = mid + 1
    # print(result)
    # ★★★★★ 투 포인터 알고리즘 시작 ★★★★★
    left, right = 0, 0
    result = float('inf')

    while right < len(unique_nums):
        min_v = unique_nums[left]
        max_v = unique_nums[right]

        if bfs(min_v, max_v):
            # 경로를 찾았다면, 정답을 갱신하고 더 좁은 범위를 시도 (left 증가)
            result = min(result, max_v - min_v)
            left += 1
        else:
            # 경로를 못 찾았다면, 더 넓은 범위가 필요 (right 증가)
            right += 1

    print(result)

solve()