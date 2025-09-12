package programmers.수식복원하기;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    public String[] solution(String[] expressions) {
        List<String[]> known = new ArrayList<>();
        List<String[]> unknown = new ArrayList<>();
        int maxDigit = 0;

        // 수식을 파싱하여 Known과 unknown으로 분리
        for (String exp : expressions) {
            String[] parts = exp.split(" ");
            // A, B, C에서 가장 큰 숫자(digit) 찾기, 게산되는 수는 최대 2자리, 결과는 최대 3자리라서 반복문 사용
            for (char ch : parts[0].toCharArray()) maxDigit = Math.max(maxDigit, ch - '0');
            for (char ch : parts[2].toCharArray()) maxDigit = Math.max(maxDigit, ch - '0');

            if (parts[4].equals("X")) {
                unknown.add(parts);
            } else {
                known.add(parts);
                for (char ch : parts[4].toCharArray()) maxDigit = Math.max(maxDigit, ch - '0');
            }
        }

        // --- 1단계: 가능한 진법 후보 찾기 ---
        Set<Integer> possibleBases = new HashSet<>();
        // (A) 최대 숫자를 기반으로 초기 후보 설정
        for (int i = maxDigit + 1; i <= 9; i++) {
            possibleBases.add(i);
        }

        for (String[] parts: known) {
            String aStr = parts[0];
            String op = parts[1];
            String bStr = parts[2];
            String cStr = parts[4];

            Set<Integer> basesToRemove = new HashSet<>();
            for (int base : possibleBases) {
                try {
                    // 검증 1 : 숫자가 진법에 맞는지 확인 (parseInt가 NumberFormatException 발생시킴)
                    long a = Long.parseLong(aStr, base);
                    long b = Long.parseLong(bStr, base);
                    long c = Long.parseLong(cStr, base);

                    // 검증 : 계산 겨로가가 맞는지 확인
                    long result;
                    if (op.equals("+")) {
                        result = a + b;
                    } else {
                        result = a - b;
                    }

                    if (result != c) {
                        basesToRemove.add(base);
                    }
                } catch (NumberFormatException e) {
                    basesToRemove.add(base);
                }
            }
            possibleBases.removeAll(basesToRemove);
        }

        // --- 2단계: X 값 결정하기 ---
        List<String> answer = new ArrayList<>();
        for (String[] parts: unknown) {
            String aStr = parts[0];
            String op = parts[1];
            String bStr = parts[2];

            Set<String> results = new HashSet<>();
            for (int base : possibleBases) {
                try {
                    long a = Long.parseLong(aStr, base);
                    long b = Long.parseLong(bStr, base);

                    long resultVal;
                    if (op.equals("+")) {
                        resultVal = a + b;
                    } else {
                        resultVal = a - b;
                    }

                    results.add(Long.toString(resultVal, base));
                } catch (NumberFormatException e) {}
            }
            String finalResult;
            if (results.size() == 1) {
                finalResult = results.iterator().next();
            } else {
                finalResult = "?";
            }
            answer.add(String.join(" ", aStr, op, bStr, "=", finalResult));
        }
        return answer.toArray(new String[0]);
    }
}
