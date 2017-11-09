package co.com.tecni.site.lógica.nodos;

import co.com.tecni.site.lógica.nodos.inmueble.Inmueble;

import java.util.ArrayList;

public class Agrupación extends Nodo {

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
        super(nombre);

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
        agrupaciones.add(agrupación);
    }

    public String genNombre() {
        return padre == null ? nombre : padre.genNombre() + ' ' + nombre;
    }

    // -----------------------------------------------
    // Getters and Setters
    // -----------------------------------------------
    public ArrayList<Object> getHijos() {
        ArrayList<Object> hijos = new ArrayList<>();

        hijos.addAll(agrupaciones);
        hijos.addAll(inmuebles);

        return hijos;
    }

    // -----------------------------------------------
    // Métodos Object
    // -----------------------------------------------
    public String toString() {
        return super.nombre+" (A: "+agrupaciones.size()+" I:"+inmuebles.size()+")";
    }
}
