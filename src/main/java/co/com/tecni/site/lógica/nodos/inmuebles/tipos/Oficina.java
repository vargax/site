package co.com.tecni.site.lógica.nodos.inmuebles.tipos;

import co.com.tecni.site.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public class Oficina extends Inmueble {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String SIGLA = "OF";
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.BUSINESS_CENTER;

    // -----------------------------------------------
    // Constructores
    // -----------------------------------------------
    public Oficina() {
        super();
        super.sigla = SIGLA;
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }

}
