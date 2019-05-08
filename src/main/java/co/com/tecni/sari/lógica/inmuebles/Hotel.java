package co.com.tecni.sari.lógica.inmuebles;

import co.com.tecni.sari.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class Hotel extends Inmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "HT";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.HOTEL;

    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public Hotel() {
        super();
        super.sigla = SIGLA;
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }

}
