package co.com.tecni.site.lógica.nodos.inmueble.tipos;

public class Bodega extends _Inmueble {

    public final static String SIGLA = "BG";

    private double altura = 0.0;
    private String acabadoPisos = "";
    private double distanciaColumnas = 0.0f;
    private int capacidadElectricaKVA = 0;
    private int numeroBaños = 0;
    private int capacidadCargaKgsM2=0;

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

    public void setCapacidadElectricaKVA(int capacidadElectricaKVA) {
        this.capacidadElectricaKVA = capacidadElectricaKVA;
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

    public int getCapacidadElectricaKVA() {
        return capacidadElectricaKVA;
    }

    public int getNumeroBaños() {
        return numeroBaños;
    }

    public void setNumeroBaños(int numeroBaños) {
        this.numeroBaños = numeroBaños;
    }

    public int getCapacidadCargaKgsM2() {
        return capacidadCargaKgsM2;
    }

    public void setCapacidadCargaKgsM2(int capacidadCargaKgsM2) {
        this.capacidadCargaKgsM2 = capacidadCargaKgsM2;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length()-2) +
                ",altura:" + altura +
                ",acabadoPisos:'" + acabadoPisos + '\'' +
                ",distanciaColumnas:" + distanciaColumnas +
                ",capacidadElectricaKVA:" + capacidadElectricaKVA +
                ",capacidadCargaKgsM2:" + capacidadCargaKgsM2 +
                ",numeroBaños:" + numeroBaños +
                " }";
    }
}
