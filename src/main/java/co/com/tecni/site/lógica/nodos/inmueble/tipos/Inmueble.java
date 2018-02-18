package co.com.tecni.site.lógica.nodos.inmueble.tipos;

import co.com.tecni.site.lógica.contrato.Contrato;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.fichas.Ficha;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Inmueble extends Nodo {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String PRIV_CONSTRUIDOS = "PC"; //"Privados Construidos";
    public final static String PRIV_LIBRES = "PL"; //"Privados Libres";
    public final static String COM_CONSTRUIDOS = "CC"; //"Comunes Construidos";
    public final static String COM_LIBRES = "CL"; //"Comunes Libres";

    private final static String[] JSON_KEYS = {"Nombre", "M2", "Características"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private HashMap<String , Double> m2;
    private ArrayList<Ficha> fichas;

    private Inmueble padre;
    private ArrayList<Inmueble> hijos;

    protected String sigla;
    protected JSONObject características;

    private Contrato contrato;

    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public Inmueble() {
        this.fichas = new ArrayList<>();
    }

    public static Inmueble englobar(String tipo, String nombre, JSONObject características, ArrayList<Inmueble> inmuebles) throws Exception {
        Inmueble englobe = (Inmueble) Class.forName(tipo).newInstance();
        englobe.nombre = nombre;
        englobe.características = características;

        englobe.hijos = inmuebles;

        englobe.m2 = new HashMap<>();
        englobe.m2.put(PRIV_CONSTRUIDOS,0.0);
        englobe.m2.put(PRIV_LIBRES,0.0);
        englobe.m2.put(COM_CONSTRUIDOS,0.0);
        englobe.m2.put(COM_LIBRES,0.0);

        for (Inmueble inmueble : inmuebles) {

            englobe.m2.put(PRIV_CONSTRUIDOS,englobe.m2.get(PRIV_CONSTRUIDOS)+inmueble.m2.get(PRIV_CONSTRUIDOS));
            englobe.m2.put(PRIV_LIBRES,englobe.m2.get(PRIV_LIBRES)+inmueble.m2.get(PRIV_LIBRES));
            englobe.m2.put(COM_CONSTRUIDOS,englobe.m2.get(COM_CONSTRUIDOS)+inmueble.m2.get(COM_CONSTRUIDOS));
            englobe.m2.put(COM_LIBRES,englobe.m2.get(COM_LIBRES)+inmueble.m2.get(COM_LIBRES));

            inmueble.padre = englobe;
        }

        englobe.infoNodo.put(JSON_KEYS[0], englobe.genId());
        englobe.infoNodo.put(JSON_KEYS[1], englobe.m2);
        englobe.infoNodo.put(JSON_KEYS[2], características);

        return englobe;
    }

    public static Inmueble hoja(String tipo, String nombre, JSONObject características, HashMap<String, Double> m2) throws Exception {
        Inmueble hoja = (Inmueble) Class.forName(tipo).newInstance();

        hoja.nombre = nombre;
        hoja.m2 = m2;
        hoja.características = características;

        hoja.infoNodo.put(JSON_KEYS[0], hoja.genId());
        hoja.infoNodo.put(JSON_KEYS[1], m2);
        hoja.infoNodo.put(JSON_KEYS[2], características);

        return hoja;
    }

    // -----------------------------------------------
    // Métodos privados
    // -----------------------------------------------

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public String genId() {
        return padre == null ? sigla + " " + nombre : padre.genId() + " " + sigla + " " + nombre;
    }

    public void registrarFicha(Ficha ficha) {
        fichas.add(ficha);
    }

    public void asociarContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return genId();
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        if (this.hijos != null)
            hijos.addAll(this.hijos);
        hijos.addAll(this.fichas);
        return hijos;
    }
}
