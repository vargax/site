package co.com.tecni.site.lógica.árboles;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.HashMap;

abstract class ÁrbolClientes extends Árbol {
    private HashMap<Integer, ClienteComercial> clientesComercialesxId;
    String nombreRaiz;

    private Raiz raiz;
    private class Raiz implements Nodo {
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

        public ArrayList<Transacción>[] transaccionesNodo() {
            return new ArrayList[0];
        }

        public String infoNodo() {
            return null;
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
