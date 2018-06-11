package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.Catastral;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos.ImpuestoPredial;
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

    private final static char COL_ID = 'A';
    private final static char COL_CHIP = 'B';
    private final static char COL_CÉDULA_CATASTRAL = 'C';
    private final static char COL_NOMENCLATURA = 'D';
    private final static char COL_M2_CONSTRUCCION = 'E';
    private final static char COL_M2_TERRENO = 'F';

    private final static char COL_PRIMER_PREDIAL = 'G';
    private final static char COL_ÚLTIMO_PREDIAL = 'I';

    private final static int colId = (int)COL_ID - 65;
    private final static int colChip = (int)COL_CHIP - 65;
    private final static int colCédulaCatastral = (int)COL_CÉDULA_CATASTRAL - 65;
    private final static int colNomenclatura = (int)COL_NOMENCLATURA - 65;
    private final static int colM2Construccion = (int)COL_M2_CONSTRUCCION - 65;
    private final static int colM2Terreno = (int)COL_M2_TERRENO - 65;
    private final static int colPrimerPredial = (int)COL_PRIMER_PREDIAL - 65;
    private final static int colÚltimoPredial = (int)COL_ÚLTIMO_PREDIAL - 65;

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

        añoPrimerPredial = Integer.parseInt(filaActual.getCell(colPrimerPredial).getStringCellValue().split(" ")[1]);

        while (filas.hasNext()) {
            filaActual = filas.next();

            String id = filaActual.getCell(colId).getStringCellValue();
            Inmueble inmueble = inmueblesxId.get(id);

            if (inmueble == null) throw new Exception("Inmueble "+id+" no encontrado");

            String chip = filaActual.getCell(colChip).getStringCellValue();
            String cedulaCatastral = filaActual.getCell(colCédulaCatastral).getStringCellValue();
            String nomenclatura = filaActual.getCell(colNomenclatura).getStringCellValue();
            double m2construcción = filaActual.getCell(colM2Construccion).getNumericCellValue();
            double m2terreno = filaActual.getCell(colM2Terreno).getNumericCellValue();

            Catastral.Json json = new Catastral.Json(chip, cedulaCatastral, nomenclatura, m2construcción, m2terreno);
            Catastral catastral = new Catastral(json);

            int año = añoPrimerPredial;

            Iterator<Cell> columnas = filaActual.cellIterator();
            Cell columnaActual = columnas.next();
            while (columnaActual.getColumnIndex() < colPrimerPredial )
                columnaActual = columnas.next();

            while (columnaActual.getColumnIndex() <= colÚltimoPredial) {
                double avaluo = columnaActual.getNumericCellValue();
                columnaActual = columnas.next();

                double impuesto = columnaActual.getNumericCellValue();
                columnaActual = columnas.next();

                ImpuestoPredial impuestoPredial = new ImpuestoPredial(new ImpuestoPredial.Json(año, avaluo, impuesto));
                catastral.registrarPredial(impuestoPredial);
                año++;
            }
            inmueble.registrarFicha(catastral);
        }
        inputStream.close();
    }
}
