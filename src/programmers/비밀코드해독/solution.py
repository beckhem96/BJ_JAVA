from itertools import combinations


def solution(n, q, ans):
    # 1부터 n까지의 숫자 리스트 생성
    numbers = list(range(1, n + 1))
    answer_count = 0

    # 1. 1부터 n까지의 수 중 5개를 뽑는 모든 조합을 생성
    for candidate_code in combinations(numbers, 5):
        is_valid = True
        # 2. 각 후보 코드가 모든 조건을 만족하는지 검증
        for i in range(len(q)):
            guess = q[i]
            expected_match_count = ans[i]

            # set의 교집합(&)을 이용해서 겹치는 원소의 개수를 효율적으로 계산
            actual_match_count = len(set(candidate_code) & set(guess))

            if actual_match_count != expected_match_count:
                is_valid = False
                break
        if is_valid:
            answer_count += 1
    answer = 0
    return answer
