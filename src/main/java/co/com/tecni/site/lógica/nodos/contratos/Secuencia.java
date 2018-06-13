package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Arrendamiento;
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

    private ArrayList<Arrendamiento> fichasArrendamiento;

    private HashMap<Integer, ClienteFacturación> clientesFacturación;
    private HashMap<Integer, Double> participaciónClientesFacturación;

    private Cánon cánon;
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

    private ArrayList<Descuento> descuentos;
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

    private Json json;
    static class Json {
        int número;
        Date fechaInicio;
        Date fechaFin;
        Cánon cánon;
        Map<String, Double> clientesFacturación;

        Json(Secuencia secuencia) {
            this.número = secuencia.ID;
            this.fechaInicio = secuencia.INICIO;
            this.fechaFin = secuencia.FIN;
            this.cánon = secuencia.cánon;
        }

        void genInfoClientesFacturación(Secuencia secuencia) {
            clientesFacturación = new HashMap<>();
            for (Map.Entry<Integer, Double> me : secuencia.participaciónClientesFacturación.entrySet()) {
                String cf = secuencia.clientesFacturación.get(me.getKey()).nombre;
                double participación = me.getValue();
                clientesFacturación.put(cf, participación);
            }
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

        fichasArrendamiento = new ArrayList<>();
        clientesFacturación = new HashMap<>();
        participaciónClientesFacturación = new HashMap<>();

        CONTRATO.secuencias.add(this);

        json = new Json(this);
    }

    public void registrarDescuento(Descuento descuento) {
        descuentos.add(descuento);
    }

    public void agregarClienteFacturación(ClienteFacturación clienteFacturación, double participación) {
        clientesFacturación.put(clienteFacturación.id, clienteFacturación);
        participaciónClientesFacturación.put(clienteFacturación.id, participación);

    }

    public void agregarInmueble(Inmueble inmueble, double participación) {
        Arrendamiento.Json json = new Arrendamiento.Json(ID, CONTRATO.clienteComercial.nombre, participación);
        Arrendamiento arrendamiento = new Arrendamiento(this, inmueble, json);
        fichasArrendamiento.add(arrendamiento);
    }

    public void verificarParticipaciónClientesFacturación() throws Exception {
        double total = 0;

        for (Double participación : participaciónClientesFacturación.values())
            total += participación;

        if (total != 1)
            throw new Exception("Participación clientes facturación para secuencia "+ID+" es "+total);

        json.genInfoClientesFacturación(this);
    }

    public void verificarParticipaciónInmuebles() throws Exception {
        double total = 0;

        for (Arrendamiento fichaArrendamiento : fichasArrendamiento)
            total += fichaArrendamiento.getParticipación();

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
    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(fichasArrendamiento);
        return hijos;
    }

    @Override
    public String infoNodo() {
        return Site.gson.toJson(json);
    }
}
