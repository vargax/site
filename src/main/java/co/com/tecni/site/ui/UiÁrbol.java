package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.Agrupación;
import co.com.tecni.site.lógica.nodos.inmueble.fichas.Ficha;
import co.com.tecni.site.lógica.nodos.inmueble.tipos.Inmueble;
import co.com.tecni.site.lógica.Árbol;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class UiÁrbol {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static int[] MIN_DIMENSIONS = {100, 50};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String nombre;
    private Árbol árbol;
    private UiSite uiSite;

    private JTree jTree;
    private UiNodoÁrbol uiNodoÁrbol;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiÁrbol(String nombre, Árbol árbol, UiSite uiSite) {
        this.nombre = nombre;
        this.árbol = árbol;
        this.uiSite = uiSite;

        uiNodoÁrbol = new UiNodoÁrbol();

        jTree = new JTree(árbol);
        jTree.setCellRenderer(uiNodoÁrbol);
        applySelectionListener();

        jTree.setShowsRootHandles(true);
        jTree.setRootVisible(false);
        jTree.setMinimumSize(new Dimension(MIN_DIMENSIONS[0], MIN_DIMENSIONS[1]));

    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    private void applySelectionListener() {
        jTree.getSelectionModel().addTreeSelectionListener(
                new TreeSelectionListener() {
                    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                        Nodo nodo = (Nodo) jTree.getLastSelectedPathComponent();
                        uiSite.actualizarDetalle(nodo.infoNodo());
                    }
                }
        );
    }

    String getNombre() {
        return nombre;
    }

    Component getComponent() {
        return jTree;
    }
}

class UiNodoÁrbol implements TreeCellRenderer {
    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private JLabel jLabel;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public UiNodoÁrbol() {
        jLabel = new JLabel();
    }

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------
    public Component getTreeCellRendererComponent(JTree jTree, Object o, boolean b, boolean b1, boolean b2, int i, boolean b3) {
        if (o instanceof Inmueble)
            jLabel.setIcon(new ImageIcon(UiSite.class.getResource("/static/íconos/inmueble.png")));

        else if (o instanceof Ficha)
            jLabel.setIcon(new ImageIcon(UiSite.class.getResource("/static/íconos/ficha.png")));

        else if (o instanceof Agrupación)
            jLabel.setIcon(new ImageIcon(UiSite.class.getResource("/static/íconos/agrupacion.png")));

        else jLabel.setIcon(new ImageIcon(UiSite.class.getResource("/static/íconos/nodo.png")));

        jLabel.setText(((Nodo)o).nombreNodo());
        return jLabel;
    }
}
