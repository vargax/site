package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.LectorCatastral;
import co.com.tecni.site.datos.LectorContrato;
import co.com.tecni.site.datos.LectorInmueble;
import co.com.tecni.site.datos.LectorJurídica;
import co.com.tecni.site.lógica.nodos.inmuebles.Agrupación;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
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
    private final static String[] INMUEBLES_IMPORTAR = {"LaEstancia", "Ecotower93"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    public static Gson gson;
    public static DecimalFormat df;

    private static Site instance;
    private String modoPonderación;

    private ÁrbolInmuebles árbolInmuebles;
    private Clientes clientes;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    private Site() {
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES).create();
        df = new DecimalFormat("#.##"); df.setRoundingMode(RoundingMode.CEILING);

        árbolInmuebles = new ÁrbolInmuebles();
        clientes = Clientes.getInstance();

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
    private void importarInmuebles() throws Exception {
        Agrupación raiz = (Agrupación) árbolInmuebles.getRoot();

        Agrupación bodegas = new Agrupación("Bodegas");
        raiz.agregarAgrupación(bodegas);

        Agrupación edificiosOficinas = new Agrupación("Edificios de oficinas");
        raiz.agregarAgrupación(edificiosOficinas);

        LectorInmueble lectorInmuebles = new LectorInmueble();
        bodegas.agregarInmueble(lectorInmuebles.leer(INMUEBLES_IMPORTAR[0]));
        edificiosOficinas.agregarInmueble(lectorInmuebles.leer(INMUEBLES_IMPORTAR[1]));

        árbolInmuebles.registrarIdentificadores();
    }

    private void importarFichas() throws Exception {
        LectorCatastral lectorCatastral = new LectorCatastral(árbolInmuebles.getInmueblesxId());
        LectorJurídica lectorJurídica = new LectorJurídica(árbolInmuebles.getInmueblesxId());

        for (String inmueble : INMUEBLES_IMPORTAR) {
            lectorCatastral.leer(inmueble);
            lectorJurídica.leer(inmueble);
        }
    }

    private void importarContratos() throws Exception {
        LectorContrato lectorContrato = new LectorContrato(árbolInmuebles.getInmueblesxId());

        lectorContrato.setClientesComerciales(clientes.getClientesComercialesxId());
        lectorContrato.setClientesFacturación(clientes.getClientesFacturaciónxId());
        lectorContrato.setContratos(clientes.getContratosxId());

        lectorContrato.leer();
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------
    public void importarDatos() throws Exception {
        importarInmuebles();
        importarFichas();
        importarContratos();
    }

    public String getModoPonderación() {
        return modoPonderación;
    }

    public ÁrbolInmuebles getÁrbolInmuebles() {
        return árbolInmuebles;
    }

    public Clientes getClientes() {
        return clientes;
    }
}