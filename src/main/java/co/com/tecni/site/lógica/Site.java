package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.Lector;
import co.com.tecni.site.lógica.inmueble.Ficha;
import co.com.tecni.site.lógica.inmueble.Inmueble;
import co.com.tecni.site.lógica.inmueble.fichas.Contable;

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
        Agrupación grandesBodegas = new Agrupación("Grandes Bodegas");
        Agrupación pequeñasBodegas = new Agrupación("Pequeñas Bodegas");
        bodegas.agregarAgrupación(grandesBodegas);
        bodegas.agregarAgrupación(pequeñasBodegas);

        Agrupación edificiosOficinas = new Agrupación("Edificios de oficinas");
        Inmueble ecotower93 = lector.leer("ecotower93");
        ecotower93.asociarFicha(new Contable());
        edificiosOficinas.agregarInmueble(ecotower93);

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
        return ((Agrupación) o).getHijos().get(i);
    }

    public int getChildCount(Object o) {
        return ((Agrupación) o).getHijos().size();
    }

    public boolean isLeaf(Object o) {
        return o instanceof Ficha;
    }

    public void valueForPathChanged(TreePath treePath, Object o) {

    }

    public int getIndexOfChild(Object o, Object o1) {
        ArrayList<Object> hijos = ((Agrupación) o).getHijos();

        for (int i = 0; i < hijos.size(); i++)
            if (hijos.get(i) == o1) return i;

        return -1;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }
}
