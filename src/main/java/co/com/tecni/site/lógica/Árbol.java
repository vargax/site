package co.com.tecni.site.lógica;

import co.com.tecni.site.lógica.nodos.Nodo;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

public abstract class Árbol implements TreeModel {

    public abstract Object getRoot();

    public Object getChild(Object o, int i) {

        return ((Nodo) o).hijosNodo(this).get(i);
    }

    public int getChildCount(Object o) {
        return ((Nodo) o).hijosNodo(this).size();
    }

    public boolean isLeaf(Object o) {
        ArrayList<Object> hijos = ((Nodo) o).hijosNodo(this);
        return hijos == null || hijos.size() == 0;
    }

    public void valueForPathChanged(TreePath treePath, Object o) {

    }

    public int getIndexOfChild(Object o, Object o1) {
        ArrayList<Object> hijos = ((Nodo) o).hijosNodo(this);

        for (int i = 0; i < hijos.size(); i++)
            if (hijos.get(i) == o1) return i;

        return -1;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }

}
