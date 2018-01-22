package co.com.tecni.site.lógica.nodos.inmueble.tipos;

public class Local extends _Inmueble {

    public final static String SIGLA = "LC";
    private double altura = 0 ;
    private int numeroBaños = 0;
    private String acabadoPisos = "";

    public Local() {
        super();
        super.sigla = SIGLA;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public int getNumeroBaños() {
        return numeroBaños;
    }

    public void setNumeroBaños(int numeroBaños) {
        this.numeroBaños = numeroBaños;
    }

    public String getAcabadoPisos() {
        return acabadoPisos;
    }

    public void setAcabadoPisos(String acabadoPisos) {
        this.acabadoPisos = acabadoPisos;
    }
    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length()-2) +
                ",altura:" + altura +
                ",acabadoPisos:'" + acabadoPisos + "'" +
                ",numeroBaños:" + numeroBaños +
                " }";
    }
}
