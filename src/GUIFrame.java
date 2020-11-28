import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Vector;

public class GUIFrame extends JFrame {
    // ������Ʈ
    private DrawPanel panel;
    private ResultPanel resultPanel = new ResultPanel();
    private ResultPanel2 resultPanel2 = new ResultPanel2();
    private JComboBox<Integer> v1Spinner;
    private JComboBox<Integer> v2Spinner;
    private JTextField weightField = new JTextField();
    private JButton addEdge = new JButton("�Է�");
  //  private JButton primHalf = new JButton("Half Prim");
  //  private JButton primResult = new JButton("Prim");
    private JButton kruskalHalf = new JButton("Half");
    private JButton kruskalResult = new JButton("Final");

    // ���� �� ��������
    private Vertex[] vertexes = new Vertex[10];
    private Vector<Edge> edges = new Vector<>();
    private int cnt = 0;

    // Spinner�� ���� ����
    private Vector<Integer> leftSpinVec = new Vector<>();
    private Vector<Integer> rightSpinVec = new Vector<>();

    public GUIFrame(){
        super("2020080064_������");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        // ũ��
        setBounds(100, 100, 1800, 700);
        
        // �׸��� �׷��� �гε�
        panel = new DrawPanel(edges);
        panel.setBounds(0, 0, 600, 600);
        resultPanel.setBounds(600, 0, 600, 600);
        resultPanel2.setBounds(1200, 0, 600, 600);

        // �߰� �۾��� ���� �κе�
        v1Spinner = new JComboBox<>(leftSpinVec);
        v2Spinner = new JComboBox<>(rightSpinVec);
        v1Spinner.setBounds(10, 620, 150, 25);
        v2Spinner.setBounds(250, 620, 150, 25);
        weightField.setBounds(500, 620, 100, 25);
        addEdge.setBounds(675, 620, 100, 25);
       // primHalf.setBounds(900, 620, 120, 25);
       // primResult.setBounds(1070, 620, 120, 25);
        kruskalHalf.setBounds(1240, 620, 120, 25);
        kruskalResult.setBounds(1410, 620, 120, 25);

        // �̺�Ʈ �߰�
        attachEvent();

        // ������ �߰�
        add(panel);
        add(resultPanel);
        add(resultPanel2);
        add(v1Spinner);
        add(v2Spinner);
        add(weightField);
        add(addEdge);
     //   add(primHalf);
     //   add(primResult);
        add(kruskalHalf);
        add(kruskalResult);

    }

    private void attachEvent(){
        // ���콺 Ŭ���� ���� �̺�Ʈ
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // ��ǥ ����
                int x = e.getX();
                int y = e.getY();
                // 10������ ����
                if (cnt < 10){
                    // ���ο� ����
                    vertexes[cnt] = new Vertex(x, y, cnt + 1);
                    panel.add(vertexes[cnt++]);
                    // spinner�� ����
                    leftSpinVec.add(cnt);
                    rightSpinVec.add(cnt);
                    // ó���� ���� 1������ ���̰�
                    if (cnt == 1) {
                        v1Spinner.setSelectedIndex(0);
                        v2Spinner.setSelectedIndex(0);
                    }
                    // �������� �׻� ������
                    v1Spinner.updateUI();
                    v2Spinner.updateUI();
                }
                // ������ ����ȭ
                revalidate();
            }
        });
        addEdge.addActionListener(e -> {
            // ���� ����
            int v1 = v1Spinner.getSelectedIndex();
            int v2 = v2Spinner.getSelectedIndex();
            // �������� �߰��� ���� �ʴ´�.
            if (v1 == v2) return;
            // ����ġ ����
            int w = Integer.parseInt(weightField.getText());
            // �ش� ���� �̸� �����.
            Edge.Point p1 = new Edge.Point(vertexes[v1].getX(), vertexes[v1].getY());
            Edge.Point p2 = new Edge.Point(vertexes[v2].getX(), vertexes[v2].getY());
            // ���� �߰�
            Edge edge = new Edge(v1, v2, w, p1, p2);
            // �̹� �߰��� ���� ����
            if (alreadyExist(edge)) return;
            if (isIntersect(edge)){
                JOptionPane.showMessageDialog(null, "������ �������� �����Դϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
                return ;
            }
            // �߰��ϰ� ��������
            edges.add(edge);
            panel.revalidate();
            panel.updateUI();
        });

        kruskalHalf.addActionListener(e -> {
            // ũ�罺Į ���ݿ� ���� �˰���
            Vector<Edge> paths = Algorithm.Kruskal(cnt, edges, true);
            System.out.println(paths.size());
            resultPanel.set(filterVertex(paths), paths);
            
            // ���� �˰��� ����
            Vector<Edge> paths2 = Algorithm.Prim(cnt, edges, true);
            System.out.println(paths2.size());
            resultPanel.set(filterVertex(paths), paths);
            resultPanel2.set(filterVertex(paths2), paths2);
        });

        kruskalResult.addActionListener(e -> {
            // ũ�罺Į ���� ���
            Vector<Edge> paths = Algorithm.Kruskal(cnt, edges, false);
            System.out.println(paths.size());
            resultPanel.set(filterVertex(paths), paths);
            
            // ���� ��ü
            Vector<Edge> paths2 = Algorithm.Prim(cnt, edges, false);
            System.out.println(paths2.size());
            resultPanel2.set(filterVertex(paths2), paths2);
        });

		/*
		 * primHalf.addActionListener(e -> { // ���� �˰��� ���� Vector<Edge> paths =
		 * Algorithm.Prim(cnt, edges, true); System.out.println(paths.size());
		 * resultPanel.set(filterVertex(paths), paths); });
		 */

		/*
		 * primResult.addActionListener(e -> { // ���� ��ü Vector<Edge> paths =
		 * Algorithm.Prim(cnt, edges, false); System.out.println(paths.size());
		 * resultPanel.set(filterVertex(paths), paths); });
		 */
    }
    
    // �ߺ��� ��� ����
    private boolean alreadyExist(Edge e){
        for (Edge edge: edges)
            if (e.equals(edge))
                return true;
        return false;
    }

    private boolean isIntersect(Edge e){
        for (int i = 0; i < cnt ; i++){
            if (e.v1 != i && e.v2 != i && vertexes[i].isIntersect(e))
                return true;
        }
        return false;
    }

    // path ��Ͽ��� ������ �߷�����.
    private Vector<Vertex> filterVertex(Vector<Edge> paths){
        // �ߺ� ������ ���� Set
        HashSet<Vertex> set = new HashSet<>();
        for (Edge path : paths){
            set.add(vertexes[path.v1]);
            set.add(vertexes[path.v2]);
        }
        // ��ȯ�� Vector�� ��ȯ
        return new Vector<>(set);
    }
}
