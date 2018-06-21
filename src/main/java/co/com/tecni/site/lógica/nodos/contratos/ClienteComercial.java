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
        return new ArrayList[3];
    }

    public String infoNodo() {
        return null;
    }
}
