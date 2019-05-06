package co.com.tecni.sari.datos;

import co.com.tecni.sari.lógica.contratos.ClienteComercial;
import co.com.tecni.sari.lógica.contratos.ClienteFacturación;
import co.com.tecni.sari.lógica.contratos.Contrato;
import co.com.tecni.sari.lógica.contratos.Versión;
import co.com.tecni.sari.lógica.inmuebles.tipos.Inmueble;
import co.com.tecni.sari.lógica.árboles.ÁrbolCartera;
import co.com.tecni.sari.lógica.árboles.ÁrbolContratos;
import co.com.tecni.sari.lógica.árboles.ÁrbolInmuebles;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;

public class Lector {
    private HashMap<String, Inmueble> inmueblesxId = ÁrbolInmuebles.inmueblesxId;
    private HashMap<Integer, ClienteComercial> clientesComercialesxId;
    private HashMap<Integer, ClienteFacturación> clientesFacturaciónxId;
    private HashMap<Integer, Contrato> contratosxId;
    private HashMap<Integer, Versión> secuenciasxId;

    // -----------------------------------------------
    // Soporte parsers
    // -----------------------------------------------
    static int charToInt(char c) {
        return (int) c - 65;
    }

    static LocalDate fecha(Row fila, char columna) {
        int col = charToInt(columna);
        LocalDate fecha = null;
        try {
            fecha = (DateUtil.getJavaDate(fila.getCell(col).getNumericCellValue())).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NullPointerException e) {
            System.err.println("Columna "+columna+" indefinida como fecha para la fila "+ fila.getRowNum());
        }
        return fecha;
    }

    static String cadena(Row fila, char columna) {
        int col = charToInt(columna);
        String cadena = "";
        try {
            cadena = fila.getCell(col).getStringCellValue();
        } catch (NullPointerException e) {
            System.err.println("Columna "+columna+" indefinida como cadena para la fila "+ fila.getRowNum());
        }
        return cadena;
    }

    static Integer entero(Row fila, char columna) {
        int col = charToInt(columna);
        Integer entero = 0;
        try {
            entero = ((Double) fila.getCell(col).getNumericCellValue()).intValue();
        } catch (NullPointerException e) {
            System.err.println("Columna "+columna+" indefinida como entero para la fila "+ fila.getRowNum());
        }
        return entero;
    }

    static Double doble(Row fila, char columna) {
        int col = charToInt(columna);
        Double doble = 0.0;
        try {
            doble = fila.getCell(col).getNumericCellValue();
        } catch (NullPointerException e) {
            System.err.println("Columna "+columna+" indefinida como doble para la fila "+ fila.getRowNum());
        }
        return doble;
    }

    public ÁrbolInmuebles importarInmuebles() throws Exception {
        LectorInmueble li = new LectorInmueble();
        LectorAgrupaciones la = new LectorAgrupaciones(li);

        ÁrbolInmuebles árbolInmuebles = la.leer();
        árbolInmuebles.registrarIdentificadores();

        importarFichas();
        return árbolInmuebles;
    }

    private void importarFichas() throws Exception {
        LectorCatastral lectorCatastral = new LectorCatastral(inmueblesxId);
        LectorJurídica lectorJurídica = new LectorJurídica(inmueblesxId);
        LectorObraCivil lectorObraCivil = new LectorObraCivil(inmueblesxId);

        for (String nombreInmueble : ÁrbolInmuebles.inmueblesRaiz.keySet()) {
            lectorJurídica.leer(nombreInmueble);
            lectorCatastral.leer(nombreInmueble);
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
