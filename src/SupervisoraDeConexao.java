import java.io.*;
import java.net.*;
import java.util.*;

/**
 @author Andre Luis, Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class SupervisoraDeConexao extends Thread
{
    private double              valor=0;
    private Parceiro            usuario;
    private Socket              conexao;
    private ArrayList<Parceiro> usuarios;
    private int                 jogadorAtual;
    //private boolean letraDnv = false;

    public SupervisoraDeConexao
    (Socket conexao, ArrayList<Parceiro> usuarios)
    throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.usuarios = usuarios;

    }

    public void run ()
    {

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
            new ObjectOutputStream(
            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor=
            new ObjectInputStream(
            this.conexao.getInputStream());
        }
        catch (Exception err0)
        {
            try
            {
                transmissor.close();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread
            
            return;
        }

        try
        {
            this.usuario =
            new Parceiro (this.conexao,
                          receptor,
                          transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos

        // Declaracao de variaveis
        ComunicadoDeDados comunicadoDeDados = null;
        int jogadores = 0;
        int idJogadorAtual = 0;

        try
        {
            synchronized (this.usuarios)
            {
                this.usuarios.add(this.usuario);

                if (this.usuarios.size() % 3 == 0)                          // Formacao dos grupos de 3 usuarios
                {
                    jogadores = this.usuarios.size();                       // Pega o tamanho do Array
                    comunicadoDeDados = new ComunicadoDeDados(jogadores);   // Passa o total de jogadores para o comunicadoDeDados

                    // Usuarios receberem o comunicadoIniciador, juntamente com o comunicadoDeDados e a devida posicao
                    this.usuarios.get(jogadores - 1).receba(new ComunicadoIniciador(comunicadoDeDados, 0));
                    this.usuarios.get(jogadores - 2).receba(new ComunicadoIniciador(comunicadoDeDados, 2));
                    this.usuarios.get(jogadores - 3).receba(new ComunicadoIniciador(comunicadoDeDados, 1));

                    // O primeiro usuario, recebe o comunicadoDeVez para iniciar jogando
                    this.usuarios.get(jogadores - 3).receba(new ComunicadoDeVez(comunicadoDeDados, 0, this.usuarios.size()));
                }
            }

            // Declaracao de variaveis
            ComunicadoIniciador comunicadoIniciador = null;
            Palavra palavra = null;

            for (; ; )
            {
                Comunicado comunicado = this.usuario.envie();

                if (comunicado == null)
                    return;

                else if (comunicado instanceof ComunicadoDeDados)       // Recebe um comunicadoDeDados com os dados atualizados
                {
                    comunicadoDeDados = ((ComunicadoDeDados)comunicado);
                }

                else if (comunicado instanceof ComunicadoDeVez)         // Passa a vez do jogador
                {
                    ComunicadoDeVez vez = null;
                    vez = ((ComunicadoDeVez) comunicado);               // Faz um cast do ComunicadoDeVez
                    vez.setNumeroJogadores(this.usuarios.size());       // Setta o numero de jogadores, com o size total do array
                    vez.setComunicadoDeDados(comunicadoDeDados);        // Setta o comunicado de dados atual
                    vez.setJogadorDaVez();                              // Pega o usuario da vez

                    // Passa a vez para o proximo usuario
                    usuarios.get(vez.getJogadorDaVez()).receba(vez);
                }

                else if (comunicado instanceof PedidoDePalavra)
                {
                    // Tratando o chute da palavra
                    try
                    {
                        PedidoDePalavra pedidoDePalavra = (PedidoDePalavra) comunicado;             // cast do comunicado
                        comunicadoDeDados = ((PedidoDePalavra) comunicado).getComunicadoDeDados();  // pega o comunicado de dados atualizado

                        // se a palavra sorteada for igual a palavra chutada irá realizar o if
                        if (comunicadoDeDados.getPalavra().chute(pedidoDePalavra.getPalavra()))
                        {
                            // revela o a palavra para o usuario
                            comunicadoDeDados.getTracinhos().reveleChute(pedidoDePalavra.getPalavra());
                            comunicadoDeDados.setTracinhos(comunicadoDeDados.getTracinhos());

                            // pega o indice do usuario atual (que venceu), e manda um comunicadoDeVitoria
                            int indiceVencedor = this.usuarios.indexOf(this.usuario);
                            this.usuario.receba(new ComunicadoDeVitoria());

                            // de acordo com o indice do vencedor, o restante ira receber um comunciadoDeDerrota
                            avisoDePerda();
                        }

                        else
                        {
                            // caso erre o chute, sera retirado do jogo
                            usuario.receba(new ComunicadoDeDerrota());

                            // se dois perderem, o que restou ganha
                            if(this.usuarios.size() == 2){
                                this.usuarios.get(1).receba(new ComunicadoDeVitoria());
                            }
                        }
                    }
                    catch (Exception error){
                        System.out.println(error.getMessage());
                    }
                }

                else if(comunicado instanceof PedidoDeLetra)
                {
                    // Tratando a letra que o usuario digitou

                    PedidoDeLetra pedidoDeLetra = (PedidoDeLetra) comunicado;                   // faz um cast de PedidoDeLetra
                    comunicadoDeDados = ((PedidoDeLetra) comunicado).getComunicadoDeDados();    // pega o comunicadoDeDados atualizado

                    // se a letra ja foi digitada, o usuario recebe um comunicadoDeLetraDigitada
                    if(comunicadoDeDados.getControladorDeLetrasJaDigitadas().isJaDigitada(pedidoDeLetra.getLetra()))
                        usuario.receba(new ComunicadoLetraDigitada());

                    else
                    {
                        // se nao, registra a letra e verifica se a palavra tem essa letra
                        comunicadoDeDados.getControladorDeLetrasJaDigitadas().registre(pedidoDeLetra.getLetra());
                        int qtd = comunicadoDeDados.getPalavra().getQuantidade(pedidoDeLetra.getLetra());

                        // se nao tiver, a qtd vai ser zero, sendo assim, o usuario recebeu um comunicadoErrouLetra
                        if (qtd==0)
                        {
                            usuario.receba(new ComunicadoErrouLetra());
                        }

                        // se nao foi digitada, e nao errou a letra, possivelmente o usuario acertou
                        else
                        {
                            // verifica os indices em que a letra esta na palavra e revela aos poucos para o usuario
                            for (int i=0; i<qtd; i++)
                            {
                                int posicao = comunicadoDeDados.getPalavra().getPosicaoDaIezimaOcorrencia(i, pedidoDeLetra.getLetra());
                                comunicadoDeDados.getTracinhos().revele(posicao, pedidoDeLetra.getLetra());//tracinhos.revele (posicao, letra);
                            }

                            // apos isso, o usuario recebe um comunicado que acertou a letra
                            usuario.receba(new ComunicadoDeAcertoLetra());

                            // se nao tiver mais nenhum tracinho, o usuario completou a palavra, ou seja, ganhou
                            if(!comunicadoDeDados.getTracinhos().isAindaComTracinhos())
                            {
                                // pega o indice do usuario vencedor e ele recebe um comunicadoDeVitoria
                                int indiceVencedor = this.usuarios.indexOf(this.usuario);
                                this.usuario.receba(new ComunicadoDeVitoria());

                                // passa um aviso de derrota para os demais usuarios
                                avisoDePerda();
                            }

                        }
                    }
                }

                // pedido para o usuario sair da partida
                else if(comunicado instanceof PedidoParaSair)
                {
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove(this.usuario);
                    }
                    this.usuario.adeus();

                }
            }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }

    private void avisoDePerda()
    {
        try
        {
            // posicao do jogador que venceu
            int vencedor = this.usuarios.indexOf(usuario);

                // verifica a posica do usuario que venceu, e apos isso manda os comunicadosDeDerrota para os demais
                if (vencedor == 0)
                {
                    if (this.usuarios.size() == 3)
                    {
                        this.usuarios.get(1).receba(new ComunicadoDeDerrota());
                        this.usuarios.get(2).receba(new ComunicadoDeDerrota());
                    }
                    else if (this.usuarios.size() == 2)
                    {
                        this.usuarios.get(1).receba(new ComunicadoDeDerrota());
                    }
                }
                else if (vencedor == 1)
                {
                    if (this.usuarios.size() == 3)
                    {
                        this.usuarios.get(0).receba(new ComunicadoDeDerrota());
                        this.usuarios.get(2).receba(new ComunicadoDeDerrota());
                    }
                    else if (this.usuarios.size() == 2)
                    {
                        this.usuarios.get(0).receba(new ComunicadoDeDerrota());
                    }

                }
                else if (vencedor == 2)
                {
                    if (this.usuarios.size() == 3)
                    {
                        this.usuarios.get(0).receba(new ComunicadoDeDerrota());
                        this.usuarios.get(1).receba(new ComunicadoDeDerrota());
                    }
                    else if (this.usuarios.size() == 2)
                    {
                        this.usuarios.get(0).receba(new ComunicadoDeDerrota());
                    }
                }

        }
        catch (Exception erro){
            erro.printStackTrace();
        }
    }

}
