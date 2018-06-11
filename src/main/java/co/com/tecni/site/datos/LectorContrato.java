package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.contratos.ClienteComercial;
import co.com.tecni.site.lógica.nodos.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.nodos.contratos.Contrato;
import co.com.tecni.site.lógica.nodos.contratos.Secuencia;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

class LectorContrato {

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
    private final static char COL_HCT_URL_OPENKM = 'D';
    // SECUENCIAS
    private final static String HS = "Secuencias";
    private final static char COL_HS_CT_ID = 'A';
    private final static char COL_HS_SC_ID = 'B';
    private final static char COL_HS_FECHA_INICIO = 'D';
    private final static char COL_HS_FECHA_FIN = 'E';
    private final static char COL_HS_CANON = 'F';
    private final static char COL_HS_FECHA_PRIMER_COBRO = 'G';
    private final static char COL_HS_FECHA_PRIMER_INCREMENTO = 'H';
    private final static char COL_HS_PERIODICIDAD_INCREMENTOS_POSTERIORES = 'I';
    private final static char COL_HS_ÍNDICE_BASE_INCREMENTO = 'J';
    private final static char COL_HS_PUNTOS_ADICIONALES_INCREMENTO = 'K';
    private final static char COL_HS_ESTADO_FACTURACIÓN = 'L';
    // SECUENCIAS CLIENTES FACTURACIÓN
    private final static String HSCF = "SecuenciaClientesFacturación";
    private final static char COL_HSCF_SC_ID = 'A';
    private final static char COL_HSCF_CF_NIT = 'D';
    private final static char COL_HSCF_PARTICIPACIÓN = 'F';
    // SECUENCIAS INMUEBLES
    private final static String HSI = "SecuenciaInmuebles";
    private final static char COL_HSI_SC_ID = 'A';
    private final static char COL_HSI_INMUEBLE_ID = 'B';
    private final static char COL_HSI_PARTICIPACIÓN = 'C';

    // CLIENTES
    private final static int colHcCcId = (int) COL_HC_CC_ID - 65;
    private final static int colHcCcNombre = (int) COL_HC_CC_NOMBRE - 65;
    private final static int colHcCfNit = (int) COL_HC_CF_NIT - 65;
    private final static int colHcCfRs = (int) COL_HC_CF_RS - 65;
    // CONTRATOS
    private final static int colHctCtId = (int) COL_HCT_CT_ID - 65;
    private final static int colHctCcId = (int) COL_HCT_CC_ID - 65;
    private final static int colHctUrlOpenkm = (int) COL_HCT_URL_OPENKM - 65;
    // SECUENCIAS
    private final static int colHsCtId = (int) COL_HS_CT_ID - 65;
    private final static int colHsScId = (int) COL_HS_SC_ID - 65;
    private final static int colHsFechaInicio = (int) COL_HS_FECHA_INICIO - 65;
    private final static int colHsFechaFin = (int) COL_HS_FECHA_FIN - 65;
    private final static int colHsCanon = (int) COL_HS_CANON - 65;
    private final static int colHsFechaPrimerCobro = (int) COL_HS_FECHA_PRIMER_COBRO - 65;
    private final static int colHsFechaPrimerIncremento = (int) COL_HS_FECHA_PRIMER_INCREMENTO - 65;
    private final static int colHsPeriodicidadIncrementosPosteriores = (int) COL_HS_PERIODICIDAD_INCREMENTOS_POSTERIORES - 65;
    private final static int colHsÍndiceBaseIncremento = (int) COL_HS_ÍNDICE_BASE_INCREMENTO - 65;
    private final static int colHsPuntosAdicionalesIncremento = (int) COL_HS_PUNTOS_ADICIONALES_INCREMENTO - 65;
    private final static int colHsEstadoFacturación = (int) COL_HS_ESTADO_FACTURACIÓN - 65;
    // SECUENCIAS CLIENTES FACTURACIÓN
    private final static int colHscfScId = (int) COL_HSCF_SC_ID - 65;
    private final static int colHscfCfNit = (int) COL_HSCF_CF_NIT -65;
    private final static int colHscfParticipación = (int) COL_HSCF_PARTICIPACIÓN -65;
    // SECUENCIAS INMUEBLES
    private final static int colHsiScId = (int) COL_HSI_SC_ID - 65;
    private final static int colHsiInmuebleId = (int) COL_HSI_INMUEBLE_ID - 65;
    private final static int colHsiParticipación = (int) COL_HSI_PARTICIPACIÓN - 65;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private XSSFWorkbook libro;

    private HashMap<String, Inmueble> inmuebles;

    HashMap<Integer, ClienteComercial> clientesComerciales;
    HashMap<Integer, ClienteFacturación> clientesFacturación;
    HashMap<Integer, Contrato> contratos;
    HashMap<Integer, Secuencia> secuencias;

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
        leerSecuencias();
        leerSecuenciasClientesFacturación();
        leerSecuenciasInmuebles();

