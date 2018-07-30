package co.com.tecni.site.lógica.árboles;

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
    HashMap<String, Inmueble> inmueblesxId;
    private Agrupación raiz;

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
        for (Object hijo : nodo.hijosNodo(this)) {
            if (hijo instanceof Inmueble)
                inmueblesxId.put(((Inmueble) hijo).genId(), (Inmueble) hijo);
            recursiónIdentificadores((Nodo) hijo);
        }
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public Agrupación registrarAgrupación(String nombre) {
        Agrupación agrupación = new Agrupación(nombre);
        raiz.agregarAgrupación(agrupación);
        return agrupación;
    }

    public HashMap<String, Inmueble> registrarIdentificadores() {
        inmueblesxId = new HashMap<>();
        recursiónIdentificadores(raiz);
        return inmueblesxId;
    }

    // -----------------------------------------------
    // Métodos Árbol
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }
}
