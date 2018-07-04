package co.com.tecni.site.lógica.árboles;

import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ÁrbolCartera extends ÁrbolClientes {

    public final static String NOMBRE_RAIZ = "CARTERA";

    public ÁrbolCartera(HashMap<Integer, ClienteComercial> clientesComercialesxId) {
        super(clientesComercialesxId);
        super.nombreRaiz = NOMBRE_RAIZ;
    }

    public void genFacturas(Date fechaInicial, int númeroMeses) {
        Calendar c = Calendar.getInstance();

        for (int i = 0; i < númeroMeses; i++) {

            for (ClienteComercial cc : super.clientesComercialesxId.values())
                cc.facturar(fechaInicial);

            c.setTime(fechaInicial);
            c.add(Calendar.MONTH, 1);
            fechaInicial = c.getTime();
        }


    }

}
