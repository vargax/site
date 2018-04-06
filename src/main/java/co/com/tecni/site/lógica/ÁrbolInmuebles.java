package co.com.tecni.site.lógica;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.Agrupación;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;

import java.util.HashMap;

public class ÁrbolInmuebles extends Árbol {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String NOMBRE_RAIZ = "INMUEBLES";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Agrupación raiz;
    private HashMap<String, Inmueble> inmueblesxId;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ÁrbolInmuebles() {
        raiz = new Agrupación(NOMBRE_RAIZ);
    }

    // -----------------------------------------------
    // Métodos Privados
    // -----------------------------------------------
    private void recursiónIdentificadores(Nodo nodo) {
        for (Object hijo : nodo.hijosNodo()) {
            if (hijo instanceof Inmueble)
                inmueblesxId.put(((Inmueble) hijo).genId(), (Inmueble) hijo);
            recursiónIdentificadores((Nodo) hijo);
        }
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public void registrarIdentificadores() {
        inmueblesxId = new HashMap<>();
        recursiónIdentificadores(raiz);
    }

    public HashMap<String, Inmueble> getInmueblesxId() {
        return inmueblesxId;
    }

    public Inmueble getInmueble(String id) {
        return inmueblesxId.get(id);
    }

    // -----------------------------------------------
    // Métodos Árbol
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }
}
