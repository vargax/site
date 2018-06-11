package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.árboles.Árbol;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class UiÁrbol {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    public final static int ÍCONO_TAMAÑO = 25;
    public final static Color ÍCONO_COLOR = Color.GRAY;

    public final static Color NODO_SEL_FONDO = new Color(225, 225, 225);

    private final static int[] MIN_DIMENSIONS = {100, 50};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private String nombre;
    private Árbol árbol;

    private JTree jTree;
    private UiNodoÁrbol uiNodoÁrbol;

    class UiNodoÁrbol implements TreeCellRenderer {
        // -----------------------------------------------
        // Constantes
        // -----------------------------------------------

        // -----------------------------------------------
        // Atributos
        // -----------------------------------------------
        private JLabel jLabel;

        // -----------------------------------------------
        // Constructor
        // -----------------------------------------------
        UiNodoÁrbol() {
            jLabel = new JLabel();
        }

        // -----------------------------------------------
        // Métodos
        // -----------------------------------------------
        public Component getTreeCellRendererComponent(JTree jTree, Object o, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Nodo nodo = (Nodo) o;

            jLabel.setIcon(nodo.getÍcono());
            jLabel.setText(nodo.nombreNodo(árbol));
            jLabel.setOpaque(false);

            if (selected) {
                jLabel.setOpaque(true);
                jLabel.setBackground(UiÁrbol.NODO_SEL_FONDO);
            }

            return jLabel;
        }
    }

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    UiÁrbol(String nombre, Árbol árbol) {
        this.nombre = nombre;
        this.árbol = árbol;

        uiNodoÁrbol = new UiNodoÁrbol();

        jTree = new JTree(árbol);
        jTree.setCellRenderer(uiNodoÁrbol);
        applySelectionListener();

        jTree.setShowsRootHandles(true);
        jTree.setRootVisible(false);
        jTree.setMinimumSize(new Dimension(MIN_DIMENSIONS[0], MIN_DIMENSIONS[1]));
    }

    // -----------------------------------------------
    // Métodos privados
    // -----------------------------------------------
    private void applySelectionListener() {
        jTree.getSelectionModel().addTreeSelectionListener(
                new TreeSelectionListener() {
                    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                        Nodo nodo = (Nodo) jTree.getLastSelectedPathComponent();
                        UiSite.instance.actualizarDetalle(nodo);
                    }
                }
        );
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    void expandAll() {
        for (int i = 0; i < jTree.getRowCount(); i++) {
            jTree.expandRow(i);
        }
    }

    String getNombre() {
        return nombre;
    }

    Component getComponent() {
        return jTree;
    }
}
