def solution(info, n, m):
    # dp[i] : A의 흔적이 i일 때, B 흔적의 최솟값
    INF = m + 1 # B의 한계치를 넘는 값으로 '무한대' 설정
    dp = [INF] * n
    dp[0] = 0

    for cost_a, cost_b in info:
        # 역순으로 순회해야 이번 아이템을 한 번만 사용하도록 보장됨
        for a_trace in range(n-1, -1, -1):
            if dp[a_trace] == INF:
                continue
            # Case 1: A가 현재 아이템을 훔치는 경우
            # 새로 물건을 훔칠 경우의 흔적 = new_a_trace
            # a_trace는 n을 넘으면 안되니 n 부터 작아지면서 + 중복 방지
            # 현재 물건을 훔칠 경우의 흔적(cost_a)을 새로운 흔적으로 해서
            # 'b의 흔적'을 최소화하는 과정
            new_a_trace = a_trace + cost_a

            if  new_a_trace < n:
                # a가 이전 물건을 훔쳤을 떄와 지금 물건을 춤쳤을 때 중에 b의 흔적이 작은 것으로 갱신
                # 왜냐하면 A가 물건을 훔쳐도 B의 흔적은 늘어나지 않으니 최소 값으로 갱신하는 것
                dp[new_a_trace] = min(dp[new_a_trace], dp[a_trace])

            # Case 2: B가 현재 아이템을 훔치는 경우
            new_b_trace = dp[a_trace] + cost_b
            if new_b_trace < m:
                dp[a_trace] = new_b_trace
            else:
                dp[a_trace] = INF
    for a_trace in range(n):
        if dp[a_trace] < m:
            return a_trace
    return -1

print(solution([[1, 2], [2, 3], [2, 1]], 4, 4))