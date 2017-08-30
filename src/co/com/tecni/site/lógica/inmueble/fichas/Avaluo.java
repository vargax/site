package co.com.tecni.site.lógica.inmueble.fichas;

import co.com.tecni.site.lógica.inmueble.Ficha;

import java.util.Date;

/**
 * Created by cvargasc on 22/08/17.
 */
public class Avaluo implements Ficha {

    private int piso;
    private int calificacion;

    private class AvaluoTecnico {
        private Date fecha;
        private float valorTerreno;
        private float valorConstrucción;
    }

}
