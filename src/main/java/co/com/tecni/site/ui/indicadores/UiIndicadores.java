package co.com.tecni.site.ui.indicadores;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.ui.UiSite;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class UiIndicadores {
    static final String REAL = "REAL";
    static final String PRESUPUESTADO = "PRESUPUESTADO";

    static final DoubleRender DR = new DoubleRender();
    static final StringRender SR = new StringRender();

    public final JPanel componente;
    public final JTabbedPane jTabbedPane;

    static ArrayList<Transacción>[] transacciones = generarArreglos();

    Acciones acciones;

    Consolidados consolidados;
    Detalle detalle;
    Cartera cartera;

    private JFormattedTextField fechaInicial;
    private JFormattedTextField fechaFinal;
    private JFormattedTextField valorInmueble;

    public UiIndicadores() throws Exception {
        acciones = new Acciones();

        detalle = new Detalle();
        consolidados = new Consolidados();
        cartera = new Cartera();

        componente = new JPanel(new BorderLayout());
        componente.add(encabezado(), BorderLayout.NORTH);

        jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab(Consolidados.NOMBRE, consolidados.componente);
        jTabbedPane.addTab(Detalle.NOMBRE, detalle.componente);

        componente.add(jTabbedPane, BorderLayout.CENTER);
    }

    private JPanel encabezado() throws Exception {

        MaskFormatter formatoFecha = new MaskFormatter("??? ####");
        formatoFecha.setAllowsInvalid(false);
        fechaInicial = new JFormattedTextField(formatoFecha);
        fechaFinal = new JFormattedTextField(formatoFecha);

        valorInmueble = new JFormattedTextField(NumberFormat.getCurrencyInstance());

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new GridLayout(0, 5));

        encabezado.add(new JLabel("Fecha inicial: ", SwingConstants.RIGHT));
        encabezado.add(fechaInicial);
        encabezado.add(new JLabel("Fecha final: ", SwingConstants.RIGHT));
        encabezado.add(fechaFinal);
        encabezado.add(acciones.generarBotón());

        encabezado.add(new JLabel("Valor Inmueble: ", SwingConstants.RIGHT));
        encabezado.add(valorInmueble);

        return encabezado;
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
            UiIndicadores.transacciones = transacciones;
        else UiIndicadores.transacciones = generarArreglos();

        jTabbedPane.removeAll();

        if (UiSite.árbolActual instanceof ÁrbolCartera) {
            cartera.mostrarTransacciones();
            jTabbedPane.addTab(Cartera.NOMBRE, cartera.componente);

        } else {
            consolidados.mostrarTransacciones();
            jTabbedPane.addTab(Consolidados.NOMBRE, consolidados.componente);
        }
        detalle.mostrarTransacciones();
        jTabbedPane.addTab(Detalle.NOMBRE, detalle.componente);
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

                    System.out.println("Buscando transacciones entre "+Site.DTF.format(fi)+" y "+Site.DTF.format(ff));
                } catch (DateTimeParseException e){
                    System.err.println(e.getParsedString()+" no es una fecha válida");
                    e.printStackTrace();
                }
            }
        }
    }

}
