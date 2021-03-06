package co.com.tecni.sari.lógica.clientes;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.inmuebles.fichas.transacciones.Tercero;
import co.com.tecni.sari.lógica.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.sari.ui.UiÁrbol;

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
    public String nombreNodo() {
        return nombre;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(facturas.values());
        return hijos;
    }

    public double[] getM2yValor() {
        return new double[2];
    }

    public ArrayList<Transacción>[] transaccionesNodo() {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        for (Factura fact : facturas.values())
            descendientes.addAll(fact.transaccionesNodo()[1]);

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public String infoNodo() {
        return Sari.GSON.toJson(json);
    }


}
