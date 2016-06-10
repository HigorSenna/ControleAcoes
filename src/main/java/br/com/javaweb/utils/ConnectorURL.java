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

import br.com.javaweb.model.Acao;

public class ConnectorURL {
	private static String todosValoresQueVemDoWebService;
	private static List<Acao> acoes = new ArrayList<>();
	
	public static List<Acao> conectarNaUrlPegandoValoresDoWebService(String url) throws Exception{
		URL urlConexao = new URL(url);				
		URLConnection yc = urlConexao.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		return pegarItemsWebService(in,yc);		
	}
	
	private static List<Acao> pegarItemsWebService(BufferedReader in,URLConnection yc) throws Exception{				
		acoes.clear();
		JSONParser parser = new JSONParser();
		todosValoresQueVemDoWebService = in.readLine();		
		Object obj = parser.parse(todosValoresQueVemDoWebService);
		
		yc = null;
		in.close(); 
		JSONArray arraysDoWebService = (JSONArray) obj;
        JSONObject arrayAcao = null;
        
        for(int i = 0; i < arraysDoWebService.size(); i++){
        	Acao acao = new Acao();
        	int j = 0;
//        	System.out.println(arraysDoWebService.size());
//			System.out.println(arraysDoWebService.get(i)); 			
			arrayAcao = (JSONObject)arraysDoWebService.get(i); //pego o array na posicao i da acao especifica	
			
			for(j = 0 ;j <= 0;j++){ //para cada i, tenho que fazer o for apenas uma vez
				acao.setNomeAcao(arrayAcao.get("acao").toString());				
				
				acao.setValorUltimaCotacao(arrayAcao.get("ult_cotacao").toString().replaceAll(",", "."));					
				
				acao.setAberturaCotacao(arrayAcao.get("aber_cotacao").toString().replaceAll(",", "."));
				
				acao.setVariacao(arrayAcao.get("variacao").toString().replaceAll(",", "."));
				
				acao.setMaximoCotacaoDia(arrayAcao.get("max_cotacao_dia").toString().replaceAll(",", "."));
				
				acao.setMinimoCotacaoDia(arrayAcao.get("min_cotacao_dia").toString().replaceAll(",", "."));
				
				acao.setMediaAcaoDia(arrayAcao.get("med_cotacao_dia").toString().replaceAll(",", "."));
				acoes.add(acao);
			}
        }
        testarMetodoAcoes(acoes);              
        return acoes;
	}
	
	private static void testarMetodoAcoes(List<Acao> acoes){
		for (Acao acao : acoes) {
			System.out.println("Nome Acao:" + acao.getNomeAcao().toUpperCase());
			System.out.print(" Valor Abertura Coracao:" + acao.getAberturaCotacao());
			System.out.print(" Valor Minimo Cotacao:" + acao.getMinimoCotacaoDia());
			System.out.print(" Valor Maximo Cotacao:" + acao.getMaximoCotacaoDia());
			System.out.print(" Variacao Cotacao:" + acao.getVariacao());
			System.out.print(" Media Cotacao:" + acao.getMediaAcaoDia()+"\n");
		}
	}
}
