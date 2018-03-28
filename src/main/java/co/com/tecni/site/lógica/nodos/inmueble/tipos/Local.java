package co.com.tecni.site.lógica.nodos.inmueble.tipos;

import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class Local extends Inmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "LC";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.STORE;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Local() {
        super();
        super.sigla = SIGLA;
        super.setÍcono(UI_ÍCONO);
    }

}
