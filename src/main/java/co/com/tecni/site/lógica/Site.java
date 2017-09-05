package co.com.tecni.site.lógica;

import co.com.tecni.site.datos.Lector;
import co.com.tecni.site.lógica.inmueble.Inmueble;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

public class Site {

    private Lector lector;

    private ArrayList<Inmueble> raices;

    public Site() throws Exception {
        lector = new Lector();
        raices = new ArrayList();

        raices.add(lector.leer("ecotower93"));
    }

    public static void main(String args[]) throws Exception {
        new Site();
    }
}
