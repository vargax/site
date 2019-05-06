package co.com.tecni.sari.ui;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.inmuebles.Agrupación;
import co.com.tecni.sari.lógica.inmuebles.tipos.Inmueble;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.árboles.Árbol;
import co.com.tecni.sari.lógica.árboles.ÁrbolCartera;
import co.com.tecni.sari.lógica.árboles.ÁrbolContratos;
import co.com.tecni.sari.lógica.árboles.ÁrbolInmuebles;
import co.com.tecni.sari.ui.indicadores.UiIndicadores;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UiSari extends JFrame {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String NOMBRE = "SARI Sistema de Administración de Recursos Inmobiliarios";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    static UiSari instance;

    private Sari sari;

    private UiÁrbol inmuebles;
    private UiÁrbol contratos;
    private UiÁrbol cartera;

    public static Árbol árbolActual;

    private UiMenu uiMenu;
    private UiJson uiJson;
    private UiIndicadores uiIndicadores;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiSari() throws Exception {

        instance = this;

        sari = Sari.instance;
        sari.generarÁrboles();

        inmuebles = new UiÁrbol(ÁrbolInmuebles.NOMBRE_RAIZ, Sari.árbolInmuebles);
        contratos = new UiÁrbol(ÁrbolContratos.NOMBRE_RAIZ, Sari.árbolContratos);
        cartera = new UiÁrbol(ÁrbolCartera.NOMBRE_RAIZ, Sari.árbolCartera);

        uiMenu = new UiMenu();
        uiJson = new UiJson();
        uiIndicadores = new UiIndicadores();

        this.setJMenuBar(uiMenu.componente);

        JSplitPane panelSecundario = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelSecundario.setRightComponent(uiJson.componente);
        panelSecundario.setLeftComponent(uiIndicadores.componente);
        panelSecundario.setResizeWeight(0.3d);

        JTabbedPane pestañasÁrboles = new JTabbedPane();
        pestañasÁrboles.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int tab = pestañasÁrboles.getSelectedIndex();
                switch (tab) {
                    case 0: árbolActual = Sari.árbolInmuebles;
                            cambioNodo(inmuebles.nodoActual);
                            break;
                    case 1: árbolActual = Sari.árbolContratos;
                            cambioNodo(contratos.nodoActual);
                            break;
                    case 2: árbolActual = Sari.árbolCartera;
                            cambioNodo(cartera.nodoActual);
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
        this.setTitle(NOMBRE);
        this.setSize(1900, 1000);
        this.setVisible(true);
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    void cambioNodo(Nodo nodo) {
        if (nodo != null) {

            if (nodo instanceof Agrupación)
                uiIndicadores.mostrarValor(((Agrupación) nodo).getValor());
            else if (nodo instanceof Inmueble)
                uiIndicadores.mostrarValor(((Inmueble) nodo).getValor());
            else
                uiIndicadores.mostrarValor(0.0);

            uiIndicadores.mostrarTransacciones(nodo.transaccionesNodo(árbolActual));
            uiJson.mostrarDetalle(nodo.infoNodo(árbolActual));

        } else {

            uiIndicadores.mostrarValor(0.0);
            uiIndicadores.mostrarTransacciones(null);
            uiJson.mostrarDetalle("");
        }
    }

    // -----------------------------------------------
    // MAIN
    // -----------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UiSari();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}