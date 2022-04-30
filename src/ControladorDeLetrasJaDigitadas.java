/**
 @author Andre Luis, Nicoli, Lucas, Samuel e Talita.
 @since 2021.
 */

public class ControladorDeLetrasJaDigitadas extends Comunicado implements Cloneable
{
    private String letrasJaDigitadas;

    public ControladorDeLetrasJaDigitadas ()
    {
        // torna this.letrasJaDigitadas igual ao String vazio
        this.letrasJaDigitadas = "";
    }

    public boolean isJaDigitada (char letra)
    {
        // percorrer o String this.letrasJaDigitadas e verificar se ele
        // possui a letra fornecida, retornando true em caso afirmativo
        // ou false em caso negativo
        for(int i = 0; i < this.letrasJaDigitadas.length(); i++){
            if(this.letrasJaDigitadas.charAt(i) == letra)
                return true;
        }
        return false;
    }

    public void registre (char letra) throws Exception
    {
        // verifica se a letra fornecida ja foi digitada (pode usar
        // o metodo this.isJaDigitada, para isso), lancando uma excecao
        // em caso afirmativo.
        // concatena a letra fornecida a this.letrasJaDigitadas.
        if(this.isJaDigitada(letra))
            throw new Exception("Letra ja digitada");
        this.letrasJaDigitadas += letra;
    }

    public String toString ()
    {
        // retorna um String com TODAS as letras presentes em
        // this.letrasJaDigitadas separadas por v?rgula (,).
        String letrasUsadas = "";
        for(int i = 0; i < this.letrasJaDigitadas.length(); i++)
            letrasUsadas += this.letrasJaDigitadas.charAt(i)+",";
        return letrasUsadas;
    }

    public boolean equals (Object obj)
    {
        // verificar se this e obj s�o iguais
        if (this==obj) return true;

        if (obj==null) return false;

        if(this.getClass() != obj.getClass()) return false;

        try{
            ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas((ControladorDeLetrasJaDigitadas)obj);
            if(!this.letrasJaDigitadas.equals(controladorDeLetrasJaDigitadas.letrasJaDigitadas))
                return false;
        }
        catch (Exception erro){}

        return true;
    }

    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        int ret=8;

        ret = 13*ret + new String (this.letrasJaDigitadas).hashCode();

        if(ret < 0)
            ret = -ret;

        return ret;
    }

    public ControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas) throws Exception // construtor de c?pia
    {
        // copiar c.letrasJaDigitadas em this.letrasJaDigitadas
        if (controladorDeLetrasJaDigitadas==null)
            throw new Exception ("Par�metro ausente");

        this.letrasJaDigitadas = controladorDeLetrasJaDigitadas.letrasJaDigitadas;
    }

    public Object clone ()
    {
        // criar uma c?pia do this com o construtor de c?pia e retornar
        ControladorDeLetrasJaDigitadas ret=null;

        try
        {
            ret = new ControladorDeLetrasJaDigitadas (this);
        }
        catch (Exception erro)
        {}

        return ret;
    }
}
