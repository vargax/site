package co.com.tecni.site.lógica.inmueble.fichas;

import co.com.tecni.site.lógica.inmueble.Ficha;

import java.util.ArrayList;

/**
 * Created by cvargasc on 17/08/17.
 */
public class Catastral implements Ficha {
    private int chip;
    private String cedulaCatastral;
    private String nomenclatura;

    private float m2construccion;
    private float m2terreno;

    private ArrayList<ImpuestoPredial> impuestosPrediales;

    private class ImpuestoPredial {
        private int añoFiscal;
        private float avaluoCatastral;
        private float impuestoACargo;
    }
}