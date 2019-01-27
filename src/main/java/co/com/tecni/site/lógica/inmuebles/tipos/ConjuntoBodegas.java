package co.com.tecni.site.lógica.inmuebles.tipos;

import co.com.tecni.site.ui.UiÁrbol;
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
