package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.HashMap;

public class Contrato implements Nodo {

    static HashMap<Integer, Contrato> contratos = new HashMap<>();

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int ID;
    final ClienteComercial CLIENTE_COMERCIAL;

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
    public Contrato(int ID, ClienteComercial CLIENTE_COMERCIAL, Json json) {
        this.ID = ID;
        this.CLIENTE_COMERCIAL = CLIENTE_COMERCIAL;
        this.json = json;

        json.númeroContrato = ID;

        secuencias = new ArrayList<>();
        CLIENTE_COMERCIAL.contratos.put(ID,this);

        contratos.put(this.ID, this);
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
