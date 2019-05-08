package co.com.tecni.sari.lógica.inmuebles;

import co.com.tecni.sari.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class Bodega extends Inmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "BG";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.STORAGE;

    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public Bodega() {
        super();
        super.sigla = SIGLA;
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }

}
