package co.com.tecni.site.lógica.nodos.inmueble.fichas.catastral;

import co.com.tecni.site.lógica.nodos.Nodo;

import java.util.ArrayList;

public class ImpuestoPredial extends Nodo {

    private int añoFiscal;
    private double avaluoCatastral;
    private double impuestoACargo;

    public ImpuestoPredial(int añoFiscal, double avaluoCatastral, double impuestoACargo) {
        this.añoFiscal = añoFiscal;
        this.avaluoCatastral = avaluoCatastral;
        this.impuestoACargo = impuestoACargo;
    }

    public String nombreNodo() {
        return "Impuesto predial "+añoFiscal;
    }

    public ArrayList<Object> hijosNodo() {
        return null;
    }

    public String toString() {
        return "{ID:'ImpuestoPredial" +
                ",añoFiscal:" + añoFiscal +
                ",avaluoCatastral:" + avaluoCatastral +
                ",impuestoACargo:" + impuestoACargo +
                "}";
    }
}
