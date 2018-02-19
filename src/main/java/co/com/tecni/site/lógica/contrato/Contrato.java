package co.com.tecni.site.lógica.contrato;

import co.com.tecni.site.lógica.nodos.inmueble.tipos.Inmueble;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Contrato {

    private int numContrato;
    private ClienteComercial clienteComercial;

    private HashMap<Integer, Double> clientesFacturación;
    private HashMap<String, Double> inmuebles;

    private JSONObject json;

    public Contrato(int numContrato, ClienteComercial clienteComercial) {
        this.numContrato = numContrato;
        this.clienteComercial = clienteComercial;

        clientesFacturación = new HashMap<>();
        inmuebles = new HashMap<>();

        clienteComercial.registrarContrato(this);
    }

    public void agregarClienteFacturación(ClienteFacturación clienteFacturación, double subtotal) {
        clientesFacturación.put(clienteFacturación.getNit(), subtotal);
        clienteFacturación.registrarContrato(this);
    }

    public void agregarInmueble(Inmueble inmueble, double participación) {
        inmueble.asociarContrato(this);
        inmuebles.put(inmueble.genId(), participación);
    }

    public double sumaParicipaciones() {
        double total = 0;

        for (Double participación : inmuebles.values())
            total += participación;

        return total;
    }

    public int getNumContrato() {
        return numContrato;
    }
}
