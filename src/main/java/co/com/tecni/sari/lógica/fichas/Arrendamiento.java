package co.com.tecni.sari.lógica.fichas;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.contratos.ClienteFacturación;
import co.com.tecni.sari.lógica.contratos.Versión;
import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.lógica.inmuebles.tipos.Inmueble;
import co.com.tecni.sari.lógica.árboles.Árbol;
import co.com.tecni.sari.lógica.árboles.ÁrbolContratos;
import co.com.tecni.sari.lógica.árboles.ÁrbolInmuebles;
import co.com.tecni.sari.ui.UiSari;

import java.time.LocalDate;
import java.util.ArrayList;

public class Arrendamiento extends Ficha {

    private final Versión versión;
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

        this.versión = null;
        this.inmueble = inmueble;
        this.json = new Json(0, "N/A", 1);
    }

    public Arrendamiento(Versión versión, Inmueble inmueble, Json json) {

        this.versión = versión;
        this.inmueble = inmueble;
        this.json = json;

        inmueble.registrarFicha(this);
    }

    public Transacción facturar(ClienteFacturación clienteFacturación,
                                LocalDate fechaFactura,
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

    public String nombreNodo() {
        if (UiSari.árbolActual instanceof ÁrbolInmuebles)
            return "Arrendamiento: " + json.númeroSecuencia + " / "+ Sari.SMALL_DECIMAL.format(json.participación);
        if (UiSari.árbolActual instanceof ÁrbolContratos)
            return "Inmueble: "+ inmueble.nombreNodo() + " / "+ Sari.SMALL_DECIMAL.format(json.participación);
        return "Típo de Árbol no definido en Ficha.Arrendamiento.nombreNodo()";
    }

    public String infoNodo() {
        return Sari.GSON.toJson(json);
    }

    @Override
    public ArrayList<Transacción>[] transaccionesNodo() {
        ArrayList[] resultado = super.transaccionesNodo();

        if (UiSari.árbolActual instanceof ÁrbolContratos) {
            ArrayList<Transacción>[] transaccionesInmueble = inmueble.transaccionesNodo();
            resultado[0].addAll(transaccionesInmueble[0]);
            resultado[1].addAll(transaccionesInmueble[1]);
            resultado[2].addAll(transaccionesInmueble[2]);
        }

        return resultado;
    }
}
