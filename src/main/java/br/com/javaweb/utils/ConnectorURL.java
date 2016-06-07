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
	private static List<Object> atributosAcoes;
	private static List<Acao> acoes = new ArrayList<>();
	
	public static List<Acao> conectarNaUrlPegandoValoresDoWebService(String url) throws Exception{
		URL urlConexao = new URL(url);				
		URLConnection yc = urlConexao.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		return pegarItemsWebService(in);
	}
	
	private static List<Acao> pegarItemsWebService(BufferedReader in) throws Exception{				
		acoes.clear();
		JSONParser parser = new JSONParser();
		todosValoresQueVemDoWebService = in.readLine();		
		Object obj = parser.parse(todosValoresQueVemDoWebService);
		JSONArray arraysDoWebService = (JSONArray) obj;
        JSONObject arrayAcao = null;
        atributosAcoes = new ArrayList<>();
        
        for(int i = 0; i < arraysDoWebService.size(); i++){
        	Acao acao = new Acao();
        	int j = 0;
//        	System.out.println(arraysDoWebService.size());
//			System.out.println(arraysDoWebService.get(i)); 			
			arrayAcao = (JSONObject)arraysDoWebService.get(i); //pego o array na posicao i da acao especifica	
			
			for(j = 0 ;j <= 0;j++){ //para cada i, tenho que fazer o for apenas uma vez
				atributosAcoes.add(arrayAcao.get("acao"));
				acao.setNomeAcao(arrayAcao.get("acao").toString());				
				
				atributosAcoes.add(arrayAcao.get("ult_cotacao"));	//				
				acao.setValorUltimaCotacao(arrayAcao.get("ult_cotacao").toString());	
				
				atributosAcoes.add(arrayAcao.get("aber_cotacao"));
				acao.setAberturaCotacao(arrayAcao.get("aber_cotacao").toString());
				
				atributosAcoes.add(arrayAcao.get("variacao"));
				acao.setVariacao(arrayAcao.get("variacao").toString());
				
				atributosAcoes.add(arrayAcao.get("max_cotacao_dia"));
				acao.setMaximoCotacaoDia(arrayAcao.get("max_cotacao_dia").toString());
				
				
				atributosAcoes.add(arrayAcao.get("min_cotacao_dia"));
				acao.setMinimoCotacaoDia(arrayAcao.get("min_cotacao_dia").toString());
				
				atributosAcoes.add(arrayAcao.get("med_cotacao_dia"));
				acao.setMediaAcaoDia(arrayAcao.get("med_cotacao_dia").toString());
				acoes.add(acao);
			}
        }
        testarMetodoAcoes(acoes);
        in.close();
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
