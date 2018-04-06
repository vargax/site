package co.com.tecni.site.lógica.nodos.inmuebles.fichas;

import co.com.tecni.site.lógica.nodos.Nodo;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.Date;

public abstract class Ficha extends Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ASSIGNMENT;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Date[] vigencia;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    protected Ficha() {
        super();
        super.íconoCódigo  = UI_ÍCONO;
    }
}
