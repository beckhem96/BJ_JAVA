import sys

input = sys.stdin.readline

INF = float('inf')

def solve():
    N, M = map(int, input().split()) # N: 도시수, M: 버스 노석 수
    edges = []
    for _ in range(M):
        A, B, C = map(int, input().split())
        edges.append((A, B, C)) # (출발, 도착, 시간)

    # 1. 거리 리스트 초기화
    dist = [INF] * (N + 1)
    dist[1] = 0 # 시작 도시(1)의 거리는 0

    # 2. N-1 번 동안 모든 간선에 대한 완화 작업 반복
    # 추가 설명: 한번 돌때는 경로 1(간선 1개를 이용해)로 완성된 최단 경로 찾음
    # 두번 돌때는 경로 2(간선 2개를 이용해)로 완성된 최단 경로 찾음...
    # N-1번 돌때는 경로 N-1(간선 N-1개를 이용해)로 완성된 최단 경로 찾음
    for i in range(N-1):
        for u, v, w in edges:
            # 출발점이 도달 가능한 상태이고, 현재 간성을 거치는 것이 더 빠른 경우
            if dist[u] != INF and dist[v] > dist[u] + w:
                dist[v] = dist[u] + w

    # 3. 음수 사이클 확인 (N번쨰 완화 작업)
    has_negative_cycle = False
    for u, v, w in edges:
        # N -1 번 반복 후에도 거리가 갱신된다면 음수 사이클 존재
        if dist[u] != INF and dist[v] > dist[u] + w:
            has_negative_cycle = True
            break

    # 음수 사이클이 존재하면 -1 출력
    if has_negative_cycle:
        print(-1)
    else:
        # 4. 결과 출력
        for i in range(2, N+1):
            if dist[i] == INF:
                print(-1)
            else:
                print(dist[i])
solve()