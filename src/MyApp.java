import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class
MyApp extends JFrame implements ActionListener, MouseListener, ComponentListener {

    //JMenu setGraphMenu;
    //JMenu setParametersMenu;
    JMenuBar mb;
    JTextField kInput;
    JTextField mInput;
    ArrayList<Coords> Tset;
    ArrayList<Coords> Qset;
    CirclePOD winCircle = null;
    int k; //k dots of T
    int m; //m dots of Q
    int winX, winY, winR;
    Boolean isParamsSet;
    Boolean isSetsSet;

    editDialog editTDialog;
    editDialog editQDialog;

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
        Tset.add(new Coords(434, 184));
        Tset.add(new Coords(199, 179));
        Tset.add(new Coords(136, 260));
        Tset.add(new Coords(312, 392));
        Tset.add(new Coords(514, 357));
        Tset.add(new Coords(334, 266));
        Tset.add(new Coords(325, 101));
        Tset.add(new Coords(266, 300));
        Tset.add(new Coords(215, 255));
        Tset.add(new Coords(316, 190));
        Tset.add(new Coords(488, 146));
        Tset.add(new Coords(549, 262));
        Tset.add(new Coords(406, 332));
        Tset.add(new Coords(404, 241));
        //Circle - x150 y250 r 100
//        Tset.add(new Coords(300, 400));
//        Tset.add(new Coords(300, 200));
//        Tset.add(new Coords(400, 300));
//        Tset.add(new Coords(200, 300));

        Tset.add(new Coords(150, 350));
        Tset.add(new Coords(150, 150));
        Tset.add(new Coords(250, 250));
        Tset.add(new Coords(50, 250));



        String[] colTheads = {"Tx", "Ty"};
        String[] colTheads_q = {"Qx", "Qy"};
        editTDialog = new editDialog("Задать множество Т", this, colTheads, Tset, true, this);
        //editTDialog.set_parent(this);
        //editTDialog.isTset = true;

        editQDialog = new editDialog("Задать множество Q", this, colTheads_q, Qset, false, this);
        //editQDialog.set_parent(this);
        //editQDialog.isTset = false;

        mb = new JMenuBar();
        setJMenuBar(mb);

        JMenu setSets = new JMenu("Ввод данных");
        JMenu setParams = new JMenu("Параметры");
        JMenu solve = new JMenu("Решение");

        mb.add(solve);

        mb.add(setSets);
        mb.add(setParams);

        JMenuItem solveStart = new JMenuItem("Решить");
        solve.add(solveStart);
        solveStart.addActionListener(e -> {
            findCircle();
        });

        JMenuItem solvResults = new JMenuItem("Результат");
        solve.add(solvResults);
        solvResults.addActionListener(e -> {
            if (winCircle == null){
                JDialog noRes = new JDialog(this);
                noRes.setLayout(new GridLayout(2,1));
                noRes.add(new JLabel("Результат не найден"));
                Button ok = new Button("Ok"); ok.addActionListener(e1 -> {noRes.dispose();});
                noRes.add(ok);
                noRes.setSize(150,100);
                noRes.setVisible(true);
                return;
            }
            JDialog resD = new JDialog(this);
            resD.setTitle("Результат");
            resD.setLayout(new GridLayout(2,1));
            String out = new String();
            out += "cX:";
            out += Integer.toString(winCircle.x);
            out += " cY:";
            out += Integer.toString(winCircle.y);
            out += " cR:";
            out += Integer.toString(winCircle.r);

            resD.add(new JLabel(out));

//            resD.setLayout(new FlowLayout());
//            resD.setVisible(true);
//            String [] ch = {"cX", "cY", "R"};
//            Object [][] obj = new Object[1][3];
//            obj[0][0] = winCircle.x;
//            obj[0][1] = winCircle.y;
//            obj[0][2] = winCircle.r;
//            JTable t = new JTable(obj, ch);
//            t.setColumnSelectionAllowed(false);
//            t.setRowSelectionAllowed(false);
//            resD.add(t);

            JButton _ok = new JButton("Ok");
            _ok.addActionListener(e1 -> {resD.dispose();});
            resD.add(_ok);
            resD.setVisible(true);
            resD.setSize(350,100);
        });

        JMenuItem editDataTableT = new JMenuItem("Редактировать Множество Т");
        JMenuItem editDataTableQ = new JMenuItem("Редактировать Множество Q");
        JMenuItem clearMI = new JMenuItem("Очистить");
        JMenuItem editProblemParams = new JMenuItem("Задать параметры");

        setSets.add(editDataTableT);
        setSets.add(editDataTableQ);
        setParams.add(editProblemParams);
        setSets.add(clearMI);

        clearMI.addActionListener(e -> {
            Qset.clear();
            Tset.clear();
            revalidate();
            repaint();
        });

        editProblemParams.addActionListener(e -> {
            System.out.println("CALLED!!");
            JDialog d = new JDialog();
            d.setName("Параметры задачи");
            d.setVisible(true);
            d.setSize(300,300);
            GridLayout gl = new GridLayout(3,2);
            d.setLayout(gl);

            d.add(new JLabel("k: "));
            kInput = new JTextField();
            d.add(kInput);
            d.add(new JLabel("M: "));
            mInput = new JTextField();
            d.add(mInput);

            Button ok = new Button("Ok");
            Button cancel = new Button("Cancel");

            ok.addActionListener(e1 -> {
                k = Integer.parseInt(kInput.getText());
                m = Integer.parseInt(mInput.getText());
                //if (k <= 3){ k = 4;}
                isParamsSet = true;
                d.dispose();
            });
            cancel.addActionListener(e1 -> {d.dispose();});

            d.add(cancel);
            d.add(ok);

        });

        editDataTableT.addActionListener(e -> {
            //System.out.println("called edit Table");
            //System.out.println(Tset.size() + " tset size");
            editTDialog.actualize(Tset);
            //Tset.remove(0);
//            System.out.println(Tset.size() + " tset size after remove");
//            editTDialog.debug();

            editTDialog.setVisible(true);

//            JDialog edit = new JDialog(this);
//            edit.setSize(300,400);
//            edit.setResizable(false);
//            edit.setName("Редактирование множества T");
//            edit.setVisible(true);
//            Container pane = edit.getContentPane();
//            //JDialog pane = edit;
//            pane.setLayout(new FlowLayout());
//            //table-init
//            String[] columnNames = {"Tx", "Ty"};
//            Object[][] data = {};
//            for (int i = 0; i < Tset.size(); i++){
//                data[i][1] = Tset.get(i).x;
//                data[i][2] = Tset.get(i).y;
//            }
//            JTable table = new JTable(data,columnNames);
//
//            //init-table
//            JScrollPane jsp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//            jsp.setPreferredSize(new java.awt.Dimension(250, 250));
//            table.setRowSelectionAllowed(true);
//            jsp.getViewport().setView(table);
//
//            JButton jbtAdd = new JButton(" + ");
//
//            jbtAdd.addActionListener(e1 -> {});
//
//            JButton jbtDel = new JButton(" - ");
//
//            jbtDel.addActionListener(e1 -> {});
//
//            JButton jbtOk = new JButton(" Ок ");
//
//            jbtOk.addActionListener(e1 -> {});
//
//            pane.add(new JLabel("Точки: "));
//
//            pane.add(jsp);
//
//            pane.add(new JLabel(""));
//
//            pane.add(jbtAdd);
//
//            pane.add(new JLabel(" "));
//
//            pane.add(jbtDel);
//
//            pane.add(new JLabel(" "));
//
//            pane.add(jbtOk);
//
//            addWindowListener (new WindowAdapter()
//
//            {
//
//                public void windowClosing(WindowEvent ie)
//
//                {
//
//                    dispose();
//
//                }
//
//            });

            //pane.add(jsp);
            //edit.add(jsp);
        });

        editDataTableQ.addActionListener(e -> {
            editQDialog.actualize(Qset);
            editQDialog.setVisible(true);
        });

    }

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
        int _x = me.getX();
        int _y = me.getY();
        int cx = _x - (1);
        int cy = _y - 1; // y - (r/2)
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.setStroke(new BasicStroke(3));
        System.out.println(_x + "x" + _y);
        if (SwingUtilities.isLeftMouseButton(me)){
            Qset.add(new Coords(_x, _y));
            g.setColor(Color.RED);
            g.drawOval(cx,cy, 2, 2);
            g.fillOval(cx,cy, 2, 2);

        }
        else if (SwingUtilities.isRightMouseButton(me)){
            Tset.add(new Coords(_x, _y));
            g.setColor(Color.GREEN);
            g.drawOval(cx,cy, 2, 2);
            g.fillOval(cx,cy, 2, 2);
        }
    }
    public void mouseReleased(MouseEvent e) {}

    public void componentHidden(ComponentEvent arg0) {}

    public void componentMoved(ComponentEvent arg0) {}

    public void componentShown(ComponentEvent arg0) {}

    public void repaintDots(){
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.setStroke(new BasicStroke(3));

        for (Coords c : Tset){
            g.setColor(Color.GREEN);
            g.drawOval(c.x -1 , c.y -1, 2, 2);
            g.fillOval(c.x -1 , c.y -1 , 2, 2);
        }
        for (Coords c : Qset){
            g.setColor(Color.RED);
            g.drawOval(c.x - 1 , c.y - 1 , 2, 2);
            g.fillOval(c.x - 1, c.y - 1, 2, 2);
        }


    }
    class CirclePOD{
        int x,y,r;
        CirclePOD(int xx,int yy,int rr){
            x = xx; y = yy; r = rr;
        }
    }
    public void findCircle(){
        int step = 50;
        int radius = 2;
        ArrayList<CirclePOD> suitableStep1 = new ArrayList<>();
        found:
        for (int x = 1; x < 500; x++){
            for (int y = 1; y < 500; y++){
                for (int r = radius; r < 500; r ++){
                    int colCount = 0;
                    for(Coords c: Tset){
                        if(checkCollision(r, x,y, c.x, c.y)){
                            colCount++;
                            //checkCollision(r,x,y, c.x, c.y);
                        }
                    }
                    if (colCount > k){
                            CirclePOD cp = new CirclePOD(x,y,r);
                            //suitableStep1.add(cp);

                            System.out.println("x: " + x + " y:" + y + " r:" +r);
                            int _m = 0;
                            for (Coords coords: Qset){
                                if (checkInside(cp, coords.x, coords.y)){
                                    _m++;
                                }
                            }
                            if (_m >= m){
                                suitableStep1.add(cp);
                                break found;
                            }

                        //suitableStep1.add(new CirclePOD(x, y, r));
                    }
                }
            }
        }

        System.out.println("finished" + " size of found:" + suitableStep1.size());


        Graphics2D g =(Graphics2D) getGraphics();
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        try{
        if (suitableStep1.size() == 0) throw  new RuntimeException();
        for (CirclePOD cpx : suitableStep1){
            int count = 0;
            for (Coords c : Tset){
                if(checkCollision(cpx.r, cpx.x, cpx.y, c.x, c.y)){
                    count++;
                }
            }
            if (count > k){
                System.out.println("Found!");
                Graphics2D g2 = (Graphics2D) this.getGraphics();
                g2.setStroke(new BasicStroke(1));
                int x = cpx.x - (cpx.r);
                int y = cpx.y - (cpx.r);
                g2.drawOval(x, y, cpx.r * 2, cpx.r * 2);
                g2.setColor(Color.BLUE);
                //g2.drawArc(cpx.x, cpx.y, cpx.r, cpx.r, 0, 360);
                //g2.drawLine(cpx.x, cpx.y, cpx.x+10, cpx.y);
                System.out.println("X:" + cpx.x + " Y:" + cpx.y + " R:" + cpx.r);
                this.repaintDots();
                winCircle = cpx;
            }
        }
        }
        catch (Exception e){
            JDialog d = new JDialog(this);
            GridLayout gl = new GridLayout(2,1);
            d.setLayout(gl);
            d.add(new JLabel("Окружность не найдена"));
            JButton ok = new JButton("Ok");
            ok.addActionListener(e1 -> {
                d.dispose();
            });
            d.add(ok);
            d.setVisible(true);
            d.setSize(200,100);
        }

    }
    boolean checkInside(CirclePOD c, int x, int y){
        Double dxO = (double) x; Double dyO = (double)y;
        Double dxC = (double) c.x; Double dyC = (double)c.y;
        Double dx = Math.abs(dxO - dxC);
        Double dy = Math.abs(dyO - dyC);
        Double qxx = Math.pow(dx,2);
        Double qyy = Math.pow(dy,2);
        Double qrr = Math.pow((double) c.r,2);
        Double qwe = qxx + qyy;
        //Double d = Math.sqrt((Math.pow((dxO - dxC),2) + (Math.pow((dyO - dyC),2))));
        if (qwe.intValue() < qrr.intValue() ){
            return true;
        }
        else return false;//12 544
    }
    boolean checkCollision(int rad, int xCircle, int yCircle, int XotherPoint, int YotherPoint){
        Double drad = (double) rad; Double dxC = (double) xCircle;
        Double dyC = (double) yCircle; Double dxO = (double) XotherPoint; Double dyO = (double) YotherPoint;
        //(x - center_x)^2 + (y - center_y)^2 < radius^2

        Double dx = Math.abs(dxO - dxC);
        Double dy = Math.abs(dyO - dyC);

//
//        if (dx>drad)return false;
//        if (dy>drad)return false;
//        //Double d = Math.sqrt((Math.pow((dxO - dxC),2) + (Math.pow((dyO - dyC),2))));//(double)
        Double qxx = Math.pow(dx,2);
        Double qyy = Math.pow(dy,2);
        Double qrr = Math.pow(drad,2);
        Double qwe = qxx + qyy;
        if (qwe.intValue() == qrr.intValue()){
            //System.out.println("rad:" + rad + " x_center:" + xCircle + " y_center:" + yCircle +
             //       " x_point:" + XotherPoint  + " y_point:" + YotherPoint);
            return true;
        }
        else
            return false;
    }

}

