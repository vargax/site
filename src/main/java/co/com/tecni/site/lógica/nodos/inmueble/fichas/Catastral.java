package co.com.tecni.site.lógica.nodos.inmueble.fichas;

import java.util.ArrayList;

/**
 * Created by cvargasc on 17/08/17.
 */
public class Catastral extends _Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String TIPO = "Catastral";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int chip;
    private String cedulaCatastral;
    private String nomenclatura;

    private float m2construccion;
    private float m2terreno;

    private ArrayList<ImpuestoPredial> impuestosPrediales;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Catastral() {
        super(TIPO);

    }

    // -----------------------------------------------
    // Subclases
    // -----------------------------------------------
    private class ImpuestoPredial {
        private int añoFiscal;
        private float avaluoCatastral;
        private float impuestoACargo;
    }
}