package co.com.tecni.site.lógica.contrato;

import java.util.ArrayList;

public class ClienteFacturación {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int nit;
    private String nombre;

    private ClienteComercial clienteComercial;
    private ArrayList<Contrato> contratos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteFacturación(ClienteComercial clienteComercial, int nit, String nombre) {
        this.clienteComercial = clienteComercial;
        this.nit = nit;
        this.nombre = nombre;

        contratos = new ArrayList<>();
    }
}
