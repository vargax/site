package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Catastral;
import co.com.tecni.site.lógica.nodos.inmuebles.tipos.Inmueble;
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
    private final static String HOJA_NOMBRE = "importar";

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
        InputStream inputStream = LectorCatastral.class.getResourceAsStream("/static/archivos/catastral "+ nombreInmueble + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        Iterator<Row> filas = libro.getSheet(HOJA_NOMBRE).iterator();
        Row filaActual = filas.next();

        añoPrimerPredial = Integer.parseInt(Lector.cadena(filaActual, PRIMER_PREDIAL).split(" ")[1]);

        while (filas.hasNext()) {
            filaActual = filas.next();

            String id = Lector.cadena(filaActual, ID);
            Inmueble inmueble = inmueblesxId.get(id);

            if (inmueble == null) throw new Exception("Inmueble "+id+" no encontrado");

            String chip = Lector.cadena(filaActual, CHIP);
            String cedulaCatastral = Lector.cadena(filaActual,CÉDULA_CATASTRAL);
            String nomenclatura = Lector.cadena(filaActual,NOMENCLATURA);
            double m2construcción = Lector.doble(filaActual, M2_CONSTRUCCION);
            double m2terreno = Lector.doble(filaActual, M2_TERRENO);

            Catastral.Json json = new Catastral.Json(chip, cedulaCatastral, nomenclatura, m2construcción, m2terreno);
            Catastral catastral = new Catastral(json);

            int año = añoPrimerPredial;

            Iterator<Cell> columnas = filaActual.cellIterator();
            Cell columnaActual = columnas.next();
            while (columnaActual.getColumnIndex() < primerPredial)
                columnaActual = columnas.next();

            while (columnaActual.getColumnIndex() <= últimoPredial) {
                double avaluo = columnaActual.getNumericCellValue();
                columnaActual = columnas.next();

                double impuesto = columnaActual.getNumericCellValue();
                columnaActual = columnas.next();

                Catastral.ImpuestoPredial impuestoPredial = new Catastral.ImpuestoPredial(new Catastral.ImpuestoPredial.Json(año, avaluo, impuesto));
                catastral.registrarPredial(impuestoPredial);
                año++;
            }
            inmueble.registrarFicha(catastral);
        }
        inputStream.close();
    }
}
