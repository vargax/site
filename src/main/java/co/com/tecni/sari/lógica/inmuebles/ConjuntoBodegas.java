package co.com.tecni.sari.lógica.inmuebles;

import co.com.tecni.sari.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class ConjuntoBodegas extends Inmueble {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "CB";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.LOCATION_CITY;

    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public ConjuntoBodegas() {
        super();
        super.sigla = SIGLA;
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }
}
