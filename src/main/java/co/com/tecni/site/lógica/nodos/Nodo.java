package co.com.tecni.site.lógica.nodos;

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

    @Override
    public String toString() {
        return "Nodo{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
