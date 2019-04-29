package co.com.tecni.site.lógica.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.árboles.Nodo;
import co.com.tecni.site.lógica.transacciones.Transacción;
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

    ArrayList<Versión> versións;

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

        versións = new ArrayList<>();
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

    public ArrayList<Transacción>[] transaccionesNodo(Árbol árbol) {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        // Se suman las transacciones asociadas a todas las versións
        for (Versión versión : versións) {
            descendientes.addAll(versión.transaccionesNodo(árbol)[2]);
            propias.addAll(versión.transaccionesNodo(árbol)[1]);
            ancestros.addAll(versión.transaccionesNodo(árbol)[0]);
        }

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(versións);
        return hijos;
    }

    public String infoNodo(Árbol árbol) {
        return Site.GSON.toJson(json);
    }

}
