package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.Árbol;

import java.awt.*;

public class Catastral extends Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static Color UI_ÍCONO_COLOR = Color.BLUE;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
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

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Catastral(Json json) {
        super();
        super.íconoColor = UI_ÍCONO_COLOR;

        this.json = json;
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public void registrarPredial(ImpuestoPredial impuestoPredial) {
        fichas.add(impuestoPredial);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return "Cédula catastral: " + json.cedulaCatastral;
    }

    public String infoNodo() {
        return Site.gson.toJson(json);
    }
}