package co.com.tecni.site.lógica.inmueble;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Los inmuebles son la entidad principal de SAIT
 */
public class Inmueble {

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
    private String nombre;

    private HashMap<String , Double> m2;

    private Inmueble padre;
    private ArrayList<Inmueble> hijos;

    private ArrayList<Ficha> fichas;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Inmueble(String nombre, HashMap<String, Double> m2) {
        this.nombre = nombre;
        this.m2 = m2;

        this.padre = null;
        this.hijos = new ArrayList();
    }

    private Inmueble(String nombre, ArrayList<Inmueble> hijos) {
        this.nombre = nombre;

        this.padre = null;
        this.hijos = hijos;
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public static Inmueble englobar(String nombre, ArrayList<Inmueble> inmuebles) {
        Inmueble englobe = new Inmueble(nombre, inmuebles);
        englobe.m2 = new HashMap<String, Double>();

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
            this.hijos.add(inmueble);
        }
    }

    // -----------------------------------------------
    // Getters and Setters
    // -----------------------------------------------
    public String getNombre() {
        return padre == null ? nombre : padre.getNombre() + ' ' + nombre;
    }

    public ArrayList<Inmueble> getHijos() {
        return hijos;
    }

    // -----------------------------------------------
    // Métodos Object
    // -----------------------------------------------
    public String toString() {
        String nombre = this.nombre+" ("+hijos.size()+") "+m2;

        if (hijos.size() != 0)
            for (Inmueble hijo : hijos)
                nombre += ("\n "+hijo.toString());
        else
            nombre = " "+nombre;

        return nombre;
    }
}
