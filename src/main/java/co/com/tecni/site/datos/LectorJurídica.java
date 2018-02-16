package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.inmueble.fichas.Jurídica;
import co.com.tecni.site.lógica.nodos.inmueble.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class LectorJurídica {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HOJA_NOMBRE = "importar";

    private final static char COL_ID = 'A';
    private final static char COL_OFICINA_REGISTRO = 'B';
    private final static char COL_MATRÍCULA_INMOBILIARIA = 'C';
    private final static char COL_FECHA_REGISTRO_COMPRA = 'D';
    private final static char COL_COEF_COPROPIEDAD = 'E';

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private HashMap<String, Inmueble> inmuebles;

    private int colId;
    private int colOficinaRegistro;
    private int colMatículaInmobiliaria;
    private int colFechaRegistroCompra;
    private int colCoefCopropiedad;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorJurídica(HashMap<String, Inmueble> inmuebles) {
        colId = (int)COL_ID - 65;
        colOficinaRegistro = (int) COL_OFICINA_REGISTRO - 65;
        colMatículaInmobiliaria = (int) COL_MATRÍCULA_INMOBILIARIA - 65;
        colFechaRegistroCompra = (int) COL_FECHA_REGISTRO_COMPRA - 65;
        colCoefCopropiedad = (int) COL_COEF_COPROPIEDAD - 65;

        this.inmuebles = inmuebles;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void leer(String nombreArchivo) throws Exception {
        InputStream inputStream = LectorJurídica.class.getResourceAsStream("/static/archivos/jurídica "+ nombreArchivo + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        Iterator<Row> filas = libro.getSheet(HOJA_NOMBRE).iterator();
        Row filaActual = filas.next();

        while(filas.hasNext()) {
            filaActual = filas.next();

            String id = filaActual.getCell(colId).getStringCellValue();
            Inmueble inmueble = inmuebles.get(id);

            if (inmueble == null) throw new Exception("Inmueble "+id+" no encontrado");

            String oficinaRegistro = filaActual.getCell(colOficinaRegistro).getStringCellValue();
            String matrículaInmobiliaria = filaActual.getCell(colMatículaInmobiliaria).getStringCellValue();
            Date fechaRegistroCompra  = new Date((long)filaActual.getCell(colFechaRegistroCompra).getNumericCellValue());
            double coefCopropiedad = filaActual.getCell(colCoefCopropiedad).getNumericCellValue();

            Jurídica jurídica = new Jurídica(oficinaRegistro,matrículaInmobiliaria,fechaRegistroCompra,coefCopropiedad);

            inmueble.registrarFicha(jurídica);
        }
        inputStream.close();
    }
}
