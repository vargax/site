package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.LectorCatastral;
import co.com.tecni.site.datos.LectorInmueble;
import co.com.tecni.site.lógica.nodos.Agrupación;
import co.com.tecni.site.lógica.nodos.Nodo;
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
    private HashMap<String, _Inmueble> inmueblesxId;

    private LectorInmueble lectorInmuebles;

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------
    public Site() throws Exception {
        raiz = new Agrupación(NOMBRE_RAIZ);
        inmueblesxId = new HashMap<>();

        importarInmuebles();
        recursiónIdentificadores(raiz);

        importarFichas();
    }

    // -----------------------------------------------
    // Métodos Privados
    // -----------------------------------------------
    private void importarInmuebles() throws Exception {
        Agrupación bodegas = new Agrupación("Bodegas");
        raiz.agregarAgrupación(bodegas);

        Agrupación edificiosOficinas = new Agrupación("Edificios de oficinas");
        raiz.agregarAgrupación(edificiosOficinas);

        lectorInmuebles = new LectorInmueble();
        bodegas.agregarInmueble(lectorInmuebles.leer("LaEstancia"));
        edificiosOficinas.agregarInmueble(lectorInmuebles.leer("Ecotower93"));
    }

    private void recursiónIdentificadores(Nodo nodo) {
        for (Object hijo : nodo.hijosNodo()) {
            if (hijo instanceof _Inmueble)
                inmueblesxId.put(((_Inmueble) hijo).genId(), (_Inmueble) hijo);
            recursiónIdentificadores((Nodo) hijo);
        }
    }

    private void importarFichas() throws Exception {
        LectorCatastral lectorCatastral = new LectorCatastral(inmueblesxId);
        lectorCatastral.leer("Ecotower93");
        lectorCatastral.leer("LaEstancia");
    }

    // -----------------------------------------------
    // Métodos Interfaz
    // -----------------------------------------------
    public Object getRoot() {
        return raiz;
    }

    public Object getChild(Object o, int i) {

        return ((Nodo) o).hijosNodo().get(i);
    }

    public int getChildCount(Object o) {
        return ((Nodo) o).hijosNodo().size();
    }

    public boolean isLeaf(Object o) {
        ArrayList<Object> hijos = ((Nodo) o).hijosNodo();
        return hijos == null || hijos.size() == 0;
    }

    public void valueForPathChanged(TreePath treePath, Object o) {

    }

    public int getIndexOfChild(Object o, Object o1) {
        ArrayList<Object> hijos = ((Nodo) o).hijosNodo();

        for (int i = 0; i < hijos.size(); i++)
            if (hijos.get(i) == o1) return i;

        return -1;
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }
}
