/**
 @author Andre Luis.
 */

public class BancoDePalavras extends Comunicado
{
    private static String[] palavras = 
    {
            "PARALELEPIPEDO",
            "ONOMETOPEIA",
            "OFTALMOTORRINOLARINGOLOGISTA",
            "LARINGOLOGISTA",
            "OFTALMOLOGISTA",
            "ANTICONSTITUCIONALMENTE",
            "FOTOCROMOMETALOGRAFICO",
            "TRAQUELATLOIDOCCIPITAL",
            "HIPOPOTOMONSTROSESQUIPEDALIOFOBIA",
            "PNEUMOULTRAMICROSCOPICOSSILICOVULCANOCONIOTICO",
            "ACROCEFALOSSINDACTILIA",
            "DESCONSTITUCIONALIZACAO",
            "INCONSTITUCIONALMENTE",
            "INTERCONFESSIONALISMO",
            "INTERDISCIPLINARIDADE",
            "INTERDISCIPLINARMENTE",
            "MULTIDIMENSIONALIDADE",
            "MULTIDISCIPLINARIDADE",
            "PLURIDIMENSIONALIDADE",
            "PLURIDISCIPLINARIDADE",
            "TRANSDISCIPLINARIDADE"
    };

    public static Palavra getPalavraSorteada ()
    {
        Palavra palavra = null;

        try
        {
            palavra =
            new Palavra (BancoDePalavras.palavras[
            (int)(Math.random() * BancoDePalavras.palavras.length)]);
        }
        catch (Exception e)
        {}

        return palavra;
    }
}
