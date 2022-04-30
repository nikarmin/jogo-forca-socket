import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
	@author Andre Luis, Nicoli, Lucas, Samuel e Talita.
 	@since 2021.
 */

public class Cliente {
	public static final String HOST_PADRAO  = "localhost";
	public static final int    PORTA_PADRAO = 3000;
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";

	public static void main (String[] args) {
		if (args.length > 2) {
			System.err.println("Uso esperado: java Cliente [HOST [PORTA]]\n");
			return;
		}

		Socket conexao = null;
		try {
			String host = Cliente.HOST_PADRAO;
			int porta = Cliente.PORTA_PADRAO;

			if (args.length > 0)
				host = args[0];

			if (args.length == 2)
				porta = Integer.parseInt(args[1]);

			conexao = new Socket(host, porta);
		} catch (Exception erro) {
			System.err.println("Indique o servidor e a porta corretos!\n");
			return;
		}

		ObjectOutputStream transmissor = null;
		try {
			transmissor =
					new ObjectOutputStream(
							conexao.getOutputStream());
		} catch (Exception erro) {
			System.err.println("Indique o servidor e a porta corretos!\n");
			return;
		}

		ObjectInputStream receptor = null;
		try {
			receptor =
					new ObjectInputStream(
							conexao.getInputStream());
		} catch (Exception erro) {
			System.err.println("Indique o servidor e a porta corretos!\n");
			return;
		}

		Parceiro servidor = null;
		try {
			servidor =
					new Parceiro(conexao, receptor, transmissor);
		} catch (Exception erro) {
			System.err.println("Indique o servidor e a porta corretos!\n");
			return;
		}

		TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
		try {
			tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento(servidor);
		} catch (Exception erro) {
		} // sei que servidor foi instanciado

		tratadoraDeComunicadoDeDesligamento.start();

		// Começando a parte da forca no cliente

		// Instanciando classes que serão utilizadas
		ComunicadoDeDados comunicadoDeDados = null;
		ComunicadoIniciador comunicadoIniciador = null;
		Comunicado comunicado = null;
		ControladorDeErros controladorDeErros = null;
		Tracinhos tracinhos = null;
		Palavra palavra = null;
		ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas = null;
		ComunicadoDeVitoria comunicadoDeVitoria = null;
		ComunicadoDeDerrota comunicadoDeDerrota = null;
		boolean primeiraVez = true;

		try {
			// Aguardando os usuários, esperando pelo comunicadoIniciador
			// e esperando os usuários receberem o comunicadoDeDados

			System.out.println("Aguarde os jogadores...");
			do
			{
				comunicado = (Comunicado) servidor.espie();
			}
			while (!(comunicado instanceof ComunicadoIniciador));	//Esperando o comunicado iniciador, que irá agrupar os 3 jogadores
			comunicado = servidor.envie();
			comunicadoIniciador = (ComunicadoIniciador)comunicado;
			comunicadoDeDados = ((ComunicadoIniciador) comunicado).getComunicadoDeDados();
		}
		catch (Exception error) {}

		ComunicadoDeVez vez = null;
		boolean desconectarJogador = false;		// Declara uma variavel boolean, se o jogador
												// vai ser desconectado ou nao

		// Palavras
			for (;;)
			{
				try
				{
					// Verifica se foi a primeira vez que passou pelos print's
					if (primeiraVez) {
						System.out.println("\r\nJogo da Forca via localhost\r\n");
						System.out.println("QUAL É A PALAVRA?\r\n");
						System.out.println("Olá jogador Nº " + comunicadoIniciador.pegarJogador());
						primeiraVez = false;
					}

					// Esperando um comunicado do servior
					do
					{
						comunicado = (Comunicado) servidor.espie();
					}
					while  (!(comunicado instanceof ComunicadoDeDerrota) &&
							!(comunicado instanceof ComunicadoDeVitoria) &&
							!(comunicado instanceof ComunicadoDeVez));
					comunicado = servidor.envie();
				}
				catch (Exception error){}

				// Se vier um comunicadoDeDerrota, o servidor irá fechar a thread e o cliente
				// além de passar para o próximo
				if (comunicado instanceof ComunicadoDeDerrota)
				{
					System.out.println("\n" + ANSI_RED +
							" __     _____   ____ _____   ____  _____ ____  ____  _____ _   _ \n" +
							" \\ \\   / / _ \\ / ___| ____| |  _ \\| ____|  _ \\|  _ \\| ____| | | |\n" +
							"  \\ \\ / / | | | |   |  _|   | |_) |  _| | |_) | | | |  _| | | | |\n" +
							"   \\ V /| |_| | |___| |___  |  __/| |___|  _ <| |_| | |___| |_| |\n" +
							"    \\_/  \\___/ \\____|_____| |_|   |_____|_| \\_\\____/|_____|\\___/ \n" +
							ANSI_RESET);
					System.out.println(ANSI_RED + "Que pena, voce perdeu! A palavra era: "+comunicadoDeDados.getPalavra() + ANSI_RESET);
					System.out.println("Voce esta sendo retirado do jogo, ate mais!");

					// Indica que ele podera ser desconectado, afinal, perdeu
					desconectarJogador = true;
				}

				// Se vier um comunicadoDeVitoria, o servidor irá fechar a thread e o cliente,
				// além de mandar um comunicadoDeDerrota para os demais.
				else if (comunicado instanceof ComunicadoDeVitoria)
				{
					System.out.println("\n" + ANSI_PURPLE +
							" __     _____   ____ _____    ____    _    _   _ _   _  ___  _   _ \n" +
							" \\ \\   / / _ \\ / ___| ____|  / ___|  / \\  | \\ | | | | |/ _ \\| | | |\n" +
							"  \\ \\ / / | | | |   |  _|   | |  _  / _ \\ |  \\| | |_| | | | | | | |\n" +
							"   \\ V /| |_| | |___| |___  | |_| |/ ___ \\| |\\  |  _  | |_| | |_| |\n" +
							"    \\_/  \\___/ \\____|_____|  \\____/_/   \\_\\_| \\_|_| |_|\\___/ \\___/ \n"+
							ANSI_RESET);
					System.out.println(ANSI_PURPLE + "Parabens, voce ganhou! Que tal jogar novamente?" + ANSI_RESET);
					System.out.println("Voce esta sendo retirado do jogo, ate mais!");

					// Indica que ele podera ser desconectado, afinal, ganhou
					desconectarJogador = true;
				}

				// Se vier um comunicadoDeVez, o usuário receberá as informações do jogo, e enfim, jogar.
				else if(comunicado instanceof ComunicadoDeVez)
				{
					try
					{
						// Faz um cast do comunicadoDeVez e pega o comunicadoDeDados.
						vez = (ComunicadoDeVez) comunicado;
						comunicadoDeDados = vez.getComunicadoDeDados();
					}
					catch (Exception error)
					{
						System.out.println(error.getMessage());
					}

					// Print's recorrentes do jogo, que sempre serao atualizados
					System.out.println("Palavra: " + comunicadoDeDados.getTracinhos());
					System.out.println("Digitadas: " + comunicadoDeDados.getControladorDeLetrasJaDigitadas());

					try {
						// Usuário faz uma escolha, chutar a palavra ou dizer uma letra.

						char resposta = ' ';

						try
						{
							System.out.print("Você deseja chutar a Palavra ou dizer a Letra? (P/L) ");
							resposta = Character.toUpperCase(Teclado.getUmChar());
						}
						catch (Exception error) {}

						if (resposta == 'P')    // Caso a pessoa escolha chutar a palavra
						{
							System.out.print("Diga a palavra que deseja chutar: ");
							String chutePalavra;

							try {
								chutePalavra = Teclado.getUmString();
								chutePalavra = chutePalavra.toUpperCase();        // Pega a string e a deixa maiuscula.
							}
							catch (Exception error)
							{
								System.err.println("Opcao invalida!");
								continue;
							}

							// Servidor recebe um pedidoDePalavra, com a palavra
							// e o comunicadoDeDados com as informacoes
							servidor.receba(new PedidoDePalavra(chutePalavra, comunicadoDeDados));

							// Espera receber um comunicadoDeDerrota ou vitoria.
							do
							{
								comunicado = (Comunicado) servidor.espie();
							}
							while (!(comunicado instanceof ComunicadoDeDerrota) &&
								   !(comunicado instanceof ComunicadoDeVitoria));
							comunicado = servidor.envie();

							// Ambos recebem print's de seus respectivos comunicados.

							if (comunicado instanceof ComunicadoDeDerrota)
							{
								System.out.println("\n" + ANSI_RED +
										" __     _____   ____ _____   ____  _____ ____  ____  _____ _   _ \n" +
										" \\ \\   / / _ \\ / ___| ____| |  _ \\| ____|  _ \\|  _ \\| ____| | | |\n" +
										"  \\ \\ / / | | | |   |  _|   | |_) |  _| | |_) | | | |  _| | | | |\n" +
										"   \\ V /| |_| | |___| |___  |  __/| |___|  _ <| |_| | |___| |_| |\n" +
										"    \\_/  \\___/ \\____|_____| |_|   |_____|_| \\_\\____/|_____|\\___/ \n" +
										ANSI_RESET);
								System.out.println(ANSI_RED + "Que pena, voce perdeu! A palavra era: " + comunicadoDeDados.getPalavra() + ANSI_RESET);
								System.out.println("Voce esta sendo retirado do jogo, ate mais!");
							}

							else if (comunicado instanceof ComunicadoDeVitoria)
							{
								System.out.println("\n" + ANSI_PURPLE +
										" __     _____   ____ _____    ____    _    _   _ _   _  ___  _   _ \n" +
										" \\ \\   / / _ \\ / ___| ____|  / ___|  / \\  | \\ | | | | |/ _ \\| | | |\n" +
										"  \\ \\ / / | | | |   |  _|   | |  _  / _ \\ |  \\| | |_| | | | | | | |\n" +
										"   \\ V /| |_| | |___| |___  | |_| |/ ___ \\| |\\  |  _  | |_| | |_| |\n" +
										"    \\_/  \\___/ \\____|_____|  \\____/_/   \\_\\_| \\_|_| |_|\\___/ \\___/ \n" +
										ANSI_RESET);
								System.out.println(ANSI_PURPLE + "Parabens, voce ganhou! Que tal jogar novamente?" + ANSI_RESET);
								System.out.println("Voce esta sendo retirado do jogo, ate mais!");
							}

							desconectarJogador = true;						   // Indica que o usuario pode ser desconectado, pois ele ganhou ou perdeu
							vez.setDesconectandoJogadorAnterior(true);        // Marca que o jogador foi desconectado
						}

						else if (resposta == 'L')        				// Caso a pessoa escolha a letra
						{
							System.out.print("Diga a letra: ");        // Caso a pessoa escolher dizer a letra
							char letra = ' ';

							try
							{
								letra = Character.toUpperCase(Teclado.getUmChar());        // Passa a letra para maiusculo
							}
							catch (Exception error){}

							servidor.receba(new PedidoDeLetra(letra, comunicadoDeDados));        // Passa a letra para o servidor

							// Espera algum dos comunicados disponiveis para a opcao da letra.
							do
							{
								comunicado = (Comunicado) servidor.espie();
							}
							while (!(comunicado instanceof ComunicadoDeDerrota) &&
									!(comunicado instanceof ComunicadoDeVitoria) &&
									!(comunicado instanceof ComunicadoDeAcertoLetra) &&
									!(comunicado instanceof ComunicadoErrouLetra) &&
									!(comunicado instanceof ComunicadoLetraDigitada));
							comunicado = servidor.envie();

							// Todos recebem print's de seus respectivos comunicados.
							if (comunicado instanceof ComunicadoDeDerrota)
							{
								System.out.println("\n" + ANSI_RED +
										" __     _____   ____ _____   ____  _____ ____  ____  _____ _   _ \n" +
										" \\ \\   / / _ \\ / ___| ____| |  _ \\| ____|  _ \\|  _ \\| ____| | | |\n" +
										"  \\ \\ / / | | | |   |  _|   | |_) |  _| | |_) | | | |  _| | | | |\n" +
										"   \\ V /| |_| | |___| |___  |  __/| |___|  _ <| |_| | |___| |_| |\n" +
										"    \\_/  \\___/ \\____|_____| |_|   |_____|_| \\_\\____/|_____|\\___/ \n" +
										ANSI_RESET);
								System.out.println(ANSI_RED + "Que pena, voce perdeu! A palavra era: " + comunicadoDeDados.getPalavra() + ANSI_RESET);
								System.out.println("Voce esta sendo retirado do jogo, ate mais!");

								vez.setDesconectandoJogadorAnterior(true);
								desconectarJogador = true;        // Indica que o usuario pode ser desconectado, pois ele perdeu
							}

							else if (comunicado instanceof ComunicadoDeVitoria)
							{
								System.out.println("\n" + ANSI_PURPLE +
										" __     _____   ____ _____    ____    _    _   _ _   _  ___  _   _ \n" +
										" \\ \\   / / _ \\ / ___| ____|  / ___|  / \\  | \\ | | | | |/ _ \\| | | |\n" +
										"  \\ \\ / / | | | |   |  _|   | |  _  / _ \\ |  \\| | |_| | | | | | | |\n" +
										"   \\ V /| |_| | |___| |___  | |_| |/ ___ \\| |\\  |  _  | |_| | |_| |\n" +
										"    \\_/  \\___/ \\____|_____|  \\____/_/   \\_\\_| \\_|_| |_|\\___/ \\___/ \n" +
										ANSI_RESET);
								System.out.println(ANSI_PURPLE + "Parabens, voce ganhou! Que tal jogar novamente?" + ANSI_RESET);
								System.out.println("Voce esta sendo retirado do jogo, ate mais!");

								vez.setDesconectandoJogadorAnterior(true);	// Marca que o jogador foi desconectado
								desconectarJogador = true;            		// O usuario ganhou, por isso sera desconectado.
							}

							else if (comunicado instanceof ComunicadoDeAcertoLetra)
							{
								vez.setDesconectandoJogadorAnterior(false);
								System.out.println("\n- [STATUS] Você acertou a letra!\n");
							}

							else if (comunicado instanceof ComunicadoErrouLetra){
								vez.setDesconectandoJogadorAnterior(false);
								System.err.println("\n- [STATUS] Você errou a letra!\n");
							}

							else if (comunicado instanceof ComunicadoLetraDigitada)
							{
								vez.setDesconectandoJogadorAnterior(false);
								System.out.println("\n- [STATUS] Essa letra já foi digitada!\n");
							}
						}

						// Se o usuario digitar algum caractere invalido, ele perde sua vez e passa para o proximo.
						else
						{
							System.err.println("\nCaracter invalido, tente novamente na proxima vez!\n");
						}

						servidor.receba(comunicadoDeDados);            // Servidor recebe um comunicadoDeDados atualizado
						servidor.receba(vez);                         // Passa a vez
					}
					catch (Exception error) {}
				}

				if (desconectarJogador)                        // Se o jogador foi desconectado, ira tira-lo do jogo
				{
					try
					{
						servidor.receba(new PedidoParaSair());
					}
					catch (Exception error) {}

					System.exit(0);
				}

			}
		}
	}
