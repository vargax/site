package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;

import javax.swing.*;

class UiSite extends JFrame {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    static UiSite instance;

    private Site site;

    private UiÁrbol inmuebles;
    private UiÁrbol contratos;
    private UiÁrbol cartera;

    private UiInfo uiInfo;
    private UiTransacciones uiTransacciones;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiSite() throws Exception {

        instance = this;

        site = Site.getInstance();
        site.generarÁrboles();

        inmuebles = new UiÁrbol(ÁrbolInmuebles.NOMBRE_RAIZ, Site.árbolInmuebles);
        contratos = new UiÁrbol(ÁrbolContratos.NOMBRE_RAIZ, Site.árbolContratos);
        cartera = new UiÁrbol(ÁrbolCartera.NOMBRE_RAIZ, Site.árbolCartera);

        uiInfo = new UiInfo();
        uiTransacciones = new UiTransacciones();

        JSplitPane panelSecundario = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelSecundario.setRightComponent(new JScrollPane(uiInfo.getComponent()));
        panelSecundario.setLeftComponent(uiTransacciones.getComponent());
        panelSecundario.setResizeWeight(0.3d);

        JTabbedPane pestañasÁrboles = new JTabbedPane();
        pestañasÁrboles.addTab(inmuebles.getNombre(), new JScrollPane(inmuebles.getComponent()));
        pestañasÁrboles.addTab(contratos.getNombre(), new JScrollPane(contratos.getComponent()));
        pestañasÁrboles.addTab(cartera.getNombre(), new JScrollPane(cartera.getComponent()));

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
    void actualizarDetalle(Nodo nodo) {
        String infoNodo = nodo.infoNodo();
        if (infoNodo != null)
            uiInfo.mostrarDetalle(infoNodo);

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