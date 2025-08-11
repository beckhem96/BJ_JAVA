import sys
import heapq

# 빠른 입력
input = sys.stdin.readline
# 도달할 수 없는 경우를 나타낼 무한대 값
INF = float('inf')

# 특정 지점에서 다른 모든 지점까지의 최단 거리를 계산하는 다익스트라 알고리즘
def dijkstra(start, n, graph):
    dist = [INF] * (n + 1) # 거리 리스트를 무한대로 초기화
    pq = [] # 최소 힙으로 사용할 히스트

    dist[start] = 0 # 시작점의 거리는 0
    heapq.heappush(pq, (0, start)) # 우선순위 큐에 (거리, 노드) 형태로 추가

    while pq:
        d, u = heapq.heappop(pq) # 현재까지의 거리가 가장 짧은 노드를 꺼냄

        # 큐에 저장된 거리보다 이미 더 짧은 경로를 발견했다면 무시
        if d > dist[u]:
            continue
        # 현재 노드 u와 연결된 이웃 노드 v들을 순회
        for v, weight in graph[u]:
            # 현재 노드를 거쳐 이웃 노드로 가는 것이 더 빠르다면 거리 갱신
            if dist[v] > d + weight:
                dist[v] = d + weight
                heapq.heappush(pq, (dist[v], v))
    return dist

def solve():
    # n : 교차로 (정점 수), m: 도로(간선) 수, t: 목적지 후보 수
    n, m, t = map(int, input().split())
    # s: 시작점, g, h: 반드시 거쳐야하는 도로의 양 끝 교차로
    s, g, h = map(int, input().split())

    # 그래프는 인접 리스트 형태로 표현
    graph = [[] for _ in range(n + 1)]
    gh_cost = 0 # g-h 도로의 길이를 저장할 변수
    for _ in range(m):
        a, b, d = map(int, input().split())
        # 양방향 그래프이므로 양쪽에 간선 정보 추가
        graph[a].append((b, d))
        graph[b].append((a, d))
        if (a == g and b == h) or (a == h and b == g):
            gh_cost = d

    # 목적지 후보들을 리스트에 저장
    candidates = [int(input()) for _ in range(t)]

    # --- 1단계: 다익스트라 알고리즘 3회 실행 ---
    # s, g, h 각 지점에서 다른 모든 지점까지의 최단 거리를 미리 계산
    dist_from_s = dijkstra(s, n, graph)
    dist_from_g = dijkstra(g, n, graph)
    dist_from_h = dijkstra(h, n, graph)

    # --- 2단계: 후보 목적지 판별 ---
    result = []
    for x in candidates:
        # s에서 x까지 도달 불가능한 경우는 무시
        if x not in dist_from_s[x] == INF:
            continue

        # s -> g -> h -> x 경로의 총 길이 계산
        path1 = dist_from_s[g] + gh_cost + dist_from_h[x]
        # s -> h -> g -> x 경로의 총 길이 계산
        path2 = dist_from_s[h] + gh_cost + dist_from_g[x]

        # s에서 x까지의 실제 최단 거리가, g-h를 경유하는 경로 중 하나의 길이와 같다면 정답 후보
        if dist_from_s[x] == path1 or dist_from_s[x] == path2:
            result.append(x)
    # 결과 오름 차순 정렬 후 출력
    result.sort()
    print(*result)
T = int(input())
for _ in range(T):
    solve()