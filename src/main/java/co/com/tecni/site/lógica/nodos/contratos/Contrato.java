package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Contrato extends Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int numContrato;

    private ClienteComercial clienteComercial;
    private ArrayList<ClienteFacturación> clientesFacturación;
    private ArrayList<Inmueble> inmuebles;

    private HashMap<Integer, Double> participaciónClientesFacturación;
    private HashMap<String, Double> participaciónInmuebles;

    private JSONObject json;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Contrato(int numContrato, ClienteComercial clienteComercial) {
        this.numContrato = numContrato;
        this.clienteComercial = clienteComercial;

        clientesFacturación = new ArrayList<>();
        inmuebles = new ArrayList<>();

        participaciónClientesFacturación = new HashMap<>();
        participaciónInmuebles = new HashMap<>();

        clienteComercial.registrarContrato(this);
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public void agregarClienteFacturación(ClienteFacturación clienteFacturación, double subtotal) {
        clientesFacturación.add(clienteFacturación);
        participaciónClientesFacturación.put(clienteFacturación.getNit(), subtotal);
        clienteFacturación.registrarContrato(this);
    }

    public void agregarInmueble(Inmueble inmueble, double participación) {
        inmuebles.add(inmueble);
        participaciónInmuebles.put(inmueble.genId(), participación);
        inmueble.asociarContrato(this);
    }

    public double sumaParicipaciones() {
        double total = 0;

        for (Double participación : participaciónInmuebles.values())
            total += participación;

        return total;
    }

    public int getNumContrato() {
        return numContrato;
    }

    public ClienteComercial getClienteComercial() {
        return clienteComercial;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return "Contrato "+numContrato;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(clientesFacturación);
        return hijos;
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
}
