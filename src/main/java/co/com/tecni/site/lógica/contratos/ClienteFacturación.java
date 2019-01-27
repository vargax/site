package co.com.tecni.site.lógica.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.árboles.Nodo;
import co.com.tecni.site.lógica.transacciones.Tercero;
import co.com.tecni.site.lógica.transacciones.Transacción;
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

    public ArrayList<Transacción>[] transaccionesNodo(Árbol árbol) {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        for (Factura fact : facturas.values())
            descendientes.addAll(fact.transaccionesNodo(árbol)[1]);

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
