package co.com.tecni.site.ui.table;

import co.com.tecni.site.lógica.Site;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class DoubleRender extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);

        setHorizontalAlignment(RIGHT);
        setText(Site.bdf.format(value));
        if ((Double) value < 0) setForeground(Color.RED);
        else setForeground(Color.BLACK);

        return this;
    }
}

