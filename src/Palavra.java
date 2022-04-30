/**
 @author Andre Luis, Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class Palavra extends Comunicado implements Comparable<Palavra>
{
    private String texto;

    public Palavra (String texto) throws Exception
    {
        // verifica se o texto recebido ? nulo ou ent?o vazio,
        // ou seja, sem nenhum caractere, lan?ando exce??o.
        // armazena o texto recebido em this.texto.
        if (texto == null || texto == "")
            throw new Exception("Palavra vazia!");
        this.texto = texto;
    }


    public int getQuantidade (char letra)
    {
        // percorre o String this.texto, conta e retorna
        // quantas letras existem nele iguais a letra fornecida
        int letrasIguais = 0;
        for (int i = 0; i < this.texto.length(); i++){
            if(letra == this.texto.charAt(i))
                letrasIguais++;
        }
        return letrasIguais;
    }

    // Novo metodo implementado para verificar se o chute que o usuario digitou, é igual a palavra sorteada.
    public Boolean chute(String chute)
    {
        if(chute.equals(this.texto))
            return true;
        else
            return false;
    }

    public int getPosicaoDaIezimaOcorrencia (int i, char letra) throws Exception
    {
        // se i==0, retorna a posicao em que ocorre a primeira
        // aparicao de letra fornecida em this.texto;
        // se i==1, retorna a posicao em que ocorre a segunda
        // aparicao de letra fornecida em this.texto;
        // se i==2, retorna a posicao em que ocorre a terceira
        // aparicao de letra fornecida em this.texto;
        // e assim por diante.
        // lan?ar excecao caso nao encontre em this.texto
        // a I?zima apari??o da letra fornecida.
        int ocorrencias = 0;

        for (int cnt = 0; cnt < this.texto.length(); cnt++){
            if(letra == this.texto.charAt(cnt)){
                if(i == ocorrencias)
                    return cnt;
                else
                    ocorrencias++;
            }
        }
        throw new Exception("Letra nao encontrada");
    }

    public int getTamanho ()
    {
        return this.texto.length();
    }

    public String toString ()
    {
        return this.texto;
    }

    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conte?do, retornando
        // true no caso afirmativo ou false no caso negativo
        if (this==obj) return true;

        if (obj==null) return false;

        if(obj.getClass() != Palavra.class) return false;

        Palavra palavra = (Palavra)obj;
        if(this.texto != palavra.texto)
            return false;

        return true;
    }

    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        int ret=8;

        ret = 13*ret + new String (this.texto).hashCode();

        if(ret < 0)
            ret = -ret;

        return ret;
    }

    public int compareTo (Palavra palavra)
    {
        return this.texto.compareTo(palavra.texto);
    }
}
