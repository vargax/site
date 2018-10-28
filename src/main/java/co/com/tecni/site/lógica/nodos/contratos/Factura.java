package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Tercero;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.movimientos.Movimiento;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

class Factura implements Nodo {
    private final static double PORCENTAJE_IVA = 0.19;
    private final static double PORCENTAJE_RETEFUENTE = -0.035;

    private static int consecutivoFactura = 1;
    static HashMap<Integer, Factura> facturas = new HashMap<>();

    private final Tercero tercero;
    private ArrayList<Movimiento> movimientos;

    Json json;
    static class Json {
        int consecutivo;
        String tercero;
        LocalDate fecha;

        double subtotal;
        double iva;
        double retefuente;
        double total;

        Json(LocalDate fecha, Factura factura) {
            consecutivo = (consecutivoFactura += 1);
            tercero = factura.tercero.nombre;
            this.fecha = fecha;

            subtotal = 0;
            for(Movimiento movimiento : factura.movimientos) {
                subtotal += movimiento.monto;
            }
            iva = subtotal*PORCENTAJE_IVA;
            retefuente = subtotal*PORCENTAJE_RETEFUENTE;
            total = subtotal + iva + retefuente;
        }
    }

    Factura(Tercero tercero, LocalDate fecha, ArrayList<Movimiento> movimientos) {
        this.tercero = tercero;
        this.movimientos = movimientos;

        json = new Json(fecha, this);

        facturas.put(json.consecutivo, this);
    }

    public String nombreNodo(Árbol árbol) {
        return "Factura "+json.consecutivo + " / "+ Site.DTF.format(json.fecha);
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        return null;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Movimiento>[] movimientosNodo(Árbol árbol) {
        ArrayList[] resultado = new ArrayList[3];

        ArrayList<Movimiento> descendientes = new ArrayList<>();
        resultado[2] = descendientes;

        ArrayList<Movimiento> propias = new ArrayList<>();
        propias.addAll(this.movimientos);
        resultado[1] = propias;

        ArrayList<Movimiento> ancestros = new ArrayList<>();
        resultado[0] = ancestros;

        return resultado;
    }

    public String infoNodo(Árbol árbol) {
        return Site.GSON.toJson(json);
    }
}
