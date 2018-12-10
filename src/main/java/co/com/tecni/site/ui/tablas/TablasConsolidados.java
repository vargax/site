package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Arrendamiento;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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

    void mostrarTransacciones() {
        resumenConsolidado.setTransxTipoPariente(UiTablas.transacciones);
        detalleConsolidado.setTransxTipoPariente(UiTablas.transacciones);
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

            for (int i = 0; i < transxTipoPariente.length; i++)
                for (Transacción transacción : transxTipoPariente[i])

                    if (transacción.ficha instanceof Arrendamiento)
                        if (transacción.ficha.presupuestado)
                            ingresosPresupuestados += transacción.monto;
                        else
                            ingresosReales += transacción.monto;
                    else if (transacción.ficha.presupuestado)
                        gastosPresupuestados += transacción.monto;
                    else
                        gastosReales += transacción.monto;

            totales[0][0] = ingresosReales;
            totales[1][0] = ingresosPresupuestados;
            totales[2][0] = ingresosReales - ingresosPresupuestados;
            totales[3][0] = totales[0][0] / totales[1][0];

            totales[0][1] = gastosReales;
            totales[1][1] = gastosPresupuestados;
            totales[2][1] = gastosReales - gastosPresupuestados;
            totales[3][1] = totales[0][1] / totales[1][1];

            totales[0][2] = ingresosReales + gastosReales;
            totales[1][2] = ingresosPresupuestados + gastosPresupuestados;
            totales[2][2] = totales[2][0] + totales[2][1];
            totales[3][2] = totales[0][2] / totales[1][2];

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
        private final String[] COLUMNAS = {"Ficha",
                "Real",
                "Presupuestado",
                "Diferencia",
                "% Cumplimiento"};

        JTable tabla;
        private LinkedHashMap<String, double[]> resumen;

        DetalleConsolidado() {
            super();
            super.columnas = COLUMNAS;

            resumen = new LinkedHashMap<>();

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {
            resumen.clear();

            for (int i = 0; i < transxTipoPariente.length; i++) {
                for (Transacción transacción : transxTipoPariente[i]) {
                    String llave = transacción.ficha.getClass().getSimpleName();
                    double[] valores = resumen.get(llave);

                    if (valores == null) {
                        valores = new double[4];
                        resumen.put(llave, valores);
                    }

                    if (transacción.ficha.presupuestado)
                        valores[1] += transacción.monto;
                    else
                        valores[0] += transacción.monto;

                }
            }

            double[] totales = new double[4];

            for (double[] valores : resumen.values()) {
                valores[2] = valores[0] - valores[1]; // Diferencia
                valores[3] = valores[0] / valores[1]; // Cumplimiento

                totales[0] += valores[0];
                totales[1] += valores[1];
            }

            totales[2] = totales[0] - totales[1]; // Diferencia
            totales[3] = totales[0] / totales[1]; // Cumplimiento
            resumen.put("TOTAL", totales);

            tabla.updateUI();
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

}
