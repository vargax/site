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

    private final static char COL_NOMBRE = 'A';
    private final static char COL_PRIVADO_CONSTRUIDOS = 'L';
    private final static char COL_PRIVADO_LIBRES = 'M';
    private final static char COL_COMUNES_CONSTRUIDO = 'N';
    private final static char COL_COMUNES_LIBRES = 'O';

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int colNombre;
    private int colPrivadoConstruido;
    private int colPrivadoLibre;
    private int colComunConstruido;
    private int colComunLibre;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Lector() {
        colNombre = (int)COL_NOMBRE - 65;
        colPrivadoConstruido = (int)COL_PRIVADO_CONSTRUIDOS - 65;
        colPrivadoLibre = (int)COL_PRIVADO_LIBRES - 65;
        colComunConstruido = (int)COL_COMUNES_CONSTRUIDO - 65;
        colComunLibre = (int)COL_COMUNES_LIBRES - 65;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public Inmueble leer(String nombre) throws Exception {
        InputStream inputStream = Lector.class.getResourceAsStream("/"+nombre+".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        Iterator<Row> filas = libro.getSheet(HOJA_NOMBRE).iterator();

        ArrayList<Inmueble> niveles = new ArrayList<Inmueble>();
        ArrayList<Inmueble> inmueblesxNivel = new ArrayList<Inmueble>();

        while (filas.hasNext()) {
            Row fila = filas.next();

            if (fila.getCell(colNombre) == null ) continue;

            String nombreInmueble = fila.getCell(colNombre).getStringCellValue();
            System.out.println(nombreInmueble);

            if (nombreInmueble.startsWith("Total ")) {
                niveles.add(Inmueble.englobar(nombreInmueble.replace("Total ",""), inmueblesxNivel));
                inmueblesxNivel = new ArrayList<Inmueble>();
                continue;
            }

            HashMap<String, Double> metros = new HashMap<String, Double>();
            metros.put(Inmueble.PRIV_CONSTRUIDOS, fila.getCell(colPrivadoConstruido).getNumericCellValue());
            metros.put(Inmueble.PRIV_LIBRES, fila.getCell(colPrivadoLibre).getNumericCellValue());
            metros.put(Inmueble.COM_CONSTRUIDOS, fila.getCell(colComunConstruido).getNumericCellValue());
            metros.put(Inmueble.COM_LIBRES, fila.getCell(colComunLibre).getNumericCellValue());

            inmueblesxNivel.add(new Inmueble(nombreInmueble, metros));
        }
        Inmueble inmueble = Inmueble.englobar(nombre, niveles);

        inputStream.close();

        System.out.println(inmueble);
        return inmueble;
    }
}
