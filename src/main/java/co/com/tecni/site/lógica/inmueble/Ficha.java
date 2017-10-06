package co.com.tecni.site.lógica.inmueble;

/**
 * Created by cvargasc on 17/08/17.
 */
public abstract class Ficha {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    protected String tipo;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    protected Ficha(String tipo) {
        this.tipo = tipo;
    }

    // -----------------------------------------------
    // Métodos Object
    // -----------------------------------------------
    public String toString() {
        return tipo;
    }
}
