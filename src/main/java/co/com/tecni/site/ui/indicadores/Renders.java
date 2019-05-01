package co.com.tecni.site.ui.indicadores;

import co.com.tecni.site.lógica.Site;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class DoubleRender extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);

        if (value == null) return this;

        setHorizontalAlignment(RIGHT);

        double valor = (double) value;
        String texto = (-10 <= valor && valor <= 10) ? Site.SMALL_DECIMAL.format(valor) : Site.BIG_DECIMAL.format(valor);

        setText(texto);
        if ((Double) value < 0) setForeground(Color.RED);
        else setForeground(Color.BLACK);

        return this;
    }
}

class StringRender extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);

        if (value == null) return this;

        String valor = (String) value;
        if (valor.toUpperCase().equals(valor))
            setFont(this.getFont().deriveFont(Font.BOLD));

        return this;
    }
}

