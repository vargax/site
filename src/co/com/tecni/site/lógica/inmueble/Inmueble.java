package co.com.tecni.site.lógica.inmueble;

import java.util.ArrayList;

/**
 * Los inmuebles son la entidad principal de SAIT
 */
public class Inmueble {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String nombre;
    private double m2;

    private Inmueble padre;
    private ArrayList<Inmueble> hijos;

    private ArrayList<Ficha> fichas;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Inmueble(String nombre) {
        this.nombre = nombre;
        this.m2 = m2;

        this.padre = null;
        this.hijos = new ArrayList<>();
    }

    private Inmueble(String nombre, ArrayList<Inmueble> hijos) {
        this.nombre = nombre;

        this.padre = null;
        this.hijos = hijos;
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public static Inmueble englobar(String nombre, ArrayList<Inmueble> inmuebles) {
        Inmueble englobe = new Inmueble(nombre, inmuebles);
        for (Inmueble inmueble : inmuebles) {
            inmueble.padre = englobe;
        }
        return englobe;
    }

    public void desenglobar(ArrayList<Inmueble> hijos) {
        for (Inmueble inmueble : hijos) {
            inmueble.padre = this;
            this.hijos.add(inmueble);
        }
    }

    // -----------------------------------------------
    // Getters and Setters
    // -----------------------------------------------

    public String getNombre() {
        return padre == null ? nombre : padre.getNombre() + ' ' + nombre;
    }

    public ArrayList<Inmueble> getHijos() {
        return hijos;
    }
}
