import java.util.ArrayList;
import java.util.Vector;

/**
 * Comunicado utilizado para iniciar o jogo, ou seja,
 * aguarda os usuários entrarem para depois mandar os dados
 *
 @author Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class ComunicadoIniciador extends Comunicado{

    private ComunicadoDeDados comunicadoDeDados;
    private int posicao;                        // pega posicao do usuario

    // construtor pegando as informacoes dos parametros
    public ComunicadoIniciador(ComunicadoDeDados comunicadoDeDados, int posicao) {
        this.comunicadoDeDados = comunicadoDeDados;
        this.posicao = posicao;
    }

    // metodo para mostrar para o usuario a ordem dele
    public int pegarJogador()
    {
        int indiceCorreto = 0;

        if(posicao == 0)
        {
            indiceCorreto = 3;
            return indiceCorreto;
        }
        else
            return posicao;
    }

    public ComunicadoIniciador(int posicao) {
        this.posicao = posicao;
    }

    public ComunicadoIniciador() {
    }

    // getter do comunicadoDeDados
    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    // setter do comunicadoDeDados
    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }

    // getter da posicao
    public int getPosicao() {
        return posicao;
    }

    // setter da posicao
    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}
