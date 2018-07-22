package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.ObraCivil;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class LectorObraCivil {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HOJA_NOMBRE = "importar";

    private final static char ID = 'A';
    private final static char FECHA_INICIAL = 'B';
    private final static char FECHA_FINAL = 'C';
    private final static char TIPO = 'D';
    private final static char DESCRIPCIÓN = 'E';
    private final static char PRESUPUESTO = 'F';
    private final static char CONTRATISTA = 'G';
    private final static char OBSERVACIONES = 'H';

    private HashMap<String, Inmueble> inmueblesxId;

    LectorObraCivil(HashMap<String, Inmueble> inmueblesxId) {
        this.inmueblesxId = inmueblesxId;
    }

    void leer(String nombreInmueble) throws Exception {
        InputStream inputStream = LectorObraCivil.class.getResourceAsStream("/static/archivos/obraCivil "+ nombreInmueble + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        HashMap<String, Integer> contadores = new HashMap<>();

        Iterator<Row> filas = libro.getSheet(HOJA_NOMBRE).iterator();
        Row filaActual = filas.next();

        while(filas.hasNext()) {
            filaActual = filas.next();
            if(filaActual.getCell(Lector.charToInt(ID)) == null) break;

            String id = Lector.cadena(filaActual, ID);
            Inmueble inmueble = inmueblesxId.get(id);
            if (inmueble == null) throw new Exception("Inmueble "+id+" no encontrado");

            Date fechaInicial = Lector.fecha(filaActual, FECHA_INICIAL);
            Date fechaFinal = Lector.fecha(filaActual, FECHA_FINAL);

            String tipo = Lector.cadena(filaActual, TIPO);
            if (!ObraCivil.TIPOS.contains(tipo)) throw new Exception("Tipo de ObraCivil "+tipo+" no definido");
            try {
                contadores.put(tipo, contadores.get(tipo) + 1);
            } catch (NullPointerException e) {
                contadores.put(tipo, 1);
            }

            String descripción = Lector.cadena(filaActual, DESCRIPCIÓN);
            double presupuesto = Lector.doble(filaActual, PRESUPUESTO);

            String observaciones = Lector.cadena(filaActual, OBSERVACIONES);

            ObraCivil.Json json = new ObraCivil.Json(tipo, fechaInicial, fechaFinal, presupuesto, descripción, observaciones);
            inmueble.registrarFicha(new ObraCivil(json));
        }

        for (Map.Entry<String, Integer> e : contadores.entrySet()) {
            System.out.println("Asociadas "+e.getValue()+" obras civiles de tipo "+e.getKey()+" para "+nombreInmueble);
        }
        inputStream.close();
    }
}
