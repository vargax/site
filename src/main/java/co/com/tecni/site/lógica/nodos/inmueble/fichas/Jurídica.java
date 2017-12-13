package co.com.tecni.site.lógica.nodos.inmueble.fichas;

import java.util.ArrayList;
import java.util.Date;

public class Jurídica extends _Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String oficinaRegistro;
    private String matrículaInmobiliaria;
    private Date fechaRegistroCompra;
    private double coefCopropiedad;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Jurídica(String oficinaRegistro, String matrículaInmobiliaria, Date fechaRegistroCompra, double coefCopropiedad) {
        this.oficinaRegistro = oficinaRegistro;
        this.matrículaInmobiliaria = matrículaInmobiliaria;
        this.fechaRegistroCompra = fechaRegistroCompra;
        this.coefCopropiedad = coefCopropiedad;
    }

    // -----------------------------------------------
    // Métodos nodo
    // -----------------------------------------------
    public String nombreNodo() {
        return "Matrícula inmobiliaria: "+matrículaInmobiliaria;
    }

    public ArrayList<Object> hijosNodo() {
        return null;
    }

    // -----------------------------------------------
    // Métodos object
    // -----------------------------------------------
    public String toString() {
        return "Jurídica{" +
                "oficinaRegistro='" + oficinaRegistro + '\'' +
                ", matrículaInmobiliaria='" + matrículaInmobiliaria + '\'' +
                ", fechaRegistroCompra='" + fechaRegistroCompra + '\'' +
                ", coefCopropiedad=" + coefCopropiedad +
                '}';
    }
}
