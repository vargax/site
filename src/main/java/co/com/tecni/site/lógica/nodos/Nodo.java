package co.com.tecni.site.lógica.nodos;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.ui.UiÁrbol;

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
