package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class b1062 {
    static int N, K;
    // 1. 전처리 단계에서 생성될 데이터
    // processedWords: 각 단어를 읽기 위해 '추가로' 배워야 하는 글자들의 Set을 저장
    static List<Set<Character>> processedWords = new ArrayList<>();
    // candidateList: 'anta', 'tica'를 제외하고, 입력된 단어들에 등장하는 모든 '추가 글자' 후보
    static List<Character> candidateList = new ArrayList<>();
    // 2. DFS(백트래킹)에서 사용될 데이터
    // leanred: 'a'부터 'z'까지 각 알파벳을 배웠는지 여부를 체크하는 배열
    static boolean[] learned = new boolean[26];
    // maxReadableWords: 탐색 중 찾은 "읽을 수 있는 단어의 최대 개수"
    static int maxReadableWords = 0;
    // numToLeanr: 'anta', 'tica' 외에 추가로 배울 수 있는 글자의 수 (K -5)
    static int numToLearn;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        N = Integer.parseInt(line[0]);
        K = Integer.parseInt(line[1]);

        // -- 1. 예외 처리--
        if (K < 5) {
            System.out.println(0);
            return;
        }
        if (K == 26) {
            System.out.println(N);
            return;
        }
        numToLearn = K - 5; // K개 중 5개는 필수 글자
        // 필수 5개 글자를 Set으로 정의
        Set<Character> essentialLetters = new HashSet<>(Set.of('a', 'n', 't', 'i', 'c'));
        // 추가로 배울 글자 후보들을 중복 없이 모으기 위한 Set
        Set<Character> candidatePool = new HashSet<>();

        // -- 2. 전처리 단계 --
        for (int i = 0; i < N;i++) {
            String word = br.readLine().strip();
            // 'anta'와 'tica' 사이의 글자들만 추출
            word = word.substring(4, word.length() - 4);

            Set<Character> requiredAdditional = new HashSet<>();
            for (char c : word.toCharArray()) {
                // 이 글자가 필수 글자가 아니라면
                if (!essentialLetters.contains(c)) {
                    requiredAdditional.add(c); // 이 단어를 읽기 위해 추가로 배워야 함
                    candidatePool.add(c); // 전체 글자 후보 풀에도 추가
                }
            }
            processedWords.add(requiredAdditional);
        }

        candidateList.addAll(candidatePool);

        // 필수 5개 글자는 이미 배운 것으로 처리
        for (char c : essentialLetters) {
            learned[c - 'a'] = true;
        }

        // 3. 조합 탐색 --
        // 만약 추가로 배울 수 있는 글자 수(numToLearn)가
        // 후보 글자 전체 수보다 많거나 같다면, 후보를 다 배우면 되므로 DFS 불필요
        if (numToLearn >= candidateList.size()) {
            for (char c : candidateList) {
                learned[c - 'a'] = true;
            }
            checkReadableWords();
        } else {
            // 후보 글자(candidateList) 중에서 numToLearn 개를 뽑는 조합 시작
            // 0번쨰 후보 글자부터, 현재 0개를 배운 상태로 시작
            dfs(0, 0);
        }
        System.out.println(maxReadableWords);
    }
    // 백트래킹으로 'numToLearn'개의 글자를 선택하는 조합을 생성하는 함수
    static void dfs(int startIdx, int learnedCount) {
        // 1. base case: K - 5개의 글자를 모두 선택한 경우
        if (learnedCount == numToLearn) {
            // 현재 배운 글자 조합으로 읽을 수 있는 단어 수 확인
            checkReadableWords();
            return;
        }

        // 2. 재귀 단계: 후보 리스트를 순히하며 다음 배울 글자 선택
        for (int i = startIdx; i < candidateList.size(); i++) {
            char c = candidateList.get(i);
            int charIndex = c - 'a';
            // 이 글자를 아지 배우지 않았다면
            if (!learned[charIndex]) {
                learned[charIndex] = true;
                dfs(i + 1, learnedCount + 1);
                learned[charIndex] = false;
            }
        }
    }
    // 현재 배운 글자(learned 배열) 상태를 기준으로
    // 읽을 수있는 단어의 총 개수를 계산하고, 최댓값을 갱신하는 함수
    static void checkReadableWords() {
        int currentReadableWords = 0;
        // 전처리된 모든 단어를 순회
        for (Set<Character> requiredSet : processedWords) {
            boolean cnaRead = true;
            for (char c: requiredSet) {
                if (!learned[c - 'a']) {
                    cnaRead = false;
                    break;
                }
            }
            if (cnaRead) {
                currentReadableWords++;
            }
        }
        maxReadableWords = Math.max(maxReadableWords, currentReadableWords);
    }
}
