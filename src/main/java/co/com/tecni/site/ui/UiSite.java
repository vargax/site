package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.ÁrbolClientes;
import co.com.tecni.site.lógica.ÁrbolInmuebles;

import javax.swing.*;
import java.awt.*;

public class UiSite extends JFrame {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Site site;

    private UiÁrbol inmuebles;
    private UiÁrbol clientes;

    private UiInfo uiInfo;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiSite() throws Exception {

        site = new Site();

        inmuebles = new UiÁrbol(ÁrbolInmuebles.NOMBRE_RAIZ, site.getÁrbolInmuebles(), this);
        clientes = new UiÁrbol(ÁrbolClientes.NOMBRE_RAIZ, site.getÁrbolClientes(), this);
        uiInfo = new UiInfo();

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab(inmuebles.getNombre(), new JScrollPane(inmuebles.getComponent()));
        jTabbedPane.addTab(clientes.getNombre(), new JScrollPane(clientes.getComponent()));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setPreferredSize(new Dimension(1200, 600));

        splitPane.setLeftComponent(jTabbedPane);
        splitPane.setRightComponent(new JScrollPane(uiInfo.getComponent()));

        add(splitPane);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SITE");
        this.setSize(1200, 600);
        this.setVisible(true);
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void actualizarDetalle(String detalle) {
        uiInfo.mostrarDetalle(detalle);
    }

    // -----------------------------------------------
    // MAIN
    // -----------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UiSite();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}