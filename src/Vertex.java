import javax.swing.*;
import java.awt.*;

// 정점 정보
public class Vertex extends JLabel {
    // 렌더링상 위치
    private int pX;
    private int pY;
    private int number;

    // 크기는 상수로
    private final int WIDTH = 50;
    private final int HEIGHT = 50;

    public Vertex(int x, int y, int number) {
        super("" + number, JLabel.CENTER);
        this.pX = x;
        this.pY = y;
        this.number = number;
        // 배경색을 사용하기 위한 설정
        setOpaque(true);
        // 실제 위치는 조금 다르게
        setLocation(x, y);
        // 크기를 강제로 지정
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }

    // 복사 생성자
    public Vertex(Vertex v) {
        this(v.pX, v.pY, v.number);
    }

    public boolean isIntersect(Edge e){
        int diffX = Math.abs(e.p2.x - e.p1.x);
        if (diffX == 0){
            int x = e.p1.x;
            int maxY = Math.max(e.p1.y, e.p2.y), minY = Math.min(e.p1.y, e.p2.y);
            return pX <= x && x <= (pX + 50) && (minY <= pY && pY <= maxY || minY <= (pY + 50) && (pY + 50) <= maxY);
        }
        int incX = e.p2.x - e.p1.x, incY = e.p2.y - e.p1.y;
        double slope = (incY * 1.f) / incX;
        if (incX < 0){
            for (int i = 0; i >= incX; i++){
                int nX = e.p1.x + i + 25;
                int nY = (int)((double)(e.p1.y) + (slope * i)) + 25;
                if (pX <= nX && nX <= pX + 50 && pY <= nY && nY <= pY + 50)
                    return true;
            }
        }
        else {
            for (int i = 0; i < incX; i++){
                int nX = e.p1.x + i + 25;
                int nY = (int)((double)(e.p1.y) + (slope * i)) + 25;
                if (pX <= nX && nX <= pX + 50 && pY <= nY && nY <= pY + 50)
                    return true;
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        // 점을 그린다.
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        // 글자 위치를 강제한다
        g.drawString("" + number, 22, 28);
    }

    public int getX() {
        return pX;
    }

    public int getY() {
        return pY;
    }
}
