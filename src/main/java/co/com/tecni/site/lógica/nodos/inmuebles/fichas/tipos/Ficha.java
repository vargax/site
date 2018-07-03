package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.ArrayList;

public abstract class Ficha implements Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ASSIGNMENT;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    Ficha padre;

    ArrayList<Ficha> fichas;
    ArrayList<Transacción> transacciones;

    UiÁrbol.Ícono ícono;
    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    Ficha() {
        fichas = new ArrayList<>();
        transacciones = new ArrayList<>();

        ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public ArrayList<Transacción>[] recursiónTransacciones(double factorPonderación) {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        // DESCENDIENTES
        for(Ficha ficha : fichas) {
            ArrayList<Transacción>[] transaccionesFicha = ficha.recursiónTransacciones(factorPonderación);
            descendientes.addAll(transaccionesFicha[1]);
            descendientes.addAll(transaccionesFicha[2]);
        }

        // PROPIAS
        for (Transacción transacción : this.transacciones)
            propias.add(transacción.ponderar(factorPonderación));

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public UiÁrbol.Ícono íconoNodo() {
        return ícono;
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(this.fichas);
        return hijos;
    }

    public ArrayList<Transacción>[] transaccionesNodo(Árbol árbol) {
        return recursiónTransacciones(1);
    }
}
