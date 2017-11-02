package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.inmueble.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Lector {
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

    private final static String HIJOS_INICIO = "inicio";
    private final static String HIJOS_FIN = "fin";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int colHijos;
    private int colTipo;
    private int colNombre;
    private int colPrivadoConstruido;
    private int colPrivadoLibre;
    private int colComunConstruido;
    private int colComunLibre;

    private Iterator<Row> filas;
    private Row filaActual;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Lector() {
        colHijos = (int)COL_HIJOS - 65;
        colTipo = (int)COL_TIPO - 65;
        colNombre = (int)COL_NOMBRE - 65;
        colPrivadoConstruido = (int)COL_PRIVADO_CONSTRUIDOS - 65;
        colPrivadoLibre = (int)COL_PRIVADO_LIBRES - 65;
        colComunConstruido = (int)COL_COMUNES_CONSTRUIDO - 65;
        colComunLibre = (int)COL_COMUNES_LIBRES - 65;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public Inmueble leer(String nombreArchivo) throws Exception {
        InputStream inputStream = Lector.class.getResourceAsStream("/" + nombreArchivo + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        filas = libro.getSheet(HOJA_NOMBRE).iterator();
        filaActual = filas.next();

        while (!inicioInmueble())
            filaActual = filas.next();

        Inmueble inmueble = recursión();

        inputStream.close();
        System.out.println(inmueble);
        return inmueble;
    }

    private Inmueble recursión() {
        String nombre = filaActual.getCell(colNombre).getStringCellValue();
        ArrayList<Inmueble> hijos = new ArrayList<>();

        System.out.println("r "+nombre);

        filaActual = filas.next();
        while (!finInmueble()) {
            if (inicioInmueble())
                hijos.add(recursión());
            else
                hijos.add(hoja());
            filaActual = filas.next();
        }

        return Inmueble.englobar(nombre, hijos);
    }

    private Inmueble hoja() {

        String nombre = filaActual.getCell(colNombre).getStringCellValue();

        System.out.println("h "+nombre);

        HashMap<String, Double> metros = new HashMap<>();
        metros.put(Inmueble.PRIV_CONSTRUIDOS, filaActual.getCell(colPrivadoConstruido).getNumericCellValue());
        metros.put(Inmueble.PRIV_LIBRES, filaActual.getCell(colPrivadoLibre).getNumericCellValue());
        metros.put(Inmueble.COM_CONSTRUIDOS, filaActual.getCell(colComunConstruido).getNumericCellValue());
        metros.put(Inmueble.COM_LIBRES, filaActual.getCell(colComunLibre).getNumericCellValue());

        return new Inmueble(nombre, metros);
    }

    private boolean inicioInmueble() {
        return filaActual.getCell(colHijos) != null && HIJOS_INICIO.equals(filaActual.getCell(colHijos).getStringCellValue());
    }

    private boolean finInmueble() {
        return filaActual.getCell(colHijos) != null && HIJOS_FIN.equals(filaActual.getCell(colHijos).getStringCellValue());
    }
}
