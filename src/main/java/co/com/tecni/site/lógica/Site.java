package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.LectorInmuebles;
import co.com.tecni.site.lógica.nodos.Agrupación;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.fichas._Ficha;
import co.com.tecni.site.lógica.nodos.inmueble.tipos._Inmueble;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.HashMap;

public class Site implements ISite {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String NOMBRE_RAIZ = "TECNI";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Agrupación raiz;
    private HashMap<String, _Inmueble> identificadores;

    private LectorInmuebles lectorInmuebles;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Site() throws Exception {
        raiz = new Agrupación(NOMBRE_RAIZ);

        Agrupación bodegas = new Agrupación("Bodegas");
        raiz.agregarAgrupación(bodegas);

        Agrupación edificiosOficinas = new Agrupación("Edificios de oficinas");
        raiz.agregarAgrupación(edificiosOficinas);

        lectorInmuebles = new LectorInmuebles();
        bodegas.agregarInmueble(lectorInmuebles.leer("LaEstancia"));
        edificiosOficinas.agregarInmueble(lectorInmuebles.leer("Ecotower93"));

        identificadores = new HashMap<>();
        recursiónIdentificadores(raiz);
    }

    // -----------------------------------------------
    // Métodos Privados
    // -----------------------------------------------
    private void recursiónIdentificadores(Nodo nodo) {
        for (Object hijo : nodo.guiÁrbolHijos()) {
            if (hijo instanceof _Inmueble)
                identificadores.put(((_Inmueble) hijo).genId(), (_Inmueble)hijo);
            recursiónIdentificadores((Nodo) hijo);
        }
    }

    // -----------------------------------------------
    // Métodos Interfaz
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }

    public Object getChild(Object o, int i) {

        return ((Nodo) o).guiÁrbolHijos().get(i);
    }

    public int getChildCount(Object o) {
        return ((Nodo) o).guiÁrbolHijos().size();
    }

    public boolean isLeaf(Object o) {
        return o instanceof _Ficha;
    }

    public void valueForPathChanged(TreePath treePath, Object o) {

    }

    public int getIndexOfChild(Object o, Object o1) {
        ArrayList<Object> hijos = ((Nodo) o).guiÁrbolHijos();

        for (int i = 0; i < hijos.size(); i++)
            if (hijos.get(i) == o1) return i;

        return -1;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }
}
