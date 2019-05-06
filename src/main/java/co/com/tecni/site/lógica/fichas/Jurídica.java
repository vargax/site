package co.com.tecni.site.lógica.fichas;

import co.com.tecni.site.lógica.Sari;
import co.com.tecni.site.lógica.árboles.Árbol;

import java.time.LocalDate;

public class Jurídica extends Ficha {

    private Json json;
    public static class Json {
        String oficinaRegistro;
        String matrículaInmobiliaria;
        LocalDate fechaRegistroCompra;
        double coefCopropiedad;

        public Json(String oficinaRegistro, String matrículaInmobiliaria, LocalDate fechaRegistroCompra, double coefCopropiedad) {
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
        this.json = json;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo(Árbol árbol) {
        return "Matrícula inmobiliaria: "+json.matrículaInmobiliaria;
    }

    public String infoNodo(Árbol árbol) {
        return Sari.GSON.toJson(json);
    }
}
