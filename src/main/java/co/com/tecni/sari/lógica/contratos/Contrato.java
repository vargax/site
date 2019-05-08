package co.com.tecni.sari.lógica.contratos;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.HashMap;

public class Contrato implements Nodo {

    static HashMap<Integer, Contrato> contratos = new HashMap<>();

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    final int ID;
    final ClienteComercial CLIENTE_COMERCIAL;

    ArrayList<Versión> versiones;

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

        versiones = new ArrayList<>();
        CLIENTE_COMERCIAL.contratos.put(ID,this);

        contratos.put(this.ID, this);
    }

    // -----------------------------------------------
    // Nodo
    // -----------------------------------------------
    public String nombreNodo() {
        return "Contrato: "+json.númeroContrato;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Transacción>[] transaccionesNodo() {
        ArrayList<Transacción> descendientes = new ArrayList<>();
        ArrayList<Transacción> propias = new ArrayList<>();
        ArrayList<Transacción> ancestros = new ArrayList<>();

        // Se suman las transacciones asociadas a todas las versiones
        for (Versión versión : versiones) {
            descendientes.addAll(versión.transaccionesNodo()[2]);
            propias.addAll(versión.transaccionesNodo()[1]);
            ancestros.addAll(versión.transaccionesNodo()[0]);
        }

        ArrayList[] resultado = new ArrayList[3];
        resultado[2] = descendientes;
        resultado[1] = propias;
        resultado[0] = ancestros;
        return resultado;
    }

    /**
     * Los M2 y valor de un Contrato son los de su última versión
     */
    public double[] getM2yValor() {
        return (versiones.get(versiones.size()-1)).getM2yValor();
    }

    public ArrayList<Object> hijosNodo() {
        ArrayList<Object> hijos = new ArrayList<>();
        hijos.addAll(versiones);
        return hijos;
    }

    public String infoNodo() {
        return Sari.GSON.toJson(json);
    }

}
