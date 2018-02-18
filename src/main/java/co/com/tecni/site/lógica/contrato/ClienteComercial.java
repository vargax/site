package co.com.tecni.site.lógica.contrato;

import java.util.HashMap;

public class ClienteComercial {

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
    public ClienteComercial(int id, String Nombre) {
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

}
