package co.com.tecni.site.lógica.nodos.inmueble.tipos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.fichas._Ficha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Los inmuebles son la entidad principal de SAIT
 */
public abstract class _Inmueble extends Nodo {

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
    private ArrayList<_Ficha> fichas;

    private _Inmueble padre;
    private ArrayList<_Inmueble> hijos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public _Inmueble() {
        this.fichas = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos privados
    // -----------------------------------------------

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public static _Inmueble englobar(String tipo, String nombre, ArrayList<_Inmueble> inmuebles) throws Exception {
        _Inmueble englobe = (_Inmueble) Class.forName(tipo).newInstance();
        englobe.nombre = nombre;
        englobe.hijos =inmuebles;

        englobe.m2 = new HashMap<>();

        englobe.m2.put(PRIV_CONSTRUIDOS,0.0);
        englobe.m2.put(PRIV_LIBRES,0.0);
        englobe.m2.put(COM_CONSTRUIDOS,0.0);
        englobe.m2.put(COM_LIBRES,0.0);

        for (_Inmueble inmueble : inmuebles) {

            englobe.m2.put(PRIV_CONSTRUIDOS,englobe.m2.get(PRIV_CONSTRUIDOS)+inmueble.m2.get(PRIV_CONSTRUIDOS));
            englobe.m2.put(PRIV_LIBRES,englobe.m2.get(PRIV_LIBRES)+inmueble.m2.get(PRIV_LIBRES));
            englobe.m2.put(COM_CONSTRUIDOS,englobe.m2.get(COM_CONSTRUIDOS)+inmueble.m2.get(COM_CONSTRUIDOS));
            englobe.m2.put(COM_LIBRES,englobe.m2.get(COM_LIBRES)+inmueble.m2.get(COM_LIBRES));

            inmueble.padre = englobe;
        }
        return englobe;
    }

    public static _Inmueble hoja(String tipo, String nombre, HashMap<String, Double> m2) throws Exception {
        _Inmueble hoja = (_Inmueble) Class.forName(tipo).newInstance();
        hoja.nombre = nombre;
        hoja.m2 = m2;

        return hoja;
    }

    public void desenglobar(ArrayList<_Inmueble> hijos) {
        for (_Inmueble inmueble : hijos) {
            inmueble.padre = this;
            this.hijos.add(inmueble);
        }
    }

    public void asociarFicha(_Ficha ficha) {
        fichas.add(ficha);
    }

    // -----------------------------------------------
    // Getters and Setters
    // -----------------------------------------------
    public ArrayList<Object> getHijos() {
        ArrayList<Object> hijos = new ArrayList<>();
        if (this.hijos != null)
            hijos.addAll(this.hijos);
        hijos.addAll(this.fichas);
        return hijos;
    }

    // -----------------------------------------------
    // Métodos Object
    // -----------------------------------------------
    public String toString() {
        String nombre = super.nombre + " (";

        for (Map.Entry<String, Double> entry : m2.entrySet()) {
            nombre += entry.getKey() + ": "+String.format("%.2f", entry.getValue())+" ";
        }

        return nombre.substring(0, nombre.length()-1)+")";
    }
}
