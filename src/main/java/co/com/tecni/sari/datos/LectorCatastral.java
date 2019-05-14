package co.com.tecni.sari.datos;

import co.com.tecni.sari.lógica.inmuebles.fichas.Catastral;
import co.com.tecni.sari.lógica.inmuebles.Inmueble;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

class LectorCatastral {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HJ_REAL = "real";
    private final static String HJ_PRESUPUESTADO = "presupuestado";

    private final static char ID = 'A';
    private final static char CHIP = 'B';
    private final static char CÉDULA_CATASTRAL = 'C';
    private final static char NOMENCLATURA = 'D';
    private final static char M2_CONSTRUCCION = 'E';
    private final static char M2_TERRENO = 'F';

    private final static char PRIMER_PREDIAL = 'G';
    private final static char ÚLTIMO_PREDIAL = 'I';

    private final static int primerPredial = Lector.charToInt(PRIMER_PREDIAL);
    private final static int últimoPredial = Lector.charToInt(ÚLTIMO_PREDIAL);

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    HashMap<String, Inmueble> inmueblesxId;
    private int añoPrimerPredial;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------

    public LectorCatastral(HashMap<String, Inmueble> inmueblesxId) {
        this.inmueblesxId = inmueblesxId;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void leer(String nombreInmueble) throws Exception {
        String resource = "/static/archivos/catastral "+ nombreInmueble + ".xlsx";
        InputStream inputStream = LectorCatastral.class.getResourceAsStream(resource);
        if (inputStream == null) {
            System.err.println("Archivo '" + resource + "' no encontrado");
            return;
        }

        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        iterador(libro.getSheet(HJ_REAL).iterator(), false);
        iterador(libro.getSheet(HJ_PRESUPUESTADO).iterator(), true);

        inputStream.close();
    }

    private void iterador(Iterator<Row> filas, boolean presupuestado) throws Exception {
        Row filaActual = filas.next();
        añoPrimerPredial = Integer.parseInt(Lector.cadena(filaActual, PRIMER_PREDIAL).split(" ")[1]);

        while (filas.hasNext()) {
            filaActual = filas.next();
            if(filaActual.getCell(Lector.charToInt(ID)) == null) break;

            String id = Lector.cadena(filaActual, ID);
            Inmueble inmueble = inmueblesxId.get(id);

            if (inmueble == null) throw new Exception("Inmueble "+id+" no encontrado");

            String chip = Lector.cadena(filaActual, CHIP);
            String cedulaCatastral = Lector.cadena(filaActual,CÉDULA_CATASTRAL);
            String nomenclatura = Lector.cadena(filaActual,NOMENCLATURA);
            double m2construcción = Lector.doble(filaActual, M2_CONSTRUCCION);
            double m2terreno = Lector.doble(filaActual, M2_TERRENO);

            Catastral.Json json = new Catastral.Json(chip, cedulaCatastral, nomenclatura, m2construcción, m2terreno);

            Catastral catastral = null;
            if (!presupuestado) {
                catastral = new Catastral(inmueble, json);
            }

            int año = añoPrimerPredial;

            Iterator<Cell> columnas = filaActual.cellIterator();
            Cell columnaActual = columnas.next();
            while (columnaActual.getColumnIndex() < primerPredial)
                columnaActual = columnas.next();

            while (columnaActual.getColumnIndex() <= últimoPredial) {

                if (presupuestado) {
                    catastral = new Catastral(inmueble.getPresupuesto(año).gastos(), json);
                }

                double avaluo = columnaActual.getNumericCellValue();
                columnaActual = columnas.next();

                double impuesto = columnaActual.getNumericCellValue();
                columnaActual = columnas.next();

                new Catastral.ImpuestoPredial(catastral, new Catastral.ImpuestoPredial.Json(año, avaluo, impuesto));
                año++;
            }
        }
    }
}
