package co.com.tecni.site.lógica.nodos.inmuebles.tipos;

import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class Garaje extends Inmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "GR";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.DIRECTIONS_CAR;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Garaje() {
        super();
        super.sigla = SIGLA;
        super.íconoCódigo = UI_ÍCONO;
    }
}
