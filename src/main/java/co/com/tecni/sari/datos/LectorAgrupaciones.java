package co.com.tecni.sari.datos;

import co.com.tecni.sari.lógica.inmuebles.Agrupación;
import co.com.tecni.sari.lógica.inmuebles.Inmueble;
import co.com.tecni.sari.lógica.inmuebles.PerspectivaInmuebles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

class LectorAgrupaciones {

    private BufferedReader bufferedReader;
    private String líneaActual;

    final LectorInmueble lectorInmueble;


    LectorAgrupaciones(LectorInmueble lectorInmueble) throws Exception {
        this.lectorInmueble = lectorInmueble;
    }

    PerspectivaInmuebles leer() throws Exception {
        bufferedReader = new BufferedReader(new InputStreamReader(LectorAgrupaciones.class.getResourceAsStream("/static/agrupaciones.txt")));
        líneaActual = bufferedReader.readLine();

        return new PerspectivaInmuebles(recursión());
    }

    Agrupación recursión() throws Exception {
        Agrupación agrupación = new Agrupación(formatear(líneaActual));
        int nivel = nivel();

        líneaActual = bufferedReader.readLine();
        while (líneaActual != null) {
            if (nivel >= nivel())
                break;

            if (líneaActual.endsWith(":"))
                agrupación.agregarAgrupación(recursión());
            else {
                String nombre = formatear(líneaActual);
                ArrayList<Inmueble> inmuebles = lectorInmueble.leer(nombre);

                for (Inmueble i : inmuebles) {
                    agrupación.agregarInmueble(i);
                    PerspectivaInmuebles.inmueblesRaiz.put(nombre, i);
                }

                líneaActual = bufferedReader.readLine();
            }

        }
        return agrupación;
    }

    private String formatear(String línea) {
        while (línea.startsWith(" "))
            línea = línea.substring(1);

        return línea.replace(":", "");
    }

    private int nivel() {
        int nivel = 0;
        CharacterIterator ci = new StringCharacterIterator(líneaActual);
        while (ci.current() == ' ') {
            nivel++;
            ci.next();
        }
        return nivel;
    }

}
