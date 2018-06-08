package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.nodos.contratos.Contrato;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.Árbol;
import co.com.tecni.site.lógica.ÁrbolContratos;
import co.com.tecni.site.lógica.ÁrbolInmuebles;

public class Contractual extends Ficha {

    private Contrato contrato;
    private Inmueble inmueble;
    private Json json;

    public static class Json {
        int númeroContrato;
        String clienteComercial;
        double participación;

        public Json(double participación) {
            this.participación = participación;
        }
    }

    public Contractual(Contrato contrato, Inmueble inmueble, Json json) {
        this.contrato = contrato;
        this.inmueble = inmueble;
        this.json = json;

        json.clienteComercial = contrato.getClienteComercial().getNombre();
        json.númeroContrato = contrato.getNumContrato();

        inmueble.registrarFicha(this);
    }

    public double getParticipación() {
        return json.participación;
    }

    public String nombreNodo(Árbol árbol) {
        if (árbol instanceof ÁrbolInmuebles)
            return "Contrato: " + contrato.getNumContrato() + " / "+Site.df.format(json.participación);
        if (árbol instanceof ÁrbolContratos)
            return "Inmueble: "+ inmueble.nombreNodo(árbol) + " / "+Site.df.format(json.participación);
        return "Típo de Árbol no definido en Ficha.Contractual.nombreNodo()";
    }

    @Override
    public String infoNodo() {
        return Site.gson.toJson(json);
    }
}
