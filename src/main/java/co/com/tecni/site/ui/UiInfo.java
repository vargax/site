package co.com.tecni.site.ui;

import com.doitnext.swing.widgets.json.JSONEditPanel;

import java.awt.*;

class UiInfo {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private JSONEditPanel jsonEditPanel;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiInfo() {
        jsonEditPanel = new JSONEditPanel();
    }


    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    Component getComponent() {
        return jsonEditPanel;
    }

    void mostrarDetalle(String json) {
        jsonEditPanel.setJson(json, JSONEditPanel.UpdateType.REPLACE);
    }
}
