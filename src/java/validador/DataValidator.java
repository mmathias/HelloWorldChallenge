package validador;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class DataValidator implements Serializable 
{
	private final static long serialVersionUID = 1;

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static boolean validaData(String umaData)
    {
        // System.out.println(">>>>>>>>>>>>>>>>>>>> " + umaData);
    	try
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
}

