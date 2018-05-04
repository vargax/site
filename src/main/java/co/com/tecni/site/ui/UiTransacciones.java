package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UiTransacciones {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private ModeloTransacciones modeloTransacciones;
    private JTable jTable;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public UiTransacciones() {
        modeloTransacciones = new ModeloTransacciones();
        jTable = new JTable(modeloTransacciones);
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    Component getComponent() {
        return jTable;
    }

    void mostrarTransacciones(ArrayList<Transacción> transacciones) {
        modeloTransacciones.setTransacciones(transacciones);
        jTable.updateUI();
    }
}

class ModeloTransacciones extends AbstractTableModel {
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
    public ModeloTransacciones() {
        transacciones = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    void setTransacciones(ArrayList<Transacción> transacciones) {
        this.transacciones = transacciones;
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
