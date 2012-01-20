package util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Util
{	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static NumberFormat nf;
	
	static
	{	nf = NumberFormat.getNumberInstance(new Locale("pt","BR"));
								
		// NumberFormat nf = NumberFormat.getNumberInstance();
		// NumberFormat nf = NumberFormat.getCurrencyInstance();

		nf.setMaximumFractionDigits (2);	   // O default � 3.
		nf.setMinimumFractionDigits (2);
		//nf.setGroupingUsed(false);
	}
	
	
	public static boolean dataValida(String umaData)
	{	try
		{	
			if(umaData.length() != 10) return false;

			Integer.parseInt(umaData.substring(0,2));
			if (!umaData.substring(2,3).equals("/")) return false;
			Integer.parseInt(umaData.substring(3,5));
			if (!umaData.substring(5,6).equals("/")) return false;
			Integer.parseInt(umaData.substring(6,10));
		
			sdf.setLenient(false);
		    sdf.parse(umaData);
			return true;
		}
		catch(Exception e)
		{	return false;
		} 
	}

	public static Date strToDate(String umaData)
	{	String dia = umaData.substring(0,2);
		String mes = umaData.substring(3,5);
		String ano = umaData.substring(6,10);

		return java.sql.Date.valueOf(ano + "-" + mes + "-" + dia);
	}

	public static String dateToStr(Date umaData)
	{	if (umaData == null)
			return "";
		else
			return sdf.format(umaData);
	}
	
	public static BigDecimal strToBigDecimal(String valor) throws Exception
	{     
		// Mesmo  especificando  um  Locale  pt-BR  na   criaÃ§Ã£o  do   NumberFormat,
		// se digitarmos o nÃºmero 2,000.00 o mÃ©todo parse nÃ£o dÃ¡ erro. DaÃ­ ter  sido
		// acrescentada a expressÃ£o regular para verificar se Ã© possÃ­vel converter o
		// String em um valor monetÃ¡rio. Se nÃ£o for possÃ­vel converter  ocorrerÃ¡  um 
		// erro de conversÃ£o. Observe que o valor monetÃ¡rio nÃ£o  pode  ser  digitado 
		// com separador de milhar.

		//                      9999999,99      ou 999,99          ou 999.999,99               ou 9.999.999,99
		if(Pattern.compile("^(\\d{1,7}(,\\d{2})?|\\d{1,3}(,\\d{2})?|\\d{1,3}[.]\\d{3}(,\\d{2})?|\\d{1}([.]\\d{3}){2}(,\\d{2})?)$")
				  .matcher(valor)
				  .matches())
		{
			int i = valor.indexOf(".");
			while(i != -1)
			{	String inicio = valor.substring(0, i);
			    String fim = valor.substring(i+1);
			    valor = inicio + fim;
			    i = valor.indexOf(".");
			}
			
			BigDecimal numero = new BigDecimal(valor.replace(',', '.'));
			
			return numero;
		}
		
		throw new Exception("ConversÃ£o invÃ¡lida"); 
	}

	public static Double strToValorMonetario(String valor) throws Exception
	{     
		// Mesmo  especificando  um  Locale  pt-BR  na   cria��o  do   NumberFormat,
		// se digitarmos o n�mero 2,000.00 o m�todo parse n�o d� erro. Da� ter  sido
		// acrescentada a express�o regular para verificar se � poss�vel converter o
		// String em um valor monet�rio. Se n�o for poss�vel converter  ocorrer�  um 
		// erro de convers�o. Observe que o valor monet�rio n�o  pode  ser  digitado 
		// com separador de milhar.

		//                      9999999,99      ou 999,99          ou 999.999,99               ou 9.999.999,99
		if(Pattern.compile("^(\\d{1,7}(,\\d{2})?|\\d{1,3}(,\\d{2})?|\\d{1,3}[.]\\d{3}(,\\d{2})?|\\d{1}([.]\\d{3}){2}(,\\d{2})?)$")
				  .matcher(valor)
				  .matches())
		{
			return nf.parse(valor).doubleValue();
		}
		
		throw new Exception("Convers�o inv�lida"); 
	}
	
	public static String valorMonetarioToStr(Double valor)
	{	return nf.format(valor);
	}
}