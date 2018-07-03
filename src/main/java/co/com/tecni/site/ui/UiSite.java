package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class UiSite extends JFrame {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    static UiSite instance;

    private Site site;

    private UiÁrbol inmuebles;
    private UiÁrbol contratos;
    private UiÁrbol cartera;

    private Árbol árbolActual;

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
        pestañasÁrboles.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int tab = pestañasÁrboles.getSelectedIndex();
                switch (tab) {
                    case 0: árbolActual = Site.árbolInmuebles;
                            break;
                    case 1: árbolActual = Site.árbolContratos;
                            break;
                    case 2: árbolActual = Site.árbolCartera;
                }
            }
        });
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
        String infoNodo = nodo.infoNodo(árbolActual);
        if (infoNodo != null)
            uiInfo.mostrarDetalle(infoNodo);

        uiTransacciones.mostrarTransacciones(nodo.transaccionesNodo(árbolActual));
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