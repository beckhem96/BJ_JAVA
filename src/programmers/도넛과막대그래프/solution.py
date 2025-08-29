from collections import defaultdict, deque


def solution(edges):
    # 1. 모든 정점의 in-degree, out-degree 계산 및 그래프 생성
    out_degree = defaultdict(int)
    in_degree = defaultdict(int)
    graph = defaultdict(list)
    nodes = set()

    for u, v in edges:
        out_degree[u] += 1
        in_degree[v] += 1
        graph[u].append(v)
        nodes.add(u)
        nodes.add(v)

    # 2. 생성된 정점 찾기
    generated_node = -1
    for node in nodes:
        if in_degree[node] == 0 and out_degree[node] >= 2:
            generated_node = node
            break
    # 3. 각 그래프 탐색 및 종류 판별
    donut_count = 0
    stick_count = 0
    eight_count = 0

    # if generated_node == -1:
    #     # 이 문제에서는 항상 생성된 정점이 존재함
    #     return []

    # 생성된 정점에서 시작된 각 서브 그래프를 탐색
    for start_node in graph[generated_node]:
        q = deque([start_node])
        visited = {start_node}
        vertex_count = 0
        edge_count = 0

        # BFS로 서브 그래프 순회
        while q:
            u = q.popleft()
            vertex_count += 1
            edge_count += out_degree[u]

            for v in graph[u]:
                if v not in visited:
                    visited.add(v)
                    q.append(v)


        # 각 그래프 모양의 '지문'으로 종류 판별
        if edge_count == vertex_count - 1:
            stick_count += 1
        elif edge_count == vertex_count:
            donut_count += 1
        elif edge_count == vertex_count + 1:
            eight_count += 1
    return [generated_node, donut_count, stick_count, eight_count]
