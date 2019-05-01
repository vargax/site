package co.com.tecni.site.ui.indicadores;

import co.com.tecni.site.lógica.transacciones.Transacción;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class Cartera {
    final static String NOMBRE = "Cartera";

    JSplitPane componente;

    private XDias xDias;
    private EstadoCuenta estadoCuenta;

    Cartera() {

        xDias = new XDias();
        estadoCuenta = new EstadoCuenta();

        componente = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        componente.setLeftComponent(new JScrollPane(xDias.tabla));
        componente.setRightComponent(new JScrollPane(estadoCuenta.tabla));
        componente.setResizeWeight(0.5d);

    }

    void mostrarTransacciones() {
        xDias.setTransxTipoPariente(UiIndicadores.transacciones);
        estadoCuenta.setTransxTipoPariente(UiIndicadores.transacciones);
    }

    // -----------------------------------------------
    // Subclases
    // -----------------------------------------------
    class XDias extends UiIndicadores.ModeloTabla {
        private final String[] COLUMNAS = {
                "Cliente", ">90", ">60", ">30", "<30", "Total", "Días atrazo"
        };

        JTable tabla;
        private LinkedHashMap<String, double[]> carteraxclienteFacturación;

        XDias() {
            super();
            super.columnas = COLUMNAS;

            carteraxclienteFacturación = new LinkedHashMap<>();

            tabla = new JTable(this);
            tabla.setDefaultRenderer(Double.class, UiIndicadores.DR);
        }

        void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {
            carteraxclienteFacturación.clear();

            for (int i = 0; i < transxTipoPariente.length; i++) {
                for (Transacción transacción : transxTipoPariente[i]) {
                    double[] mock = new double[6];
                    carteraxclienteFacturación.put(transacción.tercero.nombre, mock);
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

    class EstadoCuenta extends UiIndicadores.ModeloTabla {
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
            tabla.setDefaultRenderer(Double.class, UiIndicadores.DR);
        }

        void setTransxTipoPariente(ArrayList<Transacción>[] transxTipoPariente) {

        }

        public int getRowCount() {
            return filas.size();
        }

        public Object getValueAt(int row, int col) {
            return 0;
        }
    }


}
