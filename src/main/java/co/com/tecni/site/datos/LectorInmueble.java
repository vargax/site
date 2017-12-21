package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.nodos.inmueble.tipos.Bodega;
import co.com.tecni.site.lógica.nodos.inmueble.tipos.Oficina;
import co.com.tecni.site.lógica.nodos.inmueble.tipos._Inmueble;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LectorInmueble {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String HOJA_NOMBRE = "Cuadro de Areas";

    private final static char COL_HIJOS = 'A';
    private final static char COL_TIPO = 'B';
    private final static char COL_NOMBRE = 'C';
    private final static char COL_PRIVADO_CONSTRUIDOS = 'L';
    private final static char COL_PRIVADO_LIBRES = 'M';
    private final static char COL_COMUNES_CONSTRUIDO = 'N';
    private final static char COL_COMUNES_LIBRES = 'O';
    private final static char COL_ALTURA = 'S';
    private final static char COL_ACABADO_PISOS = 'T';

    private final static String HIJOS_INICIO = "inicio";
    private final static String HIJOS_FIN = "fin";
    private final static String SALTAR = "saltar";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String paqueteTipos;

    private int colHijos;
    private int colTipo;
    private int colNombre;
    private int colPrivadoConstruido;
    private int colPrivadoLibre;
    private int colComunConstruido;
    private int colComunLibre;
    private int colAltura;
    private int colAcabadoPisos;

    private Iterator<Row> filas;
    private Row filaActual;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public LectorInmueble() {
        paqueteTipos = _Inmueble.class.getPackage().toString().substring(8)+'.';

        colHijos = (int)COL_HIJOS - 65;
        colTipo = (int)COL_TIPO - 65;
        colNombre = (int)COL_NOMBRE - 65;
        colPrivadoConstruido = (int)COL_PRIVADO_CONSTRUIDOS - 65;
        colPrivadoLibre = (int)COL_PRIVADO_LIBRES - 65;
        colComunConstruido = (int)COL_COMUNES_CONSTRUIDO - 65;
        colComunLibre = (int)COL_COMUNES_LIBRES - 65;
        colAltura = (int)COL_ALTURA - 65;
        colAcabadoPisos = (int)COL_ACABADO_PISOS - 65;
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public _Inmueble leer(String nombreArchivo) throws Exception {
        InputStream inputStream = LectorInmueble.class.getResourceAsStream("/static/archivos/inmueble " + nombreArchivo + ".xlsx");
        XSSFWorkbook libro = new XSSFWorkbook(inputStream);

        filas = libro.getSheet(HOJA_NOMBRE).iterator();
        filaActual = filas.next();

        while (!inicioInmueble())
            filaActual = filas.next();

        _Inmueble inmueble = recursión();

        inputStream.close();
        System.out.println(inmueble);
        return inmueble;
    }

    private _Inmueble recursión() throws Exception {

        String tipo = paqueteTipos+filaActual.getCell(colTipo).getStringCellValue();
        String nombre = filaActual.getCell(colNombre).getStringCellValue();

        ArrayList<_Inmueble> hijos = new ArrayList<>();

        System.out.println("r "+nombre);

        filaActual = filas.next();
        while (!finInmueble()) {
            if (inicioInmueble())
                hijos.add(recursión());
            else if (!saltar())
                hijos.add(hoja());
            filaActual = filas.next();
        }

        return _Inmueble.englobar(tipo, nombre, hijos);
    }

    private _Inmueble hoja() throws Exception {

        String tipo = paqueteTipos+filaActual.getCell(colTipo).getStringCellValue();
        String nombre = filaActual.getCell(colNombre).getStringCellValue();

        System.out.println("h "+nombre);

        HashMap<String, Double> metros = new HashMap<>();
        metros.put(_Inmueble.PRIV_CONSTRUIDOS, filaActual.getCell(colPrivadoConstruido).getNumericCellValue());
        metros.put(_Inmueble.PRIV_LIBRES, filaActual.getCell(colPrivadoLibre).getNumericCellValue());
        metros.put(_Inmueble.COM_CONSTRUIDOS, filaActual.getCell(colComunConstruido).getNumericCellValue());
        metros.put(_Inmueble.COM_LIBRES, filaActual.getCell(colComunLibre).getNumericCellValue());

        _Inmueble inm = _Inmueble.hoja(tipo, nombre, metros);

       if(filaActual.getCell(colAltura) != null){
        if (inm instanceof Oficina) {
            ((Oficina) inm).setAltura(filaActual.getCell(colAltura).getNumericCellValue());
            ((Oficina) inm).setAcabadoPisos(filaActual.getCell(colAcabadoPisos).getStringCellValue());
        }  else  if (inm instanceof Bodega) {
            ((Bodega) inm).setAltura(filaActual.getCell(colAltura).getNumericCellValue());
            ((Bodega) inm).setAcabadoPisos(filaActual.getCell(colAcabadoPisos).getStringCellValue());
        }
       }
        return inm;
    }

    private boolean inicioInmueble() {
        return filaActual.getCell(colHijos) != null && HIJOS_INICIO.equals(filaActual.getCell(colHijos).getStringCellValue());
    }

    private boolean finInmueble() {
        return filaActual.getCell(colHijos) != null && HIJOS_FIN.equals(filaActual.getCell(colHijos).getStringCellValue());
    }

    private boolean saltar() {
        return filaActual.getCell(colHijos) != null && SALTAR.equals(filaActual.getCell(colHijos).getStringCellValue());
    }
}
