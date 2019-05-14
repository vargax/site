package co.com.tecni.sari.lógica.clientes;

import java.util.HashMap;

public class PerspectivaContratos extends ÁrbolClientes {
    public final static String NOMBRE = "CONTRATOS";

    public PerspectivaContratos(HashMap<Integer, ClienteComercial> clientesComercialesxId) {
        super(clientesComercialesxId);
        super.nombreRaiz = NOMBRE;
    }
}
