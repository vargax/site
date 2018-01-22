package co.com.tecni.site.lógica.nodos.inmueble.tipos;

public class ZonaComún extends _Inmueble {

    public final static String SIGLA = "ZC";
    private int altura = 0;
    private int numeroBaños = 0;

    public ZonaComún() {
        super();
        super.sigla = SIGLA;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getNumeroBaños() {
        return numeroBaños;
    }

    public void setNumeroBaños(int numeroBaños) {
        this.numeroBaños = numeroBaños;
    }
    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length()-2) +
                ",altura:" + altura +
                ",numeroBaños:" + numeroBaños +
                " }";
    }
}
