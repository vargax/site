package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.árboles.Nodo;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;
import co.com.tecni.site.ui.tablas.UiTablas;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UiSite extends JFrame {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    static UiSite instance;

    private Site site;

    private UiÁrbol inmuebles;
    private UiÁrbol contratos;
    private UiÁrbol cartera;

    public static Árbol árbolActual;

    private UiMenu uiMenu;
    private UiJson uiJson;
    private UiTablas uiTablas;

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

        uiMenu = new UiMenu();
        uiJson = new UiJson();
        uiTablas = new UiTablas();

        this.setJMenuBar(uiMenu.componente);

        JSplitPane panelSecundario = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelSecundario.setRightComponent(uiJson.componente);
        panelSecundario.setLeftComponent(uiTablas.componente);
        panelSecundario.setResizeWeight(0.3d);

        JTabbedPane pestañasÁrboles = new JTabbedPane();
        pestañasÁrboles.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int tab = pestañasÁrboles.getSelectedIndex();
                switch (tab) {
                    case 0: árbolActual = Site.árbolInmuebles;
                            actualizarDetalle(inmuebles.nodoActual);
                            break;
                    case 1: árbolActual = Site.árbolContratos;
                            actualizarDetalle(contratos.nodoActual);
                            break;
                    case 2: árbolActual = Site.árbolCartera;
                            actualizarDetalle(cartera.nodoActual);
                }
            }
        });
        pestañasÁrboles.addTab(inmuebles.nombre, new JScrollPane(inmuebles.componente));
        pestañasÁrboles.addTab(contratos.nombre, new JScrollPane(contratos.componente));
        pestañasÁrboles.addTab(cartera.nombre, new JScrollPane(cartera.componente));

        JSplitPane panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelPrincipal.setLeftComponent(pestañasÁrboles);
        panelPrincipal.setRightComponent(panelSecundario);

        add(panelPrincipal);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SITE");
        this.setSize(1900, 1000);
        //this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    void actualizarDetalle(Nodo nodo) {
        if (nodo != null) {
            uiJson.mostrarDetalle(nodo.infoNodo(árbolActual));
            uiTablas.mostrarTransacciones(nodo.transaccionesNodo(árbolActual));
        } else {
            uiJson.mostrarDetalle("");
            uiTablas.mostrarTransacciones(null);
        }
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