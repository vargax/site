package co.com.tecni.site.lógica.nodos.inmueble.tipos;

public class Bodega extends _Inmueble {

    public final static String SIGLA = "BG";

    private float altura;
    private String acabadoPisos = "";
    private float distanciaColumnas = 0.0f;
    private int capacidadElectrica;

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public void setAcabadoPisos(String acabadoPisos) {
        this.acabadoPisos = acabadoPisos;
    }

    public void setDistanciaColumnas(float distanciaColumnas) {
        this.distanciaColumnas = distanciaColumnas;
    }

    public void setCapacidadElectrica(int capacidadElectrica) {
        this.capacidadElectrica = capacidadElectrica;
    }

    public float getAltura() {
        return altura;
    }

    public String getAcabadoPisos() {
        return acabadoPisos;
    }

    public float getDistanciaColumnas() {
        return distanciaColumnas;
    }

    public int getCapacidadElectrica() {
        return capacidadElectrica;
    }

    public Bodega() {
        super();
        super.sigla = SIGLA;
    }
}
