package co.com.tecni.sari.lógica.inmuebles.fichas;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

public class Mejora extends Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    final static double ANTICIPO = 0.3;
    final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.BUILD;

    public static final HashSet<String> TIPOS;
    static {
        String[] tipos = {"Adecuación", "Mantenimiento"};
        TIPOS = new HashSet<>(Arrays.asList(tipos));
    }

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Json json;
    public static class Json {
        String tipo;
        LocalDate fechaInicial;
        LocalDate fechaFinal;
        Double presupuesto;
        String descripción;
        String observaciones;

        public Json(String tipo, LocalDate fechaInicial, LocalDate fechaFinal, double presupuesto, String descripción, String observaciones) {
            this.tipo = tipo;
            this.fechaInicial = fechaInicial;
            this.fechaFinal = fechaFinal;
            this.presupuesto = presupuesto;
            this.descripción = descripción;
            this.observaciones = observaciones;
        }
    }

    public Mejora(Nodo padre, Json json) {
        super(padre);
        inicializar(json);
    }

    private void inicializar(Json json) {
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
        this.json = json;

        Transacción anticipo = new Transacción(this,
                "Anticipo "+json.descripción, json.fechaInicial,
                ANTICIPO*json.presupuesto, null);

        Transacción saldo = new Transacción(this,
                "Saldo "+json.descripción, json.fechaFinal,
                (1-ANTICIPO)*json.presupuesto, null);

        super.transacciones.add(anticipo);
        super.transacciones.add(saldo);
    }

    @Override
    public String darTipo() {
        return json.tipo;
    }

    public String nombreNodo() {
        return json.tipo + " "+json.descripción;
    }

    public String infoNodo() {
        return Sari.GSON.toJson(json);
    }
}
