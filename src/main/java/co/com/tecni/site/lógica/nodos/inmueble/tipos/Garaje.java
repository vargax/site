package co.com.tecni.site.lógica.nodos.inmueble.tipos;

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
        super.setÍcono(UI_ÍCONO);
    }
}
