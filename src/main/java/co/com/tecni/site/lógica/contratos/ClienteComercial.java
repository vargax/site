package co.com.tecni.site.lógica.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.árboles.Nodo;
import co.com.tecni.site.lógica.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.ui.UiÁrbol;

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
                secuencias += contrato.secuencias.size();
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
            ArrayList<Secuencia> secuencias = contrato.secuencias;
            (secuencias.get(secuencias.size()-1)).facturar(fechaCorte);
        }
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return nombre;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        if (árbol instanceof ÁrbolContratos)
            return new ArrayList<>(contratos.values());
        else if (árbol instanceof ÁrbolCartera)
            return new ArrayList<>(clientesFacturación.values());

        return null;
    }

    public ArrayList<Transacción>[] transaccionesNodo(Árbol árbol) {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        if (árbol instanceof ÁrbolCartera)
            for (ClienteFacturación clFact : clientesFacturación.values())
                descendientes.addAll(clFact.transaccionesNodo(árbol)[2]);

        // Se suman las transacciones asociadas a todos los contratos
        /* ToDo NO coinciden con los valores presentados en ÁrbolInmuebles
        *  |> Sólo se incluyen las transacciones de los inmuebles asociados a secuencias
        *  |> Siempre debería coincidir el valor de los ingresos por arrendamiento reales
        */
        if (árbol instanceof ÁrbolContratos)
            for (Contrato contrato : contratos.values()) {
                descendientes.addAll(contrato.transaccionesNodo(árbol)[2]);
                propias.addAll(contrato.transaccionesNodo(árbol)[1]);
                ancestros.addAll(contrato.transaccionesNodo(árbol)[0]);
            }


        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public String infoNodo(Árbol árbol) {
        if (json == null)
            json = new Json(this);

        return Site.GSON.toJson(json);
    }
}
