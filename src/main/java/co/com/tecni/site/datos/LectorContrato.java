package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contratos.Contrato;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class LectorContrato {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    // CLIENTES
    private final static String HC = "Clientes";
    private final static char COL_HC_CC_ID = 'A';       // Cliente comercial
    private final static char COL_HC_CC_NOMBRE = 'B';
    private final static char COL_HC_CF_NIT = 'C';      // Cliente Facturación
    private final static char COL_HC_CF_RS = 'D';

    // CONTRATOS
    private final static String HCT = "Contratos";
    private final static char COL_HCT_CT_ID = 'A';
    private final static char COL_HCT_CC_ID = 'B';

    // CONTRATOS FACTURACIÓN
    private final static String HCF = "ContratosFacturacion";
    private final static char COL_HCF_CT_NUM = 'B';
    private final static char COL_HCF_CF_NIT = 'E';
    private final static char COL_HCF_PARTICIPACIÓN = 'G';

    // CONTRATOS INMUEBLES
    private final static String HCI = "ContratosInmuebles";
    private final static char COL_HCI_CT_NUM = 'B';
    private final static char COL_HCI_INMUEBLE_ID = 'C';
    private final static char COL_HCI_PARTICIPACIÓN = 'D';

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private XSSFWorkbook libro;

    private HashMap<Integer, ClienteComercial> clientesComerciales;
    private HashMap<Integer, ClienteFacturación> clientesFacturación;

    private HashMap<String, Inmueble> inmuebles;
    private HashMap<Integer, Contrato> contratos;

    // CLIENTES
    private int colHcCcId = (int) COL_HC_CC_ID - 65;
    private int colHcCcNombre = (int) COL_HC_CC_NOMBRE - 65;
    private int colHcCfNit = (int) COL_HC_CF_NIT - 65;
    private int colHcCfRs = (int) COL_HC_CF_RS - 65;
    // CONTRATOS
    private int colHctCtId = (int) COL_HCT_CT_ID - 65;
    private int colHctCcId = (int) COL_HCT_CC_ID - 65;
    // CONTRATOS FACTURACIÓN
    private int colHcfCtNum = (int) COL_HCF_CT_NUM - 65;
    private int colHcfNitCf = (int) COL_HCF_CF_NIT -65;
    private int colHcfParticipación = (int) COL_HCF_PARTICIPACIÓN -65;
    // CONTRATOS INMUEBLES
    private int colHciCtNum = (int) COL_HCI_CT_NUM - 65;
    private int colHciInmuebleId = (int) COL_HCI_INMUEBLE_ID - 65;
    private int colHciParticipación = (int) COL_HCI_PARTICIPACIÓN - 65;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorContrato(HashMap<String, Inmueble> inmuebles) {

        this.inmuebles = inmuebles;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void leer() throws Exception {
        InputStream inputStream = LectorContrato.class.getResourceAsStream("/static/archivos/clientes y contratos.xlsx");
        libro = new XSSFWorkbook(inputStream);

        leerClientes();
        leerContratos();
        leerContratosFacturación();
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

    private void leerContratos() {
        Iterator<Row> filas = libro.getSheet(HCT).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();
            if(filaActual.getCell(colHctCtId) == null) break;

            int numContrato = ((Double) filaActual.getCell(colHctCtId).getNumericCellValue()).intValue();
            ClienteComercial clienteComercial = clientesComerciales.get(((Double) filaActual.getCell(colHctCcId).getNumericCellValue()).intValue());

            Contrato contrato = new Contrato(numContrato, clienteComercial);

            contratos.put(numContrato, contrato);
        }

        System.err.println("Recuperados " + contratos.size() + " Contratos");
    }

    private void leerContratosFacturación() {
        Iterator<Row> filas = libro.getSheet(HCF).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();
            if (filaActual.getCell(colHcfCtNum) == null) break;

            int numContrato = ((Double) filaActual.getCell(colHcfCtNum).getNumericCellValue()).intValue();
            Contrato contrato = contratos.get(numContrato);
            if (contrato == null) {
                System.err.println("Contrato "+ numContrato + " no encontrado");
                continue;
            }

            int nitClienteFacturación = ((Double) filaActual.getCell(colHcfNitCf).getNumericCellValue()).intValue();
            ClienteFacturación clienteFacturación = clientesFacturación.get(nitClienteFacturación);

            double participación = filaActual.getCell(colHcfParticipación).getNumericCellValue();
            contrato.agregarClienteFacturación(clienteFacturación, participación);
        }

        for (Contrato contrato : contratos.values())
            if (contrato.participaciónClientesFacturación() != 1)
                System.err.println(" Suma participaciones ClientesFacturación para contrato "+ contrato.getNumContrato() + " es "+ contrato.participaciónInmuebles());
    }

    private void leerContratosInmuebles() {
        Iterator<Row> filas = libro.getSheet(HCI).iterator();
        Row filaActual = filas.next();
        int contador = 0;

        while (filas.hasNext()) {
            filaActual = filas.next();
            if (filaActual.getCell(colHciCtNum) == null) break;

            int numContrato = ((Double) filaActual.getCell(colHciCtNum).getNumericCellValue()).intValue();
            String idInmueble = filaActual.getCell(colHciInmuebleId).getStringCellValue();
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
            if (contrato.participaciónInmuebles() != 1)
                System.err.println(" Suma participaciones para contrato "+ contrato.getNumContrato() + " es "+ contrato.participaciónInmuebles());

        System.err.println("Asociados "+contador+ " inmuebles a "+contratos.size()+" contratos");
    }
}
