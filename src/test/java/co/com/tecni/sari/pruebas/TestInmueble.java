package co.com.tecni.sari.pruebas;

import co.com.tecni.sari.lógica.inmuebles.Inmueble;

import java.util.ArrayList;

public class TestInmueble {

    private final static int OFICINASxPISO = 4;

    private Inmueble ecotower93;
    private ArrayList<Inmueble> niveles;
/*
    @Before
    public void setUp() throws Exception {

        HashMap<String, Double> m2nivel = new HashMap<>();
        m2nivel.put(Inmueble.A_PRIV_CONSTRUIDOS, 643.87);
        m2nivel.put(Inmueble.A_PRIV_LIBRES, 0.0);
        m2nivel.put(Inmueble.A_COM_CONSTRUIDOS, 204.57);
        m2nivel.put(Inmueble.A_COM_LIBRES, 50.39);

        HashMap<String, Double> m2oficina = new HashMap<>();
        m2oficina.put(Inmueble.A_PRIV_CONSTRUIDOS, 160.9675);
        m2oficina.put(Inmueble.A_PRIV_LIBRES, 0.0);
        m2oficina.put(Inmueble.A_COM_CONSTRUIDOS, 51.1425);
        m2oficina.put(Inmueble.A_COM_LIBRES, 12.5975);

        niveles = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            niveles.add(new Inmueble("Nivel "+i, m2nivel));
        }

        // Cada nivel se desengloba en 4 oficinas
        for (Inmueble nivel : niveles) {
            ArrayList<Inmueble> oficinas = new ArrayList<>();

            for (int i = 1; i <= OFICINASxPISO; i++) {
                oficinas.add(new Inmueble("Oficina "+i, m2oficina));
            }

            nivel.desenglobar(oficinas);
        }

        // Ecotower se genera englobando todos sus niveles
        ecotower93 = Inmueble.englobar("CR 17 93 03", niveles);
    }

    @Test
    public void englobar() throws Exception {
        assertEquals(niveles.size(), ecotower93.hijosNodo().size());
    }

    @Test
    public void desenglobar() throws Exception {
        for(Agrupación nivel : ecotower93.hijosNodo())
            assertEquals(OFICINASxPISO, nivel.hijosNodo().size());
    }

    @Test
    public void nombreNodo() throws Exception {

        for (Agrupación nivel : niveles)
            for (Agrupación oficina : nivel.hijosNodo()) {
                System.out.println(oficina.genNombre());
                assertEquals(29, oficina.genNombre().length());
            }
    }*/
}