        inputStream.close();
    }
    // -----------------------------------------------
    // Métodos de soporte
    // -----------------------------------------------
    private void leerClientes() {
        clientesComerciales = new HashMap<>();
        clientesFacturación = new HashMap<>();

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

        System.out.println("Recuperados "+ clientesComerciales.size() + " Clientes Comerciales y "
                + clientesFacturación.size() + " Clientes Facturación");
    }

    private void leerContratos() {
        contratos = new HashMap<>();

        Iterator<Row> filas = libro.getSheet(HCT).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();
            if(filaActual.getCell(colHctCtId) == null) break;

            int idContrato = Lector.entero(filaActual, colHctCtId);
            ClienteComercial clienteComercial = clientesComerciales.get(Lector.entero(filaActual, colHctCcId));
            String openKM = Lector.cadena(filaActual, colHctUrlOpenkm);

            Contrato.Json json = new Contrato.Json(openKM);
            Contrato contrato = new Contrato(idContrato, clienteComercial, json);

            contratos.put(idContrato, contrato);
        }

        System.out.println("Recuperados " + contratos.size() + " contratos");
    }

    private void leerSecuencias() {
        secuencias = new HashMap<>();

        Iterator<Row> filas = libro.getSheet(HS).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();
            if(filaActual.getCell(colHsScId) == null) break;

            int idSecuencia = Lector.entero(filaActual, colHsScId);
            int idContrato = Lector.entero(filaActual, colHsCtId);

            Contrato contrato = contratos.get(idContrato);
            if (contrato == null) {
                System.err.println("Contrato "+ idContrato + " no encontrado");
                continue;
            }

            Date inicio = Lector.fecha(filaActual, colHsFechaInicio);
            Date fin = Lector.fecha(filaActual, colHsFechaFin);

            double cánonMensual = Lector.doble(filaActual, colHsCanon);
            Date primerCobro = Lector.fecha(filaActual, colHsFechaPrimerCobro);

            Date primerIncremento = Lector.fecha(filaActual, colHsFechaPrimerIncremento);
            int periodicidadIncremento = Lector.entero(filaActual, colHsPeriodicidadIncrementosPosteriores);
            String índiceBaseIncremento = Lector.cadena(filaActual, colHsÍndiceBaseIncremento);
            double ptosAdicionalesIncremento = Lector.doble(filaActual, colHsPuntosAdicionalesIncremento);
            Secuencia.Cánon.Incremento incremento = new Secuencia.Cánon.Incremento(índiceBaseIncremento, ptosAdicionalesIncremento, periodicidadIncremento, primerIncremento);

            Secuencia.Cánon cánon = new Secuencia.Cánon(cánonMensual, primerCobro, incremento);

            Secuencia secuencia = new Secuencia(idSecuencia, inicio, fin, contrato, cánon);

            secuencias.put(idSecuencia, secuencia);
        }

        System.out.println("Recuperadas "+ secuencias.size() + " secuencias");

    }

    private void leerSecuenciasClientesFacturación() {
        Iterator<Row> filas = libro.getSheet(HSCF).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();
            if (filaActual.getCell(colHscfScId) == null) break;

            int idSecuencia = Lector.entero(filaActual, colHscfScId);
            Secuencia secuencia = secuencias.get(idSecuencia);
            if (secuencia == null) {
                System.err.println("Secuencia "+ idSecuencia + " no encontrada");
                continue;
            }

            int nitClienteFacturación = Lector.entero(filaActual, colHscfCfNit);
            ClienteFacturación clienteFacturación = clientesFacturación.get(nitClienteFacturación);
            if (clienteFacturación == null) {
                System.err.println("ClienteFacturación"+ nitClienteFacturación + " no encontrado para secuencia "+idSecuencia);
                continue;
            }

            double participación = Lector.doble(filaActual, colHscfParticipación);
            secuencia.agregarClienteFacturación(clienteFacturación, participación);
        }

        for (Secuencia secuencia : secuencias.values())
            try {
                secuencia.verificarParticipaciónClientesFacturación();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

    }

    private void leerSecuenciasInmuebles() {
        Iterator<Row> filas = libro.getSheet(HSI).iterator();
        Row filaActual = filas.next();
        int contador = 0;

        while (filas.hasNext()) {
            filaActual = filas.next();
            if (filaActual.getCell(colHsiScId) == null) break;

            int idSecuencia = Lector.entero(filaActual, colHsiScId);
            Secuencia secuencia = secuencias.get(idSecuencia);

            String idInmueble = Lector.cadena(filaActual, colHsiInmuebleId);
            Inmueble inmueble = inmuebles.get(idInmueble);

            if (secuencia == null || inmueble == null) {
                System.err.println("Error al recuperar secuencia "+idSecuencia+" o inmueble "+ idInmueble);
                continue;
            }

            Double participación = Lector.doble(filaActual, colHsiParticipación);

            secuencia.agregarInmueble(inmueble, participación);
            contador++;
        }

        for (Secuencia secuencia : secuencias.values())
            try {
                secuencia.verificarParticipaciónInmuebles();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        System.out.println("Asociados "+contador+ " inmuebles a "+secuencias.size()+" secuencias en "+contratos.size() +" contratos");
    }
}
