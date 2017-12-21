package co.com.tecni.site.lógica.nodos.inmueble.fichas.catastral;

import co.com.tecni.site.lógica.nodos.inmueble.fichas._Ficha;

import java.util.ArrayList;

public class Catastral extends _Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String chip;
    private String cedulaCatastral;

    private String nomenclatura;

    private double m2construccion;
    private double m2terreno;

    private ArrayList<ImpuestoPredial> impuestosPrediales;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Catastral(String chip, String cedulaCatastral, String nomenclatura, double m2construccion, double m2terreno) {
        this.chip = chip;
        this.cedulaCatastral = cedulaCatastral;
        this.nomenclatura = nomenclatura;
        this.m2construccion = m2construccion;
        this.m2terreno = m2terreno;

        this.impuestosPrediales = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public void registrarPredial(ImpuestoPredial impuestoPredial) {
        impuestosPrediales.add(impuestoPredial);
    }

    // -----------------------------------------------
    // Métodos nodo
    // -----------------------------------------------
    public String nombreNodo() {
        return "Cédula catastral: " + cedulaCatastral;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(this.impuestosPrediales);
        return hijos;
    }

    // -----------------------------------------------
    // Métodos object
    // -----------------------------------------------
    public String toString() {
        return "{ID:'Catastral'" +
                ",chip:'" + chip + "'" +
                ",cedulaCatastral:'" + cedulaCatastral + "'" +
                ",nomenclatura:'" + nomenclatura + "'" +
                ",m2construccion:" + m2construccion +
                ",m2terreno:" + m2terreno +
                ",impuestosPrediales:" + impuestosPrediales +
                '}';
    }
}