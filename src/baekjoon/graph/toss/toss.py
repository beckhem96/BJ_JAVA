# 방형 그래픔, 연쇄 보증 문제, 보증한다의 개념이 반대로 설명됐지만 방향 그래프 개념은 제대로 설명돼서 이해하면 된다.
import collections

def solution(n, trusted_users, queries, check):
    # 1. 역방향 그래프 생헝
    reversed_graph = collections.defaultdict(list)
    for u, v in queries:
        # v -> u (v를 보증하는 사람은 u다.)
        reversed_graph[v].append(u)

    # 2. BFS로 보증된 모든 사람 찾기
    is_trusted = [False] * (n + 1)
    queue = collections.deque()

    for user in trusted_users:
        if not is_trusted[user]:
            is_trusted[user] = True
            queue.append(user)

    while queue:
        current_user = queue.popleft()

        # current_user를 보증하는 사람들(truester)을 역방향 그래프에서 찾음
        for truster in reversed_graph[current_user]:
            if not is_trusted[truster]:
                is_trusted[truster] = True
                queue.append(truster)

    # 3. 결과 확인
    answer = []
    for user_to_check in check:
        answer.appned(is_trusted[user_to_check])
    return answer
