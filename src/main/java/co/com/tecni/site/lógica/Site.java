package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.LectorCatastral;
import co.com.tecni.site.datos.LectorContrato;
import co.com.tecni.site.datos.LectorInmueble;
import co.com.tecni.site.datos.LectorJurídica;
import co.com.tecni.site.lógica.contrato.ClienteComercial;
import co.com.tecni.site.lógica.contrato.ClienteFacturación;
import co.com.tecni.site.lógica.contrato.Contrato;
import co.com.tecni.site.lógica.nodos.Agrupación;
import co.com.tecni.site.lógica.nodos.Nodo;
import co.com.tecni.site.lógica.nodos.inmueble.tipos.Inmueble;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.HashMap;

public class Site implements TreeModel {
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------
    private final static String NOMBRE_RAIZ = "TECNI";
    private final static String[] INMUEBLES_IMPORTAR = {"LaEstancia", "Ecotower93"};

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    private Agrupación raiz;
    private HashMap<Integer, ClienteComercial> clientesComerciales;
    private HashMap<Integer, ClienteFacturación> clientesFacturación;
    private HashMap<Integer, Contrato> contratos;

    private HashMap<String, Inmueble> inmueblesxId;

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
        importarContratos();
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
        bodegas.agregarInmueble(lectorInmuebles.leer(INMUEBLES_IMPORTAR[0]));
        edificiosOficinas.agregarInmueble(lectorInmuebles.leer(INMUEBLES_IMPORTAR[1]));
    }

    private void recursiónIdentificadores(Nodo nodo) {
        for (Object hijo : nodo.hijosNodo()) {
            if (hijo instanceof Inmueble)
                inmueblesxId.put(((Inmueble) hijo).genId(), (Inmueble) hijo);
            recursiónIdentificadores((Nodo) hijo);
        }
    }

    private void importarFichas() throws Exception {
        LectorCatastral lectorCatastral = new LectorCatastral(inmueblesxId);
        LectorJurídica lectorJurídica = new LectorJurídica(inmueblesxId);

        for (String inmueble : INMUEBLES_IMPORTAR) {
            lectorCatastral.leer(inmueble);
            lectorJurídica.leer(inmueble);
        }
    }

    private void importarContratos() throws Exception {
        LectorContrato lectorContrato = new LectorContrato(inmueblesxId);
        lectorContrato.leer();

        clientesComerciales = lectorContrato.getClientesComerciales();
        clientesFacturación = lectorContrato.getClientesFacturación();
        contratos = lectorContrato.getContratos();
    }

    // -----------------------------------------------
    // Métodos Públicos
    // -----------------------------------------------

    // -----------------------------------------------
    // Métodos Interfaz TreeModel
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
