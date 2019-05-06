package co.com.tecni.sari.ui;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class UiMenu {

    JMenuBar componente;

    BufferedReader bufferedReader;
    String líneaActual;

    UiMenu() throws Exception {

        componente = new JMenuBar();

        bufferedReader = new BufferedReader(new InputStreamReader(UiMenu.class.getResourceAsStream("/static/menu.txt")));

        líneaActual = bufferedReader.readLine();
        do {
            componente.add(recursión());
        } while (líneaActual != null);
    }

    JMenu recursión() throws Exception {

        JMenu menu = new JMenu(formatear(líneaActual));
        int nivel = líneaActual.lastIndexOf(" ");

        líneaActual = bufferedReader.readLine();
        while (líneaActual != null) {
            if (nivel >= líneaActual.lastIndexOf(" "))
                break;

            if (líneaActual.endsWith(":")) {
                menu.add(recursión());
            } else {
                menu.add(new JMenuItem(formatear(líneaActual)));
                líneaActual = bufferedReader.readLine();
            }
        }
        return menu;
    }

    private String formatear(String línea) {
        while (línea.startsWith(" "))
            línea = línea.substring(1);

        línea = línea.replace("_", " ");
        línea = línea.replace(":", "");
        return línea;
    }
}
