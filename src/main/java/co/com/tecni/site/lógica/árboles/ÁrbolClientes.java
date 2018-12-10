package co.com.tecni.site.lógica.árboles;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.HashMap;

abstract class ÁrbolClientes extends Árbol {
    HashMap<Integer, ClienteComercial> clientesComercialesxId;
    String nombreRaiz;

    private Raiz raiz;
    private class Raiz implements Nodo {

        private Json json = new Json();
        class Json {
            String nombre = "Sistema de Información de Inmuebles de TECNI";
        }

        public String nombreNodo(Árbol árbol) {
            return nombreRaiz;
        }

        public ArrayList<Object> hijosNodo(Árbol árbol) {
            ArrayList<Object> hijos = new ArrayList<>();
            hijos.addAll(clientesComercialesxId.values());
            return hijos;
        }

        public UiÁrbol.Ícono íconoNodo() {
            return null;
        }

        public ArrayList<Transacción>[] transaccionesNodo(Árbol árbol) {
            ArrayList<Transacción> descendientes = new ArrayList<>();
            ArrayList<Transacción> propias = new ArrayList<>();
            ArrayList<Transacción> ancestros = new ArrayList<>();

            for (ClienteComercial cc : clientesComercialesxId.values()) {
                descendientes.addAll(cc.transaccionesNodo(árbol)[2]);
                propias.addAll(cc.transaccionesNodo(árbol)[1]);
                ancestros.addAll(cc.transaccionesNodo(árbol)[0]);
            }

            ArrayList[] resultado = new ArrayList[3];
            resultado[2] = descendientes;
            resultado[1] = propias;
            resultado[0] = ancestros;
            return resultado;
        }

        public String infoNodo(Árbol árbol) {
            return Site.GSON.toJson(json);
        }
    }

    ÁrbolClientes(HashMap<Integer, ClienteComercial> clientesComercialesxId) {
        this.clientesComercialesxId = clientesComercialesxId;
        raiz = new Raiz();
    }

    public Object getRoot() {
        return raiz;
    }
}
