# def solution(players, m, k): # 내풀이
#     curServer = [0] * (k + 2) # 0은 비워두고, 마지막 칸은 앞 칸을 위해
#     answer = 0
#     for player in players:
#         n = 0 # 현재 증설된 서버
#         # 증설된 서버 시간 지남 표시
#         for i in range(len(curServer)-1):
#             curServer[i] = curServer[i+1] # 인덱스 마지막은 항상 0
#             if (i != 0):
#                 n += curServer[i] # 증설되어 있는 서버 수 갱신
#         # 사용자 수 확인 후 증설할 서버 계산
#         share = player // m
#         remainer = player % m
#
#         if (share < 1): # 사용자 수가 m보다 작으니 패스
#             pass
#         elif (share >= 1): # 사용자 수가 m보다 크면
#             if (n < share):
#                 curServer[k] += share - n # 증설된 서버 수 만큼 제외하고 증설
#                 answer += curServer[k]
#             elif (n >= share):
#                 pass
#     return answer

# 제미나이 풀이
def solution(players, m, k):
    total_expansions = 0
    running_servers = 0

    expiring_servers = [0] * (24 + k)

    for t in range(24):
        if t < len(expiring_servers):
            running_servers -= expiring_servers[t]

        players_now = players[t]

        # ★★★★★ 핵심 수정사항 ★★★★★
        # 문제의 조건을 정확히 반영하여, 필요한 서버 수를 정수 나눗셈으로 계산합니다.
        needed_servers = players_now // m

        if running_servers < needed_servers:
            new_expansions = needed_servers - running_servers

            total_expansions += new_expansions
            running_servers += new_expansions

            if t + k < len(expiring_servers):
                expiring_servers[t + k] += new_expansions

    return total_expansions
print(solution([0, 2, 3, 3, 1, 2, 0, 0, 0, 0, 4, 2, 0, 6, 0, 4, 2, 13, 3, 5, 10, 0, 1, 5], 3, 5))