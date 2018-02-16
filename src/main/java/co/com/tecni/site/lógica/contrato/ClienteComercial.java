package co.com.tecni.site.lógica.contrato;

import java.util.ArrayList;

public class ClienteComercial {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int id;
    private String nombre;

    private ArrayList<ClienteFacturación> clientesFacturación;
    private ArrayList<Contrato> contratos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteComercial(int id, String Nombre) {
        this.id = id;
        this.nombre = nombre;

        clientesFacturación = new ArrayList<>();
        contratos = new ArrayList<>();
    }
}
