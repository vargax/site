package co.com.tecni.site.lógica.nodos.inmueble.fichas;

/**
 * Created by cvargasc on 17/08/17.
 */
public abstract class _Ficha {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    protected String tipo;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    protected _Ficha(String tipo) {
        this.tipo = tipo;
    }

    // -----------------------------------------------
    // Métodos Object
    // -----------------------------------------------
    public String toString() {
        return tipo;
    }
}
