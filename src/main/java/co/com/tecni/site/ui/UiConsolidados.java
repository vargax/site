package co.com.tecni.site.ui;

import co.com.tecni.site.ui.table.DoubleRender;

import javax.swing.*;
import java.awt.*;

public class UiConsolidados {
    private JSplitPane panelResumen;

    private JTable tablaResumen;
    /*private ResumenConsolidado resumenConsolidado;
    class ResumenConsolidado extends AbstractTableModel {

        private final String[] COLUMNAS = {
                "Real", "Presupuestado", "Diferencia", "Cumplimiento"
        };

        private LinkedHashMap<String, double[]> resumen;

        ResumenConsolidado() {
            resumen = new LinkedHashMap<>();
        }


    }*/

    private JTable tablaDetalle;


    UiConsolidados() {
        DoubleRender doubleRender = new DoubleRender();

    }

    Component getComponent() {
        return panelResumen;
    }

}
