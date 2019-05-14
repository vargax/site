package co.com.tecni.sari.lógica.clientes;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.inmuebles.fichas.Arrendamiento;
import co.com.tecni.sari.lógica.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.sari.lógica.inmuebles.Inmueble;
import co.com.tecni.sari.ui.UiÁrbol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Versión implements Nodo {
    static HashMap<Integer, Versión> secuencias = new HashMap<>();

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int ID;
    final Contrato CONTRATO;
    final LocalDate INICIO;
    final LocalDate FIN;

    private double[] m2yValor = {-1.0, -1.0};
    private ArrayList<Arrendamiento> fichasArrendamiento;

    private HashMap<Integer, ClienteFacturación> clientesFacturación;
    private HashMap<Integer, Double> participaciónClientesFacturación;

    private Cánon cánon;
    public static class Cánon {
        double cánon;
        LocalDate primerCobro;

        Incremento incremento;
        public static class Incremento {
            String base;
            double ptosAdicionales;
            int periodicidad;
            LocalDate primerIncremento;

            public Incremento(String base, double ptosAdicionales, int periodicidad, LocalDate primerIncremento) {
                this.base = base;
                this.ptosAdicionales = ptosAdicionales;
                this.periodicidad = periodicidad;
                this.primerIncremento = primerIncremento;
            }
        }

        public Cánon(double cánon, LocalDate primerCobro, Incremento incremento) {
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
        LocalDate fechaInicio;
        LocalDate fechaFin;
        Cánon cánon;
        Map<String, Double> clientesFacturación;

        Json(Versión versión) {
            this.número = versión.ID;
            this.fechaInicio = versión.INICIO;
            this.fechaFin = versión.FIN;
            this.cánon = versión.cánon;
        }

        void genInfoClientesFacturación(Versión versión) {
            clientesFacturación = new HashMap<>();
            for (Map.Entry<Integer, Double> me : versión.participaciónClientesFacturación.entrySet()) {

                int nit = me.getKey();
                String nombre = versión.clientesFacturación.get(nit).nombre;
                double participación = me.getValue();

                clientesFacturación.put(nombre, participación);
            }
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Versión(int ID, LocalDate INICIO, LocalDate FIN, Contrato CONTRATO, Cánon cánon) {
        this.ID = ID;
        this.CONTRATO = CONTRATO;
        this.INICIO = INICIO;
        this.FIN = FIN;

        this.cánon = cánon;

        descuentos = new ArrayList<>();

        fichasArrendamiento = new ArrayList<>();
        clientesFacturación = new HashMap<>();
        participaciónClientesFacturación = new HashMap<>();

        CONTRATO.versiones.add(this);

        json = new Json(this);

        secuencias.put(this.ID, this);
    }

    public void registrarDescuento(Descuento descuento) {
        descuentos.add(descuento);
    }

    public void agregarClienteFacturación(ClienteFacturación clienteFacturación, double participación) {
        clientesFacturación.put(clienteFacturación.nit, clienteFacturación);
        participaciónClientesFacturación.put(clienteFacturación.nit, participación);

    }

    public void agregarInmueble(Inmueble inmueble, double participación) {
        Arrendamiento.Json json = new Arrendamiento.Json(ID, CONTRATO.CLIENTE_COMERCIAL.nombre, participación);
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
    private void calcularValoryM2() {
        this.m2yValor[0] = 0.0;
        this.m2yValor[1] = 0.0;

        for (Object o : hijosNodo()) {
            Nodo n = (Nodo) o;
            double[] m2yValor = n.getM2yValor();
            this.m2yValor[0] += m2yValor[0];
            this.m2yValor[1] += m2yValor[1];
        }
    }

    void facturar(LocalDate fechaCorte) {

        if (fechaCorte.isAfter(FIN))
            System.err.println("Versión "+json.número+".facturar() / Imposible facturar "
                    + Sari.DTF.format(fechaCorte) + " / Fecha fin es " + Sari.DTF.format(FIN));

        else {
            for (Map.Entry<Integer, Double> me : participaciónClientesFacturación.entrySet()) {

                ClienteFacturación cf = clientesFacturación.get(me.getKey());
                double participación = me.getValue();

                ArrayList<Transacción> transacciones = new ArrayList<>();
                for (Arrendamiento arrendamiento : fichasArrendamiento) {
                    double valor = this.cánon.cánon * participación;
                    transacciones.add(arrendamiento.facturar(cf, fechaCorte, valor));
                }

                Factura f = new Factura(cf, fechaCorte, transacciones);
                cf.facturas.put(f.json.consecutivo, f);

            }
        }
    }

    // -----------------------------------------------
    // Nodo
    // -----------------------------------------------
    public String nombreNodo() {
        return "Versión: "+ ID;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(fichasArrendamiento);
        return hijos;
    }

    public double[] getM2yValor() {
        if (this.m2yValor[0] < 0 && this.m2yValor[1] < 0) calcularValoryM2();
        return m2yValor;
    }

    public ArrayList<Transacción>[] transaccionesNodo() {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        // Se suman las transacciones asociadas a todos los inmuebles
        for (Arrendamiento arrendamiento : fichasArrendamiento) {
            descendientes.addAll(arrendamiento.transaccionesNodo()[2]);
            propias.addAll(arrendamiento.transaccionesNodo()[1]);
            ancestros.addAll(arrendamiento.transaccionesNodo()[0]);
        }

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public String infoNodo() {
        return Sari.GSON.toJson(json);
    }
}
