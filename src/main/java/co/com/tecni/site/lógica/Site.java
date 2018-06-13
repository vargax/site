package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.Lector;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Site {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    private final static String MODO_PONDERACIÓN = Inmueble.A_TOTAL;


    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    public static Gson gson;
    public static DecimalFormat df;

    private static Site instance;
    private String modoPonderación;

    public static ÁrbolInmuebles árbolInmuebles;
    public static ÁrbolContratos árbolContratos;
    public static ÁrbolCartera árbolCartera;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    private Site() {
        gson = genGson();
        df = new DecimalFormat("#.##"); df.setRoundingMode(RoundingMode.CEILING);

        modoPonderación = MODO_PONDERACIÓN;
    }

    public static Site getInstance() {
        if (instance == null)
            instance = new Site();
        return instance;
    }

    // -----------------------------------------------
    // Métodos Privados
    // -----------------------------------------------
    private static Gson genGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {

                DecimalFormat df;
                if (aDouble < 10) {
                    df = new DecimalFormat("#.##");
                    df.setRoundingMode(RoundingMode.CEILING);
                } else {
                    df = new DecimalFormat("#,###,###");
                }

                return new JsonPrimitive(df.format(aDouble));
            }
        });
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES);
        return gsonBuilder.create();
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public void generarÁrboles() throws Exception {
        Lector lector = new Lector();

        árbolInmuebles = lector.importarInmuebles();

        lector.importarContratos();
        árbolContratos = lector.genÁrbolContratos();
        árbolCartera = lector.genÁrbolCartera();
    }

    public String getModoPonderación() {
        return modoPonderación;
    }
}