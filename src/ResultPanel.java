import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Vector;

// ��� ��¿� �г�
public class ResultPanel extends JPanel {

    // ���� ���
    private Vector<Vertex> vertexes;
    // ���� ���
    private Vector<Edge> edges;

    public ResultPanel(){
        super();
        setBackground(Color.white);
    }

    public void set(Vector<Vertex> vertexes, Vector<Edge> edges){
        // �׸� �� ���� �ʱ�ȭ
        this.vertexes = vertexes;
        this.edges = edges;
        // �׸��°ŵ� �ʱ�ȭ
        removeAll();
        repaint();
        for (Vertex v : this.vertexes)
            add(new Vertex(v));
        revalidate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // �ʱ⿡ edges�� ���� �� ����
        if (edges == null) return;
        // ����� �Ķ������� ǥ�����ش�
        Graphics2D g2=(Graphics2D)g;
        g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,0));
        for (Edge edge: edges) {
            Edge.Point p1 = edge.getP1();
            Edge.Point p2 = edge.getP2();
            g.setColor(Color.BLUE);
            g2.draw(new Line2D.Double(p1.x + 25, p1.y + 25, p2.x + 25, p2.y + 25));
            int cenX = ((p1.x + p2.x) / 2) + 25;
            int cenY = ((p1.y + p2.y) / 2) + 25;
            g.setColor(Color.BLACK);
            g2.drawString(edge.w + "" , cenX - 10, cenY + 25);
        }
    }
}
