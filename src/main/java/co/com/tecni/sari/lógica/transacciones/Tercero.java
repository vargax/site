package co.com.tecni.sari.lógica.transacciones;

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
