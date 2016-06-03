package br.com.javaweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

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
	        
	        System.out.println(obj2.size());//pegando o tamanho de um vetor especifico de acoes
	        
	        JSONObject arrayAcao = null;
	        List<Object> atributosAcoes = new ArrayList<>();
	        
	        for(int i = 0 ; i< array.size() ; i++){
	        	int j = 0;
				System.out.println(array.get(i)); 			
				arrayAcao = (JSONObject)array.get(i); //pego o array da acao especifica	
				
				for(j = 0 ;j < 1;j++){ //para cada i, tenho que fazer o for apenas uma vez
					atributosAcoes.add("\n");
					atributosAcoes.add("Nome AÇÃO ");
					atributosAcoes.add(arrayAcao.get("acao"));
					atributosAcoes.add("\n");
					
					atributosAcoes.add("------------------------------");
					atributosAcoes.add("\n");
					atributosAcoes.add("\n");
					atributosAcoes.add("Ultima Cotação ");
					atributosAcoes.add(arrayAcao.get("ult_cotacao"));
					atributosAcoes.add("\n");
					
					atributosAcoes.add("Abertura Cotação ");
					atributosAcoes.add(arrayAcao.get("aber_cotacao"));
					atributosAcoes.add("\n");
					
					atributosAcoes.add("Variação");
					atributosAcoes.add(arrayAcao.get("variacao"));
					atributosAcoes.add("\n");
					
					atributosAcoes.add("Maximo cotação dia ");
					atributosAcoes.add(arrayAcao.get("max_cotacao_dia"));
					atributosAcoes.add("\n");
					
					atributosAcoes.add("Minimo Cotação dia: ");
					atributosAcoes.add(arrayAcao.get("min_cotacao_dia"));
					atributosAcoes.add("\n");
					
					atributosAcoes.add("Media Cotação");
					atributosAcoes.add(arrayAcao.get("med_cotacao_dia"));
				}
			}
	  
	        for(Object atributo: atributosAcoes){
	        	System.out.print(atributo);
	        }
			
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
