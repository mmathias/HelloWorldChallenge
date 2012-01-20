package validador;

import java.io.Serializable;
import java.util.regex.Pattern;

public class ValorInteiroValidator implements Serializable 
{
	private final static long serialVersionUID = 1;

    public static boolean validaValorInteiro(String valor)
    {
        // System.out.println(">>>>>>>>>>>>>>>>>>>> " + valor);

		//                        99999999999
		return Pattern.compile("^(\\d{1,11})$")
				      .matcher(valor)
				      .matches();
	}
}

