package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.fichas.Mejora;
import co.com.tecni.site.lógica.inmuebles.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// ToDo recomiendo en la medida de lo posible utilizar datos reales / Se requiere la información del tercero
class LectorObraCivil {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HJ_REAL = "real";
    private final static String HJ_PRESUPUESTADO = "presupuestado";

    private final static char ID = 'A';
    private final static char FECHA_INICIAL = 'B';
    private final static char FECHA_FINAL = 'C';
    private final static char TIPO = 'D';
    private final static char DESCRIPCIÓN = 'E';
    private final static char PRESUPUESTO = 'F';
    private final static char CONTRATISTA = 'G';
    private final static char OBSERVACIONES = 'H';

    private HashMap<String, Inmueble> inmueblesxId;
    private String nombreInmueble;

    LectorObraCivil(HashMap<String, Inmueble> inmueblesxId) {
        this.inmueblesxId = inmueblesxId;
    }

    void leer(String nombreInmueble) throws Exception {
        this.nombreInmueble = nombreInmueble;

        InputStream inputStream = LectorObraCivil.class.getResourceAsStream("/static/archivos/obraCivil "+ nombreInmueble + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        iterador(libro.getSheet(HJ_REAL).iterator(), false);
        iterador(libro.getSheet(HJ_PRESUPUESTADO).iterator(), true);

        inputStream.close();
    }

    private void iterador(Iterator<Row> filas, boolean presupuestado) throws Exception {
        HashMap<String, Integer> contadores = new HashMap<>();
        Row filaActual = filas.next();

        while(filas.hasNext()) {
            filaActual = filas.next();
            if(filaActual.getCell(Lector.charToInt(ID)) == null) break;

            String id = Lector.cadena(filaActual, ID);
            Inmueble inmueble = inmueblesxId.get(id);
            if (inmueble == null) throw new Exception("Inmueble "+id+" no encontrado");

            LocalDate fechaInicial = Lector.fecha(filaActual, FECHA_INICIAL);
            LocalDate fechaFinal = Lector.fecha(filaActual, FECHA_FINAL);

            String tipo = Lector.cadena(filaActual, TIPO);
            if (!Mejora.TIPOS.contains(tipo)) throw new Exception("Tipo de Mejora "+tipo+" no definido");
            try {
                contadores.put(tipo, contadores.get(tipo) + 1);
            } catch (NullPointerException e) {
                contadores.put(tipo, 1);
            }

            String descripción = Lector.cadena(filaActual, DESCRIPCIÓN);
            double presupuesto = Lector.doble(filaActual, PRESUPUESTO);

            String observaciones = Lector.cadena(filaActual, OBSERVACIONES);

            Mejora.Json json = new Mejora.Json(tipo, fechaInicial, fechaFinal, presupuesto, descripción, observaciones);

            if (presupuestado)
                new Mejora(inmueble.getPresupuesto(fechaInicial.getYear()).gastos(), json);
            else
                inmueble.registrarFicha(new Mejora(json));
        }

        for (Map.Entry<String, Integer> e : contadores.entrySet()) {
            System.out.println("Asociadas "+e.getValue()+" obras civiles de tipo "+e.getKey()+" para "+nombreInmueble);
        }
    }
}
