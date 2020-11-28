import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Vector;

public class GUIFrame extends JFrame {
    // 컴포넌트
    private DrawPanel panel;
    private ResultPanel resultPanel = new ResultPanel();
    private ResultPanel2 resultPanel2 = new ResultPanel2();
    private JComboBox<Integer> v1Spinner;
    private JComboBox<Integer> v2Spinner;
    private JTextField weightField = new JTextField();
    private JButton addEdge = new JButton("입력");
  //  private JButton primHalf = new JButton("Half Prim");
  //  private JButton primResult = new JButton("Prim");
    private JButton kruskalHalf = new JButton("Half");
    private JButton kruskalResult = new JButton("Final");

    // 정점 및 간선정보
    private Vertex[] vertexes = new Vertex[10];
    private Vector<Edge> edges = new Vector<>();
    private int cnt = 0;

    // Spinner를 위한 변수
    private Vector<Integer> leftSpinVec = new Vector<>();
    private Vector<Integer> rightSpinVec = new Vector<>();

    public GUIFrame(){
        super("2020080064_염혜윤");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        // 크기
        setBounds(100, 100, 1800, 700);
        
        // 그림이 그려질 패널들
        panel = new DrawPanel(edges);
        panel.setBounds(0, 0, 600, 600);
        resultPanel.setBounds(600, 0, 600, 600);
        resultPanel2.setBounds(1200, 0, 600, 600);

        // 추가 작업을 위한 부분들
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

        // 이벤트 추가
        attachEvent();

        // 실제로 추가
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
        // 마우스 클릭에 대한 이벤트
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 좌표 추출
                int x = e.getX();
                int y = e.getY();
                // 10개까지 제한
                if (cnt < 10){
                    // 새로운 정점
                    vertexes[cnt] = new Vertex(x, y, cnt + 1);
                    panel.add(vertexes[cnt++]);
                    // spinner도 조작
                    leftSpinVec.add(cnt);
                    rightSpinVec.add(cnt);
                    // 처음인 경우는 1번으로 보이게
                    if (cnt == 1) {
                        v1Spinner.setSelectedIndex(0);
                        v2Spinner.setSelectedIndex(0);
                    }
                    // 렌더링은 항상 강제로
                    v1Spinner.updateUI();
                    v2Spinner.updateUI();
                }
                // 렌더링 강제화
                revalidate();
            }
        });
        addEdge.addActionListener(e -> {
            // 정점 정보
            int v1 = v1Spinner.getSelectedIndex();
            int v2 = v2Spinner.getSelectedIndex();
            // 같은곳은 추가를 하지 않는다.
            if (v1 == v2) return;
            // 가중치 정보
            int w = Integer.parseInt(weightField.getText());
            // 해당 점을 미리 만든다.
            Edge.Point p1 = new Edge.Point(vertexes[v1].getX(), vertexes[v1].getY());
            Edge.Point p2 = new Edge.Point(vertexes[v2].getX(), vertexes[v2].getY());
            // 간선 추가
            Edge edge = new Edge(v1, v2, w, p1, p2);
            // 이미 추가된 경우는 제외
            if (alreadyExist(edge)) return;
            if (isIntersect(edge)){
                JOptionPane.showMessageDialog(null, "정점을 지나가는 간선입니다.", "알림", JOptionPane.PLAIN_MESSAGE);
                return ;
            }
            // 추가하고 리렌더링
            edges.add(edge);
            panel.revalidate();
            panel.updateUI();
        });

        kruskalHalf.addActionListener(e -> {
            // 크루스칼 절반에 대한 알고리즘
            Vector<Edge> paths = Algorithm.Kruskal(cnt, edges, true);
            System.out.println(paths.size());
            resultPanel.set(filterVertex(paths), paths);
            
            // 프림 알고리즘 절반
            Vector<Edge> paths2 = Algorithm.Prim(cnt, edges, true);
            System.out.println(paths2.size());
            resultPanel.set(filterVertex(paths), paths);
            resultPanel2.set(filterVertex(paths2), paths2);
        });

        kruskalResult.addActionListener(e -> {
            // 크루스칼 최종 결과
            Vector<Edge> paths = Algorithm.Kruskal(cnt, edges, false);
            System.out.println(paths.size());
            resultPanel.set(filterVertex(paths), paths);
            
            // 프림 전체
            Vector<Edge> paths2 = Algorithm.Prim(cnt, edges, false);
            System.out.println(paths2.size());
            resultPanel2.set(filterVertex(paths2), paths2);
        });

		/*
		 * primHalf.addActionListener(e -> { // 프림 알고리즘 절반 Vector<Edge> paths =
		 * Algorithm.Prim(cnt, edges, true); System.out.println(paths.size());
		 * resultPanel.set(filterVertex(paths), paths); });
		 */

		/*
		 * primResult.addActionListener(e -> { // 프림 전체 Vector<Edge> paths =
		 * Algorithm.Prim(cnt, edges, false); System.out.println(paths.size());
		 * resultPanel.set(filterVertex(paths), paths); });
		 */
    }
    
    // 중복된 경로 방지
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

    // path 목록에서 정점을 추려낸다.
    private Vector<Vertex> filterVertex(Vector<Edge> paths){
        // 중복 방지를 위한 Set
        HashSet<Vertex> set = new HashSet<>();
        for (Edge path : paths){
            set.add(vertexes[path.v1]);
            set.add(vertexes[path.v2]);
        }
        // 반환은 Vector로 변환
        return new Vector<>(set);
    }
}
