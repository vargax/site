package co.com.tecni.site.lógica;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.contrato.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contrato.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contrato.Contrato;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ÁrbolClientes extends Árbol {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private RaizClientes raiz;

    private HashMap<Integer, ClienteComercial> clientesComerciales;
    private HashMap<Integer, ClienteFacturación> clientesFacturación;
    private HashMap<Integer, Contrato> contratos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ÁrbolClientes() {

        clientesComerciales = new HashMap<>();
        clientesFacturación = new HashMap<>();
        contratos = new HashMap<>();

        raiz = new RaizClientes(clientesComerciales.values());
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public HashMap<Integer, ClienteComercial> getClientesComerciales() {
        return clientesComerciales;
    }

    public HashMap<Integer, ClienteFacturación> getClientesFacturación() {
        return clientesFacturación;
    }

    public HashMap<Integer, Contrato> getContratos() {
        return contratos;
    }

    // -----------------------------------------------
    // Métodos Árbol
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }
}

class RaizClientes extends Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String NOMBRE_RAIZ = "CLIENTES";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Collection<ClienteComercial> clientesComerciales;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public RaizClientes(Collection<ClienteComercial> clientesComerciales) {
        this.clientesComerciales = clientesComerciales;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return NOMBRE_RAIZ;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(clientesComerciales);
        return hijos;
    }
}
