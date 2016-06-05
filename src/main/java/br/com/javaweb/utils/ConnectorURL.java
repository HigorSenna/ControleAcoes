package br.com.javaweb.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ConnectorURL {
	private static String todosValoresQueVemDoWebService;
	private static List<Object> atributosAcoes;
	
	public static List<Object> conectarNaUrl(String url) throws Exception{
		URL urlConexao = new URL(url);				
		URLConnection yc = urlConexao.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		
		return pegarParametrosUrl(in);
	}
	
	private static List<Object> pegarParametrosUrl(BufferedReader in) throws Exception{		
		JSONParser parser = new JSONParser();
		todosValoresQueVemDoWebService = in.readLine();		
		Object obj = parser.parse(todosValoresQueVemDoWebService);
		JSONArray arraysDoWebService = (JSONArray) obj;
        JSONObject arrayAcao = null;
        atributosAcoes = new ArrayList<>();
        
        for(int i = 0; i < arraysDoWebService.size(); i++){
        	int j = 0;
			System.out.println(arraysDoWebService.get(i)); 			
			arrayAcao = (JSONObject)arraysDoWebService.get(i); //pego o array na posicao i da acao especifica	
			
			for(j = 0 ;j < 1;j++){ //para cada i, tenho que fazer o for apenas uma vez
				atributosAcoes.add(arrayAcao.get("acao"));
				atributosAcoes.add(arrayAcao.get("ult_cotacao"));
				atributosAcoes.add(arrayAcao.get("aber_cotacao"));
				atributosAcoes.add(arrayAcao.get("variacao"));
				atributosAcoes.add(arrayAcao.get("max_cotacao_dia"));
				atributosAcoes.add(arrayAcao.get("min_cotacao_dia"));
				atributosAcoes.add(arrayAcao.get("med_cotacao_dia"));
			}
        }
        in.close();
        return atributosAcoes;
	}
}
