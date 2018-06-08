package co.com.tecni.site.lógica;

import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contratos.Contrato;

import java.util.HashMap;

public class Clientes {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private static Clientes instance;

    private HashMap<Integer, ClienteComercial> clientesComercialesxId;
    private HashMap<Integer, ClienteFacturación> clientesFacturaciónxId;
    private HashMap<Integer, Contrato> contratosxId;

    private ÁrbolContratos árbolContratos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    private Clientes() {

        clientesComercialesxId = new HashMap<>();
        clientesFacturaciónxId = new HashMap<>();
        contratosxId = new HashMap<>();

        árbolContratos = new ÁrbolContratos(clientesComercialesxId.values());
    }

    public static Clientes getInstance() {
        if (instance == null)
            instance = new Clientes();
        return instance;
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public HashMap<Integer, ClienteComercial> getClientesComercialesxId() {
        return clientesComercialesxId;
    }

    public HashMap<Integer, ClienteFacturación> getClientesFacturaciónxId() {
        return clientesFacturaciónxId;
    }

    public HashMap<Integer, Contrato> getContratosxId() {
        return contratosxId;
    }

    public ÁrbolContratos getÁrbolContratos() {
        return árbolContratos;
    }
}