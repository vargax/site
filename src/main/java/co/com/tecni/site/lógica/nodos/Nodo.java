package co.com.tecni.site.lógica.nodos;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public abstract class Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    protected String nombre;
    protected JSONObject infoNodo;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Nodo() {
        infoNodo = new JSONObject();
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public abstract String nombreNodo();
    public abstract ArrayList<Object> hijosNodo();

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public String infoNodo() {
        return infoNodo.toJSONString();
    }
}
