package co.com.tecni.site.lógica.nodos.inmueble.tipos;

public class Garaje extends _Inmueble {

    public final static String SIGLA = "GR";
    private double altura = 0 ;

    public Garaje() {
        super();
        super.sigla = SIGLA;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length()-2) +
                ",altura:" + altura +
                " }";
    }
}
