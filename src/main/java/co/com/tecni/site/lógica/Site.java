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
import java.time.LocalDate;

public class Site {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String MODO_PONDERACIÓN = Inmueble.A_PRIV_TOTAL;

    private final static LocalDate FECHA_INICIAL_FACTURACIÓN = LocalDate.parse("2016-01-01");
    private final static int MESES_FACTURACIÓN = 24;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    public final static Gson gson;
    public final static DecimalFormat smallDecimal;
    public final static DecimalFormat bigDecimal;
    public final static SimpleDateFormat df;
    static {
        gson = genGson();
        df = new SimpleDateFormat("MMM yyyy");

        smallDecimal = new DecimalFormat("#.##"); smallDecimal.setRoundingMode(RoundingMode.CEILING);
        bigDecimal = new DecimalFormat("###,###,###");
    }

    private static Site instance;
    private String modoPonderación;

    public static ÁrbolInmuebles árbolInmuebles;
    public static ÁrbolContratos árbolContratos;
    public static ÁrbolCartera árbolCartera;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    private Site() {
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
                    df = Site.smallDecimal;
                else
                    df = Site.bigDecimal;

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

        árbolCartera.presupuestarIngresosyGenerarFacturas(FECHA_INICIAL_FACTURACIÓN, MESES_FACTURACIÓN);
    }

    public String getModoPonderación() {
        return modoPonderación;
    }
}