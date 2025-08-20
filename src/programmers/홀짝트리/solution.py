import collections


def solution(nodes, edges):
    # 1. 그래프 생성 및 차수 계산
    graph = collections.defaultdict(list)
    degrees = collections.defaultdict(int)
    for u, v in edges:
        graph[u].append(v)
        graph[v].append(u)
        degrees[u] += 1
        degrees[v] += 1

    odd_even_tree_count = 0
    reverse_odd_even_tree_count = 0
    visited = set()

    # 2. 모든 노드를 순회하며 아직 방문 안 한 노드에서 탐색 시작 (트리 분리)
    for start_node in nodes:
        if start_node not in visited:
            current_tree_nodes = []
            queue = collections.deque([start_node])
            visited.add(start_node)

            # BFS로 현재 트리에 속한 모든 노드를 찾음
            while queue:
                u = queue.popleft()
                current_tree_nodes.append(u)
                for v in graph[u]:
                    if v not in visited:
                        visited.add(v)
                        queue.append(v)

            # 3. 분리된 트리를 판별
            matching_count = 0 # 노드 값과 차수의 홀짝성이 같은 노드 수

            for node in current_tree_nodes:
                if (node % 2) == (degrees[node] % 2):
                    matching_count += 1

            non_matching_count = len(current_tree_nodes) - matching_count

            # "매칭" 노드가 1개면 그 노드를 루트로 삼아 홀짝 트리를 만들 수 있음
            if matching_count == 1:
                odd_even_tree_count += 1

            # "불일치" 노드가 1개면 그 노드를 루트로 삼아 역홀짝 트리를 만들 수 있음
            if non_matching_count == 1:
                reverse_odd_even_tree_count += 1

    return [odd_even_tree_count, reverse_odd_even_tree_count]

print(solution([11, 9, 3, 2, 4, 6], [[9, 11], [2, 3], [6, 3], [3, 4]]))

# 1. 그래프 구분
# 2. 하나씩 돌면서 홀짝, 역홀짝 되는지 확인
