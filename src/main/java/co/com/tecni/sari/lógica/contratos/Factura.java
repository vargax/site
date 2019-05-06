package co.com.tecni.sari.lógica.contratos;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.transacciones.Tercero;
import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.lógica.árboles.Árbol;
import co.com.tecni.sari.ui.UiÁrbol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

class Factura implements Nodo {
    private final static double PORCENTAJE_IVA = 0.19;
    private final static double PORCENTAJE_RETEFUENTE = -0.035;

    private static int consecutivoFactura = 1;
    static HashMap<Integer, Factura> facturas = new HashMap<>();

    private final Tercero tercero;
    private ArrayList<Transacción> transacciones;

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
            for(Transacción transacción : factura.transacciones) {
                subtotal += transacción.monto;
            }
            iva = subtotal*PORCENTAJE_IVA;
            retefuente = subtotal*PORCENTAJE_RETEFUENTE;
            total = subtotal + iva + retefuente;
        }
    }

    Factura(Tercero tercero, LocalDate fecha, ArrayList<Transacción> transacciones) {
        this.tercero = tercero;
        this.transacciones = transacciones;

        json = new Json(fecha, this);

        facturas.put(json.consecutivo, this);
    }

    public String nombreNodo(Árbol árbol) {
        return "Factura "+json.consecutivo + " / "+ Sari.DTF.format(json.fecha);
    }

    public ArrayList<Object> hijosNodo(Árbol árbol) {
        return null;
    }

    public UiÁrbol.Ícono íconoNodo() {
        return null;
    }

    public ArrayList<Transacción>[] transaccionesNodo(Árbol árbol) {
        ArrayList[] resultado = new ArrayList[3];

        ArrayList<Transacción> descendientes = new ArrayList<>();
        resultado[2] = descendientes;

        ArrayList<Transacción> propias = new ArrayList<>();
        propias.addAll(this.transacciones);
        resultado[1] = propias;

        ArrayList<Transacción> ancestros = new ArrayList<>();
        resultado[0] = ancestros;

        return resultado;
    }

    public String infoNodo(Árbol árbol) {
        return Sari.GSON.toJson(json);
    }
}
