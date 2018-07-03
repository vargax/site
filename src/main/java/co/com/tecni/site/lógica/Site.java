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
import java.text.SimpleDateFormat;

public class Site {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String MODO_PONDERACIÓN = Inmueble.A_TOTAL;

    private final static String FECHA_INICIAL_FACTURACIÓN = "ene 2017";
    private final static int MESES_FACTURACIÓN = 24;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    public static Gson gson;
    public static DecimalFormat sdf;
    public static DecimalFormat bdf;
    public static SimpleDateFormat df;

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
        df = new SimpleDateFormat("MMM yyyy");

        sdf = new DecimalFormat("#.##"); sdf.setRoundingMode(RoundingMode.CEILING);
        bdf = new DecimalFormat("###,###,###");

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
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES);
        gsonBuilder.setDateFormat("yyyy MMM dd");

        gsonBuilder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                DecimalFormat df;
                if (-10 < aDouble && aDouble < 10)
                    df = Site.sdf;
                else
                    df = Site.bdf;

                return new JsonPrimitive(df.format(aDouble));
            }
        });

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

        árbolCartera.genFacturas(df.parse(FECHA_INICIAL_FACTURACIÓN), MESES_FACTURACIÓN);
    }

    public String getModoPonderación() {
        return modoPonderación;
    }
}