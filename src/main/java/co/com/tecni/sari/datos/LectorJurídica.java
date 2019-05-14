package co.com.tecni.sari.datos;

import co.com.tecni.sari.lógica.inmuebles.fichas.Jurídica;
import co.com.tecni.sari.lógica.inmuebles.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;

class LectorJurídica {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HOJA_NOMBRE = "importar";

    private final static char ID = 'A';
    private final static char OFICINA_REGISTRO = 'B';
    private final static char MATRÍCULA_INMOBILIARIA = 'C';
    private final static char FECHA_REGISTRO_COMPRA = 'D';
    private final static char COEF_COPROPIEDAD = 'E';

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private HashMap<String, Inmueble> inmueblesxId;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    LectorJurídica(HashMap<String, Inmueble> inmueblesxId) {
        this.inmueblesxId = inmueblesxId;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    void leer(String nombreInmueble) throws Exception {
        String resource = "/static/archivos/jurídica "+ nombreInmueble + ".xlsx";
        InputStream inputStream = LectorJurídica.class.getResourceAsStream(resource);
        if (inputStream == null) {
            System.err.println("Archivo '" + resource + "' no encontrado");
            return;
        }

        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        Iterator<Row> filas = libro.getSheet(HOJA_NOMBRE).iterator();
        Row filaActual = filas.next();

        while (filas.hasNext()) {
            filaActual = filas.next();

            String id = Lector.cadena(filaActual, ID);
            Inmueble inmueble = inmueblesxId.get(id);

            if (inmueble == null) throw new Exception("Inmueble " + id + " no encontrado");

            String oficinaRegistro = Lector.cadena(filaActual, OFICINA_REGISTRO);
            String matrículaInmobiliaria = Lector.cadena(filaActual, MATRÍCULA_INMOBILIARIA);
            LocalDate fechaRegistroCompra = Lector.fecha(filaActual, FECHA_REGISTRO_COMPRA);
            double coefCopropiedad = Lector.doble(filaActual, COEF_COPROPIEDAD);

            Jurídica.Json json = new Jurídica.Json(oficinaRegistro, matrículaInmobiliaria, fechaRegistroCompra, coefCopropiedad);
            new Jurídica(inmueble, json);

            inputStream.close();
        }
    }
}
