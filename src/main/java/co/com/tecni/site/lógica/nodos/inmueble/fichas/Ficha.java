package co.com.tecni.site.lógica.nodos.inmueble.fichas;

import co.com.tecni.site.lógica.nodos.Nodo;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

public abstract class Ficha extends Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ASSIGNMENT;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------


    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    protected Ficha() {
        super();
        super.íconoCódigo  = UI_ÍCONO;
    }
}
