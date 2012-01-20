package validador;

import java.io.Serializable;
import java.util.regex.Pattern;

public class ValorMinimoInteiroValidator implements Serializable 
{
	private final static long serialVersionUID = 1;

    public static boolean validaValorMinimo(String valor, String min)
    {
        // System.out.println(">>>>>>>>>>>>>>>>>>>> " + valor);

		//                                     999.999.999.999
		boolean resultado = Pattern.compile("^(\\d{1,12})$").matcher(valor).matches();
		long minimo = Long.parseLong(min);
		
		if(resultado) 
		{
			long numero = Long.parseLong(valor);
			return numero >= minimo;			
		}
		else
		{	return false;
		}
	}
}

