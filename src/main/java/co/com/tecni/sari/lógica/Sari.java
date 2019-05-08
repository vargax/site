package co.com.tecni.sari.lógica;

import co.com.tecni.sari.datos.Lector;
import co.com.tecni.sari.lógica.inmuebles.Inmueble;
import co.com.tecni.sari.lógica.árboles.PerspectivaInmuebles;
import co.com.tecni.sari.lógica.árboles.PerspectivaCartera;
import co.com.tecni.sari.lógica.árboles.PerspectivaClientes;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Sari {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    /* ToDo área_privada_total como modo_ponderación
        |> Se requiere para que prorratas de Arrendamientos y Prediales funcionen correctamente.
        |> Debería la ficha definir cómo debe proratearse?
           |> Puede tener un impacto al incorporar el factor ponderación para los inmuebles hermanos
    */
    private final static String MODO_PONDERACIÓN = Inmueble.A_PRIV_TOTAL;

    private final static LocalDate FECHA_INICIAL_FACTURACIÓN = LocalDate.parse("2016-01-01");
    private final static int MESES_FACTURACIÓN = 24;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    public final static Sari instance;

    public final static Gson GSON;
    public final static DecimalFormat SMALL_DECIMAL;
    public final static DecimalFormat BIG_DECIMAL;
    public final static DateTimeFormatter DTF;

    static {
        GSON = genGson();
        DTF = DateTimeFormatter.ofPattern("MMM yyyy");

        SMALL_DECIMAL = new DecimalFormat("#.###"); SMALL_DECIMAL.setRoundingMode(RoundingMode.CEILING);
        BIG_DECIMAL = new DecimalFormat("###,###,###");

        instance = new Sari();
    }

    private String modoPonderación;

    public static PerspectivaInmuebles perspectivaInmuebles;
    public static PerspectivaClientes perspectivaClientes;
    public static PerspectivaCartera perspectivaCartera;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    private Sari() {
        modoPonderación = MODO_PONDERACIÓN;
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
                    df = Sari.SMALL_DECIMAL;
                else
                    df = Sari.BIG_DECIMAL;

                return new JsonPrimitive(df.format(aDouble));
            }
        });

        gsonBuilder.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(Sari.DTF.format(localDate));
            }
        });

        return gsonBuilder.create();
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public void generarÁrboles() {
        Lector lector = new Lector();

        try {
            perspectivaInmuebles = lector.importarInmuebles();
            lector.importarContratos();
        } catch (Exception e) {
            e.printStackTrace();
        }

        perspectivaClientes = lector.genÁrbolContratos();
        perspectivaCartera = lector.genÁrbolCartera();

        perspectivaCartera.presupuestarIngresosyGenerarFacturas(FECHA_INICIAL_FACTURACIÓN, MESES_FACTURACIÓN);
    }

    public String getModoPonderación() {
        return modoPonderación;
    }
}