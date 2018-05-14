package co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Ficha;
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
    private Ficha ficha;

    private String concepto;
    private LocalDate fecha;
    private Double monto;
    private Tercero tercero;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Transacción(Ficha ficha, String concepto, LocalDate fecha, Double monto, Tercero tercero) {
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

    // -----------------------------------------------
    // Getters
    // -----------------------------------------------


    public Ficha getFicha() {
        return ficha;
    }

    public String getConcepto() {
        return concepto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public Tercero getTercero() {
        return tercero;
    }
}
