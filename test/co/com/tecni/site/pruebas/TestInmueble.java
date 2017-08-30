package co.com.tecni.site.pruebas;

import co.com.tecni.site.lógica.inmueble.Inmueble;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestInmueble {

    private final static int OFICINASxPISO = 4;

    private Inmueble ecotower93;
    private ArrayList<Inmueble> niveles;

    @Before
    public void setUp() throws Exception {

        niveles = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            niveles.add(new Inmueble("Nivel "+i ));
        }

        // Cada nivel se desengloba en 4 oficinas
        for (Inmueble nivel : niveles) {
            ArrayList<Inmueble> oficinas = new ArrayList<>();

            for (int i = 1; i <= OFICINASxPISO; i++) {
                oficinas.add(new Inmueble("Oficina "+i));
            }

            nivel.desenglobar(oficinas);
        }

        // Ecotower se genera englobando todos sus niveles
        ecotower93 = Inmueble.englobar("CR 17 93 03", niveles);
    }

    @Test
    public void englobar() throws Exception {
        assertEquals(niveles.size(), ecotower93.getHijos().size());
    }

    @Test
    public void desenglobar() throws Exception {
        for(Inmueble nivel : ecotower93.getHijos())
            assertEquals(OFICINASxPISO, nivel.getHijos().size());
    }

    @Test
    public void getNombre() throws Exception {

        for (Inmueble nivel : niveles)
            for (Inmueble oficina : nivel.getHijos()) {
                System.out.println(oficina.getNombre());
                assertEquals(29, oficina.getNombre().length());
            }
    }
}