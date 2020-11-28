import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Vector;

// ���� ���� �г�
public class DrawPanel extends JPanel {

    // �� �г��� Edge�� ���� ����
    private Vector<Edge> edges;

    public DrawPanel(Vector<Edge> edges){
        super();
        this.edges = edges;
        setBackground(Color.white);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // ������ �׸��� ����
        g.setColor(Color.BLACK);
        Graphics2D g2=(Graphics2D)g;
        g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,0));
        for (Edge edge: edges) {
            Edge.Point p1 = edge.getP1();
            Edge.Point p2 = edge.getP2();
            // ���� ����� ���߱� ����
            g2.draw(new Line2D.Double(p1.x + 25, p1.y + 25, p2.x + 25, p2.y + 25));
            int cenX = ((p1.x + p2.x) / 2) + 25;
            int cenY = ((p1.y + p2.y) / 2) + 25;
            g2.drawString(edge.w + "" , cenX - 10, cenY + 25);
        }
    }
}
