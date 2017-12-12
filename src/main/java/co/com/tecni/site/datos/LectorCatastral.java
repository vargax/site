package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.inmueble.fichas.catastral.Catastral;
import co.com.tecni.site.lógica.nodos.inmueble.fichas.catastral.ImpuestoPredial;
import co.com.tecni.site.lógica.nodos.inmueble.tipos._Inmueble;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class LectorCatastral {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HOJA_NOMBRE = "importar";

    private final static char COL_ID = 'A';
    private final static char COL_DESCRIPCIÓN = 'B';
    private final static char COL_CHIP = 'C';
    private final static char COL_CÉDULA_CATASTRAL = 'D';
    private final static char COL_NOMENCLATURA = 'E';
    private final static char COL_M2_CONSTRUCCION = 'F';
    private final static char COL_M2_TERRENO = 'G';

    private final static char COL_PRIMER_PREDIAL = 'H';
    private final static char COL_ÚLTIMO_PREDIAL = 'J';

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private HashMap<String, _Inmueble> inmuebles;

    private int colId;
    private int colDescripcion;
    private int colChip;
    private int colCédulaCatastral;
    private int colNomenclatura;
    private int colM2Construccion;
    private int colM2Terreno;

    private int colPrimerPredial;
    private int colÚltimoPredial;
    private int añoPrimerPredial;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorCatastral(HashMap<String, _Inmueble> inmuebles) {

        colId = (int)COL_ID - 65;
        colDescripcion = (int)COL_DESCRIPCIÓN - 65;
        colChip = (int)COL_CHIP - 65;
        colCédulaCatastral = (int)COL_CÉDULA_CATASTRAL - 65;
        colNomenclatura = (int)COL_NOMENCLATURA - 65;
        colM2Construccion = (int)COL_M2_CONSTRUCCION - 65;
        colM2Terreno = (int)COL_M2_TERRENO - 65;

        colPrimerPredial = (int)COL_PRIMER_PREDIAL - 65;
        colÚltimoPredial = (int)COL_ÚLTIMO_PREDIAL - 65;

        this.inmuebles = inmuebles;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public void leer(String nombreArchivo) throws Exception {
        InputStream inputStream = LectorCatastral.class.getResourceAsStream("/static/archivos/catastral "+ nombreArchivo + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        Iterator<Row> filas = libro.getSheet(HOJA_NOMBRE).iterator();
        Row filaActual = filas.next();

        añoPrimerPredial = Integer.parseInt(filaActual.getCell(colPrimerPredial).getStringCellValue().split(" ")[1]);

        while (filas.hasNext()) {
            filaActual = filas.next();

            String id = filaActual.getCell(colId).getStringCellValue();
            _Inmueble inmueble = inmuebles.get(id);

            if (inmueble == null) throw new Exception("Inmueble "+id+" no encontrado");

            String descripcion = filaActual.getCell(colDescripcion).getStringCellValue();
            String chip = filaActual.getCell(colChip).getStringCellValue();
            String cedulaCatastral = filaActual.getCell(colCédulaCatastral).getStringCellValue();
            String nomenclatura = filaActual.getCell(colNomenclatura).getStringCellValue();
            double m2construccion = filaActual.getCell(colM2Construccion).getNumericCellValue();
            double m2terreno = filaActual.getCell(colM2Terreno).getNumericCellValue();

            Catastral catastral = new Catastral(descripcion, chip, cedulaCatastral, nomenclatura, m2construccion, m2terreno);

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

                catastral.registrarPredial(new ImpuestoPredial(año, avaluo, impuesto));
                año++;
            }

            inmueble.registrarFicha(catastral);
        }
    }
}
