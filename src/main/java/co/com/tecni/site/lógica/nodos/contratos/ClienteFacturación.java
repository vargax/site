package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Tercero;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Movimiento;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ClienteFacturación extends Tercero implements Nodo {

    static HashMap<Integer, ClienteFacturación> clientesFacturación = new HashMap<>();

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final ClienteComercial clienteComercial;
    final LinkedHashMap<Integer, Factura> facturas;

    private Json json;
    static class Json {
        String nombre;
        int nit;
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteFacturación(ClienteComercial clienteComercial, int nit, String nombre) {
        super(nit, nombre);

        this.clienteComercial = clienteComercial;
        facturas = new LinkedHashMap<>();

        json = new Json();
        json.nit = super.nit;
        json.nombre = super.nombre;

        clienteComercial.clientesFacturación.put(nit, this);

        clientesFacturación.put(super.nit, this);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return nombre;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(facturas.values());
        return hijos;
    }

    public ArrayList<Movimiento>[] movimientosNodo(Árbol árbol) {
        ArrayList<Movimiento> descendientes = new ArrayList<>();
        ArrayList<Movimiento> propias = new ArrayList<>();
        ArrayList<Movimiento> ancestros = new ArrayList<>();

        for (Factura fact : facturas.values())
            descendientes.addAll(fact.movimientosNodo(árbol)[1]);

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public String infoNodo(Árbol árbol) {
        return Site.GSON.toJson(json);
    }


}
