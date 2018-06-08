package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.Site;
import co.com.tecni.site.lógica.Árbol;

import java.awt.*;
import java.util.Date;

public class Jurídica extends Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static Color UI_ÍCONO_COLOR = Color.YELLOW;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Json json;

    public static class Json {
        String oficinaRegistro;
        String matrículaInmobiliaria;
        Date fechaRegistroCompra;
        double coefCopropiedad;

        public Json(String oficinaRegistro, String matrículaInmobiliaria, Date fechaRegistroCompra, double coefCopropiedad) {
            this.oficinaRegistro = oficinaRegistro;
            this.matrículaInmobiliaria = matrículaInmobiliaria;
            this.fechaRegistroCompra = fechaRegistroCompra;
            this.coefCopropiedad = coefCopropiedad;
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Jurídica(Json json) {
        super();
        super.íconoColor = UI_ÍCONO_COLOR;

        this.json = json;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return "Matrícula inmobiliaria: "+json.matrículaInmobiliaria;
    }

    @Override
    public String infoNodo() {
        return Site.gson.toJson(json);
    }
}
