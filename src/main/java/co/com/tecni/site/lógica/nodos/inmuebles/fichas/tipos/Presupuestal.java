package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.árboles.Árbol;

public class Presupuestal extends Ficha {

    private Json json;
    static class Json {
        int año;

        public Json(int año) {
            this.año = año;
        }
    }

    public Presupuestal(int año) {
        super(true);
        this.json = new Json(año);
    }

    public String nombreNodo(Árbol árbol) {
        return "Presupuesto "+json.año;
    }

    public String infoNodo(Árbol árbol) {
        return Site.gson.toJson(json);
    }
}
