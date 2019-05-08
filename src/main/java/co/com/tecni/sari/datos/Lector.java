package co.com.tecni.sari.datos;

import co.com.tecni.sari.lógica.contratos.ClienteComercial;
import co.com.tecni.sari.lógica.contratos.ClienteFacturación;
import co.com.tecni.sari.lógica.contratos.Contrato;
import co.com.tecni.sari.lógica.contratos.Versión;
import co.com.tecni.sari.lógica.inmuebles.Inmueble;
import co.com.tecni.sari.lógica.árboles.PerspectivaInmuebles;
import co.com.tecni.sari.lógica.árboles.PerspectivaCartera;
import co.com.tecni.sari.lógica.árboles.PerspectivaClientes;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;

public class Lector {
    private HashMap<String, Inmueble> inmueblesxId = PerspectivaInmuebles.inmueblesxId;
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

    public PerspectivaInmuebles importarInmuebles() throws Exception {
        LectorInmueble li = new LectorInmueble();
        LectorAgrupaciones la = new LectorAgrupaciones(li);

        PerspectivaInmuebles perspectivaInmuebles = la.leer();
        perspectivaInmuebles.registrarIdentificadores();

        importarFichas();
        return perspectivaInmuebles;
    }

    private void importarFichas() throws Exception {
        LectorCatastral lectorCatastral = new LectorCatastral(inmueblesxId);
        LectorJurídica lectorJurídica = new LectorJurídica(inmueblesxId);
        LectorObraCivil lectorObraCivil = new LectorObraCivil(inmueblesxId);

        for (String nombreInmueble : PerspectivaInmuebles.inmueblesRaiz.keySet()) {
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

    public PerspectivaClientes genÁrbolContratos() {
        return new PerspectivaClientes(clientesComercialesxId);
    }

    public PerspectivaCartera genÁrbolCartera() {
        return new PerspectivaCartera(clientesComercialesxId);
    }

}
