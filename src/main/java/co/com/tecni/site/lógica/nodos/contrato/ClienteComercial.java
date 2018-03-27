package co.com.tecni.site.lógica.nodos.contrato;

import co.com.tecni.site.lógica.nodos.Nodo;

import java.util.ArrayList;
import java.util.HashMap;

public class ClienteComercial extends Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int id;
    private String nombre;

    private HashMap<Integer, ClienteFacturación> clientesFacturación;
    private HashMap<Integer, Contrato> contratos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteComercial(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;

        clientesFacturación = new HashMap<>();
        contratos = new HashMap<>();
    }

    protected void registrarClienteFacturación(ClienteFacturación clienteFacturación) {
        clientesFacturación.put(clienteFacturación.getNit(), clienteFacturación);
    }

    protected void registrarContrato(Contrato contrato) {
        contratos.put(contrato.getNumContrato(), contrato);
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------


    public String getNombre() {
        return nombre;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return nombre;
    }

    public ArrayList<Object> hijosNodo() {
        return new ArrayList<>(contratos.values());
    }
}
