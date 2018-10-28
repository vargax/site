package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contratos.Secuencia;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Movimiento;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;

import java.time.LocalDate;
import java.util.ArrayList;

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

    // Constructor para presupuestos
    public Arrendamiento(Ficha padre, Inmueble inmueble) {
        super(padre);

        this.secuencia = null;
        this.inmueble = inmueble;
        this.json = new Json(0, "N/A", 1);
    }

    public Arrendamiento(Secuencia secuencia, Inmueble inmueble, Json json) {

        this.secuencia = secuencia;
        this.inmueble = inmueble;
        this.json = json;

        inmueble.registrarFicha(this);
    }

    public Movimiento facturar(ClienteFacturación clienteFacturación,
                               LocalDate fechaFactura,
                               double cánon) {

        Movimiento movimiento = new Movimiento(
                this,
                "Ingreso por arrendamiento",
                fechaFactura,
                json.participación*cánon,
                clienteFacturación
                );

        movimientos.add(movimiento);
        return movimiento;
    }

    public double getParticipación() {
        return json.participación;
    }

    public String nombreNodo(Árbol árbol) {
        if (árbol instanceof ÁrbolInmuebles)
            return "Arrendamiento: " + json.númeroSecuencia + " / "+Site.SMALL_DECIMAL.format(json.participación);
        if (árbol instanceof ÁrbolContratos)
            return "Inmueble: "+ inmueble.nombreNodo(árbol) + " / "+Site.SMALL_DECIMAL.format(json.participación);
        return "Típo de Árbol no definido en Ficha.Arrendamiento.nombreNodo()";
    }

    public String infoNodo(Árbol árbol) {
        return Site.GSON.toJson(json);
    }

    @Override
    public ArrayList<Movimiento>[] movimientosNodo(Árbol árbol) {
        ArrayList[] resultado = super.movimientosNodo(árbol);

        if (árbol instanceof ÁrbolContratos) {
            ArrayList<Movimiento>[] transaccionesInmueble = inmueble.movimientosNodo(árbol);
            resultado[0].addAll(transaccionesInmueble[0]);
            resultado[1].addAll(transaccionesInmueble[1]);
            resultado[2].addAll(transaccionesInmueble[2]);
        }

        return resultado;
    }
}
