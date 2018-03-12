package co.com.tecni.site.lógica;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ÁrbolClientes implements TreeModel {

    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String NOMBRE_RAIZ = "CLIENTES";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------


    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public ÁrbolClientes() {
    }

    // -----------------------------------------------
    // Métodos Interfaz TreeModel
    // -----------------------------------------------
    public Object getRoot() {
        return null;
    }

    public Object getChild(Object o, int i) {
        return null;
    }

    public int getChildCount(Object o) {
        return 0;
    }

    public boolean isLeaf(Object o) {
        return false;
    }

    public void valueForPathChanged(TreePath treePath, Object o) {

    }

    public int getIndexOfChild(Object o, Object o1) {
        return 0;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }
}
