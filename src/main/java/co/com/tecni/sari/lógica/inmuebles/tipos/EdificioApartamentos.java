package co.com.tecni.sari.lógica.inmuebles.tipos;

import co.com.tecni.sari.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class EdificioApartamentos extends Inmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "EA";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.HOME;

    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public EdificioApartamentos() {
        super();
        super.sigla = SIGLA;
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }

}
