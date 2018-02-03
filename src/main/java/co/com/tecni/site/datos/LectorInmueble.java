package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.inmueble.tipos._Inmueble;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

import java.io.InputStream;
import java.util.*;

public class LectorInmueble {
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

    private final static int FILA_TIPOS_CARACTERÍSTICAS = 2;
    private final static char COL_INICIO_CARACTERÍSTICAS = 'S';

    private final static String HIJOS_INICIO = "inicio";
    private final static String HIJOS_FIN = "fin";
    private final static String SALTAR = "saltar";

    private final static String[] TIPOS_CARACTERÍSTICAS = {"double", "String", "int"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String paqueteTipos;

    private int colHijos;
    private int colTipo;
    private int colNombre;

    private int colPrivadoConstruido;
    private int colPrivadoLibre;
    private int colComunConstruido;
    private int colComunLibre;

    private int colInicioCaracterísticas;
    private HashMap<String, String> característicasxImportar;

    private Iterator<Row> filas;
    private Row filaActual;

    private Iterator<Cell> columnas;
    private Cell columnaActual;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorInmueble() {
        paqueteTipos = _Inmueble.class.getPackage().toString().substring(8)+'.';

        colHijos = (int)COL_HIJOS - 65;
        colTipo = (int)COL_TIPO - 65;
        colNombre = (int)COL_NOMBRE - 65;
        colPrivadoConstruido = (int)COL_PRIVADO_CONSTRUIDOS - 65;
        colPrivadoLibre = (int)COL_PRIVADO_LIBRES - 65;
        colComunConstruido = (int)COL_COMUNES_CONSTRUIDO - 65;
        colComunLibre = (int)COL_COMUNES_LIBRES - 65;

        colInicioCaracterísticas = (int) COL_INICIO_CARACTERÍSTICAS - 65;
    }

    // -----------------------------------------------
    // Métodos principales
    // -----------------------------------------------
    public _Inmueble leer(String nombreArchivo) throws Exception {
        InputStream inputStream = LectorInmueble.class.getResourceAsStream("/static/archivos/inmueble " + nombreArchivo + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        filas = libro.getSheet(HOJA_NOMBRE).iterator();

        cargarCaracterísticas();

        while (!inicioInmueble())
            filaActual = filas.next();

        _Inmueble inmueble = recursión();

        inputStream.close();
        System.out.println(inmueble);
        return inmueble;
    }

    private _Inmueble recursión() throws Exception {
        String tipo = paqueteTipos+filaActual.getCell(colTipo).getStringCellValue();
        String nombre = filaActual.getCell(colNombre).getStringCellValue();
        JSONObject características = leerCaracterísticas();

        ArrayList<_Inmueble> hijos = new ArrayList<>();

        filaActual = filas.next();
        while (!finInmueble()) {
            if (inicioInmueble())
                hijos.add(recursión());
            else if (!saltar())
                hijos.add(hoja());
            filaActual = filas.next();
        }

        _Inmueble inmueble = _Inmueble.englobar(tipo, nombre, características, hijos);
        System.out.println("r "+inmueble);
        return inmueble;
    }

    private _Inmueble hoja() throws Exception {
        String tipo = paqueteTipos+filaActual.getCell(colTipo).getStringCellValue();
        String nombre = filaActual.getCell(colNombre).getStringCellValue();
        JSONObject características = leerCaracterísticas();

        System.out.println("h "+nombre);

        HashMap<String, Double> metros = new HashMap<>();
        metros.put(_Inmueble.PRIV_CONSTRUIDOS, filaActual.getCell(colPrivadoConstruido).getNumericCellValue());
        metros.put(_Inmueble.PRIV_LIBRES, filaActual.getCell(colPrivadoLibre).getNumericCellValue());
        metros.put(_Inmueble.COM_CONSTRUIDOS, filaActual.getCell(colComunConstruido).getNumericCellValue());
        metros.put(_Inmueble.COM_LIBRES, filaActual.getCell(colComunLibre).getNumericCellValue());

        _Inmueble inmueble = _Inmueble.hoja(tipo, nombre, características, metros);
        System.out.println("h "+inmueble);
        return inmueble;
    }

    // -----------------------------------------------
    // Métodos soporte
    // -----------------------------------------------

    private JSONObject leerCaracterísticas() {
        JSONObject características = new JSONObject();

        inicioCaracterísticas();

        for (Map.Entry<String, String> característica : característicasxImportar.entrySet()) {

            try {
                columnaActual = columnas.next();
            } catch (NoSuchElementException e) {
                break;
            }

            if (característica.getValue().equals(TIPOS_CARACTERÍSTICAS[0])) {
                Double valor = columnaActual.getNumericCellValue();
                if (valor != 0) características.put(característica.getKey(), valor);
            } else if (característica.getValue().equals(TIPOS_CARACTERÍSTICAS[1])) {
                String valor = columnaActual.getStringCellValue();
                if (valor.length() != 0) características.put(característica.getKey(), valor);
            } else if (característica.getValue().equals(TIPOS_CARACTERÍSTICAS[2])) {
                int valor = ((Double) columnaActual.getNumericCellValue()).intValue();
                if (valor != 0) características.put(característica.getKey(), valor);
            }
        }

        return características;
    }

    private void cargarCaracterísticas() throws Exception {
        for (int i = 0; i < FILA_TIPOS_CARACTERÍSTICAS; i++ )
            filaActual = filas.next();

        ArrayList<String> tipos = new ArrayList<>();
        ArrayList<String> nombres = new ArrayList<>();

        inicioCaracterísticas();
        while (columnas.hasNext()) {
            String tipoCaracterística = columnaActual.getStringCellValue();
            if (tipoCaracterística.length() == 0)
                break;
            if (!Arrays.asList(TIPOS_CARACTERÍSTICAS).contains(tipoCaracterística))
                throw new Exception("Tipo característica "+ tipoCaracterística + " no admitido");

            tipos.add(columnaActual.getStringCellValue());

            columnaActual = columnas.next();
        }

        filaActual = filas.next();
        inicioCaracterísticas();
        while (columnas.hasNext()) {
            nombres.add(columnaActual.getStringCellValue());
            columnaActual = columnas.next();
        }
        nombres.add(columnaActual.getStringCellValue());

        if (tipos.size() != nombres.size()) {
            System.err.println("ADVERTENCIA: Se identificaron "+tipos.size()+" tipos y "+nombres.size()+ " nombres.");
            System.err.println("  TIPOS: "+tipos);
            System.err.println("  NOMBRES: "+nombres);
        }

        característicasxImportar = new HashMap<>();
        for (int i = 0; i < tipos.size(); i++)
            característicasxImportar.put(nombres.get(i), tipos.get(i));

        System.err.println("Características: "+ característicasxImportar);
    }

    private void inicioCaracterísticas() {
        Cell columnaObjetivo = filaActual.getCell(colInicioCaracterísticas);

        columnas = filaActual.cellIterator();

        while (columnaActual != columnaObjetivo)
            try {
                columnaActual = columnas.next();
            } catch (NoSuchElementException e) {
                break;
            }
    }

    private boolean inicioInmueble() {
        return filaActual.getCell(colHijos) != null && HIJOS_INICIO.equals(filaActual.getCell(colHijos).getStringCellValue());
    }

    private boolean finInmueble() {
        return filaActual.getCell(colHijos) != null && HIJOS_FIN.equals(filaActual.getCell(colHijos).getStringCellValue());
    }

    private boolean saltar() {
        return filaActual.getCell(colHijos) != null && SALTAR.equals(filaActual.getCell(colHijos).getStringCellValue());
    }
}
