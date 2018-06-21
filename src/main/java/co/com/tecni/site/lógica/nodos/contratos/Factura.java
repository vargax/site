package co.com.tecni.site.lógica.nodos.contratos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Tercero;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;

import java.util.ArrayList;
import java.util.Date;

class Factura implements Nodo {
    private final static double PORCENTAJE_IVA = 0.19;
    private final static double PORCENTAJE_RETEFUENTE = -0.035;

    private static int consecutivoFactura = 1;

    private final Tercero tercero;
    private ArrayList<Transacción> transacciones;

    Json json;
    static class Json {
        int consecutivo;
        String tercero;
        Date fecha;

        double subtotal;
        double iva;
        double retefuente;
        double total;

        Json(Date fecha, Factura factura) {
            consecutivo = (consecutivoFactura += 1);
            tercero = factura.tercero.nombre;
            this.fecha = fecha;

            subtotal = 0;
            for(Transacción transacción : factura.transacciones) {
                subtotal += transacción.monto;
            }
            iva = subtotal*PORCENTAJE_IVA;
            retefuente = subtotal*PORCENTAJE_RETEFUENTE;
            total = subtotal + iva + retefuente;
        }
    }

    Factura(Tercero tercero, Date fecha, ArrayList<Transacción> transacciones) {
        this.tercero = tercero;
        this.transacciones = transacciones;

        json = new Json(fecha, this);
    }

    public String nombreNodo(Árbol árbol) {
        return "Factura "+json.consecutivo + " / "+ Site.df.format(json.fecha);
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        return null;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Transacción>[] transaccionesNodo() {
        return new ArrayList[3];
    }

    public String infoNodo() {
        return Site.gson.toJson(json);
    }
}
