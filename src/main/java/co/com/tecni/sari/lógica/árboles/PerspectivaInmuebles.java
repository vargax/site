package co.com.tecni.sari.lógica.árboles;

import co.com.tecni.sari.lógica.inmuebles.Agrupación;
import co.com.tecni.sari.lógica.inmuebles.Inmueble;

import java.util.HashMap;

public class PerspectivaInmuebles extends Árbol {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String NOMBRE_RAIZ = "INMUEBLES";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    public final static HashMap<String, Inmueble> inmueblesRaiz = new HashMap<>();
    public final static HashMap<String, Inmueble> inmueblesxId = new HashMap<>();
    private Agrupación raiz;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public PerspectivaInmuebles(Agrupación raiz) {
        this.raiz = raiz;
    }

    // -----------------------------------------------
    // Métodos Privados
    // -----------------------------------------------
    private void recursiónIdentificadores(Nodo nodo) {
        for (Object hijo : nodo.hijosNodo()) {
            if (hijo instanceof Inmueble)
                inmueblesxId.put(((Inmueble) hijo).genNombre(), (Inmueble) hijo);
            recursiónIdentificadores((Nodo) hijo);
        }
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public void registrarIdentificadores() {
        recursiónIdentificadores(raiz);
    }

    // -----------------------------------------------
    // Métodos Árbol
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }
}
