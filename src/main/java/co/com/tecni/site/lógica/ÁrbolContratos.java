package co.com.tecni.site.lógica;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;

import java.util.ArrayList;
import java.util.Collection;

public class ÁrbolContratos extends Árbol {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String NOMBRE_RAIZ = "CONTRATOS";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Collection<ClienteComercial> clientesComerciales;
    private RaizContratos raiz;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ÁrbolContratos(Collection<ClienteComercial> clientesComerciales) {
        this.clientesComerciales = clientesComerciales;
        raiz = new RaizContratos();
    }

    // -----------------------------------------------
    // Métodos Árbol
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }

    class RaizContratos extends Nodo {
        public String nombreNodo(Árbol árbol) {
            return NOMBRE_RAIZ;
        }

        public ArrayList<Object> hijosNodo(Object padre) {
            ArrayList<Object> hijos = new ArrayList<>();
            hijos.addAll(clientesComerciales);
            return hijos;
        }
    }
}
