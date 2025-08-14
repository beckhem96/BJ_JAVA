import collections
from bisect import bisect_left, bisect_right

# def solution(n, bans):
def solution(n=30, bans=["d", "e", "bb", "aa", "ae"]):
    # bans를 길이별로 분류하고 정렬
    bannded_map = collections.defaultdict(list)
    for ban in bans:
        bannded_map[len(ban)].append(ban)
    for L in bannded_map:
        bannded_map[L].sort()

    target_length = 0
    # 1. 정답 주문의 '길이' 결정
    for L in range(1, 12):
        total_count = 26**L
        bannded_count = len(bannded_map[L])
        valid_count = total_count - bannded_count

        if n <= valid_count:
            target_length = L
            break
        n -= valid_count
    if target_length == 0:
        return "" # n이 너무 커서 11글자 범위를 벗어나는 경우

    answer = ""
    # 2. 정답 주문의 '문자' 결정
    for i in range(target_length):
        for code in range(26):
            char = chr(ord('a') + code)
            prefix = answer + char
            remaining_lenth = target_length - len(prefix)
            total_count = 26**remaining_lenth

            # 현재 prefix로 시작하는 금지된 단어 수 계산 (이진 탐색 bisect 활용)
            ban_list = bannded_map[target_length]

            # 다음 prefix를 만들어 구간을 찾음 (예: 'az' 다음은 'b')
            next_prefix_char = chr(ord(char) + 1)
            next_prefix = answer + next_prefix_char

            start_idx = bisect_left(ban_list, prefix)
            end_idx = bisect_left(ban_list, next_prefix)

            bannded_prefix_count = end_idx - start_idx
            valid_count = total_count - bannded_prefix_count

            if n <= valid_count:
                answer += char
                break
            n -= valid_count
    return answer
solution()