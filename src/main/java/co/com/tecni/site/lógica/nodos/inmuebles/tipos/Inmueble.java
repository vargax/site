package co.com.tecni.site.lógica.nodos.inmuebles.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Arrendamiento;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Ficha;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Presupuestal;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;
import org.json.simple.JSONObject;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Inmueble implements Nodo {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.MAP;
    private final static Color UI_ÍCONO_COLOR_ARRENDADO = new Color(0, 255, 128);

    public final static String A_PRIV_CONSTRUIDOS = "PC";
    public final static String A_PRIV_LIBRES = "PL";
    public final static String A_PRIV_TOTAL = "P";
    public final static String A_COM_CONSTRUIDOS = "CC";
    public final static String A_COM_LIBRES = "CL";
    public final static String A_COM_TOTAL = "C";
    public final static String A_TOTAL = "T";

    private final static String[] JK = {"Nombre", "Área", "Características", "Contrato"};
    private final static String[] JK_ÁREA = {"Privada", "Común", "Total", "Detalle"};
    private final static String[] JK_ÁREA_DETALLE = {"Privados Construidos", "Privados Libres", "Comunes Construidos", "Comunes Libres"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String nombre;
    UiÁrbol.Ícono ícono;

    private Site site;

    private HashMap<String , Double> m2;
    private ArrayList<Ficha> fichas;

    private Inmueble padre;
    private ArrayList<Inmueble> hijos;

    String sigla;
    private JSONObject infoNodo;
    private JSONObject características;
    private HashMap<Integer, Presupuestal> presupuestos;
    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public Inmueble() {
        hijos = new ArrayList<>();
        fichas = new ArrayList<>();
        presupuestos = new HashMap<>();

        ícono = new UiÁrbol.Ícono(UI_ÍCONO);

        site = Site.getInstance();
    }

    public static Inmueble englobar(String tipo, String nombre, JSONObject características, ArrayList<Inmueble> inmuebles) throws Exception {
        Inmueble englobe = (Inmueble) Class.forName(tipo).newInstance();
        englobe.nombre = nombre;
        englobe.características = características;

        englobe.hijos = inmuebles;

        englobe.m2 = new HashMap<>();
        englobe.m2.put(A_PRIV_CONSTRUIDOS,0.0);
        englobe.m2.put(A_PRIV_LIBRES,0.0);
        englobe.m2.put(A_COM_CONSTRUIDOS,0.0);
        englobe.m2.put(A_COM_LIBRES,0.0);

        for (Inmueble inmueble : inmuebles) {

            englobe.m2.put(A_PRIV_CONSTRUIDOS,englobe.m2.get(A_PRIV_CONSTRUIDOS)+inmueble.m2.get(A_PRIV_CONSTRUIDOS));
            englobe.m2.put(A_PRIV_LIBRES,englobe.m2.get(A_PRIV_LIBRES)+inmueble.m2.get(A_PRIV_LIBRES));
            englobe.m2.put(A_COM_CONSTRUIDOS,englobe.m2.get(A_COM_CONSTRUIDOS)+inmueble.m2.get(A_COM_CONSTRUIDOS));
            englobe.m2.put(A_COM_LIBRES,englobe.m2.get(A_COM_LIBRES)+inmueble.m2.get(A_COM_LIBRES));

            inmueble.padre = englobe;
        }

        englobe.calcularÁreas();
        return englobe;
    }

    public static Inmueble hoja(String tipo, String nombre, JSONObject características, HashMap<String, Double> m2) throws Exception {
        Inmueble hoja = (Inmueble) Class.forName(tipo).newInstance();

        hoja.nombre = nombre;
        hoja.m2 = m2;
        hoja.características = características;

        hoja.calcularÁreas();
        return hoja;
    }

    // -----------------------------------------------
    // Métodos privados
    // -----------------------------------------------
    private void calcularÁreas() {
        double áreaPrivada = m2.get(A_PRIV_CONSTRUIDOS) + m2.get(A_PRIV_LIBRES);
        double áreaComún = m2.get(A_COM_CONSTRUIDOS) + m2.get(A_COM_LIBRES);

        m2.put(A_PRIV_TOTAL, áreaPrivada);
        m2.put(A_COM_TOTAL, áreaComún);
        m2.put(A_TOTAL, áreaPrivada + áreaComún);
    }

    /**
     * Devuelve las transacciones del inmueble ponderadas por el factor especificado.
     * @param factorPonderación Factor por el cual se ponderarán las transacciones:
     *                          Si 0 < factor < 1 sólo se incluyen las transacciones propias y de los ancestros
     *                          Si     factor = 1 sólo se incluyen las transacciones propias y de los descendientes
     * @return Arreglo con:
     *  [0] Transacciones Ancestros
     *  [1] Transacciones Propias
     *  [2] Transacciones Descendientes
     */
    private ArrayList<Transacción>[] recursiónTransacciones(double factorPonderación, Árbol árbol) {
        ArrayList[] resultado = new ArrayList[3];

        ArrayList<Transacción> descendientes = new ArrayList<>();
        if (factorPonderación == 1) {
            for (Inmueble hijo : hijos) {
                ArrayList<Transacción>[] transaccionesHijo = hijo.recursiónTransacciones(factorPonderación, árbol);
                descendientes.addAll(transaccionesHijo[1]);
                descendientes.addAll(transaccionesHijo[2]);
            }
        }
        resultado[2] = descendientes;

        ArrayList<Transacción> propias = new ArrayList<>();
        for (Ficha ficha : fichas) {

            if (ficha instanceof Arrendamiento && árbol instanceof ÁrbolContratos) continue;

            ArrayList<Transacción>[] transaccionesFichas = ficha.recursiónTransacciones(factorPonderación);
            propias.addAll(transaccionesFichas[1]);
            propias.addAll(transaccionesFichas[2]);
        }
        resultado[1] = propias;

        ArrayList<Transacción> ancestros = new ArrayList<>();
        if (padre != null) {
            factorPonderación = factorPonderación*(this.m2.get(site.getModoPonderación())/padre.m2.get(site.getModoPonderación()));
            ArrayList<Transacción>[] transaccionesAncestro = padre.recursiónTransacciones(factorPonderación, árbol);

            ancestros.addAll(transaccionesAncestro[0]);
            ancestros.addAll(transaccionesAncestro[1]);
        }
        resultado[0] = ancestros;

        return resultado;
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public String genId() {
        return padre == null ? sigla + " " + nombre : padre.genId() + " " + sigla + " " + nombre;
    }

    public void registrarFicha(Ficha ficha) {
        fichas.add(ficha);

        if (ficha instanceof Arrendamiento)
            recursiónColorÍcono(UI_ÍCONO_COLOR_ARRENDADO);
    }

    void recursiónColorÍcono(Color color) {
        ícono.setColor(color);
        for (Inmueble inmueble : hijos)
            inmueble.recursiónColorÍcono(color);
    }

    public Presupuestal getPresupuesto(int año) {
        Presupuestal presupuesto = presupuestos.get(año);

        if (presupuesto == null) {
            presupuesto = new Presupuestal(año);
            presupuestos.put(año, presupuesto);
            fichas.add(presupuesto);
        }

        return presupuesto;
    }


    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return genId();
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(this.hijos);
        hijos.addAll(this.fichas);
        return hijos;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return ícono;
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public ArrayList<Transacción>[] transaccionesNodo(Árbol árbol) {
        return recursiónTransacciones(1, árbol);
    }

    public String infoNodo(Árbol árbol) {
        if (infoNodo == null) {
            infoNodo = new JSONObject();
            infoNodo.put(JK[0], genId());

            DecimalFormat df = Site.smallDecimal;

            JSONObject áreaDetalle = new JSONObject();
            áreaDetalle.put(JK_ÁREA_DETALLE[0], df.format(m2.get(A_PRIV_CONSTRUIDOS)));
            áreaDetalle.put(JK_ÁREA_DETALLE[1], df.format(m2.get(A_PRIV_LIBRES)));
            áreaDetalle.put(JK_ÁREA_DETALLE[2], df.format(m2.get(A_COM_CONSTRUIDOS)));
            áreaDetalle.put(JK_ÁREA_DETALLE[3], df.format(m2.get(A_COM_LIBRES)));

            JSONObject área = new JSONObject();
            if (m2.get(A_PRIV_TOTAL) != 0) área.put(JK_ÁREA[0], df.format(m2.get(A_PRIV_TOTAL)));
            if (m2.get(A_COM_TOTAL) != 0) área.put(JK_ÁREA[1], df.format(m2.get(A_COM_TOTAL)));
            área.put(JK_ÁREA[2], df.format(m2.get(A_TOTAL)));
            área.put(JK_ÁREA[3], áreaDetalle);

            infoNodo.put(JK[1], área);

            if (!características.isEmpty())
                infoNodo.put(JK[2], características);
        }

        return infoNodo.toJSONString();
    }
}
