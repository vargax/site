package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Contractual;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.Árbol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Secuencia extends Nodo {
    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int ID;
    final Contrato CONTRATO;
    final Date INICIO;
    final Date FIN;

    private ArrayList<Contractual> fichasContractuales;

    private ArrayList<ClienteFacturación> clientesFacturación;
    private HashMap<Integer, Double> participaciónClientesFacturación;

    Cánon cánon;
    public static class Cánon {
        double cánon;
        Date primerCobro;

        Incremento incremento;
        public static class Incremento {
            String base;
            double ptosAdicionales;
            int periodicidad;
            Date primerIncremento;

            public Incremento(String base, double ptosAdicionales, int periodicidad, Date primerIncremento) {
                this.base = base;
                this.ptosAdicionales = ptosAdicionales;
                this.periodicidad = periodicidad;
                this.primerIncremento = primerIncremento;
            }
        }

        public Cánon(double cánon, Date primerCobro, Incremento incremento) {
            this.cánon = cánon;
            this.primerCobro = primerCobro;
            this.incremento = incremento;
        }
    }

    ArrayList<Descuento> descuentos;
    public static class Descuento {
        double porcentajeDescuento;
        Date inicio;
        Date fin;

        public Descuento(double porcentajeDescuento, Date inicio, Date fin) {
            this.porcentajeDescuento = porcentajeDescuento;
            this.inicio = inicio;
            this.fin = fin;
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Secuencia(int ID, Date INICIO, Date FIN, Contrato CONTRATO, Cánon cánon) {
        this.ID = ID;
        this.CONTRATO = CONTRATO;
        this.INICIO = INICIO;
        this.FIN = FIN;

        this.cánon = cánon;

        descuentos = new ArrayList<>();

        fichasContractuales = new ArrayList<>();
        clientesFacturación = new ArrayList<>();
        participaciónClientesFacturación = new HashMap<>();
    }

    public void registrarDescuento(Descuento descuento) {
        descuentos.add(descuento);
    }

    public void agregarClienteFacturación(ClienteFacturación clienteFacturación, double participación) {
        clientesFacturación.add(clienteFacturación);
        participaciónClientesFacturación.put(clienteFacturación.id, participación);

    }

    public void agregarInmueble(Inmueble inmueble, double participación) {
        Contractual.Json json = new Contractual.Json(ID, CONTRATO.clienteComercial.nombre, participación);
        Contractual contractual = new Contractual(this, inmueble, json);
        fichasContractuales.add(contractual);
    }

    public void verificarParticipaciónClientesFacturación() throws Exception {
        double total = 0;

        for (Double participación : participaciónClientesFacturación.values())
            total += participación;

        if (total != 1)
            throw new Exception("Participación clientes facturación para secuencia "+ID+" es "+total);
    }

    public void verificarParticipaciónInmuebles() throws Exception {
        double total = 0;

        for (Contractual fichaContractual : fichasContractuales)
            total += fichaContractual.getParticipación();

        if (total != 1)
            throw new Exception("Participación inmuebles para secuencia "+ID+" es "+total);
    }

    // -----------------------------------------------
    // Lógica
    // -----------------------------------------------
    public void facturar() {
        HashMap<Integer, Double> valorxClienteFacturación = new HashMap<>();
        for (Map.Entry<Integer, Double> participaciónClienteFacturación : participaciónClientesFacturación.entrySet()) {
            int idClienteFacturación = participaciónClienteFacturación.getKey();
            double participación = participaciónClienteFacturación.getValue();

        }
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------

    @Override
    public String nombreNodo(Árbol árbol) {
        return "Secuencia: "+ ID;
    }

    @Override
    public ArrayList<Object> hijosNodo(Object padre) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(fichasContractuales);
        return hijos;
    }
}
