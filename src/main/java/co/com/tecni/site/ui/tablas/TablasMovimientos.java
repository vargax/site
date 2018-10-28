package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Movimiento;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class TablasMovimientos {
    final static String NOMBRE = "Movimientos";

    JSplitPane componente;

    private Herencia herenciaReal;
    private Movimientos movimientosReales;

    private Herencia herenciaPresupuestada;
    private Movimientos movimientosPresupuestados;

    TablasMovimientos() {

        herenciaReal = new Herencia(UiTablas.REAL);
        herenciaPresupuestada = new Herencia(UiTablas.PRESUPUESTADO);

        JSplitPane jspDistribución = UiTablas.genJSplitPane(true, herenciaReal.tabla, herenciaPresupuestada.tabla);

        movimientosReales = new Movimientos(UiTablas.REAL);
        movimientosPresupuestados = new Movimientos(UiTablas.PRESUPUESTADO);

        JSplitPane jspTransacciones = UiTablas.genJSplitPane(true, movimientosReales.tabla, movimientosPresupuestados.tabla);

        componente = UiTablas.genJSplitPane(false, jspDistribución, jspTransacciones);
    }

    void mostrarMovimientos() {

        ArrayList<Movimiento>[] transacciones = UiTablas.movimientos;

        ArrayList<Movimiento>[] real = UiTablas.generarArreglos();
        ArrayList<Movimiento>[] presupuestado = UiTablas.generarArreglos();

        for (int i = 0; i < transacciones.length; i++)
            for (Movimiento t : transacciones[i])
                if (t.ficha.presupuestado)
                    presupuestado[i].add(t);
                else
                    real[i].add(t);

        herenciaReal.setMovxTipoPariente(real);
        movimientosReales.setMovsxTipoPariente(real);

        herenciaPresupuestada.setMovxTipoPariente(presupuestado);
        movimientosPresupuestados.setMovsxTipoPariente(presupuestado);
    }

    // -----------------------------------------------
    // Subclases
    // -----------------------------------------------
    class Herencia extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {"Ficha",
                "Ancestros",
                "Propias",
                "Descencientes",
                "TOTAL"
        };

        JTable tabla;
        private LinkedHashMap<String, double[]> resumen;

        Herencia(String título) {
            super();
            super.columnas = COLUMNAS;
            super.columnas[0] = título;

            resumen = new LinkedHashMap<>();

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        void setMovxTipoPariente(ArrayList<Movimiento>[] movxTipoPariente) {
            resumen.clear();

            double[] totales = new double[4];

            for (int i = 0; i < movxTipoPariente.length; i++) {
                for (Movimiento movimiento : movxTipoPariente[i]) {
                    String llave = movimiento.ficha.getClass().getSimpleName();
                    double[] valores = resumen.get(llave);

                    if (valores == null) {
                        valores = new double[4];
                        resumen.put(llave, valores);
                    }

                    valores[i] += movimiento.monto;

                    totales[i] += movimiento.monto;
                }
            }

            for (double[] valores : resumen.values())
                valores[3] = valores[0] + valores[1] + valores[2];

            totales[3] = totales[0] + totales[1] + totales[2];
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

    class Movimientos extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {"Fecha",
                "Monto",
                "Concepto",
                "Tercero"};

        JTable tabla;
        private ArrayList<Movimiento> movimientos;

        Movimientos(String título) {
            super();
            super.columnas = COLUMNAS;
            super.columnas[0] = título;

            movimientos = new ArrayList<>();

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        void setMovsxTipoPariente(ArrayList<Movimiento>[] movsxTipoPariente) {
            movimientos.clear();

            for (int i = 0; i < movsxTipoPariente.length; i++)
                movimientos.addAll(movsxTipoPariente[i]);

            tabla.updateUI();
        }

        public int getRowCount() {
            return movimientos.size();
        }

        public Object getValueAt(int row, int col) {
            Movimiento movimiento = movimientos.get(row);
            switch (col) {
                case 0: return movimiento.fecha;
                case 1: return movimiento.monto;
                case 2: return movimiento.concepto;
                case 3: return movimiento.tercero.nombre;
                default: return null;
            }
        }
    }

}