package co.com.tecni.site.lógica.nodos;

import co.com.tecni.site.lógica.nodos.inmuebles.fichas.transacciones.Transacción;
import co.com.tecni.site.ui.UiÁrbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Nodo {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode UI_ÍCONO = GoogleMaterialDesignIcons.INSERT_DRIVE_FILE;

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    protected String nombre;
    protected IconCode íconoCódigo;
    protected Color íconoColor = UiÁrbol.ÍCONO_COLOR;

    protected JSONObject infoNodo;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Nodo() {
        infoNodo = new JSONObject();
        íconoCódigo = UI_ÍCONO;
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public abstract String nombreNodo();
    public abstract ArrayList<Object> hijosNodo();

    public Icon getÍcono() {
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        return IconFontSwing.buildIcon(íconoCódigo, UiÁrbol.ÍCONO_TAMAÑO, íconoColor);
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public ArrayList<Transacción>[] transaccionesNodo() {
        return new ArrayList[3];
    }

    public String infoNodo() {
        return infoNodo.toJSONString();
    }

}
