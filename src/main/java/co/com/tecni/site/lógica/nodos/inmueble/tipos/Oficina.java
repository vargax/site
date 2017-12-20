package co.com.tecni.site.lógica.nodos.inmueble.tipos;

public class Oficina extends _Inmueble {

    public final static String SIGLA = "OF";
    private double altura = 0 ;
    private int numBaños = 0;
    private String acabadoPisos = "";

    public Oficina() {
        super();
        super.sigla = SIGLA;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setNumBaños(int numBaños) {
        this.numBaños = numBaños;
    }

    public void setAcabadoPisos(String acabadoPisos) {
        this.acabadoPisos = acabadoPisos;
    }

    public double getAltura () {
        return altura;
    }

    public String getAcabadoPisos() {
        return acabadoPisos;
    }

    @Override
    public String toString() {
        return super.toString() +
                "{ altura=" + altura +
                ", acabadoPisos='" + acabadoPisos + '\'' +
                " }";
    }
}
