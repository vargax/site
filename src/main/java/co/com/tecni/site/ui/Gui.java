package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.ISite;
import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Agrupación;
import co.com.tecni.site.lógica.nodos.inmueble.fichas._Ficha;
import co.com.tecni.site.lógica.nodos.inmueble.tipos._Inmueble;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class Gui extends JFrame {

    private JTree tree;
    private JLabel info;

    public Gui() throws Exception {

        ISite site = new Site();

        //tree = new JTree(tecni);
        tree = new JTree(site);
        NewTreeCellRenderer renderer = new NewTreeCellRenderer();

        tree.setCellRenderer(renderer);
        tree.setShowsRootHandles(true);
        tree.setRootVisible(false);

        info = new JLabel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(new JScrollPane(tree));
        splitPane.setRightComponent(new JScrollPane(info));
        Dimension minimumSize = new Dimension(100, 50);
        tree.setMinimumSize(minimumSize);
        info.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(250);
        splitPane.setPreferredSize(new Dimension(750, 500));

        add(splitPane);

        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            //@Override
            public void valueChanged(TreeSelectionEvent e) {
                Object o = tree.getLastSelectedPathComponent();
                if (o instanceof _Inmueble) {
                    _Inmueble i = (_Inmueble)o;
                    info.setText(i.toString());
                }
                else if (o instanceof _Ficha) {
                    _Ficha f = (_Ficha)o;
                    info.setText(f.toString());
                }
                else if (o instanceof Agrupación) {
                    Agrupación a = (Agrupación)o;
                    info.setText(a.toString());
                }
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SITE");
        this.setSize(800, 600);
        this.setVisible(true);
    }
    class NewTreeCellRenderer implements TreeCellRenderer {
        private JLabel label;

        NewTreeCellRenderer() {
            label = new JLabel();
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            if (value instanceof _Inmueble) {
                label.setIcon(new ImageIcon(Gui.class.getResource("/inmueble.png")));
                label.setText(((_Inmueble)value).getNombre());
            }
            else if (value instanceof _Ficha) {
                label.setIcon(new ImageIcon(Gui.class.getResource("/ficha.png")));
                label.setText(value+"");
            }
            else if (value instanceof Agrupación) {
                label.setIcon(new ImageIcon(Gui.class.getResource("/agrupacion.png")));
                label.setText(((Agrupación)value).getNombre());
            }
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            //@Override
            public void run() {
                try {
                    new Gui();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}