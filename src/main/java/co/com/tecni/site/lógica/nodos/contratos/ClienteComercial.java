package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;

import java.util.ArrayList;
import java.util.HashMap;

public class ClienteComercial extends Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int id;
    final String nombre;

    private final HashMap<Integer, ClienteFacturación> clientesFacturación;
    private final HashMap<Integer, Contrato> contratos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteComercial(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;

        clientesFacturación = new HashMap<>();
        contratos = new HashMap<>();
    }

    void registrarClienteFacturación(ClienteFacturación clienteFacturación) {
        clientesFacturación.put(clienteFacturación.id, clienteFacturación);
    }

    void registrarContrato(Contrato contrato) {
        contratos.put(contrato.id, contrato);
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return nombre;
    }

    public ArrayList<Object> hijosNodo(Object padre) {
        if (padre instanceof ÁrbolContratos)
            return new ArrayList<>(contratos.values());
        else if (padre instanceof ÁrbolCartera)
            return new ArrayList<>(clientesFacturación.values());

        return null;
    }
}
