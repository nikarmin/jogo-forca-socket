/**
 * Comunicado utilizado para poder passar a letra que o usuario digitou, para a supervisora
 *
 @author Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class PedidoDeLetra extends Comunicado{
    private char letra;
    private ComunicadoDeDados comunicadoDeDados;

    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }

    public PedidoDeLetra(char letra, ComunicadoDeDados com)
    {
        this.letra = letra;
        this.comunicadoDeDados = com;
    }

    public char getLetra() {return this.letra;}

    public String toString ()
    {
        return (""+this.letra);
    }
}
