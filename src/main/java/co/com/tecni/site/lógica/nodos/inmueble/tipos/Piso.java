package co.com.tecni.site.lógica.nodos.inmueble.tipos;

import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class Piso extends Inmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "PI";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.LAYERS;

    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public Piso() {
        super();
        super.sigla = SIGLA;
        super.íconoCódigo = UI_ÍCONO;
    }
}
