package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;

import javax.swing.*;
import java.util.ArrayList;

class TablasConsolidados {
    final static String NOMBRE = "Consolidados";

    JSplitPane componente;
    private ResumenConsolidado resumenConsolidado;
    private DetalleConsolidado detalleConsolidado;


    TablasConsolidados() {
        resumenConsolidado = new ResumenConsolidado();
        detalleConsolidado = new DetalleConsolidado();

        componente = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        componente.setLeftComponent(new JScrollPane(resumenConsolidado.tabla));
        componente.setRightComponent(new JScrollPane(detalleConsolidado.tabla));
        componente.setResizeWeight(0.5d);

    }

    /**
     * Muestras las transacciones asociadas al nodo seleccionado
     * @param transacciones arreglo con las transacciones dividas en:
     *                      transacciones[0] ancestros
     *                      transacciones[1] propias
     *                      transacciones[2] descendientes
     */
    void mostrarTransacciones(ArrayList<Transacción>[] transacciones) {
        //resumenConsolidado.setTransxTipoPariente(transacciones);
        //detalleConsolidado.setTransxTipoPariente(transacciones);
    }

    // -----------------------------------------------
    // Subclases
    // -----------------------------------------------
    class ResumenConsolidado extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {"",
                "Real",
                "Proyectado",
                "Diferencia",
                "% Cumplimiento"};

        private final String[] FILAS = {"Ingresos",
                "Gastos",
                "Utilidad Operacional",
                "Ajuste Valor Razonable",
                "Inversión"};

        JTable tabla;

        ResumenConsolidado() {
            super();
            super.columnas = COLUMNAS;

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        public int getRowCount() {
            return FILAS.length;
        }

        public Object getValueAt(int row, int col) {
            if (col == 0)
                return FILAS[row];
            return 0;
        }
    }

    class DetalleConsolidado extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {"",
                "Real",
                "Proyectado",
                "Diferencia",
                "% Cumplimiento"};

        JTable tabla;

        DetalleConsolidado() {
            super();
            super.columnas = COLUMNAS;

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        public int getRowCount() {
            return 0;
        }

        public Object getValueAt(int row, int col) {
            return null;
        }
    }

}
