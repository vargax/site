package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.Lector;
import co.com.tecni.site.lógica.inmueble.Inmueble;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

public class Site implements ISite {
    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Lector lector;
    private ArrayList<Inmueble> raices;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Site() throws Exception {
        lector = new Lector();
        raices = new ArrayList();

        raices.add(lector.leer("ecotower93"));
    }
    // -----------------------------------------------
    // Método MAIN
    // -----------------------------------------------
    public static void main(String args[]) throws Exception {
        new Site();
    }

    // -----------------------------------------------
    // Métodos Interfaz
    // -----------------------------------------------
    public Object getRoot() {
        return raices;
    }

    public Object getChild(Object o, int i) {
        return ((Inmueble) o).getHijos().get(i);
    }

    public int getChildCount(Object o) {
        return ((Inmueble) o).getHijos().size();
    }

    public boolean isLeaf(Object o) {
        // Otros inmuebles nunca son hojas... Las fichas podrían ser hojas...
        return false;
    }

    public void valueForPathChanged(TreePath treePath, Object o) {

    }

    public int getIndexOfChild(Object o, Object o1) {
        ArrayList<Inmueble> hijos = ((Inmueble) o).getHijos();

        for (int i = 0; i < hijos.size(); i++)
            if (hijos.get(i) == o1) return i;

        return -1;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }
}
