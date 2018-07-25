package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contratos.Secuencia;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;

import java.util.ArrayList;
import java.util.Date;

public class Arrendamiento extends Ficha {

    private final Secuencia secuencia;
    private final Inmueble inmueble;
    private Json json;

    public static class Json {
        int númeroSecuencia;
        String clienteComercial;
        double participación;

        public Json(int númeroSecuencia, String clienteComercial, double participación) {
            this.númeroSecuencia = númeroSecuencia;
            this.clienteComercial = clienteComercial;
            this.participación = participación;
        }
    }

    public Arrendamiento(Secuencia secuencia, Inmueble inmueble, Json json) {
        super(false);

        this.secuencia = secuencia;
        this.inmueble = inmueble;
        this.json = json;

        inmueble.registrarFicha(this);
    }

    public Transacción facturar(ClienteFacturación clienteFacturación,
                                Date fechaFactura,
                                double cánon) {

        Transacción transacción = new Transacción(
                this,
                "Ingreso por arrendamiento",
                fechaFactura,
                json.participación*cánon,
                clienteFacturación
                );

        transacciones.add(transacción);
        return transacción;
    }

    public double getParticipación() {
        return json.participación;
    }

    public String nombreNodo(Árbol árbol) {
        if (árbol instanceof ÁrbolInmuebles)
            return "Arrendamiento: " + json.númeroSecuencia + " / "+Site.sdf.format(json.participación);
        if (árbol instanceof ÁrbolContratos)
            return "Inmueble: "+ inmueble.nombreNodo(árbol) + " / "+Site.sdf.format(json.participación);
        return "Típo de Árbol no definido en Ficha.Arrendamiento.nombreNodo()";
    }

    public String infoNodo(Árbol árbol) {
        return Site.gson.toJson(json);
    }

    @Override
    public ArrayList<Transacción>[] transaccionesNodo(Árbol árbol) {
        ArrayList[] resultado = super.transaccionesNodo(árbol);

        if (árbol instanceof ÁrbolContratos) {
            ArrayList<Transacción>[] transaccionesInmueble = inmueble.transaccionesNodo(árbol);
            resultado[0].addAll(transaccionesInmueble[0]);
            resultado[1].addAll(transaccionesInmueble[1]);
            resultado[2].addAll(transaccionesInmueble[2]);
        }

        return resultado;
    }
}
