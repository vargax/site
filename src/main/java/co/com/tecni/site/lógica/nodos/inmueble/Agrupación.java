package co.com.tecni.site.lógica.nodos.inmueble;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.tipos.Inmueble;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.ArrayList;

public class Agrupación extends Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.FOLDER_SPECIAL;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Agrupación padre;
    private ArrayList<Agrupación> agrupaciones;
    protected ArrayList<Inmueble> inmuebles;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Agrupación(String nombre) {
        super();
        super.setÍcono(UI_ÍCONO);

        this.nombre = nombre;
        this.agrupaciones = new ArrayList<>();
        this.inmuebles = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public void agregarInmueble(Inmueble inmueble) {
        inmuebles.add(inmueble);
    }

    public void agregarAgrupación(Agrupación agrupación) {
        agrupación.padre = this;
        agrupaciones.add(agrupación);
    }

    public String genNombre() {
        return padre == null ? nombre : padre.genNombre() + ' ' + nombre;
    }

    // -----------------------------------------------
    // Getters and Setters
    // -----------------------------------------------

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return nombre;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();

        hijos.addAll(agrupaciones);
        hijos.addAll(inmuebles);

        return hijos;
    }

    // -----------------------------------------------
    // Métodos Object
    // -----------------------------------------------
    public String toString() {
        return "{ID:'"+super.nombre+"',A: "+agrupaciones.size()+",I:"+inmuebles.size()+"}";
    }
}
