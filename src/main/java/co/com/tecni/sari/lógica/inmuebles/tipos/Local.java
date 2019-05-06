package co.com.tecni.sari.lógica.inmuebles.tipos;

import co.com.tecni.sari.ui.UiÁrbol;
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
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }

}
