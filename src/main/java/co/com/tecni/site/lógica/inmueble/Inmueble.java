package co.com.tecni.site.lógica.inmueble;

import co.com.tecni.site.lógica.Agrupación;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Los inmuebles son la entidad principal de SAIT
 */
public class Inmueble extends Agrupación {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String PRIV_CONSTRUIDOS = "PC";
    public final static String PRIV_LIBRES = "PL";
    public final static String COM_CONSTRUIDOS = "CC";
    public final static String COM_LIBRES = "CL";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private HashMap<String , Double> m2;
    private ArrayList<Ficha> fichas;

    private Inmueble padre;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Inmueble(String nombre, HashMap<String, Double> m2) {
        super(nombre);

        this.m2 = m2;
        this.fichas = new ArrayList<>();
    }

    private Inmueble(String nombre, ArrayList<Inmueble> hijos) {
        super(nombre);

        this.padre = null;
        this.inmuebles = hijos;

        this.fichas = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos privados
    // -----------------------------------------------

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public static Inmueble englobar(String nombre, ArrayList<Inmueble> inmuebles) {
        Inmueble englobe = new Inmueble(nombre, inmuebles);
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
        return englobe;
    }

    public void desenglobar(ArrayList<Inmueble> hijos) {
        for (Inmueble inmueble : hijos) {
            inmueble.padre = this;
            this.inmuebles.add(inmueble);
        }
    }

    public void asociarFicha(Ficha ficha) {
        fichas.add(ficha);
    }

    // -----------------------------------------------
    // Getters and Setters
    // -----------------------------------------------
    public ArrayList<Object> getHijos() {
        ArrayList<Object> hijos = super.getHijos();
        hijos.addAll(fichas);
        return hijos;
    }

    // -----------------------------------------------
    // Métodos Object
    // -----------------------------------------------
    public String toString() {
        String nombre = this.nombre + " (";

        for (Map.Entry<String, Double> entry : m2.entrySet()) {
            nombre += entry.getKey() + ": "+String.format("%.2f", entry.getValue())+" ";
        }

        return nombre.substring(0, nombre.length()-1)+")";
    }
}
