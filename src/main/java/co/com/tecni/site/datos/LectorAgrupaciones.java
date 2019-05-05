package co.com.tecni.site.datos;

import co.com.tecni.site.lógica.inmuebles.Agrupación;
import co.com.tecni.site.lógica.inmuebles.tipos.Inmueble;
import co.com.tecni.site.lógica.árboles.Árbol;
import co.com.tecni.site.lógica.árboles.ÁrbolInmuebles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

class LectorAgrupaciones {

    private BufferedReader bufferedReader;
    private String líneaActual;

    final LectorInmueble lectorInmueble;


    LectorAgrupaciones(LectorInmueble lectorInmueble) throws Exception {
        this.lectorInmueble = lectorInmueble;
    }

    ÁrbolInmuebles leer() throws Exception {
        bufferedReader = new BufferedReader(new InputStreamReader(LectorAgrupaciones.class.getResourceAsStream("/static/agrupaciones.txt")));
        líneaActual = bufferedReader.readLine();

        return new ÁrbolInmuebles(recursión());
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
                Inmueble inm = lectorInmueble.leer(nombre);

                agrupación.agregarInmueble(inm);
                ÁrbolInmuebles.inmueblesRaiz.put(nombre, inm);

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
