package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contratos.Contrato;
import co.com.tecni.site.lógica.nodos.contratos.Secuencia;
import co.com.tecni.site.lógica.nodos.inmuebles.Agrupación;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.ÁrbolCartera;
import co.com.tecni.site.lógica.árboles.ÁrbolContratos;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.HashMap;

public class Lector {
    private final static String[] INMUEBLES_IMPORTAR = {"LaEstancia", "Ecotower93"};

    private HashMap<String, Inmueble> inmueblesxId;
    private HashMap<Integer, ClienteComercial> clientesComercialesxId;
    private HashMap<Integer, ClienteFacturación> clientesFacturaciónxId;
    private HashMap<Integer, Contrato> contratosxId;
    private HashMap<Integer, Secuencia> secuenciasxId;

    // -----------------------------------------------
    // Soporte parsers
    // -----------------------------------------------
    static int charToInt(char c) {
        return (int) c - 65;
    }

    static Date fecha(Row fila, char columna) {
        int col = columna - 65;
        Date fecha = null;
        try {
            fecha = DateUtil.getJavaDate(fila.getCell(col).getNumericCellValue());
        } catch (NullPointerException e) {
            System.err.println("Columna "+columna+" indefinida como fecha para la fila "+ fila.getRowNum());
        }
        return fecha;
    }

    static String cadena(Row fila, char columna) {
        int col = columna - 65;
        String cadena = null;
        try {
            cadena = fila.getCell(col).getStringCellValue();
        } catch (NullPointerException e) {
            System.err.println("Columna "+columna+" indefinida como cadena para la fila "+ fila.getRowNum());
        }
        return cadena;
    }

    static Integer entero(Row fila, char columna) {
        int col = columna - 65;
        Integer entero = null;
        try {
            entero = ((Double) fila.getCell(col).getNumericCellValue()).intValue();
        } catch (NullPointerException e) {
            System.err.println("Columna "+columna+" indefinida como entero para la fila "+ fila.getRowNum());
        }
        return entero;
    }

    static Double doble(Row fila, char columna) {
        int col = columna - 65;
        Double doble = null;
        try {
            doble = fila.getCell(col).getNumericCellValue();
        } catch (NullPointerException e) {
            System.err.println("Columna "+columna+" indefinida como doble para la fila "+ fila.getRowNum());
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
        LectorObraCivil lectorObraCivil = new LectorObraCivil(inmueblesxId);

        for (String nombreInmueble : INMUEBLES_IMPORTAR) {
            lectorCatastral.leer(nombreInmueble);
            lectorJurídica.leer(nombreInmueble);
            lectorObraCivil.leer(nombreInmueble);
        }
    }

    public void importarContratos() throws Exception {
        LectorContrato lectorContrato = new LectorContrato(inmueblesxId);

        lectorContrato.leer();

        clientesComercialesxId = lectorContrato.clientesComerciales;
        clientesFacturaciónxId = lectorContrato.clientesFacturación;
        contratosxId = lectorContrato.contratos;
        secuenciasxId = lectorContrato.secuencias;
    }

    public ÁrbolContratos genÁrbolContratos() {
        return new ÁrbolContratos(clientesComercialesxId);
    }

    public ÁrbolCartera genÁrbolCartera() {
        return new ÁrbolCartera(clientesComercialesxId);
    }

}
