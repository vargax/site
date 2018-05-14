package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.ÁrbolClientes;
import co.com.tecni.site.lógica.ÁrbolInmuebles;

import javax.swing.*;

public class UiSite extends JFrame {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Site site;

    private UiÁrbol inmuebles;
    private UiÁrbol clientes;

    private UiInfo uiInfo;
    private UiTransacciones uiTransacciones;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiSite() throws Exception {

        site = Site.getInstance();
        site.importarDatos();

        inmuebles = new UiÁrbol(ÁrbolInmuebles.NOMBRE_RAIZ, site.getÁrbolInmuebles(), this);
        clientes = new UiÁrbol(ÁrbolClientes.NOMBRE_RAIZ, site.getÁrbolClientes(), this);
        uiInfo = new UiInfo();
        uiTransacciones = new UiTransacciones();

        JSplitPane panelSecundario = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelSecundario.setRightComponent(new JScrollPane(uiInfo.getComponent()));
        panelSecundario.setLeftComponent(uiTransacciones.getComponent());
        panelSecundario.setResizeWeight(0.3d);

        JTabbedPane pestañasÁrboles = new JTabbedPane();
        pestañasÁrboles.addTab(inmuebles.getNombre(), new JScrollPane(inmuebles.getComponent()));
        pestañasÁrboles.addTab(clientes.getNombre(), new JScrollPane(clientes.getComponent()));

        JSplitPane panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelPrincipal.setLeftComponent(pestañasÁrboles);
        panelPrincipal.setRightComponent(panelSecundario);

        add(panelPrincipal);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SITE");
        this.setSize(1280, 800);
        this.setVisible(true);
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void actualizarDetalle(Nodo nodo) {
        uiInfo.mostrarDetalle(nodo.infoNodo());
        uiTransacciones.mostrarTransacciones(nodo.transaccionesNodo());
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