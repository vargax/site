package co.com.tecni.site.lógica.nodos.contratos;

class Factura {

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------
    static int consecutivoFactura = 1;

    static int generarConsecutivo() {
        return consecutivoFactura += 1;
    }

}
