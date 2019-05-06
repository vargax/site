package co.com.tecni.sari.ui.indicadores;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.lógica.árboles.ÁrbolCartera;
import co.com.tecni.sari.ui.UiSari;

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
    Distribución distribución;
    Cartera cartera;

    private JFormattedTextField fechaInicial;
    private JFormattedTextField fechaFinal;
    private JLabel valor;

    public UiIndicadores() throws Exception {
        acciones = new Acciones();

        distribución = new Distribución();
        consolidados = new Consolidados();
        cartera = new Cartera();

        componente = new JPanel(new BorderLayout());
        componente.add(encabezado(), BorderLayout.NORTH);

        jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab(Consolidados.NOMBRE, consolidados.componente);
        jTabbedPane.addTab(Distribución.NOMBRE, distribución.componente);

        componente.add(jTabbedPane, BorderLayout.CENTER);
    }

    private JPanel encabezado() throws Exception {

        MaskFormatter formatoFecha = new MaskFormatter("??? ####");
        formatoFecha.setAllowsInvalid(false);
        fechaInicial = new JFormattedTextField(formatoFecha);
        fechaFinal = new JFormattedTextField(formatoFecha);

        valor = new JLabel();

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new GridLayout(0, 5));

        encabezado.add(new JLabel("Fecha inicial: ", SwingConstants.RIGHT));
        encabezado.add(fechaInicial);
        encabezado.add(new JLabel("Fecha final: ", SwingConstants.RIGHT));
        encabezado.add(fechaFinal);
        encabezado.add(acciones.generarBotón());

        encabezado.add(new JLabel("Valor: ", SwingConstants.RIGHT));
        encabezado.add(valor);

        return encabezado;
    }

    private void minMaxFechas() {

    }


    public void mostrarValor(double valor) {
        this.valor.setText(Sari.BIG_DECIMAL.format(valor));
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

        if (UiSari.árbolActual instanceof ÁrbolCartera) {
            cartera.mostrarTransacciones();
            jTabbedPane.addTab(Cartera.NOMBRE, cartera.componente);

        } else {
            consolidados.mostrarTransacciones();
            jTabbedPane.addTab(Consolidados.NOMBRE, consolidados.componente);
        }
        distribución.mostrarTransacciones();
        jTabbedPane.addTab(Distribución.NOMBRE, distribución.componente);
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
                    LocalDate fi = LocalDate.from((YearMonth.parse(fechaInicial.getText(), Sari.DTF)).atDay(1));
                    LocalDate ff = LocalDate.from((YearMonth.parse(fechaFinal.getText(), Sari.DTF)).atEndOfMonth());

                    System.out.println("Buscando transacciones entre "+ Sari.DTF.format(fi)+" y "+ Sari.DTF.format(ff));
                } catch (DateTimeParseException e){
                    System.err.println(e.getParsedString()+" no es una fecha válida");
                    e.printStackTrace();
                }
            }
        }
    }

}
