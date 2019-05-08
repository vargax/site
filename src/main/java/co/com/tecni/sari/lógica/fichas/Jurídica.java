package co.com.tecni.sari.lógica.fichas;

import co.com.tecni.sari.lógica.Sari;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.árboles.Árbol;

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
    public Jurídica(Nodo padre, Json json) {
        super(padre);
        this.json = json;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return "Matrícula inmobiliaria: "+json.matrículaInmobiliaria;
    }

    public String infoNodo() {
        return Sari.GSON.toJson(json);
    }
}
