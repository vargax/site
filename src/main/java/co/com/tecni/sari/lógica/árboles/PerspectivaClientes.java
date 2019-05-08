package co.com.tecni.sari.lógica.árboles;

import co.com.tecni.sari.lógica.contratos.ClienteComercial;

import java.util.HashMap;

public class PerspectivaClientes extends ÁrbolClientes {
    public final static String NOMBRE_RAIZ = "CLIENTES";

    public PerspectivaClientes(HashMap<Integer, ClienteComercial> clientesComercialesxId) {
        super(clientesComercialesxId);
        super.nombreRaiz = NOMBRE_RAIZ;
    }
}
