package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.contratos.Secuencia;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;

public class Contractual extends Ficha {

    final Secuencia secuencia;
    final Inmueble inmueble;
    private Json json;

    public static class Json {
        int númeroContrato;
        String clienteComercial;
        double participación;

        public Json(int númeroContrato, String clienteComercial, double participación) {
            this.númeroContrato = númeroContrato;
            this.clienteComercial = clienteComercial;
            this.participación = participación;
        }
    }

    public Contractual(Secuencia secuencia, Inmueble inmueble, Json json) {
        this.secuencia = secuencia;
        this.inmueble = inmueble;
        this.json = json;

        inmueble.registrarFicha(this);
    }

    public double getParticipación() {
        return json.participación;
    }

    public String nombreNodo(Árbol árbol) {
        if (árbol instanceof ÁrbolInmuebles)
            return "Secuencia: " + json.númeroContrato + " / "+Site.df.format(json.participación);
        if (árbol instanceof ÁrbolContratos)
            return "Inmueble: "+ inmueble.nombreNodo(árbol) + " / "+Site.df.format(json.participación);
        return "Típo de Árbol no definido en Ficha.Contractual.nombreNodo()";
    }

    @Override
    public String infoNodo() {
        return Site.gson.toJson(json);
    }
}
