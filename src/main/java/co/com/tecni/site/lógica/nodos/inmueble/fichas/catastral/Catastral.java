package co.com.tecni.site.lógica.nodos.inmueble.fichas.catastral;

import co.com.tecni.site.lógica.nodos.inmueble.fichas._Ficha;

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
    private String descripcion;

    private String chip;
    private String cedulaCatastral;

    private String nomenclatura;

    private double m2construccion;
    private double m2terreno;

    private ArrayList<ImpuestoPredial> impuestosPrediales;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Catastral(String descripcion, String chip, String cedulaCatastral, String nomenclatura, double m2construccion, double m2terreno) {
        this.descripcion = descripcion;
        this.chip = chip;
        this.cedulaCatastral = cedulaCatastral;
        this.nomenclatura = nomenclatura;
        this.m2construccion = m2construccion;
        this.m2terreno = m2terreno;

        this.impuestosPrediales = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void registrarPredial(ImpuestoPredial impuestoPredial) {
        impuestosPrediales.add(impuestoPredial);
    }

    public String nombreNodo() {
        return chip;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(this.impuestosPrediales);
        return hijos;
    }

    public String toString() {
        return "Catastral{" +
                "descripcion='" + descripcion + '\'' +
                ", chip='" + chip + '\'' +
                ", cedulaCatastral='" + cedulaCatastral + '\'' +
                ", nomenclatura='" + nomenclatura + '\'' +
                ", m2construccion=" + m2construccion +
                ", m2terreno=" + m2terreno +
                ", impuestosPrediales=" + impuestosPrediales +
                '}';
    }
}