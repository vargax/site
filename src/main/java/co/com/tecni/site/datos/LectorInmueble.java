package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.inmuebles.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

import java.io.InputStream;
import java.util.*;

class LectorInmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HOJA_NOMBRE = "Cuadro de Areas";

    private final static char COL_HIJOS = 'A';
    private final static char COL_TIPO = 'B';
    private final static char COL_NOMBRE = 'C';
    private final static char COL_PRIVADO_CONSTRUIDOS = 'L';
    private final static char COL_PRIVADO_LIBRES = 'M';
    private final static char COL_COMUNES_CONSTRUIDO = 'N';
    private final static char COL_COMUNES_LIBRES = 'O';

    private final static char COL_VALOR_RAZONABLE = 'S';

    private final static int FILA_TIPOS_CARACTERÍSTICAS = 2;
    private final static char COL_CARACTERÍSTICAS = 'T';

    private final static String HIJOS_INICIO = "inicio";
    private final static String HIJOS_FIN = "fin";
    private final static String SALTAR = "saltar";

    private final static String[] TIPOS_CARACTERÍSTICAS = {"double", "String", "int"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String paqueteTipos;

    private ArrayList<String[]> característicasxImportar;

    private Iterator<Row> filas;
    private Row filaActual;

    private Iterator<Cell> columnas;
    private Cell columnaActual;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    LectorInmueble() {
        paqueteTipos = Inmueble.class.getPackage().toString().substring(8)+'.';
    }

    // -----------------------------------------------
    // Métodos principales
    // -----------------------------------------------
    Inmueble leer(String nombreInmueble) throws Exception {
        InputStream inputStream = LectorInmueble.class.getResourceAsStream("/static/archivos/inmueble " + nombreInmueble + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        filas = libro.getSheet(HOJA_NOMBRE).iterator();

        cargarCaracterísticas();

        while (!inicioInmueble())
            filaActual = filas.next();

        Inmueble inmueble = recursión();

        inputStream.close();
        return inmueble;
    }

    private Inmueble recursión() throws Exception {
        String tipo = paqueteTipos+Lector.cadena(filaActual, COL_TIPO);
        String nombre = Lector.cadena(filaActual, COL_NOMBRE);
        JSONObject características = leerCaracterísticas();

        ArrayList<Inmueble> hijos = new ArrayList<>();

        filaActual = filas.next();
        while (!finInmueble()) {
            if (inicioInmueble())
                hijos.add(recursión());
            else if (!saltar())
                hijos.add(hoja());
            filaActual = filas.next();
        }

        Inmueble inmueble = Inmueble.englobar(tipo, nombre, características, hijos);
        System.out.println("r "+inmueble.infoNodo(null));
        return inmueble;
    }

    private Inmueble hoja() throws Exception {
        String tipo = paqueteTipos+Lector.cadena(filaActual, COL_TIPO);
        String nombre = Lector.cadena(filaActual, COL_NOMBRE);
        double valor = Lector.doble(filaActual, COL_VALOR_RAZONABLE);
        JSONObject características = leerCaracterísticas();

        HashMap<String, Double> metros = new HashMap<>();
        metros.put(Inmueble.A_PRIV_CONSTRUIDOS, Lector.doble(filaActual, COL_PRIVADO_CONSTRUIDOS));
        metros.put(Inmueble.A_PRIV_LIBRES, Lector.doble(filaActual, COL_PRIVADO_LIBRES));
        metros.put(Inmueble.A_COM_CONSTRUIDOS, Lector.doble(filaActual,COL_COMUNES_CONSTRUIDO));
        metros.put(Inmueble.A_COM_LIBRES, Lector.doble(filaActual, COL_COMUNES_LIBRES));

        Inmueble inmueble = Inmueble.hoja(tipo, nombre, valor, características, metros);
        System.out.println("h "+inmueble.infoNodo(null));
        return inmueble;
    }

    // -----------------------------------------------
    // Métodos soporte
    // -----------------------------------------------
    private JSONObject leerCaracterísticas() {
        JSONObject características = new JSONObject();

        inicioCaracterísticas();
        for (String[] característica : característicasxImportar) {

            try {
                columnaActual = columnas.next();

                if (característica[0].equals(TIPOS_CARACTERÍSTICAS[0])) {
                    Double valor = columnaActual.getNumericCellValue();
                    if (valor != 0) características.put(característica[1], valor);
                } else if (característica[0].equals(TIPOS_CARACTERÍSTICAS[1])) {
                    String valor = columnaActual.getStringCellValue();
                    if (valor.length() != 0) características.put(característica[1], valor);
                } else if (característica[0].equals(TIPOS_CARACTERÍSTICAS[2])) {
                    int valor = ((Double) columnaActual.getNumericCellValue()).intValue();
                    if (valor != 0) características.put(característica[1], valor);
                }

            } catch (NoSuchElementException e) {
                break;
            } catch (IllegalStateException e) {
                System.err.println("Característica '"+característica[1]+"' no coincide con su tipo '"+característica[0]+"' en celda "+columnaActual.getAddress().formatAsString());
                System.err.println(" "+e.getMessage());
            }
        }

        return características;
    }

    private void cargarCaracterísticas() throws Exception {
        for (int i = 0; i < FILA_TIPOS_CARACTERÍSTICAS; i++ )
            filaActual = filas.next();

        inicioCaracterísticas();
        ArrayList<String> tipos = new ArrayList<>();
        while (columnas.hasNext()) {
            columnaActual = columnas.next();
            String tipoCaracterística = columnaActual.getStringCellValue();
            if (tipoCaracterística.length() == 0)
                break;
            if (!Arrays.asList(TIPOS_CARACTERÍSTICAS).contains(tipoCaracterística))
                throw new Exception("Tipo característica "+ tipoCaracterística + " no admitido");

            tipos.add(tipoCaracterística);
        }

        filaActual = filas.next();

        inicioCaracterísticas();
        ArrayList<String> nombres = new ArrayList<>();
        while (columnas.hasNext()) {
            columnaActual = columnas.next();
            String nombreCaracterística = columnaActual.getStringCellValue();
            if (nombreCaracterística.length() == 0)
                break;

            nombres.add(nombreCaracterística);
        }

        if (tipos.size() != nombres.size()) {
            System.err.println("ADVERTENCIA: Se identificaron "+tipos.size()+" tipos y "+nombres.size()+ " nombres.");
            System.err.println("  TIPOS: "+tipos);
            System.err.println("  NOMBRES: "+nombres);
        }

        característicasxImportar = new ArrayList<>();
        for (int i = 0; i < tipos.size(); i++) {
            String[] tipoNombre = new String[2];
            tipoNombre[0] = tipos.get(i);
            tipoNombre[1] = nombres.get(i);
            característicasxImportar.add(tipoNombre);
        }

        String cadena = "Características: ";
        for (String[] tipoNombre : característicasxImportar) {
            cadena += tipoNombre[1]+":"+tipoNombre[0]+" / ";
        }
        System.err.println(cadena);
    }

    private void inicioCaracterísticas() {
        Cell columnaObjetivo = filaActual.getCell(Lector.charToInt(COL_CARACTERÍSTICAS));

        columnas = filaActual.cellIterator();
        while (columnaActual != columnaObjetivo)
            try {
                columnaActual = columnas.next();
            } catch (NoSuchElementException e) {
                break;
            }
    }

    private boolean inicioInmueble() {
        return filaActual.getCell(Lector.charToInt(COL_HIJOS)) != null && HIJOS_INICIO.equals(Lector.cadena(filaActual, COL_HIJOS));
    }

    private boolean finInmueble() {
        return filaActual.getCell(Lector.charToInt(COL_HIJOS)) != null && HIJOS_FIN.equals(Lector.cadena(filaActual, COL_HIJOS));
    }

    private boolean saltar() {
        return filaActual.getCell(Lector.charToInt(COL_HIJOS)) != null && SALTAR.equals(Lector.cadena(filaActual, COL_HIJOS));
    }
}
