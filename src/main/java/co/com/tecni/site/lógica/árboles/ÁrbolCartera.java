package co.com.tecni.site.lógica.árboles;

import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;

import java.util.HashMap;

public class ÁrbolCartera extends ÁrbolClientes {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static String NOMBRE_RAIZ = "CARTERA";

    public ÁrbolCartera(HashMap<Integer, ClienteComercial> clientesComercialesxId) {
        super(clientesComercialesxId);
        super.nombreRaiz = NOMBRE_RAIZ;
    }
}
