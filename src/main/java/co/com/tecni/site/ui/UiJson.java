package co.com.tecni.site.ui;

import com.doitnext.swing.widgets.json.JSONEditPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

class UiJson {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    JSplitPane componente;

    private JSONEditPanel info;
    private JSONEditPanel comentarios;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiJson() {
        info = new JSONEditPanel();
        comentarios = new JSONEditPanel();
        cargarComentarios();

        componente = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        componente.setLeftComponent(new JScrollPane(info));
        componente.setRightComponent(new JScrollPane(comentarios));
        componente.setResizeWeight(0.3d);
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    void mostrarDetalle(String json) {
        info.setJson(json, JSONEditPanel.UpdateType.REPLACE);
    }

    private void cargarComentarios() {

        JSONParser jsonParser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("src/main/resources/static/comentarios.json"));
            comentarios.setJson(jsonArray.toJSONString(), JSONEditPanel.UpdateType.REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
