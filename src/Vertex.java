import javax.swing.*;
import java.awt.*;

// ���� ����
public class Vertex extends JLabel {
    // �������� ��ġ
    private int pX;
    private int pY;
    private int number;

    // ũ��� �����
    private final int WIDTH = 50;
    private final int HEIGHT = 50;

    public Vertex(int x, int y, int number) {
        super("" + number, JLabel.CENTER);
        this.pX = x;
        this.pY = y;
        this.number = number;
        // ������ ����ϱ� ���� ����
        setOpaque(true);
        // ���� ��ġ�� ���� �ٸ���
        setLocation(x, y);
        // ũ�⸦ ������ ����
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }

    // ���� ������
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
        // ���� �׸���.
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        // ���� ��ġ�� �����Ѵ�
        g.drawString("" + number, 22, 28);
    }

    public int getX() {
        return pX;
    }

    public int getY() {
        return pY;
    }
}
