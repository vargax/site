package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Contractual;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.Árbol;

import java.util.ArrayList;
import java.util.HashMap;

public class Contrato extends Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int numContrato;

    private ClienteComercial clienteComercial;
    private ArrayList<Contractual> fichasContractuales;

    private ArrayList<ClienteFacturación> clientesFacturación;
    private HashMap<Integer, Double> participaciónClientesFacturación;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Contrato(int numContrato, ClienteComercial clienteComercial) {
        this.numContrato = numContrato;
        this.clienteComercial = clienteComercial;

        fichasContractuales = new ArrayList<>();

        clientesFacturación = new ArrayList<>();
        participaciónClientesFacturación = new HashMap<>();

        clienteComercial.registrarContrato(this);
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public void agregarClienteFacturación(ClienteFacturación clienteFacturación, double participación) {
        clientesFacturación.add(clienteFacturación);
        participaciónClientesFacturación.put(clienteFacturación.getNit(), participación);
        clienteFacturación.registrarContrato(this);
    }

    public double participaciónClientesFacturación() {
        double total = 0;

        for (Double participación : participaciónClientesFacturación.values())
            total += participación;

        return total;
    }

    public void agregarInmueble(Inmueble inmueble, double participación) {

        fichasContractuales.add(new Contractual(this,  inmueble, new Contractual.Json(participación)));

    }

    public double participaciónInmuebles() {
        double total = 0;

        for (Contractual fichaContractual : fichasContractuales)
            total += fichaContractual.getParticipación();

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
    public String nombreNodo(Árbol árbol) {
        return "Contrato "+numContrato;
    }

    public ArrayList<Object> hijosNodo(Object padre) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(fichasContractuales);
        return hijos;
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------

}
