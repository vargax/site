package co.com.tecni.site.lógica.nodos.inmueble.fichas;

import java.util.Date;

/**
 * Created by cvargasc on 22/08/17.
 */
public class Avaluo extends _Ficha {

    private final static String TIPO = "Avaluo";

    private int piso;
    private int calificacion;

    public Avaluo() {
        super(TIPO);
    }

    private class AvaluoTecnico {
        private Date fecha;
        private float valorTerreno;
        private float valorConstrucción;
    }

}
