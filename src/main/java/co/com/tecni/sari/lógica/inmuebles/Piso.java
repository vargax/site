package co.com.tecni.sari.lógica.inmuebles;

import co.com.tecni.sari.ui.UiÁrbol;
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
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }
}
