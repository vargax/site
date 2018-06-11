package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.Lector;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    private Site() {
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES).create();
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
    public void importarDatos() throws Exception {
        Lector lector = new Lector();
        árbolInmuebles = lector.importarInmuebles();
        árbolContratos = lector.importarContratos();
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------


    public String getModoPonderación() {
        return modoPonderación;
    }

    public ÁrbolInmuebles getÁrbolInmuebles() {
        return árbolInmuebles;
    }

}