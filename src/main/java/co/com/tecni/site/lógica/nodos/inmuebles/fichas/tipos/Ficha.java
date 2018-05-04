package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.ArrayList;

public abstract class Ficha extends Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ASSIGNMENT;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    ArrayList<Ficha> fichas;
    protected ArrayList<Transacción> transacciones;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    protected Ficha() {
        super();
        super.íconoCódigo  = UI_ÍCONO;

        fichas = new ArrayList<>();
        transacciones = new ArrayList<>();
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(this.fichas);
        return hijos;
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public ArrayList<Transacción> transaccionesNodo() {
        ArrayList<Transacción> transaccionesNodo = super.transaccionesNodo();

        transaccionesNodo.addAll(this.transacciones);
        for(Ficha ficha : fichas)
            transaccionesNodo.addAll(ficha.transaccionesNodo());

        return transaccionesNodo;
    }
}
