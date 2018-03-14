package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.contrato.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contrato.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contrato.Contrato;
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

    private final static String HCC = "ContratosClientes";
    private final static char COL_HCC_NUM_CONTRATO = 'A';
    private final static char COL_HCC_ID_CC = 'B';
    private final static char COL_HCC_NIT_CF = 'D';
    private final static char COL_HCC_SUBTOTAL = 'F';

    private final static String HCI = "ContratosInmuebles";
    private final static char COL_HCI_NUM_CONTRATO = 'A';
    private final static char COL_HCI_ID_INMUEBLE = 'B';
    private final static char COL_HCI_PARTICIPACIÓN = 'C';

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private XSSFWorkbook libro;

    private int colHcCcId;
    private int colHcCcNombre;
    private int colHcCfNit;
    private int colHcCfRs;

    private int colHccNumContrato;
    private int colHccIdCc;
    private int colHccNitCf;
    private int colHccSubtotal;

    private int colHciNumContrato;
    private int colHciIdInmueble;
    private int colHciParticipación;

    private HashMap<Integer, ClienteComercial> clientesComerciales;
    private HashMap<Integer, ClienteFacturación> clientesFacturación;

    private HashMap<String, Inmueble> inmuebles;
    private HashMap<Integer, Contrato> contratos;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorContrato(HashMap<String, Inmueble> inmuebles) {
        colHcCcId = (int) COL_HC_CC_ID - 65;
        colHcCcNombre = (int) COL_HC_CC_NOMBRE - 65;
        colHcCfNit = (int) COL_HC_CF_NIT - 65;
        colHcCfRs = (int) COL_HC_CF_RS - 65;

        colHccNumContrato = (int) COL_HCC_NUM_CONTRATO -65;
        colHccIdCc = (int) COL_HCC_ID_CC - 65;
        colHccNitCf = (int) COL_HCC_NIT_CF -65;
        colHccSubtotal = (int) COL_HCC_SUBTOTAL -65;

        colHciNumContrato = (int) COL_HCI_NUM_CONTRATO - 65;
        colHciIdInmueble = (int) COL_HCI_ID_INMUEBLE - 65;
        colHciParticipación = (int) COL_HCI_PARTICIPACIÓN - 65;

        this.inmuebles = inmuebles;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void leer() throws Exception {
        InputStream inputStream = LectorContrato.class.getResourceAsStream("/static/archivos/clientes y contratos.xlsx");
        libro = new XSSFWorkbook(inputStream);

        leerClientes();
        leerContratosClientes();
        leerContratosInmuebles();

        inputStream.close();
    }

    public void setClientesComerciales(HashMap<Integer, ClienteComercial> clientesComerciales) {
        this.clientesComerciales = clientesComerciales;
    }

    public void setClientesFacturación(HashMap<Integer, ClienteFacturación> clientesFacturación) {
        this.clientesFacturación = clientesFacturación;
    }

    public void setContratos(HashMap<Integer, Contrato> contratos) {
        this.contratos = contratos;
    }

    // -----------------------------------------------
    // Métodos de soporte
    // -----------------------------------------------
    private void leerClientes() {
        Iterator<Row> filas = libro.getSheet(HC).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();
            if (filaActual.getCell(colHcCcId) == null) break;

            int id = ((Double) filaActual.getCell(colHcCcId).getNumericCellValue()).intValue();
            ClienteComercial clienteComercial = clientesComerciales.get(id);
            if (clienteComercial == null) {
                String nombreClienteComercial = filaActual.getCell(colHcCcNombre).getStringCellValue();
                clienteComercial = new ClienteComercial(id, nombreClienteComercial);
                clientesComerciales.put(id, clienteComercial);
            }

            int nit = ((Double) filaActual.getCell(colHcCfNit).getNumericCellValue()).intValue();
            String nombreClienteFacturación = filaActual.getCell(colHcCfRs).getStringCellValue();
            ClienteFacturación clienteFacturación = new ClienteFacturación(clienteComercial, nit, nombreClienteFacturación);
            clientesFacturación.put(nit, clienteFacturación);
        }

        System.err.println("Recuperados "+ clientesComerciales.size() + " Clientes Comerciales y "
                + clientesFacturación.size() + " Clientes Facturación");
    }

    private void leerContratosClientes() {
        Iterator<Row> filas = libro.getSheet(HCC).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();
            if (filaActual.getCell(colHccNumContrato) == null) break;

            int numContrato = ((Double) filaActual.getCell(colHccNumContrato).getNumericCellValue()).intValue();
            Contrato contrato = contratos.get(numContrato);
            if (contrato == null) {
                ClienteComercial clienteComercial = clientesComerciales.get(((Double) filaActual.getCell(colHccIdCc).getNumericCellValue()).intValue());
                contrato = new Contrato(numContrato, clienteComercial);
                contratos.put(contrato.getNumContrato(), contrato);
            }

            int nitClienteFacturación = ((Double) filaActual.getCell(colHccNitCf).getNumericCellValue()).intValue();
            ClienteFacturación clienteFacturación = clientesFacturación.get(nitClienteFacturación);

            double subtotal = filaActual.getCell(colHccSubtotal).getNumericCellValue();
            contrato.agregarClienteFacturación(clienteFacturación, subtotal);
        }

        System.err.println("Recuperados " + contratos.size() + " Contratos");
    }

    private void leerContratosInmuebles() {
        Iterator<Row> filas = libro.getSheet(HCI).iterator();
        Row filaActual = filas.next();
        int contador = 0;

        while (filas.hasNext()) {
            filaActual = filas.next();
            if (filaActual.getCell(colHciNumContrato) == null) break;

            int numContrato = ((Double) filaActual.getCell(colHciNumContrato).getNumericCellValue()).intValue();
            String idInmueble = filaActual.getCell(colHciIdInmueble).getStringCellValue();
            Double participación = filaActual.getCell(colHciParticipación).getNumericCellValue();

            Contrato contrato = contratos.get(numContrato);
            if (contrato == null) {
                System.err.println("Contrato "+ numContrato + " no encontrado");
                continue;
            }

            Inmueble inmueble = inmuebles.get(idInmueble);
            if (inmueble == null) {
                System.err.println("Inmueble "+ idInmueble + " no encontrado");
                continue;
            }

            contrato.agregarInmueble(inmueble, participación);
            contador++;
        }

        for (Contrato contrato : contratos.values())
            if (contrato.sumaParicipaciones() != 1)
                System.err.println(" Suma participaciones para contrato "+ contrato.getNumContrato() + " es "+ contrato.sumaParicipaciones());

        System.err.println("Asociados "+contador+ " inmuebles a "+contratos.size()+" contratos");
    }
}
