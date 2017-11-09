package co.com.tecni.site.lógica.nodos.inmueble.fichas;

import java.util.Date;

/**
 * Created by cvargasc on 22/08/17.
 */
public class Juridica extends _Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String TIPO = "Jurídica";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String matriculaInmobiliaria;
    private float porcentajeCopropiedad;

    private String oficinaRegistro;
    private Date fechaRegistroCompra;

    private Date fechaHipoteca;
    private String bancoHipoteca;

    private String propietario;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Juridica() {
        super(TIPO);

    }
}
