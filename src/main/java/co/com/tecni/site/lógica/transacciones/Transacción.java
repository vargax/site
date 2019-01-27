package co.com.tecni.site.lógica.transacciones;

import co.com.tecni.site.lógica.fichas.Ficha;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.time.LocalDate;

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
    public final LocalDate fecha;
    public final Double monto;
    public final Tercero tercero;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Transacción(Ficha ficha, String concepto, LocalDate fecha, Double monto, Tercero tercero) {
        this.ficha = ficha;
        this.concepto = concepto;
        this.fecha = fecha;
        this.monto = monto;

        if (tercero == null)
            this.tercero = Tercero.indeterminado;
        else
            this.tercero = tercero;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public Transacción ponderar(double factorPonderación) {
        return new Transacción(ficha, concepto, fecha, factorPonderación*monto, tercero);
    }

}
