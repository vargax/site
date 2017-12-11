package co.com.tecni.site.datos;

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

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private int colId;
    private int colDescripcion;
    private int colChip;
    private int colCédulaCatastral;
    private int colNomenclatura;
    private int colM2Construccion;
    private int colM2Terreno;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorCatastral() {

        colId = (int)COL_ID - 65;
        colDescripcion = (int)COL_DESCRIPCIÓN - 65;
        colChip = (int)COL_CHIP - 65;
        colCédulaCatastral = (int)COL_CÉDULA_CATASTRAL - 65;
        colNomenclatura = (int)COL_NOMENCLATURA - 65;
        colM2Construccion = (int)COL_M2_CONSTRUCCION - 65;
        colM2Terreno = (int)COL_M2_TERRENO - 65;


    }
}
