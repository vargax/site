package co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Ficha;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.Date;

public class Transacción {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ATTACH_MONEY;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    public final Ficha ficha;

    public final String concepto;
    public final Date fecha;
    public final Double monto;
    public final Tercero tercero;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Transacción(Ficha ficha, String concepto, Date fecha, Double monto, Tercero tercero) {
        this.ficha = ficha;
        this.concepto = concepto;
        this.fecha = fecha;
        this.monto = monto;
        this.tercero = tercero;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public Transacción ponderar(double factorPonderación) {
        return new Transacción(ficha, concepto, fecha, factorPonderación*monto, tercero);
    }

}
