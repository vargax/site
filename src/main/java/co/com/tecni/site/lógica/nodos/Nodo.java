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
    // Getters and Setters
    // -----------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public abstract ArrayList<Object> getHijos();

}
