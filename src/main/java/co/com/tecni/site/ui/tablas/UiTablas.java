package co.com.tecni.site.ui.tablas;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Movimiento;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.ui.UiSite;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class UiTablas {
    static final String REAL = "REAL";
    static final String PRESUPUESTADO = "PRESUPUESTADO";

    static final DoubleRender DR = new DoubleRender();

    public final JPanel componente;
    public final JTabbedPane jTabbedPane;

    static ArrayList<Movimiento>[] movimientos = generarArreglos();

    Acciones acciones;

    TablasConsolidados tConsolidados;
    TablasMovimientos tMovimientos;
    TablasCartera tCartera;

    private JFormattedTextField fechaInicial;
    private JFormattedTextField fechaFinal;

    public UiTablas() throws Exception {
        acciones = new Acciones();

        tMovimientos = new TablasMovimientos();
        tConsolidados = new TablasConsolidados();
        tCartera = new TablasCartera();

        componente = new JPanel(new BorderLayout());
        componente.add(panelFechas(), BorderLayout.NORTH);

        jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab(TablasConsolidados.NOMBRE, tConsolidados.componente);
        jTabbedPane.addTab(TablasMovimientos.NOMBRE, tMovimientos.componente);

        componente.add(jTabbedPane, BorderLayout.CENTER);
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

        panelFechas.add(new JLabel());
        panelFechas.add(acciones.generarBotón());

        return panelFechas;
    }

    private void minMaxFechas() {

    }

    /**
     * Muestras las movimientos asociadas al nodo seleccionado
     * @param movimientos arreglo con las movimientos dividas en:
     *                      movimientos[0] ancestros
     *                      movimientos[1] propias
     *                      movimientos[2] descendientes
     */
    public void mostrarMovimientos(ArrayList<Movimiento>[] movimientos) {
        if (movimientos != null)
            UiTablas.movimientos = movimientos;
        else UiTablas.movimientos = generarArreglos();

        jTabbedPane.removeAll();

        if (UiSite.árbolActual instanceof ÁrbolCartera) {
            tCartera.mostrarMovimientos();
            jTabbedPane.addTab(TablasCartera.NOMBRE, tCartera.componente);

        } else {
            tConsolidados.mostrarMovimientos();
            jTabbedPane.addTab(TablasConsolidados.NOMBRE, tConsolidados.componente);
        }
        tMovimientos.mostrarMovimientos();
        jTabbedPane.addTab(TablasMovimientos.NOMBRE, tMovimientos.componente);
    }

    static ArrayList<Movimiento>[] generarArreglos() {

        ArrayList[] arreglos = new ArrayList[3];

        for (int i = 0; i < arreglos.length; i++)
            arreglos[i] = new ArrayList<Movimiento>();

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

    class Acciones implements ActionListener {

        final static String BUSCAR = "Buscar";

        JButton generarBotón() {
            JButton buscar = new JButton(BUSCAR);
            buscar.setActionCommand(BUSCAR);
            buscar.addActionListener(this);
            return buscar;
        }

        public void actionPerformed(ActionEvent actionEvent) {

            if (BUSCAR.equals(actionEvent.getActionCommand())) {
                try {
                    LocalDate fi = LocalDate.from((YearMonth.parse(fechaInicial.getText(), Site.DTF)).atDay(1));
                    LocalDate ff = LocalDate.from((YearMonth.parse(fechaFinal.getText(), Site.DTF)).atEndOfMonth());

                    System.out.println("Buscando movimientos entre "+Site.DTF.format(fi)+" y "+Site.DTF.format(ff));
                } catch (DateTimeParseException e){
                    System.err.println(e.getParsedString()+" no es una fecha válida");
                    e.printStackTrace();
                }
            }
        }
    }

}
