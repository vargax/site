package co.com.tecni.site.lógica.inmueble.fichas;

import co.com.tecni.site.lógica.inmueble.Ficha;

import java.util.Date;

/**
 * Created by cvargasc on 22/08/17.
 */
public class Avaluo extends Ficha {

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
