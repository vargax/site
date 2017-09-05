package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.ISite;
import co.com.tecni.site.lógica.Site;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import javax.swing.*;

public class Gui extends JFrame {

    private JTree tree;
    private JLabel info;

    public Gui() throws Exception {
        //Raíz - TECNI
        DefaultMutableTreeNode tecni = new DefaultMutableTreeNode("TECNI");
        //Inmueble Ecotower 93
        DefaultMutableTreeNode inmueble1 = new DefaultMutableTreeNode("Ecotower 93");
        //Inmueble Ecotower 93 - Piso 1
        DefaultMutableTreeNode piso1 = new DefaultMutableTreeNode("Piso 1");
        piso1.add(new DefaultMutableTreeNode("Oficina 1"));
        piso1.add(new DefaultMutableTreeNode("Oficina 2"));
        piso1.add(new DefaultMutableTreeNode("Oficina 3"));
        piso1.add(new DefaultMutableTreeNode("Oficina 4"));
        inmueble1.add(piso1);
        //Inmueble Ecotower 93 - Piso 2
        DefaultMutableTreeNode piso2 = new DefaultMutableTreeNode("Piso 2");
        piso2.add(new DefaultMutableTreeNode("Oficina 1"));
        piso2.add(new DefaultMutableTreeNode("Oficina 2"));
        piso2.add(new DefaultMutableTreeNode("Oficina 3"));
        piso2.add(new DefaultMutableTreeNode("Oficina 4"));
        inmueble1.add(piso2);
        inmueble1.add(new DefaultMutableTreeNode("Piso 3"));
        inmueble1.add(new DefaultMutableTreeNode("Piso 4"));
        inmueble1.add(new DefaultMutableTreeNode("Piso 5"));
        inmueble1.add(new DefaultMutableTreeNode("Piso 6"));

        //Inmueble Hotel Terra
        DefaultMutableTreeNode inmueble2 = new DefaultMutableTreeNode("Hotel Terra");
        inmueble2.add(new DefaultMutableTreeNode("Piso 1"));
        inmueble2.add(new DefaultMutableTreeNode("Piso 2"));
        inmueble2.add(new DefaultMutableTreeNode("Piso 3"));
        inmueble2.add(new DefaultMutableTreeNode("Piso 4"));
        inmueble2.add(new DefaultMutableTreeNode("Piso 5"));
        //Inmueble Hotel Terra - Piso 6
        DefaultMutableTreeNode piso6 = new DefaultMutableTreeNode("Piso 6");
        piso6.add(new DefaultMutableTreeNode("Habitación 1"));
        piso6.add(new DefaultMutableTreeNode("Habitación 2"));
        piso6.add(new DefaultMutableTreeNode("Habitación 3"));
        piso6.add(new DefaultMutableTreeNode("Habitación 4"));
        piso6.add(new DefaultMutableTreeNode("Habitación 5"));
        inmueble1.add(piso6);
        //Inmueble Hotel Terra - Piso 7
        DefaultMutableTreeNode piso7 = new DefaultMutableTreeNode("Piso 7");
        piso7.add(new DefaultMutableTreeNode("Habitación 1"));
        piso7.add(new DefaultMutableTreeNode("Habitación 2"));
        piso7.add(new DefaultMutableTreeNode("Habitación 3"));
        piso7.add(new DefaultMutableTreeNode("Habitación 4"));
        piso7.add(new DefaultMutableTreeNode("Habitación 5"));
        inmueble1.add(piso7);

        tecni.add(inmueble1);
        tecni.add(inmueble2);

        Site site = new Site();

        //tree = new JTree(tecni);
        tree = new JTree(site);
        ImageIcon imageIcon = new ImageIcon(Gui.class.getResource("/new.png"));
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(imageIcon);

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
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                info.setText(selectedNode.getUserObject().toString());
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SITE");
        this.setSize(800, 600);
        this.setVisible(true);
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