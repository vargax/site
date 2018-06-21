package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Tercero;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.HashMap;

public class ClienteFacturación extends Tercero implements Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final ClienteComercial clienteComercial;
    final HashMap<Integer, Factura> facturas;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteFacturación(ClienteComercial clienteComercial, int nit, String nombre) {
        super(nit, nombre);

        this.clienteComercial = clienteComercial;

        facturas = new HashMap<>();

        clienteComercial.clientesFacturación.put(nit, this);
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

    public ArrayList<Transacción>[] transaccionesNodo() {
        return new ArrayList[3];
    }

    public String infoNodo() {
        return null;
    }


}
