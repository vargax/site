package co.com.tecni.site.ui;

import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.árboles.Árbol;
import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class UiÁrbol {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static IconCode ÍCONO_DEFAULT = GoogleMaterialDesignIcons.INSERT_DRIVE_FILE;
    private final static int ÍCONO_TAMAÑO = 25;
    private final static Color ÍCONO_COLOR = Color.GRAY;

    private final static Color FONDO_NODO_SELECCIONADO = new Color(225, 225, 225);

    private final static int[] MIN_DIMENSIONS = {100, 50};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    String nombre;
    JTree componente;

    Nodo nodoActual;

    private Árbol árbol;

    private UiNodoÁrbol uiNodoÁrbol;
    class UiNodoÁrbol implements TreeCellRenderer {
        private JLabel jLabel;

        UiNodoÁrbol() {
            jLabel = new JLabel();
        }

        public Component getTreeCellRendererComponent(JTree jTree, Object o,
                                                      boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            Nodo nodo = (Nodo) o;

            Ícono ícono = nodo.íconoNodo();
            if (ícono == null) {
                //System.err.println("Ícono indefinido para "+nodo.getClass().getSimpleName());
                ícono = new Ícono();
            }

            jLabel.setIcon(IconFontSwing.buildIcon(ícono.código, ÍCONO_TAMAÑO, ícono.color));
            jLabel.setText(nodo.nombreNodo(árbol));
            jLabel.setOpaque(false);

            if (selected) {
                jLabel.setOpaque(true);
                jLabel.setBackground(UiÁrbol.FONDO_NODO_SELECCIONADO);
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
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());

        uiNodoÁrbol = new UiNodoÁrbol();

        componente = new JTree(árbol);
        componente.setCellRenderer(uiNodoÁrbol);
        applySelectionListener();

        componente.setShowsRootHandles(true);
        componente.setRootVisible(true);
        componente.setMinimumSize(new Dimension(MIN_DIMENSIONS[0], MIN_DIMENSIONS[1]));
    }

    // -----------------------------------------------
    // Métodos privados
    // -----------------------------------------------
    private void applySelectionListener() {
        componente.getSelectionModel().addTreeSelectionListener(
                new TreeSelectionListener() {
                    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                        nodoActual = (Nodo) componente.getLastSelectedPathComponent();
                        UiSite.instance.actualizarDetalle(nodoActual);
                    }
                }
        );
    }

    // -----------------------------------------------
    // Métodos públicos
    // -----------------------------------------------
    void expandAll() {
        for (int i = 0; i < componente.getRowCount(); i++) {
            componente.expandRow(i);
        }
    }

    public static class Ícono {
        IconCode código;
        Color color;

        public Ícono(IconCode código, Color color) {
            this.código = código;
            this.color = color;
        }

        public Ícono(IconCode código) {
            this.código = código;
            this.color = ÍCONO_COLOR;
        }

        public Ícono() {
            this.código = ÍCONO_DEFAULT;
            this.color = ÍCONO_COLOR;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
}
