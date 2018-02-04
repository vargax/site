package co.com.tecni.site.lógica.nodos.inmueble.fichas.catastral;

import co.com.tecni.site.lógica.nodos.inmueble.fichas._Ficha;

import java.util.ArrayList;

public class Catastral extends _Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String[] JSON_KEYS = {"Chip", "Cédula Catastral",
            "Nomenclatura", "M2 construcción", "M2 terreno"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String chip;
    private String cedulaCatastral;

    private String nomenclatura;

    private double m2construcción;
    private double m2terreno;

    private ArrayList<ImpuestoPredial> impuestosPrediales;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Catastral(String chip, String cedulaCatastral, String nomenclatura, double m2construcción, double m2terreno) {
        this.chip = chip;
        infoNodo.put(JSON_KEYS[0], chip);

        this.cedulaCatastral = cedulaCatastral;
        infoNodo.put(JSON_KEYS[1], cedulaCatastral);

        this.nomenclatura = nomenclatura;
        infoNodo.put(JSON_KEYS[2], nomenclatura);

        this.m2construcción = m2construcción;
        infoNodo.put(JSON_KEYS[3], m2construcción);

        this.m2terreno = m2terreno;
        infoNodo.put(JSON_KEYS[4], m2terreno);

        this.impuestosPrediales = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public void registrarPredial(ImpuestoPredial impuestoPredial) {
        impuestosPrediales.add(impuestoPredial);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return "Cédula catastral: " + cedulaCatastral;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(this.impuestosPrediales);
        return hijos;
    }
}