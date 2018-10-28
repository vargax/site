package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Movimiento;
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
    public final boolean presupuestado;

    ArrayList<Ficha> fichas;
    ArrayList<Movimiento> movimientos;

    UiÁrbol.Ícono ícono;
    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    Ficha() {
        this(false);
    }

    Ficha (boolean presupuestado) {
        this.presupuestado = presupuestado;

        fichas = new ArrayList<>();
        movimientos = new ArrayList<>();

        ícono = new UiÁrbol.Ícono(UI_ÍCONO);
    }

    Ficha(Ficha padre) {
        this(padre.presupuestado);

        this.padre = padre;
        padre.fichas.add(this);
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public ArrayList<Movimiento>[] recursiónMovimientos(double factorPonderación) {
        ArrayList<Movimiento> descendientes = new ArrayList<>();
        ArrayList<Movimiento> propias = new ArrayList<>();
        ArrayList<Movimiento> ancestros = new ArrayList<>();

        // DESCENDIENTES
        for(Ficha ficha : fichas) {
            ArrayList<Movimiento>[] movimientosFicha = ficha.recursiónMovimientos(factorPonderación);
            descendientes.addAll(movimientosFicha[1]);
            descendientes.addAll(movimientosFicha[2]);
        }

        // PROPIAS
        for (Movimiento movimiento : this.movimientos)
            propias.add(movimiento.ponderar(factorPonderación));

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

    public ArrayList<Movimiento>[] movimientosNodo(Árbol árbol) {
        return recursiónMovimientos(1);
    }
}
