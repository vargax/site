package co.com.tecni.sari.lógica.árboles;

import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.ui.UiÁrbol;

import java.util.ArrayList;

public interface Nodo {

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    String nombreNodo();
    ArrayList<Object> hijosNodo();
    UiÁrbol.Ícono íconoNodo();

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    ArrayList<Transacción>[] transaccionesNodo();
    String infoNodo();

}
