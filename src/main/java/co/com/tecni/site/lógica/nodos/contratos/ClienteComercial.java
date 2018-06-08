package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.Cartera;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.Árbol;
import co.com.tecni.site.lógica.ÁrbolContratos;

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
    public String nombreNodo(Árbol árbol) {
        return nombre;
    }

    public ArrayList<Object> hijosNodo(Object padre) {
        if (padre instanceof ÁrbolContratos)
            return new ArrayList<>(contratos.values());
        else if (padre instanceof Cartera)
            return new ArrayList<>(clientesFacturación.values());

        return null;
    }
}
