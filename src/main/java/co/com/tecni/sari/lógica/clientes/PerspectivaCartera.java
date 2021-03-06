package co.com.tecni.sari.lógica.clientes;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.inmuebles.PerspectivaInmuebles;
import co.com.tecni.sari.lógica.inmuebles.fichas.Arrendamiento;
import co.com.tecni.sari.lógica.inmuebles.fichas.Presupuestal;
import co.com.tecni.sari.lógica.inmuebles.Inmueble;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PerspectivaCartera extends ÁrbolClientes {

    public final static String NOMBRE = "CARTERA";

    public PerspectivaCartera(HashMap<Integer, ClienteComercial> clientesComercialesxId) {
        super(clientesComercialesxId);
        super.nombreRaiz = NOMBRE;
    }

    public void presupuestarIngresosyGenerarFacturas(LocalDate fechaInicial, int númeroMeses) {

        // ToDo no estoy satisfecho con esta forma de generar los ingresos presupuestados
        HashMap<String, Double> ingresosPresupuestadosxInmueble = new HashMap<>();
        ingresosPresupuestadosxInmueble.put("EO Ecotower 93", 370000000.00);
        ingresosPresupuestadosxInmueble.put("CB La Estancia", 750000000.00);

        for (int i = 0; i < númeroMeses; i++) {

            LocalDate fecha = fechaInicial.plusMonths(i);

            // Presupuestar Ingresos
            for (Map.Entry<String, Double> e : ingresosPresupuestadosxInmueble.entrySet()) {

                Inmueble inmueble = PerspectivaInmuebles.inmueblesxId.get(e.getKey());
                Presupuestal presupuesto = inmueble.getPresupuesto(fecha.getYear()).ingresos();

                Arrendamiento arrendamiento = new Arrendamiento(presupuesto, inmueble);
                arrendamiento.facturar(null, fecha, e.getValue());

            }

            // Generar Facturas
            for (ClienteComercial cc : super.clientesComercialesxId.values())
                cc.facturar(fecha);
        }


    }

}
