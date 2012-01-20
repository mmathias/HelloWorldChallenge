package validador;

import java.io.Serializable;

public class CPFValidator implements Serializable 
{
	private final static long serialVersionUID = 1;

    public static boolean validaCpf(String cpf)
    {
        if (cpf != null && !cpf.equals("")) 
        {	return cpfValido(cpf);
        }
        return true;
	}
	
	public static boolean cpfValido(String cpf)
	{   	
	    int tam = cpf.length();
	    if (tam < 11)
	    	return false;
	    
	    StringBuffer cpf2 = new StringBuffer();
		for(int i = 0; i < tam; i++)
	    {   String c = cpf.substring(i, i+1);
	    	if (!(c.equals(".") || c.equals("-")))
        	{	if(!((c.compareTo("0") >= 0) && (c.compareTo("9") <= 0)))
        		{   return false;
        		}
        		else
        		{	cpf2.append(c);
        		}
        	}
    	}

		String strCpf = cpf2.toString();

	    tam = strCpf.length();
	    if (tam < 11)
	    	return false;

    	int soma=0;
    	for(int i = 0; i < 9; i++)
    	{   soma += (10-i) * Integer.parseInt(strCpf.substring(i,i+1));
    	}

    	int digito_verificador = 11 - (soma % 11);
    	if((soma % 11) < 2)
    	   	digito_verificador = 0;

    	if (Integer.parseInt(strCpf.substring(9,10)) != digito_verificador)
    	{   return false;
    	}

    	soma=0;

    	for(int i = 0; i < 9; i++)
    	{   soma += (11 - i)*(Integer.parseInt(strCpf.substring(i,i+1)));
    	}

    	soma += 2*(Integer.parseInt(strCpf.substring(9,10)));

    	digito_verificador = 11-(soma % 11);
    	if((soma % 11)<2)
	        digito_verificador = 0;

	    if(Integer.parseInt(strCpf.substring(10,11)) != digito_verificador)
	    {   return false;
	    }

	    return true;
	}
}

