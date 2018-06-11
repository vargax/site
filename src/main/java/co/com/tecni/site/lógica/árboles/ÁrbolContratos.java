package co.com.tecni.site.lógica.árboles;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contratos.Contrato;
import co.com.tecni.site.lógica.nodos.contratos.Secuencia;

import java.util.ArrayList;
import java.util.HashMap;

public class ÁrbolContratos extends Árbol {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String NOMBRE_RAIZ = "CONTRATOS";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    HashMap<Integer, ClienteComercial> clientesComercialesxId;
    HashMap<Integer, ClienteFacturación> clientesFacturaciónxId;
    HashMap<Integer, Contrato> contratosxId;
    HashMap<Integer, Secuencia> secuenciasxId;

    private RaizContratos raiz;
    class RaizContratos extends Nodo {
        public String nombreNodo(Árbol árbol) {
            return NOMBRE_RAIZ;
        }

        public ArrayList<Object> hijosNodo(Object padre) {
            ArrayList<Object> hijos = new ArrayList<>();
            hijos.addAll(clientesComercialesxId.values());
            return hijos;
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------


    public ÁrbolContratos(HashMap<Integer, ClienteComercial> clientesComercialesxId, HashMap<Integer, ClienteFacturación> clientesFacturaciónxId, HashMap<Integer, Contrato> contratosxId, HashMap<Integer, Secuencia> secuenciasxId) {
        this.clientesComercialesxId = clientesComercialesxId;
        this.clientesFacturaciónxId = clientesFacturaciónxId;
        this.contratosxId = contratosxId;
        this.secuenciasxId = secuenciasxId;

        raiz = new RaizContratos();
    }

    // -----------------------------------------------
    // Métodos Árbol
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }


}
