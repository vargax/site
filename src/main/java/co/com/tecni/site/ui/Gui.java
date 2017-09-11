package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.ISite;
import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.inmueble.Inmueble;

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
                Inmueble inmueble = (Inmueble) tree.getLastSelectedPathComponent();
                info.setText(inmueble.getNombre());
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