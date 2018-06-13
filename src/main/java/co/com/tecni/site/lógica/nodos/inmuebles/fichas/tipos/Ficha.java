package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
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
    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(this.fichas);
        return hijos;
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public ArrayList<Transacción>[] recursiónTransacciones(double factorPonderación) {
        ArrayList<Transacción>[] resultado = super.transaccionesNodo();

        ArrayList<Transacción> propias = new ArrayList<>();
        for (Transacción transacción : this.transacciones)
            propias.add(transacción.ponderar(factorPonderación));
        resultado[1] = propias;

        ArrayList<Transacción> descendientes = new ArrayList<>();
        for(Ficha ficha : fichas) {
            ArrayList<Transacción>[] transaccionesFicha = ficha.recursiónTransacciones(factorPonderación);
            descendientes.addAll(transaccionesFicha[1]);
            descendientes.addAll(transaccionesFicha[2]);
        }
        resultado[2] = descendientes;

        return resultado;
    }
}
