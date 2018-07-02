package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.Date;
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
    public void facturar(Date fechaCorte) {
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

    public ArrayList<Transacción>[] transaccionesNodo() {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        for (ClienteFacturación clFact : clientesFacturación.values()) {
            ArrayList<Transacción>[] transClFact = clFact.transaccionesNodo();
            descendientes.addAll(transClFact[2]);
            propias.addAll(transClFact[1]);
            ancestros.addAll(transClFact[0]);
        }

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public String infoNodo() {
        return null;
    }
}
