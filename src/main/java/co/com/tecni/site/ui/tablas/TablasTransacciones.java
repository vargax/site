package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class TablasTransacciones {
    final static String NOMBRE = "Transacciones";

    JSplitPane componente;

    private Herencia herenciaReal;
    private Transacciones transaccionesReales;

    private Herencia herenciaPresupuestada;
    private Transacciones transaccionesPresupuestadas;

    TablasTransacciones() {

        herenciaReal = new Herencia(UiTablas.REAL);
        herenciaPresupuestada = new Herencia(UiTablas.PRESUPUESTADO);

        JSplitPane jspDistribución = UiTablas.genJSplitPane(true, herenciaReal.tabla, herenciaPresupuestada.tabla);

        transaccionesReales = new Transacciones(UiTablas.REAL);
        transaccionesPresupuestadas = new Transacciones(UiTablas.PRESUPUESTADO);

        JSplitPane jspTransacciones = UiTablas.genJSplitPane(true, transaccionesReales.tabla, transaccionesPresupuestadas.tabla);

        componente = UiTablas.genJSplitPane(false, jspDistribución, jspTransacciones);
    }

    void mostrarTransacciones() {

        ArrayList<Transacción>[] transacciones = UiTablas.transacciones;

        ArrayList<Transacción>[] real = UiTablas.generarArreglos();
        ArrayList<Transacción>[] presupuestado = UiTablas.generarArreglos();

        for (int i = 0; i < transacciones.length; i++)
            for (Transacción t : transacciones[i])
                if (t.ficha.presupuestado)
                    presupuestado[i].add(t);
                else
                    real[i].add(t);

        herenciaReal.setTransxTipoPariente(real);
        transaccionesReales.setTransxTipoPariente(real);

        herenciaPresupuestada.setTransxTipoPariente(presupuestado);
        transaccionesPresupuestadas.setTransxTipoPariente(presupuestado);
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

    class Transacciones extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {"Fecha",
                "Monto",
                "Concepto",
                "Tercero"};

        JTable tabla;
        private ArrayList<Transacción> transacciones;

        Transacciones(String título) {
            super();
            super.columnas = COLUMNAS;
            super.columnas[0] = título;

            transacciones = new ArrayList<>();

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {
            transacciones.clear();

            for (int i = 0; i < transxTipoPariente.length; i++)
                transacciones.addAll(transxTipoPariente[i]);

            tabla.updateUI();
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

}