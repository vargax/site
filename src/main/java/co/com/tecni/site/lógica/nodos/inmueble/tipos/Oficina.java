package co.com.tecni.site.lógica.nodos.inmueble.tipos;

public class Oficina extends _Inmueble {

    public final static String SIGLA = "OF";
    private int capacidadElectrica = 0 ;

    public Oficina() {
        super();
        super.sigla = SIGLA;
    }

    public int getCapacidadElectrica () {
        return capacidadElectrica;
    }
}
