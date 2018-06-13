package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.árboles.Árbol;

import java.util.ArrayList;
import java.util.HashMap;

public class ClienteFacturación extends Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int id;
    final ClienteComercial clienteComercial;

    private final HashMap<Integer, Factura> facturas;

    String nombre;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteFacturación(ClienteComercial clienteComercial, int id, String nombre) {
        this.clienteComercial = clienteComercial;
        this.id = id;
        this.nombre = nombre;

        facturas = new HashMap<>();

        clienteComercial.clientesFacturación.put(id, this);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return nombre;
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(facturas.values());
        return hijos;
    }
}
