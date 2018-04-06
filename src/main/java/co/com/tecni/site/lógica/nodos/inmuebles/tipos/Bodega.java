package co.com.tecni.site.lógica.nodos.inmuebles.tipos;

import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class Bodega extends Inmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "BG";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.HOME;

    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public Bodega() {
        super();
        super.sigla = SIGLA;
        super.íconoCódigo = UI_ÍCONO;
    }

}
