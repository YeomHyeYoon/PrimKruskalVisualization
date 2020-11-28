// 간선
public class Edge implements Comparable<Edge> {
    // 정점 정보 및 간선 가중치
    public int v1;
    public int v2;
    public int w;
    // 렌더링을 위한 변수
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

    // 정렬 호환성
    @Override
    public int compareTo(Edge o) {
        return w - o.w;
    }

    // 비교 호환성
    @Override
    public boolean equals(Object obj) {
        Edge e = (Edge)obj;
        return (p1.equals(e.p1) && p2.equals(e.p2)) || (p1.equals(e.p2) && p2.equals(e.p1));
    }

    // 렌더링을 위한 좌표 클래스
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
