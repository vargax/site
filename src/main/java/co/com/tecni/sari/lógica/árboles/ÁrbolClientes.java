package co.com.tecni.sari.lógica.árboles;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.contratos.ClienteComercial;
import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.HashMap;

abstract class ÁrbolClientes extends Árbol {
    HashMap<Integer, ClienteComercial> clientesComercialesxId;
    String nombreRaiz;

    private Raiz raiz;
    private class Raiz implements Nodo {

        private double[] m2yValor = {-1.0, -1.0};

        private Json json = new Json();
        class Json {
            String nombre = "Sistema de Administración de Recursos Inmobiliarios SARI";
        }

        private void calcularValoryM2() {
            this.m2yValor[0] = 0.0;
            this.m2yValor[1] = 0.0;

            for (Object o : hijosNodo()) {
                Nodo n = (Nodo) o;
                double[] m2yValor = n.getM2yValor();
                this.m2yValor[0] += m2yValor[0];
                this.m2yValor[1] += m2yValor[1];
            }
        }

        public String nombreNodo() {
            return nombreRaiz;
        }

        public ArrayList<Object> hijosNodo() {
            ArrayList<Object> hijos = new ArrayList<>();
            hijos.addAll(clientesComercialesxId.values());
            return hijos;
        }

        public UiÁrbol.Ícono íconoNodo() {
            return null;
        }

        public double[] getM2yValor() {
            if (this.m2yValor[0] < 0 && this.m2yValor[1] < 0) calcularValoryM2();
            return m2yValor;
        }

        public ArrayList<Transacción>[] transaccionesNodo() {
            ArrayList<Transacción> descendientes = new ArrayList<>();
            ArrayList<Transacción> propias = new ArrayList<>();
            ArrayList<Transacción> ancestros = new ArrayList<>();

            for (ClienteComercial cc : clientesComercialesxId.values()) {
                descendientes.addAll(cc.transaccionesNodo()[2]);
                propias.addAll(cc.transaccionesNodo()[1]);
                ancestros.addAll(cc.transaccionesNodo()[0]);
            }

            ArrayList[] resultado = new ArrayList[3];
            resultado[2] = descendientes;
            resultado[1] = propias;
            resultado[0] = ancestros;
            return resultado;
        }

        public String infoNodo() {
            return Sari.GSON.toJson(json);
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
