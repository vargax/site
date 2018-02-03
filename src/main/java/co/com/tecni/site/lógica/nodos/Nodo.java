package co.com.tecni.site.lógica.nodos;

import org.json.simple.JSONObject;

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
    public JSONObject darCaracterísticas() {
        return null;
    }

    public String toString() {
        return "{ID:'Nodo'" +
                "nombre:'" + nombre + '\'' +
                '}';
    }
}
