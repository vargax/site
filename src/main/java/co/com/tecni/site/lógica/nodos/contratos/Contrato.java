package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;

import java.util.ArrayList;

public class Contrato implements Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int id;
    final ClienteComercial clienteComercial;

    ArrayList<Secuencia> secuencias;

    private Json json;
    public static class Json {
        int númeroContrato;
        String openKM;

        public Json(String openKM) {
            this.openKM = openKM;
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Contrato(int id, ClienteComercial clienteComercial, Json json) {
        this.id = id;
        this.clienteComercial = clienteComercial;
        this.json = json;

        json.númeroContrato = id;

        secuencias = new ArrayList<>();
        clienteComercial.contratos.put(id,this);
    }

    // -----------------------------------------------
    // Nodo
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return "Contrato: "+json.númeroContrato;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Transacción>[] transaccionesNodo() {
        return new ArrayList[3];
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(secuencias);
        return hijos;
    }

    public String infoNodo() {
        return Site.gson.toJson(json);
    }

}
