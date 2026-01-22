package baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class b1068 {
    static int N, deleteNode;
    static int root;
    static ArrayList<Integer>[] tree;
    static int leafCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        tree = new ArrayList[N];
        for(int i = 0; i < N; i++) {
            tree[i] = new ArrayList<>();
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N;i++) {
            int parent = Integer.parseInt(st.nextToken());
            if (parent == -1) {
                root = i;
            } else {
                tree[parent].add(i);
            }
        }

        deleteNode = Integer.parseInt(br.readLine());

        if (deleteNode == root) {
            System.out.println(0);
            return;
        }

        dfs(root);
        System.out.println(leafCount);
    }

    static void dfs(int node) {
        int childCount = 0; // 유효한 자식의 개수

        for (int child: tree[node]) {
            // 삭제된 노드라면 탐색하지 않음(자식으로 치치 않음)
            if(child == deleteNode) continue;

            childCount++;
            dfs(child);
        }

        if (childCount == 0) {
            leafCount++;
        }
    }
}
