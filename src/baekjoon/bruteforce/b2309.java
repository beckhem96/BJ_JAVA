package baekjoon.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class b2309 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] nanjangList = new int[9];
        for (int i = 0; i < 9; i++) {
            nanjangList[i] = Integer.parseInt(br.readLine());
        }
        int sum = 0;
        Arrays.sort(nanjangList);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i != j) {
                    if (Arrays.stream(nanjangList).sum() - nanjangList[i] - nanjangList[j] == 100) {
                        for (int k = 0; k < 9;k++) {
                            if (k != i && k != j) {
                                System.out.print(nanjangList[k]+ "\n");
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}
