package co.com.tecni.sari.lógica.contratos;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.lógica.árboles.PerspectivaCartera;
import co.com.tecni.sari.lógica.árboles.PerspectivaClientes;
import co.com.tecni.sari.ui.UiSari;
import co.com.tecni.sari.ui.UiÁrbol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ClienteComercial implements Nodo {

    static HashMap<Integer, ClienteComercial> clientesComerciales = new HashMap<>();

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int id;
    final String nombre;

    final HashMap<Integer, ClienteFacturación> clientesFacturación;
    final HashMap<Integer, Contrato> contratos;

    private double[] m2yValor = {-1.0, -1.0};

    private Json json;
    static class Json {
        int id;
        String nombre;
        int clientesFacturación;
        int contratos;
        int secuencias;

        Json(ClienteComercial cc) {
            id = cc.id;
            nombre = cc.nombre;
            clientesFacturación = cc.clientesFacturación.size();
            contratos = cc.contratos.size();


            for (Contrato contrato : cc.contratos.values())
                secuencias += contrato.versiones.size();
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ClienteComercial(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;

        clientesFacturación = new HashMap<>();
        contratos = new HashMap<>();

        clientesComerciales.put(id, this);
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public void facturar(LocalDate fechaCorte) {
        for (Contrato contrato : contratos.values()) {
            ArrayList<Versión> versións = contrato.versiones;
            (versións.get(versións.size()-1)).facturar(fechaCorte);
        }
    }

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

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return nombre;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public double[] getM2yValor() {
        calcularValoryM2();
        return m2yValor;
    }

    public ArrayList<Object> hijosNodo() {
        if (UiSari.árbolActual instanceof PerspectivaClientes)
            return new ArrayList<>(contratos.values());
        else if (UiSari.árbolActual instanceof PerspectivaCartera)
            return new ArrayList<>(clientesFacturación.values());

        return null;
    }

    public ArrayList<Transacción>[] transaccionesNodo() {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        if (UiSari.árbolActual instanceof PerspectivaCartera)
            for (ClienteFacturación clFact : clientesFacturación.values())
                descendientes.addAll(clFact.transaccionesNodo()[2]);

        // Se suman las transacciones asociadas a todos los contratos
        /* ToDo NO coinciden con los valores presentados en PerspectivaInmuebles
        *  |> Sólo se incluyen las transacciones de los inmuebles asociados a versiones
        *  |> Siempre debería coincidir el getValor de los ingresos por arrendamiento reales
        */
        if (UiSari.árbolActual instanceof PerspectivaClientes)
            for (Contrato contrato : contratos.values()) {
                descendientes.addAll(contrato.transaccionesNodo()[2]);
                propias.addAll(contrato.transaccionesNodo()[1]);
                ancestros.addAll(contrato.transaccionesNodo()[0]);
            }


        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public String infoNodo() {
        if (json == null)
            json = new Json(this);

        return Sari.GSON.toJson(json);
    }
}
