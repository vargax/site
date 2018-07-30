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

    public final JPanel componente;

    TablasConsolidados tConsolidados;
    TablasTransacciones tTransacciones;

    public UiTablas() {

        tConsolidados = new TablasConsolidados();
        tTransacciones = new TablasTransacciones();

        componente = new JPanel(new BorderLayout());
        componente.add(panelFechas(), BorderLayout.NORTH);

        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab(TablasConsolidados.NOMBRE, new JScrollPane(tConsolidados.componente));
        jtp.addTab(TablasTransacciones.NOMBRE, tTransacciones.componente);
        componente.add(jtp, BorderLayout.CENTER);

    }

    private JPanel panelFechas() {

        JPanel jPanel = new JPanel();

        componente.add(new JLabel("Fecha inicial"));
        componente.add(new JLabel("Fecha final"));

        return jPanel;
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
