package co.com.tecni.site.lógica.fichas;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.transacciones.Tercero;
import co.com.tecni.site.lógica.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.time.LocalDate;
import java.util.ArrayList;

public class Catastral extends Ficha {

    final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ACCOUNT_BALANCE;

    private Json json;
    public static class Json {
        String chip;
        String cedulaCatastral;
        String nomenclatura;
        double m2construcción;
        double m2terreno;

        public Json(String chip, String cedulaCatastral, String nomenclatura, double m2construcción, double m2terreno) {
            this.chip = chip;
            this.cedulaCatastral = cedulaCatastral;
            this.nomenclatura = nomenclatura;
            this.m2construcción = m2construcción;
            this.m2terreno = m2terreno;
        }
    }

    public Catastral(Json json) {
        inicializar(json);
    }

    public Catastral(Ficha padre, Json json) {
        super(padre);
        inicializar(json);
    }

    private void inicializar(Json json) {
        super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);
        this.json = json;
    }

    public String nombreNodo(Árbol árbol) {
        return "Cédula catastral: " + json.cedulaCatastral;
    }

    public String infoNodo(Árbol árbol) {
        return Site.GSON.toJson(json);
    }

    // -----------------------------------------------
    // Subclases
    // -----------------------------------------------
    public static class ImpuestoPredial extends Ficha {

        private final static int PRORRATA = 12;
        private final static String[] TERCERO = {"899999061", "Secretaría de Hacienda Distrital de Bogotá"};

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

        public ImpuestoPredial(Ficha padre, Json json) {
            super(padre);
            super.ícono = new UiÁrbol.Ícono(UI_ÍCONO);

            this.json = json;

            super.transacciones = new ArrayList<>();

            double monto = -json.impuestoACargo/PRORRATA;
            Tercero tercero = new Tercero(Integer.parseInt(TERCERO[0]), TERCERO[1]);

            for (int i = 1; i <= PRORRATA; i++) {
                String concepto = "Prorrata predial mes "+i;

                LocalDate fecha = LocalDate.parse(json.añoFiscal+ (i < 10 ? "-0"+i : "-"+i) +"-01");

                transacciones.add(new Transacción(this, concepto, fecha, monto, tercero));
            }
        }

        public String nombreNodo(Árbol árbol) {
            return "Predial "+json.añoFiscal;
        }

        @Override
        public String infoNodo(Árbol árbol) {
            return Site.GSON.toJson(json);
        }
    }
}