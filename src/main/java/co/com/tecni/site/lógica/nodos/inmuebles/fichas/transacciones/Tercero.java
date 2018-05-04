package co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones;

public class Tercero {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String nit;
    private String nombre;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Tercero(String nit, String nombre) {
        this.nit = nit;
        this.nombre = nombre;
    }

    // -----------------------------------------------
    // Getters
    // -----------------------------------------------
    public String getNit() {
        return nit;
    }

    public String getNombre() {
        return nombre;
    }
}
