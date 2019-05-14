package co.com.tecni.sari.lógica.inmuebles.fichas;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.árboles.Nodo;

import java.util.ArrayList;
import java.util.HashMap;

public class Presupuestal extends Ficha {

    private final static String[] TIPOS = {"general", "ingresos", "gastos"};

    private Json json;
    static class Json {
        String tipo;
        int año;
        int fichas;

        Json(String tipo, int año) {
            this.tipo = tipo;
            this.año = año;
            this.fichas = -1;
        }
    }

    final HashMap<String, Presupuestal> presupuestos;

    public Presupuestal(Nodo padre, int año) {
        super(padre);
        super.presupuestado = true;

        this.json = new Json(TIPOS[0], año);
        presupuestos = new HashMap<>();

        for (int i = 1; i < TIPOS.length; i++) {
            Presupuestal presupuesto = new Presupuestal(this, TIPOS[i], año);
            presupuestos.put(TIPOS[i], presupuesto);
        }

    }

    private Presupuestal(Nodo padre, String tipo, int año) {
        super(padre);

        this.json = new Json(tipo, año);
        this.presupuestos = null;
    }

    public Presupuestal ingresos() {
        return presupuestos.get(TIPOS[1]);
    }

    public Presupuestal gastos() {
        return presupuestos.get(TIPOS[2]);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return "Presupuesto "+json.tipo+" "+json.año;
    }

    @Override
    public ArrayList<Object> hijosNodo() {
        if (json.tipo.equals(TIPOS[1]))
            return null; // ToDo No muestro como nodos los ingresos por arrendamiento generados?

        return super.hijosNodo();
    }

    public String infoNodo() {
        if (json.fichas == -1) json.fichas = super.fichas.size();

        return Sari.GSON.toJson(json);
    }
}
