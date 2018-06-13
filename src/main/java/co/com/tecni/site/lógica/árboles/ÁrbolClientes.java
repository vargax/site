package co.com.tecni.site.lógica.árboles;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;

import java.util.ArrayList;
import java.util.HashMap;

class ÁrbolClientes extends Árbol {
    private HashMap<Integer, ClienteComercial> clientesComercialesxId;
    String nombreRaiz;

    private Raiz raiz;
    private class Raiz extends Nodo {
        public String nombreNodo(Árbol árbol) {
            return nombreRaiz;
        }

        public ArrayList<Object> hijosNodo(Árbol árbol) {
            ArrayList<Object> hijos = new ArrayList<>();
            hijos.addAll(clientesComercialesxId.values());
            return hijos;
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
