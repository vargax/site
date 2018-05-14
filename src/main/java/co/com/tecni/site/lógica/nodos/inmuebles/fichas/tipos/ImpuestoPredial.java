package co.com.tecni.site.lógica.nodos.inmuebles.fichas.tipos;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Tercero;
import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ImpuestoPredial extends Ficha {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.ACCOUNT_BALANCE;
    private final static String[] JSON_KEYS = {"Año Fiscal",
            "Avaluo Catastral",
            "Impuesto a Cargo"
    };

    private final static int PRORRATA = 12;
    private final static String[] TERCERO = {"899.999.061-9", "Secretaría de Hacienda Distrital de Bogotá"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ImpuestoPredial(int añoFiscal, double avaluoCatastral, double impuestoACargo) {
        super();
        super.íconoCódigo = UI_ÍCONO;
        super.íconoColor = Catastral.UI_ÍCONO_COLOR;

        super.transacciones = new ArrayList<>();

        double monto = -impuestoACargo/PRORRATA;
        Tercero tercero = new Tercero(TERCERO[0], TERCERO[1]);
        for (int i = 1; i <= PRORRATA; i++) {
            String concepto = "Prorrata predial mes "+i;
            LocalDate fecha = LocalDate.parse(añoFiscal+"-"+i+"-1",DateTimeFormatter.ofPattern("yyyy-M-d"));
            transacciones.add(new Transacción(this, concepto, fecha, monto, tercero));
        }

        infoNodo.put(JSON_KEYS[0], añoFiscal);
        infoNodo.put(JSON_KEYS[1], avaluoCatastral);
        infoNodo.put(JSON_KEYS[2], impuestoACargo);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public String nombreNodo() {
        return "Predial "+infoNodo.get(JSON_KEYS[0]);
    }

}
