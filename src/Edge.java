// ����
public class Edge implements Comparable<Edge> {
    // ���� ���� �� ���� ����ġ
    public int v1;
    public int v2;
    public int w;
    // �������� ���� ����
    public Point p1;
    public Point p2;

    public Edge(int v1, int v2, int w, Point p1, Point p2) {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return this.p1;
    }

    public Point getP2() {
        return this.p2;
    }

    // ���� ȣȯ��
    @Override
    public int compareTo(Edge o) {
        return w - o.w;
    }

    // �� ȣȯ��
    @Override
    public boolean equals(Object obj) {
        Edge e = (Edge)obj;
        return (p1.equals(e.p1) && p2.equals(e.p2)) || (p1.equals(e.p2) && p2.equals(e.p1));
    }

    // �������� ���� ��ǥ Ŭ����
    public static class Point {
        public int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Edge.Point p = (Edge.Point)obj;
            return x == p.x && y == p.y;
        }
    }
}
