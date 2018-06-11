package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.inmuebles.Agrupación;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.HashMap;

public class Lector {
    private final static String[] INMUEBLES_IMPORTAR = {"LaEstancia", "Ecotower93"};

    private HashMap<String, Inmueble> inmueblesxId;
    private HashMap<Integer, ClienteComercial> clientesComerciales;
    private HashMap<Integer, ClienteFacturación> clientesFacturación;


    public Lector() {

    }

    // -----------------------------------------------
    // Soporte parsers
    // -----------------------------------------------
    static Date fecha(Row fila, int columna) {
        Date fecha = null;
        try {
            fecha = new Date((long)fila.getCell(columna).getNumericCellValue());
        } catch (NullPointerException e) {
            char col = (char) (columna + 65);
            System.err.println("Columna "+col+" indefinida como fecha para la fila "+ fila.getRowNum());
        }
        return fecha;
    }

    static String cadena(Row fila, int columna) {
        String cadena = null;
        try {
            cadena = fila.getCell(columna).getStringCellValue();
        } catch (NullPointerException e) {
            char col = (char) (columna + 65);
            System.err.println("Columna "+col+" indefinida como cadena para la fila "+ fila.getRowNum());
        }
        return cadena;
    }

    static Integer entero(Row fila, int columna) {
        Integer entero = null;
        try {
            entero = ((Double) fila.getCell(columna).getNumericCellValue()).intValue();
        } catch (NullPointerException e) {
            char col = (char) (columna + 65);
            System.err.println("Columna "+col+" indefinida como entero para la fila "+ fila.getRowNum());
        }
        return entero;
    }

    static Double doble(Row fila, int columna) {
        Double doble = null;
        try {
            doble = fila.getCell(columna).getNumericCellValue();
        } catch (NullPointerException e) {
            char col = (char) (columna + 65);
            System.err.println("Columna "+col+" indefinida como doble para la fila "+ fila.getRowNum());
        }
        return doble;
    }

    public ÁrbolInmuebles importarInmuebles() throws Exception {
        LectorInmueble lectorInmueble = new LectorInmueble();

        ÁrbolInmuebles árbolInmuebles = new ÁrbolInmuebles();

        Agrupación bodegas = árbolInmuebles.registrarAgrupación("Bodegas");
        bodegas.agregarInmueble(lectorInmueble.leer(INMUEBLES_IMPORTAR[0]));

        Agrupación edificiosOficinas = árbolInmuebles.registrarAgrupación("Edificios de oficinas");
        edificiosOficinas.agregarInmueble(lectorInmueble.leer(INMUEBLES_IMPORTAR[1]));

        inmueblesxId = árbolInmuebles.registrarIdentificadores();

        importarFichas();

        return árbolInmuebles;
    }

    private void importarFichas() throws Exception {
        LectorCatastral lectorCatastral = new LectorCatastral(inmueblesxId);
        LectorJurídica lectorJurídica = new LectorJurídica(inmueblesxId);

        for (String nombreInmueble : INMUEBLES_IMPORTAR) {
            lectorCatastral.leer(nombreInmueble);
            lectorJurídica.leer(nombreInmueble);
        }
    }

    public ÁrbolContratos importarContratos() throws Exception {
        LectorContrato lectorContrato = new LectorContrato(inmueblesxId);

        lectorContrato.leer();

        return new ÁrbolContratos(
                lectorContrato.clientesComerciales,
                lectorContrato.clientesFacturación,
                lectorContrato.contratos,
                lectorContrato.secuencias);
    }

}
