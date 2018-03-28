package co.com.tecni.site.lógica.nodos;

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
    private Icon ícono;

    protected JSONObject infoNodo;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Nodo() {
        infoNodo = new JSONObject();
        setÍcono(UI_ÍCONO);
    }

    // -----------------------------------------------
    // GUI / Árbol
    // -----------------------------------------------
    public abstract String nombreNodo();
    public abstract ArrayList<Object> hijosNodo();

    protected void setÍcono(IconCode códigoÍcono) {
        setÍcono(códigoÍcono, UiÁrbol.ÍCONO_COLOR);
    }

    protected void setÍcono(IconCode códigoÍcono, Color colorÍcono) {
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        this.ícono = IconFontSwing.buildIcon(códigoÍcono, UiÁrbol.ÍCONO_TAMAÑO, colorÍcono);
    }

    public Icon getÍcono() {
        return ícono;
    }

    // -----------------------------------------------
    // GUI / Detalle
    // -----------------------------------------------
    public String infoNodo() {
        return infoNodo.toJSONString();
    }
}
