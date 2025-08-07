import sys

# 빠른 입력을 위한 설정
input = sys.stdin.readline

def solve():
    N, M = map(int, input().split())
    memories = list(map(int, input().split()))
    costs = list(map(int, input().split()))

    total_cost = sum(costs)

    # dp[c] = 비용 c를 들여 확보할 수 있는 최대 메모리
    dp = [0] * (total_cost + 1)

    # 모든 앱을 순회
    for i in range(N):
        memory = memories[i]
        cost = costs[i]

        # 역순으로 순회 (핵심!)
        for j in range(total_cost, cost - 1, -1):
            # j 비용으로 i번째 앱을 비활성화하는 경우와 안하는 경우 중 최대 메모리 선택
            # dp[j] : i번째 앱을 비활성화 안 함
            # dp[j - cost] + memory : i번째 앱을 비활성화 함
            dp[j] = max(dp[j], dp[j - cost] + memory)

    # M 바이트 이상을 확보하는 최소 비용 찾기
    min_cost = 0
    for i in range(total_cost + 1):
        if dp[i] >= M:
            min_cost = i
            break

    print(min_cost)

solve()