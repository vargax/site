package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.ArrayList;

public class UiTablas {
    static final String REAL = "REAL";
    static final String PRESUPUESTADO = "PRESUPUESTADO";

    static final DoubleRender DR = new DoubleRender();

    public final JPanel componente;

    static ArrayList<Transacción>[] transacciones = generarArreglos();

    TablasConsolidados tConsolidados;
    TablasTransacciones tTransacciones;

    private JFormattedTextField fechaInicial;
    private JFormattedTextField fechaFinal;

    public UiTablas() throws Exception {

        tConsolidados = new TablasConsolidados();
        tTransacciones = new TablasTransacciones();

        componente = new JPanel(new BorderLayout());
        componente.add(panelFechas(), BorderLayout.NORTH);

        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab(TablasConsolidados.NOMBRE, new JScrollPane(tConsolidados.componente));
        jtp.addTab(TablasTransacciones.NOMBRE, tTransacciones.componente);
        componente.add(jtp, BorderLayout.CENTER);

    }

    private JPanel panelFechas() throws Exception {

        MaskFormatter formatoFecha = new MaskFormatter("??? ####");
        formatoFecha.setAllowsInvalid(false);

        fechaInicial = new JFormattedTextField(formatoFecha);
        fechaFinal = new JFormattedTextField(formatoFecha);

        JPanel panelFechas = new JPanel();
        panelFechas.setLayout(new GridLayout(1, 0));

        panelFechas.add(new JLabel("Fecha inicial: ", SwingConstants.RIGHT));
        panelFechas.add(fechaInicial);

        panelFechas.add(new JLabel("Fecha final: ", SwingConstants.RIGHT));
        panelFechas.add(fechaFinal);

        JButton buscar = new JButton("Buscar");
        panelFechas.add(new JLabel());
        panelFechas.add(buscar);

        return panelFechas;
    }

    private void minMaxFechas() {

    }

    /**
     * Muestras las transacciones asociadas al nodo seleccionado
     * @param transacciones arreglo con las transacciones dividas en:
     *                      transacciones[0] ancestros
     *                      transacciones[1] propias
     *                      transacciones[2] descendientes
     */
    public void mostrarTransacciones(ArrayList<Transacción>[] transacciones) {
        if (transacciones != null)
            UiTablas.transacciones = transacciones;
        else UiTablas.transacciones = generarArreglos();

        tConsolidados.mostrarTransacciones();
        tTransacciones.mostrarTransacciones();
    }

    static ArrayList<Transacción>[] generarArreglos() {

        ArrayList[] arreglos = new ArrayList[3];

        for (int i = 0; i < arreglos.length; i++)
            arreglos[i] = new ArrayList<Transacción>();

        return arreglos;
    }

    static JSplitPane genJSplitPane(boolean scrollPane, Component superior, Component inferior) {
        JSplitPane resultado = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        if (scrollPane) {
            superior = new JScrollPane(superior);
            inferior = new JScrollPane(inferior);
        }

        resultado.setLeftComponent(superior);
        resultado.setRightComponent(inferior);
        resultado.setResizeWeight(0.5d);

        return resultado;
    }

    static abstract class ModeloTabla extends AbstractTableModel {
        String[] columnas;

        public int getColumnCount() {
            return columnas.length;
        }

        public String getColumnName(int col) {
            return columnas[col];
        }

        public Class getColumnClass(int col) {
            return getValueAt(0,col).getClass();
        }
    }

}
