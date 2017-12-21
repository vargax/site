package co.com.tecni.site.lógica.nodos.inmueble.tipos;

public class Bodega extends _Inmueble {

    public final static String SIGLA = "BG";

    private double altura;
    private String acabadoPisos = "";
    private double distanciaColumnas = 0.0f;
    private int capacidadElectrica;

    public Bodega() {
        super();
        super.sigla = SIGLA;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setAcabadoPisos(String acabadoPisos) {
        this.acabadoPisos = acabadoPisos;
    }

    public void setDistanciaColumnas(double distanciaColumnas) {
        this.distanciaColumnas = distanciaColumnas;
    }

    public void setCapacidadElectrica(int capacidadElectrica) {
        this.capacidadElectrica = capacidadElectrica;
    }

    public double getAltura() {
        return altura;
    }

    public double getDistanciaColumnas() {
        return distanciaColumnas;
    }

    public String getAcabadoPisos() {
        return acabadoPisos;
    }

    public int getCapacidadElectrica() {
        return capacidadElectrica;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length()-2) +
                ",altura:" + altura +
                ",acabadoPisos:'" + acabadoPisos + '\'' +
                ",distanciaColumnas:" + distanciaColumnas +
                ",capacidadElectrica:" + capacidadElectrica +
                " }";
    }
}
