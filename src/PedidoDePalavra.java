/**
 * Comunicado utilizado para poder pegar o chute na supervisora, que o usurio esta mandando
 *
 @author Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class PedidoDePalavra extends Comunicado{

    private String palavra;
    private ComunicadoDeDados comunicadoDeDados;

    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }

    public PedidoDePalavra(String palavra, ComunicadoDeDados com)
    {
        this.palavra = palavra;
        this.comunicadoDeDados = com;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
}
