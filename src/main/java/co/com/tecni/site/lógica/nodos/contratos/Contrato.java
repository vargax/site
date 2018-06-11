package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.árboles.Árbol;

import java.util.ArrayList;

public class Contrato extends Nodo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int id;
    final ClienteComercial clienteComercial;

    private ArrayList<Secuencia> secuencias;

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
        clienteComercial.registrarContrato(this);
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------


    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return "Contrato: "+json.númeroContrato;
    }

    public ArrayList<Object> hijosNodo(Object padre) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(secuencias);
        return hijos;
    }

    @Override
    public String infoNodo() {
        return Site.gson.toJson(json);
    }
    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------

}
