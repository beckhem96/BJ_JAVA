package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class b1043 {
    static int[] parent;
    static int[] truePeople; // 진실을 아는 사람들 목록
    static ArrayList<Integer>[] parties; // 파티별 참가자 정보 저장

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 사람 수
        int M = Integer.parseInt(st.nextToken()); // 파티 수

        // 유니온 파인드 부모 배열 초기화
        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        // 진실을 아는 사람들 입력
        st = new StringTokenizer(br.readLine());
        int trueCount = Integer.parseInt(st.nextToken());

        // 진실을 아는 사람이 0명이면 모든 파티에서 거짓말 가능
        if (trueCount == 0) {
            System.out.println(M);
            return;
        }

        truePeople = new int[trueCount];
        for (int i = 0; i < trueCount; i++) {
            truePeople[i] = Integer.parseInt(st.nextToken());
        }

        // 파티 정보 입력 및 Union 연산 수행
        parties = new ArrayList[M];
        for (int i = 0; i < M; i++) {
            parties[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            int partySize = Integer.parseInt(st.nextToken());

            if (partySize > 0) {
                int firstPerson = Integer.parseInt(st.nextToken());
                parties[i].add(firstPerson);

                // 파티에 온 나머지 사람들을 첫 번째 사람과 Union (같은 그룹으로 묶음)
                for (int j = 1; j < partySize; j++) {
                    int nextPerson = Integer.parseInt(st.nextToken());
                    parties[i].add(nextPerson);
                    union(firstPerson, nextPerson);
                }
            }
        }

        // 거짓말 할 수 있는 파티 개수 세기
        int count = 0;
        for (int i = 0; i < M; i++) {
            boolean possible = true;
            int partyPerson = parties[i].get(0); // 파티의 대포 한 명만 확인하면 됨

            // 진실을 아는 사람들과 같은 그룹인지 확인
            for (int t: truePeople) {
                if (find(partyPerson) == find(t)) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                count++;
            }
        }
        System.out.println(count);
    }

    // Find: 경로 압축(Path Compression) 적용
    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // Union: 두 원소를 같은 집합으로 합침
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            parent[rootB] = rootA;
        }
    }
}
