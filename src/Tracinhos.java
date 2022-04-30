/**
 @author Andre Luis, Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class Tracinhos extends Comunicado implements Cloneable
{
    private char texto [];
    private String textoString [];

    public Tracinhos (int qtd) throws Exception
    {
        // verifica se qtd n?o ? positiva, lan?ando uma exce??o.
        // instancia this.texto com um vetor com tamanho igual qtd.
        // preenche this.texto com underlines (_).
        if(qtd <= 0)
            throw new Exception("Numero de caracteres invalido");
        this.texto = new char [qtd];

        for(int i = 0; i < qtd; i++)
            this.texto[i] = '_';
    }

    // Novo metodo implementado para mostrar logo de cara, a palavra (ou seja, o chute) para o usuario
    public void reveleChute(String chute){
        for(int i = 0; i < chute.length(); i++)
            this.texto[i] = chute.charAt(i);
    }

    public void revele (int posicao, char letra) throws Exception
    {
        // verifica se posicao ? negativa ou ent?o igual ou maior
        // do que this.texto.length, lan?ando uma exce??o.
        // armazena a letra fornecida na posicao tambem fornecida
        // do vetor this.texto
        if(posicao < 0 || posicao >= this.texto.length)
            throw new Exception("Posi��o inv�lida");
        this.texto[posicao] = letra;
    }

    public boolean isAindaComTracinhos ()
    {
        // percorre o vetor de char this.texto e verifica
        // se o mesmo ainda contem algum underline ou se ja
        // foram todos substituidos por letras; retornar true
        // caso ainda reste algum underline, ou false caso
        // contrario
        for(int i = 0; i < this.texto.length; i++){
            if(this.texto[i] == '_')
                return true;
        }
        return false;
    }

    public String toString ()
    {
        // retorna um String com TODOS os caracteres que h?
        // no vetor this.texto, intercalados com espa?os em
        // branco
        String palavra = "";
        for(int i = 0; i < this.texto.length; i++){
            palavra += this.texto[i]+" ";
        }
        return palavra;
    }

    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conte?do, retornando
        // true no caso afirmativo ou false no caso negativo
        if (this==obj) return true;

        if (obj==null) return false;

        if(obj.getClass() != Tracinhos.class) return false;

        Tracinhos tracinhos = (Tracinhos)obj;
        for(int i = 0; i < this.texto.length; i++)
            if(this.texto[i] != tracinhos.texto[i])
                return false;

        return true;
    }

    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        int ret=8;

        for(int i = 0; i < this.texto.length; i++)
            ret = 13*ret + Character.valueOf (this.texto[i]).hashCode();

        if(ret < 0)
            ret = -ret;

        return ret;
    }

    public Tracinhos (Tracinhos t) throws Exception // construtor de c?pia
    {
        // intanciar this.texto um vetor com o mesmo tamanho de t.texto
        // e copilar o conte?do de t.texto para this.texto
        if (t==null)
            throw new Exception ("Par�metro ausente");

        this.texto = new char [t.texto.length];
        for(int i = 0; i < t.texto.length; i++)
            this.texto[i] = t.texto[i];
    }

    public Object clone ()
    {
        // retornar uma copia de this
        Tracinhos ret=null;

        try
        {
            ret = new Tracinhos (this);
        }
        catch (Exception erro)
        {}

        return ret;
    }
}
