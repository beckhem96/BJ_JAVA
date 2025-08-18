package programmers.p봉인된주문;

import java.util.*;

class Solution {
    public static void main(String[] args) {
        String[] banList = {"d", "e", "bb", "aa", "ae"};
        String answer = Solution.solution(30, banList);
        System.out.println(answer);
    }
    public static String solution(long n, String[] bans) {
        // --- 사전 준비 ---
        // 금지된 주문(bans)들을 갈이별로 분류하고, 각 리스트를 사전 순으로 정렬합니다.
        Map<Integer, List<String>> bannedMap = new HashMap<>();
        for (String ban : bans) {
            int len = ban.length();
            // computeIfAbset: key가 없으면 새 ArrayList를 생성하고, 있으면 기존 리스트를 반환
            bannedMap.computeIfAbsent(len, k -> new ArrayList<>()).add(ban);
        }
        // 각 길이별 리스트를 정렬
        for (List<String> list: bannedMap.values()) {
            Collections.sort(list);
        }

        int targetLength = 0;
        // --- 1단게: 정답 주문의 '길이' 결정하기 ---
        // 1글자부터 11글자까지 순회하며 n번째 주문이 몇 글자인지 찾습니다.
        for (int L = 1; L <= 11; L++) {
            // 26^L: 알파벳 소문자(26개)로 만들 수 있는 길이 L짜리 모든 주문의 개수
            // long으로 캐스팅하여 오버프로우 방지
            long totalCount = (long) Math.pow(26, L);
            // bannedMap에서 길이 L짜리 금지된 주문 리스트를 가져옴 (없으면 빈 리스트)
            long bannedCount = bannedMap.getOrDefault(L, Collections.emptyList()).size();
            long validCount = totalCount - bannedCount;

            // 만약 n이 현재 길이(L)의 유효한 주문 개수보다 작거나 같다면,
            // 우리가 찾는 주문의 길이는 L임이 확정돕니다.
            if (n <= validCount) {
                targetLength = L;
                break;
            }

            // 아니라면, n은 길이 L인 주문들보다 뒤에 있다는 의미이므로
            // 길이 L의 주문 개수만큼 n에서 빼주고 다음 길이를 확인.
            n -= validCount;
        }

        // 만약 11글자까지 확인해도 n이 남아있다면, 유효 범위를 벗어난 경우
        if (targetLength == 0) return "";
        StringBuilder answer = new StringBuilder();

        // --- 2단계: 정답 주문의 '문자' 결정하기 ---
        // targetLength 길이의 주문을 앞에서부터 한 글자씩 완성
        for (int i = 0; i < targetLength; i++) {
            // 현재 자리(i)에 올 수 있는 알파벳 'a'부터 'z'까지 하나씩 시험
            for (char ch = 'a'; ch <= 'z'; ch ++) {
                String prefix = answer.toString() + ch;
                int remainingLength = targetLength - prefix.length();

                // prefix로 시작하는 모든 주문의 개수
                long totalCount = (long) Math.pow(26, remainingLength);

                // 현재 prefix로 시작하는 '금지된' 주문 개수를 효율적으로 계산
                long bannedPrefixCount = countBannedWithPrefix(
                        bannedMap.getOrDefault(targetLength, Collections.emptyList()), prefix
                );

                // 현재 prefix로 시작하는 '유효한' 주문의 개수
                long validCount = totalCount - bannedPrefixCount;

                // 만약 n이 현재 prefix의 유효한 주문 개수보다 작거나 같다면,
                // 들어갈 문자는 ch가 맞다는 의미
                if (n <= validCount) {
                    answer.append(ch);
                    break;
                }

                // 아니라면 찾는 주문은 현재 prefix 범위보다 뒤에 있다는 의미이므로
                // 현재 prefix의 유효 주문 개수만큼 n에서 빼주고 다음 알파벳을 시험
                n -= validCount;
            }
        }
        return answer.toString();
    }

    /**
     * 정렬된 금지 단어 리스트(banList)에서, 특정 접두사(prefix)로 시작하는 단어의 개수를 계산합니다.
     */
    private static long countBannedWithPrefix(List<String> banList, String prefix) {
        // prefix의 마지막 문자
        char lastChar = prefix.charAt(prefix.length() - 1);

        // if-else 문 없이 하나로 통합
        // lastChar가 'z'여도 (char)('z'+1)은 '{'가 되어 올바르게 동작함
        String nextPrefix = prefix.substring(0, prefix.length() - 1)
                + (char)(lastChar + 1);

        // lowerBound: 특정 값 이상이 처음 나타나는 위치(Python의 bisect_left와 동일)
        int start = lowerBound(banList, prefix);
        int end = lowerBound(banList, nextPrefix);

        return end - start;
    }

    /**
     * Collections.binarySearch를 사용하여 lower_bound(bisect_left)를 구현
     */
    private static int lowerBound(List<String> banList, String key) {
        int index = Collections.binarySearch(banList, key);
        // binarySearch는 key를 못 찾으면 -(삽입점) - 1 을 반환합니다.
        if (index >= 0) {
            // 'bans'에는 중복이 없으므로, key를 찾으면 그 인덱스가 유일한 위치입니다.
            // 따라서 while 루프 없이 바로 index를 반환하면 됩니다.
            return index;
        } else {
            // 못 찾은 경우, -(삽입점) - 1 을 올바른 삽입점(lower_bound)으로 변환합니다.
            // 예를 들어, -(0) - 1 = -1 -> 0, -(1) - 1 = -2 -> 1 ...
            return -index - 1;
        }
    }
}
