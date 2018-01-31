package co.com.tecni.site.lógica.nodos;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.ArrayList;

public abstract class Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    protected String nombre;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public abstract String nombreNodo();
    public abstract ArrayList<Object> hijosNodo();

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public JsonObject darCaracterísticas() {
        return null;
    }

    public String toString() {
        return "{ID:'Nodo'" +
                "nombre:'" + nombre + '\'' +
                '}';
    }
}
