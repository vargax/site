package co.com.tecni.site.lógica.nodos.inmueble.fichas;

import java.util.ArrayList;
import java.util.Date;

public class Jurídica extends _Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String[] JSON_KEYS = {"Oficina Registro", "Matrícula Inmobiliaria",
            "Fecha de compra en registro", "Coeficiente de Copropiedad"};

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
        infoNodo.put(JSON_KEYS[0], oficinaRegistro);

        this.matrículaInmobiliaria = matrículaInmobiliaria;
        infoNodo.put(JSON_KEYS[1], matrículaInmobiliaria);

        this.fechaRegistroCompra = fechaRegistroCompra;
        infoNodo.put(JSON_KEYS[2], fechaRegistroCompra);

        this.coefCopropiedad = coefCopropiedad;
        infoNodo.put(JSON_KEYS[3], coefCopropiedad);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return "Matrícula inmobiliaria: "+matrículaInmobiliaria;
    }

    public ArrayList<Object> hijosNodo() {
        return null;
    }

}
