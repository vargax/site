package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.fichas.Arrendamiento;
import co.com.tecni.site.lógica.transacciones.Transacción;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class TablasConsolidados {
    final static String NOMBRE = "Consolidados";

    JSplitPane componente;

    private TablaTransacciones tablaTransacciones;
    private TablaFichas tablaFichas;

    private ArrayList<Transacción> transaccionesIngreso = new ArrayList<>();
    private ArrayList<Transacción> transaccionesGasto = new ArrayList<>();
    private ArrayList<Transacción> transacciones = new ArrayList<>();

    TablasConsolidados() {

        tablaTransacciones = new TablaTransacciones();
        tablaFichas = new TablaFichas();

        componente = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        componente.setLeftComponent(new JScrollPane(tablaTransacciones.tabla));
        componente.setRightComponent(new JScrollPane(tablaFichas.tabla));
        componente.setResizeWeight(0.5d);

    }

    void mostrarTransacciones() {
        tablaTransacciones.setTransxTipoPariente(UiTablas.transacciones);
        actualizarTablaFichas(tablaTransacciones.tabla.getSelectedRow());
    }

    void actualizarTablaFichas(int fila) {
        switch (fila) {
            case 0:
                tablaFichas.setTransacciones(transaccionesIngreso);
                break;
            case 1:
                tablaFichas.setTransacciones(transaccionesGasto);
                break;
            case 2:
                tablaFichas.setTransacciones(transacciones);
                break;
            default:
                tablaFichas.setTransacciones(new ArrayList<>());
                break;
        }
    }

    // -----------------------------------------------
    // Subclases
    // -----------------------------------------------
    class TablaTransacciones extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {"",
                "Real",
                "Presupuestado",
                "Diferencia",
                "% Cumplimiento",
                "Anticipos"};

        private final String[] FILAS = {"Ingresos",
                "Gastos",
                "Utilidad Operacional",
                "Inversión",
                "Ajustes contables",
                "Ajustes fiscales"};

        private double[][] totales;

        JTable tabla;

        TablaTransacciones() {
            super();
            super.columnas = COLUMNAS;

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
            tabla.getSelectionModel().addListSelectionListener(
                    new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent listSelectionEvent) {
                            actualizarTablaFichas(tabla.getSelectedRow());
                        }
                    }
            );

            totales = new double[COLUMNAS.length][FILAS.length];
        }

        void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {
            double ingresosReales = 0; double ingresosPresupuestados = 0;
            double gastosReales = 0;   double gastosPresupuestados = 0;

            transaccionesIngreso = new ArrayList<>();
            transaccionesGasto = new ArrayList<>();
            transacciones = new ArrayList<>();

            for (int i = 0; i < transxTipoPariente.length; i++) {
                for (Transacción transacción : transxTipoPariente[i]) {

                    if (transacción.ficha instanceof Arrendamiento) {
                        if (transacción.ficha.presupuestado)
                            ingresosPresupuestados += transacción.monto;
                        else ingresosReales += transacción.monto;

                        transaccionesIngreso.add(transacción);
                    } else {
                        if (transacción.ficha.presupuestado)
                            gastosPresupuestados += transacción.monto;
                        else gastosReales += transacción.monto;

                        transaccionesGasto.add(transacción);
                    }

                    transacciones.add(transacción);
                }
            }

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

    class TablaFichas extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {"Ficha",
                "Real",
                "Presupuestado",
                "Diferencia",
                "% Cumplimiento",
                "Anticipos"};

        JTable tabla;
        private LinkedHashMap<String, double[]> resumen;

        TablaFichas() {
            super();
            super.columnas = COLUMNAS;

            resumen = new LinkedHashMap<>();

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        void setTransacciones(ArrayList<Transacción> transacciones) {
            resumen.clear();

            for (Transacción transacción : transacciones) {
                String llave = transacción.ficha.darTipo();
                double[] valores = resumen.get(llave);

                if (valores == null) {
                    valores = new double[5];
                    resumen.put(llave, valores);
                }

                if (transacción.ficha.presupuestado)
                    valores[1] += transacción.monto;
                else
                    valores[0] += transacción.monto;

            }

            double[] totales = new double[5];

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
