package co.com.tecni.site.lógica.nodos.inmuebles.tipos;

import co.com.tecni.site.ui.UiÁrbol;
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
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }

}