class Coords{
    Coords(int x, int y){
        this.x = x; this.y = y;
    }
    int x, y;
}


//-----------------------------
class editDialog extends  JDialog{
    JScrollPane jsp;
    Object[][] data = new  Object[100][100];
    String[] colHeads;
    private JTable table;
    ArrayList<Coords> dataset;
    MyApp _parent;
    DefaultTableModel model;
    boolean isTset;
    int n;
    editDialog(String str, Frame parent, String[] colHeads, ArrayList<Coords> dataset, boolean b, MyApp pp){
        super (parent, str,true);
        this._parent = pp;
        isTset = b;
        setSize(300,400);
        setResizable(false);
        Container contPane = getContentPane();
        contPane.setLayout(new FlowLayout());

        this.dataset = dataset;
        this.colHeads = colHeads;
        checkDataset();
        data = new Object[dataset.size()][100];
        model = new DefaultTableModel(data, colHeads);
        table = new JTable();
        checkDataset();
        jsp = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setPreferredSize(new java.awt.Dimension(250, 250));
        checkDataset();
        actualize(dataset);
        //table = new JTable(data, colHeads);
        checkDataset();

        JButton jbtAdd = new JButton(" + ");
        jbtAdd.addActionListener(e -> {
            System.out.println(dataset.size() + "dataset size");
            checkDataset();
            actualize(dataset);
            syncTable();
            checkDataset();
            //actualize(dataset);
            checkDataset();
            data = new Object[][]{};
            DefaultTableModel model_ = new DefaultTableModel(data, colHeads);
            System.out.println("Dataset: ");
            for (Coords c : dataset){
                System.out.println(c.x + "x" + c.y);
            }
            checkDataset();
            dataset.add(new Coords(0,0));
            for (Coords c : dataset){
                Object[] obj = new Object[2];
                obj[0] = c.x;
                obj[1] = c.y;
                model_.addRow(obj);
            }
            checkDataset();
//            dataset.add(new Coords(0,0));
//            checkDataset();
//            Object[] obj = new Object[2];
//            obj[0] = 0;
//            obj[1] = 0;
           // model_.addRow(obj);
            table.setModel(model_);
            n++;
            checkDataset();
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            //syncTable();
            checkDataset();
        });

        JButton jbtDel = new JButton(" - ");
        jbtDel.addActionListener(e -> {
            int selRow = table.getSelectedRow();
            //Object[][] tmpData = new Object[dataset.size() - 1][4];
            dataset.remove(selRow);
            actualize(dataset);

        });
        JButton jbtOk = new JButton(" Ок ");
        checkDataset();
        jbtOk.addActionListener(e -> {
           syncTable();
           for (Coords c : _parent.Tset)
           {
               System.out.println(c.x + "x" + c.y);
           }
           _parent.repaintDots();
           this.dispose();
        });
        contPane.add(new JLabel("Точки: "));
        contPane.add(jsp);
        contPane.add(new JLabel(""));
        contPane.add(jbtAdd);
        contPane.add(new JLabel(" "));
        contPane.add(jbtDel);
        contPane.add(new JLabel(" "));
        contPane.add(jbtOk);

        addWindowListener (new WindowAdapter(){
            public void windowClosing(WindowEvent ie){
                dispose();
            }

        });
    }
    void actualize(ArrayList<Coords> datasetUpd){
        checkDataset();
        n = datasetUpd.size();
        checkDataset();
        this.set_dataset( datasetUpd);
        checkDataset();
        System.out.println(dataset.size() + " size");
        data = new Object[][]{};
        DefaultTableModel model_ = new DefaultTableModel(data, colHeads);
        model = model_;
        checkDataset();
        for (Coords c : dataset){
            Object[] obj = new Object[2];
            obj[0] = c.x;
            obj[1] = c.y;
            model_.addRow(obj);
        }
        table.setModel(model_);
        table.setRowSelectionAllowed(true);
        jsp.getViewport().setView(table);
        checkDataset();
    }
    void syncTable(){
        checkDataset();
        ArrayList<Coords> tmp = new ArrayList<>();
        checkDataset();
        int _n = table.getRowCount();
        checkDataset();
        for (int i = 0; i < _n; i++){
            int _x = Integer.parseInt(table.getValueAt(i,0).toString());
            int _y = Integer.parseInt(table.getValueAt(i,1).toString());
            tmp.add(new Coords(_x, _y));
        }
        checkDataset();
        this.set_dataset(tmp);
        checkDataset();
        if(isTset){
            checkDataset();
                _parent.Tset = tmp;
            checkDataset();
        }
            else{
            checkDataset();
                _parent.Qset = tmp;
            checkDataset();
        }
        checkDataset();
        actualize(dataset);
        checkDataset();
    }

    ArrayList<Coords> getDataset(){return dataset;}
    void debug(){
        System.out.println(dataset.size() + "dataset size in dialog");
    }
    void set_parent(MyApp p){_parent = p;}
    void set_dataset(ArrayList<Coords> ds){
        checkDataset();
        this.dataset = ds;
        if (isTset)
            _parent.Tset = ds;
        else
            _parent.Qset = ds;
        checkDataset();
//        if (ds == dataset)
//            return;
//        for (int i = 0; i < dataset.size(); i ++){
//            dataset.remove(i);
//        }
//        dataset = new ArrayList<>();
//        for (int i = 0; i < ds.size(); i++){
//            dataset.add(ds.get(i));
//        }
    }
    int bugCount;
    void checkDataset(){
        if (dataset == null)return;
        bugCount++;
        if (isTset){
            if (dataset == _parent.Tset) {//
                // System.out.println("");\
            }
            else{
                throw new RuntimeException();
            }
        }
        else if (!isTset){
            if (dataset == _parent.Tset){
                throw new RuntimeException();
            }
            else{
                //System.out.println("everythin is ok with QSet");
            }
        }
    }
}