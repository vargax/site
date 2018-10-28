package co.com.tecni.site.lógica.nodos.inmuebles;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Movimiento;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.util.ArrayList;

public class Agrupación implements Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.FOLDER_SPECIAL;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String nombre;
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

    // -----------------------------------------------
    // Getters and Setters
    // -----------------------------------------------

    // -----------------------------------------------
    // Nodo
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return nombre;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return ícono;
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();

        hijos.addAll(agrupaciones);
        hijos.addAll(inmuebles);

        return hijos;
    }

    public ArrayList<Movimiento>[] movimientosNodo(Árbol árbol) {
        ArrayList<Movimiento> descendientes = new ArrayList<>();
        ArrayList<Movimiento> propias = new ArrayList<>();
        ArrayList<Movimiento> ancestros = new ArrayList<>();

        // DESCENDIENTES
        for (Agrupación agrupación : agrupaciones)
            descendientes.addAll(agrupación.movimientosNodo(árbol)[2]);

        for (Inmueble inmueble : inmuebles) {
            ArrayList<Movimiento>[] transaccionesInmueble = inmueble.movimientosNodo(árbol);
            descendientes.addAll(transaccionesInmueble[1]);
            descendientes.addAll(transaccionesInmueble[2]);
            // Un inmueble asociado a una agrupación su ancestro es la agrupación, la cual no tiene movimientos
        }

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public String infoNodo(Árbol árbol) {
        // El Json sólo se puede generar una vez la agrupación tenga asociados todos sus inmuebles
        if (json == null)
            json = new Json(this);

        return Site.GSON.toJson(json);
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
