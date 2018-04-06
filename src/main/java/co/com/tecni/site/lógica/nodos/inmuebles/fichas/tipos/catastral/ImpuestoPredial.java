package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.catastral;

import co.com.tecni.site.lógica.nodos.Nodo;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.ArrayList;

public class ImpuestoPredial extends Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ACCOUNT_BALANCE;

    private final static String[] JSON_KEYS = {"Año Fiscal", "Avaluo Catastral",
            "Impuesto a Cargo"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int añoFiscal;
    private double avaluoCatastral;
    private double impuestoACargo;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ImpuestoPredial(int añoFiscal, double avaluoCatastral, double impuestoACargo) {
        super();
        super.íconoCódigo = UI_ÍCONO;
        super.íconoColor = Catastral.UI_ÍCONO_COLOR;

        this.añoFiscal = añoFiscal;
        infoNodo.put(JSON_KEYS[0], añoFiscal);

        this.avaluoCatastral = avaluoCatastral;
        infoNodo.put(JSON_KEYS[1], avaluoCatastral);

        this.impuestoACargo = impuestoACargo;
        infoNodo.put(JSON_KEYS[2], impuestoACargo);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return "Impuesto predial "+añoFiscal;
    }

    public ArrayList<Object> hijosNodo() {
        return null;
    }
}
