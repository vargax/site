package co.com.tecni.sari.lógica.árboles;

import co.com.tecni.sari.lógica.transacciones.Transacción;
import co.com.tecni.sari.ui.UiÁrbol;

import java.util.ArrayList;

public interface Nodo {

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    String nombreNodo(Árbol árbol);
    ArrayList<Object> hijosNodo(Árbol árbol);
    UiÁrbol.Ícono íconoNodo();

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    ArrayList<Transacción>[] transaccionesNodo(Árbol árbol);
    String infoNodo(Árbol árbol);

}
