package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Agrupación;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.fichas.Ficha;
import co.com.tecni.site.lógica.nodos.inmueble.tipos.Inmueble;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class Gui extends JFrame {

    private JTree tree;
    private JLabel info;

    public Gui() throws Exception {

        Site site = new Site();

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
        splitPane.setDividerLocation(400);
        splitPane.setPreferredSize(new Dimension(1200, 600));

        add(splitPane);

        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                Nodo o = (Nodo) tree.getLastSelectedPathComponent();
                /*
                ScriptEngineManager factory = new ScriptEngineManager();
                ScriptEngine engine = factory.getEngineByName("JavaScript");
                try{
                    System.out.println(o.toString());
                    engine.eval(
                            "var o="+o.toString()+";"+
                            "var id='<h1>'+o.ID+'</h1>';"+
                            "var html='<tr>'+"+
                            "         '  <td>&nbsp;</td>'+"+
                            "         '    <td>Construidos</td>'+"+
                            "         '    <td>Libres</td>'+"+
                            "         '    <td>TOTALES</td>'+"+
                            "         '  </tr>'+"+
                            "         '  <tr>'+"+
                            "         '    <td>Privados</td>'+"+
                            "         '    <td>'+o.PC+'</td>'+"+
                            "         '    <td>'+o.PL+'</td>'+"+
                            "         '    <td>'+(o.PC+o.PL).toFixed(2)+'</td>'+"+
                            "         '  </tr>'+"+
                            "         '  <tr>'+"+
                            "         '    <td>Comunes</td>'+"+
                            "         '    <td>'+o.CC+'</td>'+"+
                            "         '    <td>'+o.CL+'</td>'+"+
                            "         '    <td>'+(o.CC+o.CL).toFixed(2)+'</td>'+"+
                            "         '  </tr>'+"+
                            "         '  <tr>'+"+
                            "         '    <td>TOTALES</td>'+"+
                            "         '    <td>'+(o.CC+o.PC).toFixed(2)+'</td>'+"+
                            "         '    <td>'+(o.CL+o.PL).toFixed(2)+'</td>'+"+
                            "         '    <td>'+(o.CC+o.PC+o.CL+o.PL).toFixed(2)+'</td>'+"+
                            "         '  </tr>';"
                            );
                }catch(Exception ex){}


                info.setText(
                        "<html>"+
                        engine.get("id")+
                        "<table width='500px' border='1' style='border:dotted;background-color:#5DB0EC; font-size:24px; font-family:Arial'>"+
                        "  <tbody>" +
                        engine.get("html")+
                        "  </tbody>" +
                        "</table><br><h5>"+o.toString()+"</h5></html>"

                        );
                        */
                info.setText(o.infoNodo());
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SITE");
        this.setSize(1200, 600);
        this.setVisible(true);
    }

    class NewTreeCellRenderer implements TreeCellRenderer {
        private JLabel label;

        NewTreeCellRenderer() {
            label = new JLabel();
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            if (value instanceof Inmueble)
                label.setIcon(new ImageIcon(Gui.class.getResource("/static/íconos/inmueble.png")));

            else if (value instanceof Ficha)
                label.setIcon(new ImageIcon(Gui.class.getResource("/static/íconos/ficha.png")));

            else if (value instanceof Agrupación)
                label.setIcon(new ImageIcon(Gui.class.getResource("/static/íconos/agrupacion.png")));

            else label.setIcon(new ImageIcon(Gui.class.getResource("/static/íconos/nodo.png")));

            label.setText(((Nodo)value).nombreNodo());
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