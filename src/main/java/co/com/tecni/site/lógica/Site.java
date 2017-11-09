package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.Lector;
import co.com.tecni.site.lógica.nodos.Agrupación;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.fichas._Ficha;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

public class Site implements ISite {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String NOMBRE_RAIZ = "TECNI";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Lector lector;
    private Agrupación raiz;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Site() throws Exception {
        lector = new Lector();

        raiz = new Agrupación(NOMBRE_RAIZ);

        Agrupación bodegas = new Agrupación("Bodegas");
        bodegas.agregarInmueble(lector.leer("LaEstancia"));

        Agrupación edificiosOficinas = new Agrupación("Edificios de oficinas");
        edificiosOficinas.agregarInmueble(lector.leer("Ecotower93"));

        raiz.agregarAgrupación(bodegas);
        raiz.agregarAgrupación(edificiosOficinas);
    }

    // -----------------------------------------------
    // Métodos Interfaz
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }

    public Object getChild(Object o, int i) {

        return ((Nodo) o).getHijos().get(i);
    }

    public int getChildCount(Object o) {
        return ((Nodo) o).getHijos().size();
    }

    public boolean isLeaf(Object o) {
        return o instanceof _Ficha;
    }

    public void valueForPathChanged(TreePath treePath, Object o) {

    }

    public int getIndexOfChild(Object o, Object o1) {
        ArrayList<Object> hijos = ((Nodo) o).getHijos();

        for (int i = 0; i < hijos.size(); i++)
            if (hijos.get(i) == o1) return i;

        return -1;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }
}
