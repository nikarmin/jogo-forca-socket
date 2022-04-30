import java.util.ArrayList;

/**
 * Classe utilizada para guardar todas as classes que foram utilizadas na forca,
 * para maior facilidade de uso
 *
 @author Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class ComunicadoDeDados extends Comunicado{

    private ControladorDeErros controladorDeErros = null;
    private Tracinhos tracinhos = null;
    private Palavra palavra = null;
    private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas = null;
    private String chute;
    private String revelacao;
    private int posicao;
    private boolean perdeu = false;
    private boolean ganhou = false;
    private boolean letra = false;

    public ComunicadoDeDados(int qtdJogadores){

        // Construtor de comunicadoDeDados, com o parametro dos jogadores na partida
        // onde instancia todos os dados da forca, para maior facilidade.

        try {
            palavra = BancoDePalavras.getPalavraSorteada();
            tracinhos = new Tracinhos(palavra.getTamanho());
            controladorDeErros = new ControladorDeErros((int)(palavra.getTamanho() * 0.6));
            controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();
        }
        catch (Exception error){}

    }

    public ComunicadoDeDados(){
        // Construtor de comunicadoDeDados, sem nenhum parametro,
        // onde instancia todos os dados da forca, para maior facilidade.
        try {
            palavra = BancoDePalavras.getPalavraSorteada();
            tracinhos = new Tracinhos(palavra.getTamanho());
            controladorDeErros = new ControladorDeErros((int)(palavra.getTamanho() * 0.6));
            controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();
        }
        catch (Exception error){}

    }

    public boolean isLetra() {
        return letra;
    }

    public void setLetra(boolean letra) {
        this.letra = letra;
    }

    public boolean isGanhou() {
        return ganhou;
    }

    public void setGanhou(boolean ganhou) {
        this.ganhou = ganhou;
    }

    public boolean isPerdeu() {
        return perdeu;
    }

    public void setPerdeu(boolean perdeu) {
        this.perdeu = perdeu;
    }

    public ControladorDeErros getControladorDeErros() {
        return controladorDeErros;
    }

    public void setControladorDeErros(ControladorDeErros controladorDeErros) {
        this.controladorDeErros = controladorDeErros;
    }

    public Tracinhos getTracinhos() {
        return tracinhos;
    }

    public void setTracinhos(Tracinhos tracinhos) {
        this.tracinhos = tracinhos;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }

    public ControladorDeLetrasJaDigitadas getControladorDeLetrasJaDigitadas() {
        return controladorDeLetrasJaDigitadas;
    }

    public void setControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas) {
        this.controladorDeLetrasJaDigitadas = controladorDeLetrasJaDigitadas;
    }

    public ComunicadoDeDados (ComunicadoDeDados c) throws Exception
    {
        // Construtor de comunicadoDeDados, com o parametro de um ComunicadoDeDados
        // onde instancia todos os dados da forca, para maior facilidade.
        if (c==null)
            throw new Exception ("Parametro ausente");

        palavra = BancoDePalavras.getPalavraSorteada();
        tracinhos = new Tracinhos(palavra.getTamanho());
        controladorDeErros = new ControladorDeErros((int)(palavra.getTamanho() * 0.6));
        controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();
    }

}
