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
    public abstract String guiÁrbolNombre();

    public abstract ArrayList<Object> guiÁrbolHijos();

}
