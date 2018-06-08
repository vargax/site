package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Tercero;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.Árbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ImpuestoPredial extends Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ACCOUNT_BALANCE;

    private final static int PRORRATA = 12;
    private final static String[] TERCERO = {"899.999.061-9", "Secretaría de Hacienda Distrital de Bogotá"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Json json;

    public static class Json {
        int añoFiscal;
        double avalúoCatastral;
        double impuestoACargo;

        public Json(int añoFiscal, double avalúoCatastral, double impuestoACargo) {
            this.añoFiscal = añoFiscal;
            this.avalúoCatastral = avalúoCatastral;
            this.impuestoACargo = impuestoACargo;
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ImpuestoPredial(Json json) {
        super();
        super.íconoCódigo = UI_ÍCONO;
        super.íconoColor = Catastral.UI_ÍCONO_COLOR;

        this.json = json;

        super.transacciones = new ArrayList<>();

        double monto = -json.impuestoACargo/PRORRATA;
        Tercero tercero = new Tercero(TERCERO[0], TERCERO[1]);
        for (int i = 1; i <= PRORRATA; i++) {
            String concepto = "Prorrata predial mes "+i;
            LocalDate fecha = LocalDate.parse(json.añoFiscal+"-"+i+"-1",DateTimeFormatter.ofPattern("yyyy-M-d"));
            transacciones.add(new Transacción(this, concepto, fecha, monto, tercero));
        }
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return "Predial "+json.añoFiscal;
    }

    @Override
    public String infoNodo() {
        return Site.gson.toJson(json);
    }
}
