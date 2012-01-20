package validador;

import java.io.Serializable;
import java.util.regex.Pattern;

public class ValorMonetarioValidator implements Serializable 
{
	private final static long serialVersionUID = 1;

    public static boolean validaValorMonetario(String valor)
    {
        // System.out.println(">>>>>>>>>>>>>>>>>>>> " + valor);

		//                      9999999,99      ou 999,99          ou 999.999,99               ou 9.999.999,99
		return Pattern.compile("^(\\d{1,7}(,\\d{2})?|\\d{1,3}(,\\d{2})?|\\d{1,3}[.]\\d{3}(,\\d{2})?|\\d{1}([.]\\d{3}){2}(,\\d{2})?)$")
				      .matcher(valor)
				      .matches();
	}
}

