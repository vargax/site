package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UiTablas {
    static final String REAL = "REAL";
    static final String PRESUPUESTADO = "PRESUPUESTADO";

    static final DoubleRender DR = new DoubleRender();

    public final JTabbedPane componente;

    TablasConsolidados tConsolidados;
    TablasTransacciones tTransacciones;

    public UiTablas() {

        tConsolidados = new TablasConsolidados();
        tTransacciones = new TablasTransacciones();

        componente = new JTabbedPane();
        componente.addTab(TablasConsolidados.NOMBRE, new JScrollPane(tConsolidados.componente));
        componente.addTab(TablasTransacciones.NOMBRE, tTransacciones.componente);

    }

    public void mostrarTransacciones(ArrayList<Transacción>[] transacciones) {

        tConsolidados.mostrarTransacciones(transacciones);
        tTransacciones.mostrarTransacciones(transacciones);

    }

    static ArrayList<Transacción>[] generarArreglos() {

        ArrayList[] arreglos = new ArrayList[3];

        for (int i = 0; i < arreglos.length; i++)
            arreglos[i] = new ArrayList<Transacción>();

        return arreglos;
    }

    static JSplitPane genJSplitPane(boolean scrollPane, Component superior, Component inferior) {
        JSplitPane resultado = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        if (scrollPane) {
            superior = new JScrollPane(superior);
            inferior = new JScrollPane(inferior);
        }

        resultado.setLeftComponent(superior);
        resultado.setRightComponent(inferior);
        resultado.setResizeWeight(0.5d);

        return resultado;
    }

    static abstract class ModeloTabla extends AbstractTableModel {
        String[] columnas;

        public int getColumnCount() {
            return columnas.length;
        }

        public String getColumnName(int col) {
            return columnas[col];
        }

        public Class getColumnClass(int col) {
            return getValueAt(0,col).getClass();
        }
    }

}
