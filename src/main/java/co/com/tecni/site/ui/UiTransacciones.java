package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.ui.table.DoubleRender;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UiTransacciones {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private JSplitPane panelTransacciones;

    private JTable tablaResumen;
    private ResumenTransacciones resumenTransacciones;
    class ResumenTransacciones extends AbstractTableModel {
        // -----------------------------------------------
        // Constantes
        // -----------------------------------------------
        private final String[] COLUMNAS = {"Ficha",
                "Ancestros",
                "Propias",
                "Descencientes",
                "TOTAL"
        };

        // -----------------------------------------------
        // Atributos
        // -----------------------------------------------
        private LinkedHashMap<String, double[]> resumen;

        // -----------------------------------------------
        // Constructor
        // -----------------------------------------------
        ResumenTransacciones() {
            resumen = new LinkedHashMap<>();
        }

        // -----------------------------------------------
        // Métodos lógica
        // -----------------------------------------------
        void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {
            resumen.clear();

            double[] totales = new double[4];

            for (int i = 0; i < transxTipoPariente.length; i++) {
                for (Transacción transacción : transxTipoPariente[i]) {
                    String llave = transacción.ficha.getClass().getSimpleName();
                    double[] valores = resumen.get(llave);

                    if (valores == null) {
                        valores = new double[4];
                        resumen.put(llave, valores);
                    }

                    valores[i] += transacción.monto;

                    totales[i] += transacción.monto;
                }
            }

            for (double[] valores : resumen.values())
                valores[3] = valores[0] + valores[1] + valores[2];

            totales[3] = totales[0] + totales[1] + totales[2];
            resumen.put("TOTAL", totales);
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

    private JTable tablaDetalle;
    private DetalleTransacciones detalleTransacciones;
    class DetalleTransacciones extends AbstractTableModel {
        // -----------------------------------------------
        // Constantes
        // -----------------------------------------------
        private final String[] COLUMNAS = {"Fecha",
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
                case 0: return transacción.fecha;
                case 1: return transacción.monto;
                case 2: return transacción.concepto;
                case 3: return transacción.tercero.nombre;
                default: return null;
            }
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiTransacciones() {
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