package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UiTablas {
    static final DoubleRender DR = new DoubleRender();

    public final JTabbedPane componente;

    TablasConsolidados tConsolidados;
    TablasTransacciones tTransacciones;

    public UiTablas() {

        tConsolidados = new TablasConsolidados();
        tTransacciones = new TablasTransacciones();

        componente = new JTabbedPane();
        componente.addTab(TablasConsolidados.NOMBRE, new JScrollPane(tConsolidados.componente));
        componente.addTab(TablasTransacciones.NOMBRE, new JScrollPane(tTransacciones.componente));

    }

    public void mostrarTransacciones(ArrayList<Transacción>[] transacciones) {

        tConsolidados.mostrarTransacciones(transacciones);
        tTransacciones.mostrarTransacciones(transacciones);

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
