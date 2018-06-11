package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Jurídica;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

class LectorJurídica {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HOJA_NOMBRE = "importar";

    private final static char COL_ID = 'A';
    private final static char COL_OFICINA_REGISTRO = 'B';
    private final static char COL_MATRÍCULA_INMOBILIARIA = 'C';
    private final static char COL_FECHA_REGISTRO_COMPRA = 'D';
    private final static char COL_COEF_COPROPIEDAD = 'E';

    private int colId = (int)COL_ID - 65;
    private int colOficinaRegistro = (int) COL_OFICINA_REGISTRO - 65;
    private int colMatículaInmobiliaria = (int) COL_MATRÍCULA_INMOBILIARIA - 65;
    private int colFechaRegistroCompra = (int) COL_FECHA_REGISTRO_COMPRA - 65;
    private int colCoefCopropiedad = (int) COL_COEF_COPROPIEDAD - 65;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    HashMap<String, Inmueble> inmueblesxId;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorJurídica(HashMap<String, Inmueble> inmueblesxId) {
        this.inmueblesxId = inmueblesxId;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void leer(String nombreInmueble) throws Exception {
        InputStream inputStream = LectorJurídica.class.getResourceAsStream("/static/archivos/jurídica "+ nombreInmueble + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        Iterator<Row> filas = libro.getSheet(HOJA_NOMBRE).iterator();
        Row filaActual = filas.next();

        while(filas.hasNext()) {
            filaActual = filas.next();

            String id = filaActual.getCell(colId).getStringCellValue();
            Inmueble inmueble = inmueblesxId.get(id);

            if (inmueble == null) throw new Exception("Inmueble "+id+" no encontrado");

            String oficinaRegistro = filaActual.getCell(colOficinaRegistro).getStringCellValue();
            String matrículaInmobiliaria = filaActual.getCell(colMatículaInmobiliaria).getStringCellValue();
            Date fechaRegistroCompra  = new Date((long)filaActual.getCell(colFechaRegistroCompra).getNumericCellValue());
            double coefCopropiedad = filaActual.getCell(colCoefCopropiedad).getNumericCellValue();

            Jurídica.Json json = new Jurídica.Json(oficinaRegistro,matrículaInmobiliaria,fechaRegistroCompra,coefCopropiedad);
            Jurídica jurídica = new Jurídica(json);

            inmueble.registrarFicha(jurídica);
        }
        inputStream.close();
    }
}
