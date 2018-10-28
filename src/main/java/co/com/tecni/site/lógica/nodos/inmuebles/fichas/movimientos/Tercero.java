package co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos;

public class Tercero {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    public final int nit;
    public final String nombre;

    public final static Tercero indeterminado = new Tercero(0, "Indeterminado");
    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Tercero(int nit, String nombre) {
        this.nit = nit;
        this.nombre = nombre;
    }
}
