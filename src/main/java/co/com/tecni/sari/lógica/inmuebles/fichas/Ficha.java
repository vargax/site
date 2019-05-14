package co.com.tecni.sari.lógica.inmuebles.fichas;

import co.com.tecni.sari.lógica.inmuebles.Inmueble;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.sari.ui.UiÁrbol;
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
    Inmueble inmueble;
    Ficha padre;
    public boolean presupuestado;

    ArrayList<Ficha> fichas = new ArrayList<>();
    ArrayList<Transacción> transacciones = new ArrayList<>();

    UiÁrbol.Ícono ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    Ficha(Nodo padre) {
        if (padre instanceof Ficha) {
            this.inmueble = null;
            this.padre = (Ficha) padre;
            this.padre.fichas.add(this);

            this.presupuestado = this.padre.presupuestado;
        } else {
            this.padre = null;
            this.inmueble = (Inmueble) padre;
            this.inmueble.registrarFicha(this);

            this.presupuestado = false;
        }
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public String darTipo() {
        return this.getClass().getSimpleName();
    }

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

    /**
     * Los M2 y Valor de una ficha son los del padre
     */
    public double[] getM2yValor() {
        try {
            return padre.getM2yValor();
        } catch (NullPointerException e) {
            return inmueble.getM2yValor();
        }
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public UiÁrbol.Ícono íconoNodo() {
        return ícono;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(this.fichas);
        return hijos;
    }

    public ArrayList<Transacción>[] transaccionesNodo() {
        return recursiónTransacciones(1);
    }
}
