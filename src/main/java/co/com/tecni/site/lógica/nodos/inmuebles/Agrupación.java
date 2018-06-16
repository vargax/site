package co.com.tecni.site.lógica.nodos.inmuebles;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
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
            agrupaciones = agrupación.agrupaciones.size();
            inmuebles = agrupación.inmuebles.size();
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

    public ArrayList<Transacción>[] transaccionesNodo() {
        ArrayList[] transaccionesNodo = new ArrayList[3];


        return transaccionesNodo;
    }

    public String infoNodo() {
        if (json == null) {
            json = new Json(this);
        }
        return Site.gson.toJson(json);
    }
}
