package br.com.javaweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConnectorURL {

	public static void pegarParametrosWebService(String url) throws IOException{
		URL urlConexao = new URL(url);				
		URLConnection yc = urlConexao.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String valorCotacao;
		
		JSONParser parser = new JSONParser();
		valorCotacao= in.readLine();		
		
		try {
			Object obj = parser.parse(valorCotacao);
			JSONArray array = (JSONArray) obj;
//			obj = parser.parse(valorCotacao);			
			System.out.println(array.get(1));
			
			JSONObject obj2 = (JSONObject)array.get(1); //objeto 2 recebe o primeiro array da String do web service
	        System.out.println(obj2.get("ult_cotacao"));   
			
			 
		} catch (ParseException e) {
			e.printStackTrace();
		}       
//		while((valorCotacao=in.readLine())!=null){
//			  
//		      String s = valorCotacao;
//
//			System.out.println(valorCotacao);
//		}
		in.close();
	}
}
