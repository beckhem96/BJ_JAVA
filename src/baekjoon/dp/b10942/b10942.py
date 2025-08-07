import sys
input = sys.stdin.readline
def solve():
    N = int(input())
    num_list = list(map(int, input().split()))
    M = int(input())

    # dp[i][i] = i 번째 부터 j 번째 까지가 팰린드롬이면 True
    # 인댁스를 0부터 시작하도록 처리
    dp = [[False] * N for _ in range(N)]

    # 길이가 1인 경우
    for i in range(N):
        dp[i][i] = True
    # 길이가 2인 경우
    for i in range(N-1):
        if num_list[i] == num_list[i+1]:
            dp[i][i+1] = True
    # 길이가 3 이상인 경우
    # lengh는 구간의 길이
    for length in range(2, N):
        for s in range(N-length):
            e = s + length
            # 양 끝 값이 같고, 그 안쪽 구간이 팰린드롬이라면
            if num_list[s] == num_list[e] and dp[s+1][e-1]:
                dp[s][e] = True

    for _ in range(M):
        S, E = map(int, input().split())
        if (dp[S-1][E-1]):
            print(1)
        else:
            print(0)

solve()