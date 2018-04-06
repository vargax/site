package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.nodos.Nodo;

import java.util.ArrayList;
import java.util.HashMap;

public class ClienteFacturación extends Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int nit;
    private String nombre;

    private ClienteComercial clienteComercial;
    private HashMap<Integer, Contrato> contratos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteFacturación(ClienteComercial clienteComercial, int nit, String nombre) {
        this.clienteComercial = clienteComercial;
        this.nit = nit;
        this.nombre = nombre;

        contratos = new HashMap<>();
        this.clienteComercial.registrarClienteFacturación(this);
    }

    protected void registrarContrato(Contrato contrato) {
        contratos.put(contrato.getNumContrato(), contrato);
    }

    public int getNit() {
        return nit;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return nombre;
    }

    public ArrayList<Object> hijosNodo() {
        return null;
    }
}
