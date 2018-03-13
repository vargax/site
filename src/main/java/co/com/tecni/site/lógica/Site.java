package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.LectorCatastral;
import co.com.tecni.site.datos.LectorContrato;
import co.com.tecni.site.datos.LectorInmueble;
import co.com.tecni.site.datos.LectorJurídica;
import co.com.tecni.site.lógica.nodos.contrato.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contrato.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contrato.Contrato;
import co.com.tecni.site.lógica.nodos.inmueble.Agrupación;

import java.util.HashMap;

public class Site {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String[] INMUEBLES_IMPORTAR = {"LaEstancia", "Ecotower93"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private ÁrbolInmuebles árbolInmuebles;
    private ÁrbolClientes árbolClientes;

    private HashMap<Integer, ClienteComercial> clientesComerciales;
    private HashMap<Integer, ClienteFacturación> clientesFacturación;
    private HashMap<Integer, Contrato> contratos;

    private LectorInmueble lectorInmuebles;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Site() throws Exception {
        árbolInmuebles = new ÁrbolInmuebles();
        árbolClientes = new ÁrbolClientes();

        importarInmuebles();

        importarFichas();
        importarContratos();
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

        lectorInmuebles = new LectorInmueble();
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
        lectorContrato.leer();

        clientesComerciales = lectorContrato.getClientesComerciales();
        clientesFacturación = lectorContrato.getClientesFacturación();
        contratos = lectorContrato.getContratos();
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------

    public ÁrbolInmuebles getÁrbolInmuebles() {
        return árbolInmuebles;
    }

    public ÁrbolClientes getÁrbolClientes() {
        return árbolClientes;
    }
}
