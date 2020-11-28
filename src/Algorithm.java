import java.util.*;

public class Algorithm {

    // ũ�罺Į �˰���
    public static Vector<Edge> Kruskal(int n, Vector<Edge> e, boolean half){
        // ��ó��
        Vector<Edge> result = new Vector<>();
        Edge[] edges = new Edge[e.size()];
        for (int i = 0; i < edges.length; i++)
            edges[i] = new Edge(e.get(i).v1, e.get(i).v2, e.get(i).w, e.get(i).p1, e.get(i).p2);

        // �˰��� �����
        int[] par = new int[n];
        for (int i = 0; i < n; i++) par[i] = i;

        // ���� �ʼ�
        Arrays.sort(edges);
        
        // �˰��� ����
        for (Edge edge: edges){
            int v1 = edge.v1;
            int v2 = edge.v2;

            if (!union(par, v1, v2)) continue;
            result.add(edge);
        }

        // half�� ������ �߶�
        if (half) return new Vector<>(result.subList(0, result.size() / 2));

        return result;
    }

    // Disjoint-set find
    private static int find(int[] par, int v){
        return par[v] = par[v] == v ? v : find(par, par[v]);
    }

    // Disjoint-set union
    private static boolean union(int[] par, int v1, int v2){
        int p1 = find(par, v1);
        int p2 = find(par, v2);

        if (p1 == p2) return false;
        par[p1] = par[p2];
        return true;
    }

    // ���� �˰���
    public static Vector<Edge> Prim(int n, Vector<Edge> e, boolean half){
        Vector<Edge> result = new Vector<>();

        // ��ó��
        Vector<Vector<Edge>> adj = new Vector<>();
        for (int i = 0; i < n; i++)
            adj.add(new Vector<>());

        // ��� ��� �����
        int targetCount = half ? (n / 2) : (n - 1);
        boolean[] visited = new boolean[n];

        for (Edge edge : e) {
            int v1 = edge.v1;
            int v2 = edge.v2;
            adj.get(v1).add(new Edge(v1, v2, edge.w, edge.p1, edge.p2));
            adj.get(v2).add(new Edge(v2, v1, edge.w, edge.p1, edge.p2));
        }

        // ����
        Queue<Integer> q = new LinkedList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // ������ ��������
        q.add(((int)(Math.random() * 100)) % n);

        // ���� ����
        while (result.size() < targetCount){
            Integer now = q.poll();
            visited[now] = true;

            Vector<Edge> list = adj.get(now);
            for (Edge edge: list){
                if (!visited[edge.v2]) pq.add(edge);
            }

            while (!pq.isEmpty()){
                Edge top = pq.poll();
                if (!visited[top.v2]){
                    q.add(top.v2);
                    visited[top.v2] = true;
                    result.add(top);
                    break;
                }
            }
        }

        return result;
    }
}
