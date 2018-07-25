package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class ObraCivil extends Ficha {
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
        Date fechaInicial;
        Date fechaFinal;
        Double presupuesto;
        String descripción;
        String observaciones;

        public Json(String tipo, Date fechaInicial, Date fechaFinal, double presupuesto, String descripción, String observaciones) {
            this.tipo = tipo;
            this.fechaInicial = fechaInicial;
            this.fechaFinal = fechaFinal;
            this.presupuesto = presupuesto;
            this.descripción = descripción;
            this.observaciones = observaciones;
        }
    }

    public ObraCivil(boolean presupuestado, Json json) {
        super(presupuestado);
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

    public String nombreNodo(Árbol árbol) {
        return json.tipo + " "+json.descripción;
    }

    public String infoNodo(Árbol árbol) {
        return Site.gson.toJson(json);
    }
}
