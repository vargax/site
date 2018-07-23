package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Arrendamiento;
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
        resumenConsolidado.setTransxTipoPariente(transacciones);
        //detalleConsolidado.setTransxTipoPariente(transacciones);
    }

    // -----------------------------------------------
    // Subclases
    // -----------------------------------------------
    class ResumenConsolidado extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {"",
                "Real",
                "Presupuestado",
                "Diferencia",
                "% Cumplimiento"};

        private final String[] FILAS = {"Ingresos",
                "Gastos",
                "Utilidad Operacional",
                "Ajuste Valor Razonable",
                "Inversión"};

        private double[][] totales;

        JTable tabla;

        ResumenConsolidado() {
            super();
            super.columnas = COLUMNAS;

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);

            totales = new double[COLUMNAS.length][FILAS.length];
        }

        void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {
            double ingresosReales = 0; double ingresosPresupuestados = 0;
            double gastosReales = 0;   double gastosPresupuestados = 0;

            for (int i = 0; i < transxTipoPariente.length; i++) {
                for (Transacción transacción : transxTipoPariente[i])

                    if (transacción.ficha instanceof Arrendamiento)
                        if (transacción.ficha.presupuesto)
                            ingresosPresupuestados += transacción.monto;
                        else
                            ingresosReales += transacción.monto;
                    else if (transacción.ficha.presupuesto)
                        gastosPresupuestados += transacción.monto;
                    else
                        gastosReales += transacción.monto;
            }
            totales[0][0] = ingresosReales;
            totales[1][0] = ingresosPresupuestados;
            totales[2][0] = ingresosReales - ingresosPresupuestados;
            totales[3][0] = totales[2][0] / totales[1][0];

            totales[0][1] = gastosReales;
            totales[1][1] = gastosPresupuestados;
            totales[2][1] = gastosReales - gastosPresupuestados;
            totales[3][1] = totales[2][1] / totales[1][1];

            totales[0][2] = ingresosReales + gastosReales;
            totales[1][2] = ingresosPresupuestados + gastosPresupuestados;
            totales[2][2] = totales[2][0] + totales[2][1];
            totales[3][2] = totales[2][2] / totales[1][2];

            tabla.updateUI();

        }

        public int getRowCount() {
            return FILAS.length;
        }

        public Object getValueAt(int row, int col) {
            if (col == 0)
                return FILAS[row];
            col -= 1;

            return totales[col][row];
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
