package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Movimiento;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class TablasCartera {
    final static String NOMBRE = "Cartera";

    JSplitPane componente;

    private Cartera cartera;
    private EstadoCuenta estadoCuenta;

    TablasCartera() {

        cartera = new Cartera();
        estadoCuenta = new EstadoCuenta();

        componente = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        componente.setLeftComponent(new JScrollPane(cartera.tabla));
        componente.setRightComponent(new JScrollPane(estadoCuenta.tabla));
        componente.setResizeWeight(0.5d);

    }

    void mostrarMovimientos() {
        cartera.setMovsxTipoPariente(UiTablas.movimientos);
        estadoCuenta.setMovsxTipoPariente(UiTablas.movimientos);
    }

    // -----------------------------------------------
    // Subclases
    // -----------------------------------------------
    class Cartera extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {
                "Cliente", ">90", "60", "30", "<30", "Total", "Días atrazo"
        };

        JTable tabla;
        private LinkedHashMap<String, double[]> carteraxclienteFacturación;

        Cartera() {
            super();
            super.columnas = COLUMNAS;

            carteraxclienteFacturación = new LinkedHashMap<>();

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        void setMovsxTipoPariente(ArrayList<Movimiento>[] movsxTipoPariente) {
            carteraxclienteFacturación.clear();

            for (int i = 0; i < movsxTipoPariente.length; i++) {
                for (Movimiento movimiento : movsxTipoPariente[i]) {
                    double[] mock = new double[6];
                    carteraxclienteFacturación.put(movimiento.tercero.nombre, mock);
                }
            }

            tabla.updateUI();
        }

        public int getRowCount() {
            return carteraxclienteFacturación.size();
        }

        public Object getValueAt(int row, int col) {
            String fila = (String) carteraxclienteFacturación.keySet().toArray()[row];
            if (col == 0)
                return fila;
            return carteraxclienteFacturación.get(fila)[col-1];
        }
    }

    class EstadoCuenta extends UiTablas.ModeloTabla {
        private final String[] COLUMNAS = {
                "Fecha", "Documento", "Cliente", "Monto", "Total"
        };

        JTable tabla;
        private ArrayList<Fila> filas;
        class Fila {
            LocalDate fecha;
            String documento;
            String nombreCliente;
            double monto;
            double total;
        }

        EstadoCuenta() {
            super();
            super.columnas = COLUMNAS;

            filas = new ArrayList<>();

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiTablas.DR);
        }

        void setMovsxTipoPariente(ArrayList<Movimiento>[] movsxTipoPariente) {

        }

        public int getRowCount() {
            return filas.size();
        }

        public Object getValueAt(int row, int col) {
            return 0;
        }
    }


}
