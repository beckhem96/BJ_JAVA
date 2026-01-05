package baekjoon.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class b9935 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String init = br.readLine();
        String bomb = br.readLine();

        int bombLen = bomb.length();

        // 스택처럼 사용할 StringBuilder
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < init.length(); i++) {
            char c = init.charAt(i);
            sb.append(c);

            // 스택(sb)의 길이가 폭발 문자열보다 길거나 같아지면 검사 시작
            if (sb.length() >= bombLen) {
                boolean isSame = true;

                // 뒤에서부터 폭발 문자열과 일치하는지 확인
                for (int j = 0; j < bombLen; j++) {
                    // sb의 뒤쪽 문자들과 bomb의 문자들을 비교
                    char sbChar = sb.charAt(sb.length() - bombLen + j);
                    char bombChar = bomb.charAt(j);

                    if (sbChar != bombChar) {
                        isSame = false;
                        break;
                    }
                }
                // 폭발 문자열과 일치하면 제거 (pop)
                if (isSame) {
                    sb.delete(sb.length() - bombLen, sb.length());
                }
            }
        }
        if (sb.length() == 0) {
            System.out.println("FRULA");
        } else {
            System.out.println(sb);
        }
    }
}
