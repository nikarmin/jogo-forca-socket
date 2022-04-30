/**
 * Comunicado utilizado para passar a vez de cada usuario durante a partida.
 *
 @author Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class ComunicadoDeVez extends Comunicado {

    private int jogadorDaVez;       // indica o jogador atual
    private int numeroJogadores;    // indica o total de jogadores
    private boolean desconectandoJogadorAnterior = false;   // indica se o jogador anterior foi desconectado ou nao
    private ComunicadoDeDados comunicadoDeDados;

    // metodo boolean para saber se o jogador foi desconectado ou nao
    public boolean isDesconectandoJogadorAnterior() {
        return desconectandoJogadorAnterior;
    }

    // metodo boolean que setta se o jogador foi desconectado ou nao
    public void setDesconectandoJogadorAnterior(boolean desconectandoJogadorAnterior) {
        this.desconectandoJogadorAnterior = desconectandoJogadorAnterior;
    }

    // atualiza o comunicado de dados, settando ele
    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }

    // pega o comunicado de dados
    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    // pega o jogador atual
    public int getJogadorDaVez() {
        return jogadorDaVez;
    }

    // setta o jogador atual, que se ele chegar em 3, retorna para a posicao 0
    public void setJogadorDaVez() {
        jogadorDaVez++;
        /*if(jogadorDaVez == numeroJogadores)
            jogadorDaVez = 0;*/
        if(numeroJogadores == this.jogadorDaVez)
            jogadorDaVez = 0;
    }

    // setta o numero de jogadores, onde pega a diferenca do numero de jogadores que sempre
    // vai ser retirada do jogadorDaVez, e no final, o numero de jogadores recebe o que
    // foi passado no parametro
    public void setNumeroJogadores(int numeroJogadores) {
        int diferenca = this.numeroJogadores - numeroJogadores;
        if(this.jogadorDaVez > 0)
            this.jogadorDaVez -= diferenca;
        this.numeroJogadores = numeroJogadores;
    }

    public ComunicadoDeVez(){}

    // construtor de copia, recebendo os dados passados
    public ComunicadoDeVez(ComunicadoDeDados com, int jogadorDaVez, int numeroJogadores) {
        this.comunicadoDeDados = com;
        this.jogadorDaVez = jogadorDaVez;
        this.numeroJogadores = numeroJogadores;
    }

}