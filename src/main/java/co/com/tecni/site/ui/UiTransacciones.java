package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class UiTransacciones {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private JSplitPane panelTransacciones;

    private ResumenTransacciones resumenTransacciones;
    private JTable tablaResumen;

    private DetalleTransacciones detalleTransacciones;
    private JTable tablaDetalle;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public UiTransacciones() {
        DoubleRender doubleRender = new DoubleRender();

        resumenTransacciones = new ResumenTransacciones();
        tablaResumen = new JTable(resumenTransacciones);
        tablaResumen.setDefaultRenderer(Double.class, doubleRender);

        detalleTransacciones = new DetalleTransacciones();
        tablaDetalle = new JTable(detalleTransacciones);
        tablaDetalle.setDefaultRenderer(Double.class, doubleRender);

        panelTransacciones = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelTransacciones.setLeftComponent(new JScrollPane(tablaResumen));
        panelTransacciones.setRightComponent(new JScrollPane(tablaDetalle));
        panelTransacciones.setResizeWeight(0.5d);
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    Component getComponent() {
        return panelTransacciones;
    }

    /**
     * Muestras las transacciones asociadas al nodo seleccionado
     * @param transacciones arreglo con las transacciones dividas en:
     *                      transacciones[0] ancestros
     *                      transacciones[1] propias
     *                      transacciones[2] descendientes
     */
    void mostrarTransacciones(ArrayList<Transacción>[] transacciones) {
        resumenTransacciones.setTransxTipoPariente(transacciones);
        detalleTransacciones.setTransxTipoPariente(transacciones);

        tablaResumen.updateUI();
        tablaDetalle.updateUI();
    }
}

class ResumenTransacciones extends AbstractTableModel {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String[] COLUMNAS = {"Ficha",
            "Ancestros",
            "Propias",
            "Descencientes",
            "TOTAL"
    };

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private ArrayList<Transacción>[] transxTipoPariente;
    private HashMap<String, double[]> resumen;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    ResumenTransacciones() {
        transxTipoPariente = new ArrayList[3];
        resumen = new HashMap<>();
    }

    // -----------------------------------------------
    // Métodos lógica
    // -----------------------------------------------
    void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {
        this.transxTipoPariente = transxTipoPariente;
        resumen.clear();

        for (int i = 0; i < transxTipoPariente.length; i++) {
            for (Transacción transacción : transxTipoPariente[i]) {
                String llave = transacción.getFicha().getClass().getSimpleName();
                double[] valores = resumen.get(llave);

                if (valores == null) {
                    valores = new double[4];
                    resumen.put(llave, valores);
                }

                valores[i] = valores[i] + transacción.getMonto();
            }
        }

        for (double[] valores : resumen.values())
            valores[3] = valores[0] + valores[1] + valores[2];

    }

    // -----------------------------------------------
    // Métodos Interface
    // -----------------------------------------------
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    public String getColumnName(int col) {
        return COLUMNAS[col];
    }

    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }

    public int getRowCount() {
        return resumen.size();
    }

    public Object getValueAt(int row, int col) {
        String fila = (String) resumen.keySet().toArray()[row];
        if (col == 0)
            return fila;
        return resumen.get(fila)[col-1];
    }
}

class DetalleTransacciones extends AbstractTableModel {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String[] COLUMNAS = {"Fecha",
            "Monto",
            "Concepto",
            "Tercero"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private ArrayList<Transacción> transacciones;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    DetalleTransacciones() {
        transacciones = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos lógica
    // -----------------------------------------------
    void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {
        transacciones.clear();

        for (int i = 0; i < transxTipoPariente.length; i++)
                transacciones.addAll(transxTipoPariente[i]);

    }

    // -----------------------------------------------
    // Métodos Interface
    // -----------------------------------------------
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    public String getColumnName(int col) {
        return COLUMNAS[col];
    }

    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }

    public int getRowCount() {
        return transacciones.size();
    }

    public Object getValueAt(int row, int col) {
        Transacción transacción = transacciones.get(row);
        switch (col) {
            case 0: return transacción.getFecha();
            case 1: return transacción.getMonto();
            case 2: return transacción.getConcepto();
            case 3: return transacción.getTercero().getNombre();
            default: return null;
        }
    }
}

class DoubleRender extends DefaultTableCellRenderer {
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,###");

    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);

        setHorizontalAlignment(RIGHT);
        setText(DECIMAL_FORMAT.format((Number) value));
        if ((Double) value < 0) setForeground(Color.RED);

        return this;
    }
}
