package co.com.tecni.sari.ui;

import co.com.tecni.sari.lógica.contratos.Versión;
import co.com.tecni.sari.lógica.árboles.Nodo;
import co.com.tecni.sari.lógica.contratos.ClienteComercial;
import co.com.tecni.sari.lógica.contratos.ClienteFacturación;
import co.com.tecni.sari.lógica.contratos.Contrato;
import co.com.tecni.sari.lógica.inmuebles.Agrupación;
import co.com.tecni.sari.lógica.fichas.Ficha;
import co.com.tecni.sari.lógica.inmuebles.tipos.Inmueble;
import co.com.tecni.sari.lógica.árboles.Árbol;
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

    private final static Color ÍCONO_COLOR_AGRUPACIÓN = Color.RED;
    private final static Color ÍCONO_COLOR_INMUEBLE = Color.ORANGE;
    private final static Color ÍCONO_COLOR_FICHA = Color.BLUE;

    private final static Color ÍCONO_COLOR_CLIENTE = new Color(204,204,0);
    private final static Color ÍCONO_COLOR_CONTRATO = new Color(0,153,51);
    private final static Color ÍCONO_COLOR_SECUENCIA = new Color(102,255,102);

    private final static Color ÍCONO_COLOR_TERCERO = new Color(204,102,255);

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

            if (nodo instanceof Agrupación) ícono.color = ÍCONO_COLOR_AGRUPACIÓN;
            if (nodo instanceof Inmueble) ícono.color = ÍCONO_COLOR_INMUEBLE;
            if (nodo instanceof Ficha) ícono.color = ÍCONO_COLOR_FICHA;

            if (nodo instanceof ClienteComercial) ícono.color = ÍCONO_COLOR_CLIENTE;
            if (nodo instanceof Contrato) ícono.color = ÍCONO_COLOR_CONTRATO;
            if (nodo instanceof Versión) ícono.color = ÍCONO_COLOR_SECUENCIA;

            if (nodo instanceof ClienteFacturación) ícono.color = ÍCONO_COLOR_TERCERO;

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
                        UiSari.instance.cambioNodo(nodoActual);
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

        /*public Ícono(IconCode código, Color color) {
            this.código = código;
            this.color = color;
        }*/

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
