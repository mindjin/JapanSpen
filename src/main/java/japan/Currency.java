package japan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Currency{
	

	public static String yenToRub(){
		String yen = null;
		URL url;
		URLConnection urlConnection;
		InputStream gis;
		BufferedReader buff;
		Scanner scanner = null;
		try{
			url = new URL("http://www.x-rates.com/table/?from=JPY&amount=1");
			urlConnection = url.openConnection();
			gis = urlConnection.getInputStream();
		
			buff = new BufferedReader(new InputStreamReader(gis));
			scanner = new Scanner(buff);
		}catch(IOException e){
			e.printStackTrace();
		}			
			
			while(scanner.hasNext()){
				 yen = scanner.findInLine("to=RUB'>");
				try{
				if(yen!=null){
					return scanner.findInLine("\\d.+?(?=<)");
					}
				else
					scanner.nextLine();
				}catch(InputMismatchException e){
					e.printStackTrace();
				}
				
			}
			scanner.close();
			return null;
	}
	
}
