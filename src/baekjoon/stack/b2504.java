package baekjoon.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class b2504 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();

        Stack<Character> stack = new Stack<>();
        int result = 0;
        int tmp = 1; // 곱셈 누적 변수

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '(') {
                stack.push(c);
                tmp *= 2;
            } else if (c == '[') {
                stack.push(c);
                tmp *= 3;
            } else if (c == ')') {
                // 스택이 비어있거나 짝이 안 맞으면 실패
                if (stack.isEmpty() || stack.peek() != '(') {
                    result = 0;
                    break;
                }

                // 바로 직전 문자가 '(' 였을 때만 더함 (가장 안쪽 괄호)
                if (line.charAt(i - 1) == '(') {
                    result += tmp;
                }

                stack.pop();
                tmp /= 2; // 괄호 빠져나오므로 나누기
            } else if (c == ']') {
                // 스택이 비어있거나 짝이 안 맞으면 실패
                if (stack.isEmpty() || stack.peek() != '[') {
                    result = 0;
                    break;
                }

                // 바로 직전 문자가 '[' 였을 때만 더함 (가장 안쪽 괄호)
                if (line.charAt(i - 1) == '[') {
                    result += tmp;
                }

                stack.pop();
                tmp /= 3; // 괄호 빠져나오므로 나누기
            }
        }

        if (!stack.isEmpty()) {
            System.out.println(0);
        } else {
            System.out.println(result);
        }
    }
}
