package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.contrato.ClienteComercial;
import co.com.tecni.site.lógica.contrato.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.inmueble.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class LectorContrato {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    private final static String HC = "Clientes";
    // Cliente comercial
    private final static char COL_HC_CC_ID = 'A';
    private final static char COL_HC_CC_NOMBRE = 'B';
    // Cliente Facturación
    private final static char COL_HC_CF_NIT = 'C';
    private final static char COL_HC_CF_RS = 'D';

    private final static String HCI = "ContratosInmuebles";

    private final static String HCC = "ContratosClientes";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private HashMap<String, Inmueble> inmuebles;
    private XSSFWorkbook libro;

    private int colHcCcId;
    private int colHcCcNombre;
    private int colHcCfNit;
    private int colHcCfRs;

    private HashMap<Integer, ClienteComercial> clientesComerciales;
    private HashMap<Integer, ClienteFacturación> clientesFacturación;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorContrato(HashMap<String, Inmueble> inmuebles) {
        colHcCcId = (int)COL_HC_CC_ID - 65;
        colHcCcNombre = (int)COL_HC_CC_NOMBRE - 65;
        colHcCfNit = (int) COL_HC_CF_NIT - 65;
        colHcCfRs = (int) COL_HC_CF_RS - 65;

        this.inmuebles = inmuebles;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void leer() throws Exception {
        InputStream inputStream = LectorContrato.class.getResourceAsStream("/static/archivos/clientes y contratos.xlsx");
        libro = new XSSFWorkbook(inputStream);

        clientesComerciales = new HashMap<>();
        clientesFacturación = new HashMap<>();

        leerClientes();

        System.out.println("Clientes comerciales: "+clientesComerciales.size());
        System.out.println("Clientes facturación: "+clientesFacturación.size());

        inputStream.close();
    }

    private void leerClientes() {
        Iterator<Row> filas = libro.getSheet(HC).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();

            int id = ((Double) filaActual.getCell(colHcCcId).getNumericCellValue()).intValue();
            ClienteComercial clienteComercial = clientesComerciales.get(id);
            if (clienteComercial == null) {
                String nombreClienteComercial = filaActual.getCell(colHcCcNombre).getStringCellValue();
                clienteComercial = new ClienteComercial(id, nombreClienteComercial);
                clientesComerciales.put(id, clienteComercial);
            }

            int nit = ((Double) filaActual.getCell(colHcCfNit).getNumericCellValue()).intValue();
            String nombreClienteFacturación = filaActual.getCell(colHcCcNombre).getStringCellValue();
            ClienteFacturación clienteFacturación = new ClienteFacturación(clienteComercial, nit, nombreClienteFacturación);
            clientesFacturación.put(nit, clienteFacturación);
        }
    }
}
