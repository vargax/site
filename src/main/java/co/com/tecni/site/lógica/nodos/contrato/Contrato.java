package co.com.tecni.site.lógica.nodos.contrato;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.tipos.Inmueble;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Contrato extends Nodo {

    private int numContrato;
    private ClienteComercial clienteComercial;

    private HashMap<Integer, Double> clientesFacturación;
    private HashMap<String, Double> participaciónInmuebles;
    private ArrayList<Inmueble> inmuebles;


    private JSONObject json;

    public Contrato(int numContrato, ClienteComercial clienteComercial) {
        this.numContrato = numContrato;
        this.clienteComercial = clienteComercial;

        clientesFacturación = new HashMap<>();
        participaciónInmuebles = new HashMap<>();

        clienteComercial.registrarContrato(this);
    }

    public void agregarClienteFacturación(ClienteFacturación clienteFacturación, double subtotal) {
        clientesFacturación.put(clienteFacturación.getNit(), subtotal);
        clienteFacturación.registrarContrato(this);
    }

    public void agregarInmueble(Inmueble inmueble, double participación) {
        inmueble.asociarContrato(this);
        participaciónInmuebles.put(inmueble.genId(), participación);
        
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

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return ""+numContrato;
    }

    public ArrayList<Object> hijosNodo() {
        return new ArrayList<>(clientesFacturación.values());
    }
}
