import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class MyApp extends Frame implements ActionListener, MouseListener, ComponentListener {

    Menu setGraphMenu;
    Menu setParametersMenu;
    MenuBar mb;
    ArrayList<Coords> Tset;
    ArrayList<Coords> Qset;
    int k; //k dots of T
    int m; //m dots of Q

    public MyApp(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        init();
    }

    public void init() {
        addMouseListener(this);
        addComponentListener(this);

        Qset = new ArrayList<>();
        Tset = new ArrayList<>();

    }
//
//        addMouseListener(this);
//        addComponentListener(this);
//        setLayout(new BorderLayout());
//
//        graph = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
//
//        edges = new HashMap<>();
//        nodes = new HashMap<>();
//        mb = new MenuBar();
//        this.setMenuBar(mb);
//
//        setGraphMenu = new Menu("Граф");
//        mb.add(setGraphMenu);
//        MenuItem create = new MenuItem("Создать");
//        setGraphMenu.add(create);
//
//        create.addActionListener(e -> {
//            System.out.println("test");
//            LinkedList<Label> labels = new LinkedList<>(); // сделать список int-ов TODO
//            for (int i = 0; i < 9; i++) {
//                labels.add(new Label(Integer.toString(i)));
//            }
//
//            Dialog d = new Dialog(this);
//            d.setVisible(true);
//            d.setSize(300, 300);
//            GridLayout gl = new GridLayout(0, 10);
//            d.setLayout(gl);
//
//            d.add(new Label("X"));
//            for (Label l : labels) {
//                d.add(l);
//            }
////
//            HashMap<Integer, ArrayList<TextField>> inputDict = new HashMap<>();
//            //inputDict.
//            for (int i = 0; i < 9; i++) {
//                d.add(new Label(labels.get(i).getText()));
//                inputDict.put(i, new ArrayList<TextField>());
//                for (int j = 0; j <= 8; j++) {
//                    TextField f = new TextField("0", 1);
//                    d.add(f);
//                    inputDict.get(i).add(f);
//
//                }
//            }
//            for (int i = 0; i < 9; i++) {
//                System.out.println(inputDict.get(i).get(0).getText());
//            }
//
//            for (int i = 0; i < 4; i++) d.add(new Label("")); //button-align
//
//
//            Button OK = new Button("Ok");
//            OK.addActionListener(e1 -> {
//                for (int i = 0; i < 9; i++) {
//                    ArrayList<Integer> lst = new ArrayList<>();
//                    ArrayList<TextField> inp = inputDict.get(i);
//                    for (TextField tf : inp) {
//                        lst.add(Integer.parseInt(tf.getText()));
//                    }
//                    edges.put(i, lst);
//                }
//                initGraph();
//                drawGraph();
//                d.dispose();
//            });
//            d.add(OK);
//
//            char[][] table = {{'0', '0', '0', '1', '1', '0', '1', '0', '1'},//0
//                    {'0', '0', '0', '0', '1', '1', '0', '1', '1'},//1
//                    {'0', '0', '0', '0', '1', '1', '1', '1', '1'},//2
//                    {'1', '0', '0', '0', '1', '1', '1', '1', '1'},//3
//                    {'1', '0', '1', '1', '0', '1', '1', '1', '1'},//4
//                    {'0', '1', '1', '1', '1', '0', '1', '1', '1'},//5
//                    {'1', '0', '1', '1', '1', '1', '0', '1', '1'},//6
//                    {'0', '1', '1', '1', '1', '1', '1', '0', '1'},//7
//                    {'1', '1', '1', '1', '1', '1', '1', '1', '0'}};//8
//            Button templateGraph = new Button("Test");
//            templateGraph.addActionListener(e1 -> {
//                for (int i = 0; i < 9; i++) {
//                    int j = 0;
//                    for (TextField tf : inputDict.get(i)) {
//                        tf.setText(Character.toString(table[i][j]));
//                        j++;
//                    }
//                }
//            });
//            d.add(templateGraph);
//
//            Button cancel = new Button("Cancel");
//            cancel.addActionListener(e1 -> {
//                d.dispose();
//            });
//
//            d.add(cancel);
//
//        });
//
//        MenuItem clear = new MenuItem("Очистить");
//        clear.addActionListener(e -> {
//            System.out.println("called");
//            this.getGraphics().clearRect(0, 0, getWidth(), getHeight());
//            this.init();
//        });
//        setGraphMenu.add(clear);
//
//        setParametersMenu = new Menu("Параметры");
//        mb.add(setParametersMenu);
//        MenuItem _set = new MenuItem("Задать параметры задачи");
//        setParametersMenu.add(_set);
//        _set.addActionListener(e -> {
//            Dialog d = new Dialog(this);
//            d.setVisible(true);
//            d.setSize(300, 300);
//            GridLayout gl = new GridLayout(3, 2);
//            d.setLayout(gl);
//
//            TextField tf1 = new TextField();
//            TextField tf2 = new TextField();
//            d.add(new Label("Вершина 1: "));
//            d.add(tf1);
//            d.add(new Label("Вершина 2: "));
//            d.add(tf2);
//            Button OK = new Button("OK");
//            OK.addActionListener(e1 -> {
//                v1 = Integer.parseInt(tf1.getText());
//                v2 = Integer.parseInt(tf2.getText());
//                pinpointVertices();
//                d.dispose();
//            });
//            Button notok = new Button("Cancel");
//            notok.addActionListener(e1 -> d.dispose());
//            d.add(OK);
//            d.add(notok);
//        });
//
//    }

    public void actionPerformed(ActionEvent av) {
        repaint();
    }

    public void componentResized(ComponentEvent e) {
        repaint();
    }

    public void mouseClicked(MouseEvent me) {

    }

    public void mouseEntered(MouseEvent arg0) {}

    public void mouseExited(MouseEvent arg0) {}

    public void mousePressed(MouseEvent me) {
        System.out.println("clicked");
        int _x = me.getX();
        int _y = me.getY();
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.setStroke(new BasicStroke(4));

        if (SwingUtilities.isLeftMouseButton(me)){
            Qset.add(new Coords(_x, _y));
            g.setColor(Color.RED);
            g.drawOval(_x,_y, 2, 2);
            g.fillOval(_x,_y, 2, 2);

        }
        else if (SwingUtilities.isRightMouseButton(me)){
            Tset.add(new Coords(_x, _y));
            g.setColor(Color.GREEN);
            g.drawOval(_x,_y, 2, 2);
            g.fillOval(_x,_y, 2, 2);
        }
        System.out.println(_x + "," + _y);
    }

    public void mouseReleased(MouseEvent e) {}

    public void componentHidden(ComponentEvent arg0) {}

    public void componentMoved(ComponentEvent arg0) {}

    public void componentShown(ComponentEvent arg0) {}

}

class Coords{
    Coords(int x, int y){
        this.x = x; this.y = y;
    }
    int x, y;
}