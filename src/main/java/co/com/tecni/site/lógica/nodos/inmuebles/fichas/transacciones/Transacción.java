package co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones;

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
    private String concepto;
    private LocalDate fecha;
    private Double monto;
    private Tercero tercero;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Transacción(String concepto, LocalDate fecha, Double monto, Tercero tercero) {
        this.concepto = concepto;
        this.fecha = fecha;
        this.monto = monto;
        this.tercero = tercero;
    }

    // -----------------------------------------------
    // Getters
    // -----------------------------------------------

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
