package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.contratos.ClienteComercial;
import co.com.tecni.site.lógica.contratos.ClienteFacturación;
import co.com.tecni.site.lógica.contratos.Contrato;
import co.com.tecni.site.lógica.contratos.Secuencia;
import co.com.tecni.site.lógica.inmuebles.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;

class LectorContrato {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    // CLIENTES
    private final static String HC = "Clientes";
    private final static char HC_CC_ID = 'A';       // Cliente comercial
    private final static char HC_CC_NOMBRE = 'B';
    private final static char HC_CF_NIT = 'C';      // Cliente Facturación
    private final static char HC_CF_RS = 'D';
    // CONTRATOS
    private final static String HCT = "Contratos";
    private final static char HCT_CT_ID = 'A';
    private final static char HCT_CC_ID = 'B';
    private final static char HCT_URL_OPENKM = 'D';
    // SECUENCIAS
    private final static String HS = "Secuencias";
    private final static char HS_CT_ID = 'A';
    private final static char HS_SC_ID = 'B';
    private final static char HS_FECHA_INICIO = 'D';
    private final static char HS_FECHA_FIN = 'E';
    private final static char HS_CANON = 'F';
    private final static char HS_FECHA_PRIMER_COBRO = 'G';
    private final static char HS_FECHA_PRIMER_INCREMENTO = 'H';
    private final static char HS_PERIODICIDAD_INCREMENTOS_POSTERIORES = 'I';
    private final static char HS_ÍNDICE_BASE_INCREMENTO = 'J';
    private final static char HS_PUNTOS_ADICIONALES_INCREMENTO = 'K';
    private final static char HS_ESTADO_FACTURACIÓN = 'L';
    // SECUENCIAS CLIENTES FACTURACIÓN
    private final static String HSCF = "SecuenciaClientesFacturación";
    private final static char HSCF_SC_ID = 'A';
    private final static char HSCF_CF_NIT = 'D';
    private final static char HSCF_PARTICIPACIÓN = 'F';
    // SECUENCIAS INMUEBLES
    private final static String HSI = "SecuenciaInmuebles";
    private final static char HSI_SC_ID = 'A';
    private final static char HSI_INMUEBLE_ID = 'B';
    private final static char HSI_PARTICIPACIÓN = 'C';

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
            if (filaActual.getCell(Lector.charToInt(HC_CC_ID)) == null) break;

            int id = Lector.entero(filaActual, HC_CC_ID);
            ClienteComercial clienteComercial = clientesComerciales.get(id);
            if (clienteComercial == null) {
                String nombreClienteComercial = Lector.cadena(filaActual, HC_CC_NOMBRE);
                clienteComercial = new ClienteComercial(id, nombreClienteComercial);
                clientesComerciales.put(id, clienteComercial);
            }

            int nit = Lector.entero(filaActual, HC_CF_NIT);
            String nombreClienteFacturación = Lector.cadena(filaActual, HC_CF_RS);
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
            if(filaActual.getCell(Lector.charToInt(HCT_CT_ID)) == null) break;

            int idContrato = Lector.entero(filaActual, HCT_CT_ID);
            ClienteComercial clienteComercial = clientesComerciales.get(Lector.entero(filaActual, HCT_CC_ID));
            String openKM = Lector.cadena(filaActual, HCT_URL_OPENKM);

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
            if(filaActual.getCell(Lector.charToInt(HS_SC_ID)) == null) break;

            int idSecuencia = Lector.entero(filaActual, HS_SC_ID);
            int idContrato = Lector.entero(filaActual, HS_CT_ID);

            Contrato contrato = contratos.get(idContrato);
            if (contrato == null) {
                System.err.println("Contrato "+ idContrato + " no encontrado");
                continue;
            }

            LocalDate inicio = Lector.fecha(filaActual, HS_FECHA_INICIO);
            LocalDate fin = Lector.fecha(filaActual, HS_FECHA_FIN);

            double cánonMensual = Lector.doble(filaActual, HS_CANON);
            LocalDate primerCobro = Lector.fecha(filaActual, HS_FECHA_PRIMER_COBRO);

            LocalDate primerIncremento = Lector.fecha(filaActual, HS_FECHA_PRIMER_INCREMENTO);
            int periodicidadIncremento = Lector.entero(filaActual, HS_PERIODICIDAD_INCREMENTOS_POSTERIORES);
            String índiceBaseIncremento = Lector.cadena(filaActual, HS_ÍNDICE_BASE_INCREMENTO);
            double ptosAdicionalesIncremento = Lector.doble(filaActual, HS_PUNTOS_ADICIONALES_INCREMENTO);
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
            if (filaActual.getCell(Lector.charToInt(HSCF_SC_ID)) == null) break;

            int idSecuencia = Lector.entero(filaActual, HSCF_SC_ID);
            Secuencia secuencia = secuencias.get(idSecuencia);
            if (secuencia == null) {
                System.err.println("Secuencia "+ idSecuencia + " no encontrada");
                continue;
            }

            int nitClienteFacturación = Lector.entero(filaActual, HSCF_CF_NIT);
            ClienteFacturación clienteFacturación = clientesFacturación.get(nitClienteFacturación);
            if (clienteFacturación == null) {
                System.err.println("ClienteFacturación"+ nitClienteFacturación + " no encontrado para secuencia "+idSecuencia);
                continue;
            }

            double participación = Lector.doble(filaActual, HSCF_PARTICIPACIÓN);
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
            if (filaActual.getCell(Lector.charToInt(HSI_SC_ID)) == null) break;

            int idSecuencia = Lector.entero(filaActual, HSI_SC_ID);
            Secuencia secuencia = secuencias.get(idSecuencia);

            String idInmueble = Lector.cadena(filaActual, HSI_INMUEBLE_ID);
            Inmueble inmueble = inmuebles.get(idInmueble);

            if (secuencia == null || inmueble == null) {
                System.err.println("Error al recuperar secuencia "+idSecuencia+" o inmueble "+ idInmueble);
                continue;
            }

            Double participación = Lector.doble(filaActual, HSI_PARTICIPACIÓN);

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
