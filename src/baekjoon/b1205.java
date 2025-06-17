package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b1205 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int N = Integer.parseInt(st.nextToken());
        int myPoint = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        if (N == 0 ) {
            System.out.println(1);
            System.exit(0);
        }
        int[] rankList = new int[P];

        st = new StringTokenizer(br.readLine());
        for (int i=0;i<N;i++) {
            rankList[i] = Integer.parseInt(st.nextToken());
        }
        int currRank = 1;
        int myRank = -1;
        for (int i=0;i<N;i++) {
            if (rankList[i] > myPoint) currRank++;
            else if (rankList[i] < myPoint) {
                myRank = currRank;
                break;
            }
        }
        if (P - N >= 1) myRank = currRank;
        System.out.println(myRank);
    }
}
