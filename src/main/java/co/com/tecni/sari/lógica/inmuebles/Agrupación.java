package co.com.tecni.sari.lógica.inmuebles;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.árboles.Heredable;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.lógica.inmuebles.tipos.Inmueble;
import co.com.tecni.sari.lógica.árboles.Árbol;
import co.com.tecni.sari.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.ArrayList;

public class Agrupación implements Heredable {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.FOLDER_SPECIAL;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String nombre;
    private double valor;
    private double m2;
    private UiÁrbol.Ícono ícono;

    private Agrupación padre;
    private ArrayList<Agrupación> agrupaciones;
    protected ArrayList<Inmueble> inmuebles;

    private Json json;
    static class Json {
        String nombre;
        int agrupaciones;
        int inmuebles;

        Json(Agrupación agrupación) {
            nombre = agrupación.nombre;
            int[] resultado = agrupación.contador();
            agrupaciones = resultado[0];
            inmuebles = resultado[1];
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Agrupación(String nombre) {
        this.nombre = nombre;
        valor = -1.0;
        m2 = -1.0;
        ícono = new UiÁrbol.Ícono(UI_ÍCONO);

        agrupaciones = new ArrayList<>();
        inmuebles = new ArrayList<>();
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    public void agregarInmueble(Inmueble inmueble) {
        inmuebles.add(inmueble);
    }

    public void agregarAgrupación(Agrupación agrupación) {
        agrupación.padre = this;
        agrupaciones.add(agrupación);
    }

    public double getValor() {
        if (valor < 0) {
            valor = 0;
            for (Agrupación a : agrupaciones)
                valor += a.getValor();
            for (Inmueble i : inmuebles)
                valor += i.getValor();
        }
        return valor;
    }

    public double getM2() {
        if (m2 < 0) {
            m2 = 0;
            for (Agrupación a : agrupaciones)
                m2 += a.getM2();
            for (Inmueble i : inmuebles)
                m2 += i.getM2();
        }
        return m2;
    }

    // -----------------------------------------------
    // Nodo
    // -----------------------------------------------
    public String nombreNodo() {
        return nombre;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return ícono;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();

        hijos.addAll(inmuebles);
        hijos.addAll(agrupaciones);

        return hijos;
    }

    public ArrayList<Transacción>[] transaccionesNodo() {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        // DESCENDIENTES
        for (Agrupación agrupación : agrupaciones)
            descendientes.addAll(agrupación.transaccionesNodo()[2]);

        for (Inmueble inmueble : inmuebles) {
            ArrayList<Transacción>[] transaccionesInmueble = inmueble.transaccionesNodo();
            descendientes.addAll(transaccionesInmueble[1]);
            descendientes.addAll(transaccionesInmueble[2]);
            // Un inmueble asociado a una agrupación su ancestro es la agrupación, la cual no tiene transacciones
        }

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public String infoNodo() {
        // El Json sólo se puede generar una vez la agrupación tenga asociados todos sus inmuebles
        if (json == null)
            json = new Json(this);

        return Sari.GSON.toJson(json);
    }

    /**
     * Calcula recursivamente el total de agrupaciones e inmuebles
     * @return [0] = #agrupaciones / [1] = #inmuebles
     */
    private int[] contador() {
        int agrupaciones = this.agrupaciones.size();
        int inmuebles = this.inmuebles.size();

        for (Agrupación agrupación : this.agrupaciones) {
            int[] resultado = agrupación.contador();
            agrupaciones += resultado[0];
            inmuebles += resultado[1];
        }

        int[] resultado = {agrupaciones, inmuebles};
        return resultado;
    }
}
