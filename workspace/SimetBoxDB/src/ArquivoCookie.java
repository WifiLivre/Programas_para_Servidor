import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ArquivoCookie {
	
	private ArrayList<CookieNome> cookies;
	AcessaBanco a = new AcessaBanco();
	
	private String link;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public  ArquivoCookie(){
		this.cookies = new ArrayList<CookieNome>();
		this.link = null;
	}
	
	public void getAllCookies(String pasta,String csv){
		Arquivo a = new Arquivo();
		a.setCaminhoPasta(pasta);
		a.setNomeArquivo(csv);
		String dados = a.lerArquivo();
		int ini = 0;
		int fim = 0;		
		
		do{

			dados = dados.substring(dados.indexOf("LOTE")+12);
			ini = dados.indexOf("idCookie=")+9;
			fim = dados.indexOf("idCookie=")+21;		
			
			String linha;
		
			if(dados.indexOf("LOTE")>0){
				linha = dados.substring(0,dados.indexOf("LOTE"));
			}else{
				linha = dados.substring(0);
			}
		
			linha = linha.substring(linha.indexOf(",")+1, linha.length());
			linha = linha.substring(linha.indexOf(",")+1, linha.length());
			linha = linha.substring(linha.indexOf(",")+1, linha.length());
			linha = linha.substring(0,linha.indexOf(","));
			
			System.out.print(linha+" | ");
			System.out.println(dados.subSequence(ini,fim).toString());
			
			CookieNome cn = new CookieNome();
			cn.setIdCookie(dados.subSequence(ini,fim).toString());
			cn.setPraca(linha);
			cookies.add(cn);
		}
		while(dados.indexOf("LOTE") > 0);
	
	}
	
	public void mostraAllCookies(){
		for(int i = 0; i < cookies.size();i++){
			CookieNome cn = new CookieNome();
			cn = cookies.get(i);			
			System.out.println(cn.getIdCookie()+" | "+cn.getPraca());
		}
	}
	
	public void baixarCookies(String caminho){
		Arquivo a = new Arquivo();
		a.setCaminhoPasta(caminho);
		a.criarDiretorio();
		for(int i = 0; i < cookies.size();i++){
			a.baixarArquivoInt(link+cookies.get(i));			
		}
	}
	
	public void cadastrarDadosCookie(String cam){
		Arquivo arq = new Arquivo();		
		String[] nomes = arq.listarArquivos(cam);
		a.abrirConexao();
		for(int i=0;i < nomes.length; i++){
  			
  			String idcookie = nomes[i].substring(nomes[i].indexOf("idCookie=")+9,nomes[i].indexOf("idCookie=")+21);
  			a.setSql("SELECT id_praca FROM praca WHERE cookie = '"+idcookie+"'");
   			ResultSet rs = a.consulta();
  			try{
  				while(rs.next()){
  					cadastraCookieBD(a,cam,nomes[i].toString(),rs.getString(1));  					
  				}
  			}catch(Exception e){
  				e.printStackTrace();
  			}  		
		}
		a.fecharConexao();
	}
	
	public void cadastraCookieBD(AcessaBanco a, String cam, String arquivo, String id_praca){

		Arquivo ar = new Arquivo();
		ar.setCaminhoPasta(cam);
		ar.setNomeArquivo(arquivo);
		String cookie = ar.lerArquivo();
		String aux = null;
		
		try{
		while(cookie.contains("hashMeasure")){
		
			String sql1=null,sql2=null;
			String id_data_hora = null, hashMeasure = null,ipConexao = null,ptt = null,uploadStrUn = null,downloadStrUn = null,jitterDownloadStrUn = null,udpDownloadStrUn = null,udpUploadStrUn = null,latenciaStrUn = null;
			long id = -1;
		
			double tcpDownload = -1,tcpUpload = -1,udpDownload = -1,udpUpload = -1,jitterDownload = -1,jitterUpload = -1,perdaDePacotes = -1,latencia = -1,velocidadeContratada = -1,downloadStr = -1,uploadStr = -1,jitterDownloadStr = -1,perdaDePacotesStr = -1,udpDownloadStr = -1,udpUploadStr = -1,latenciaStr = -1;  
			
			sql1 = "INSERT INTO `simetpraca` (`id_praca`";
			sql2 = ") VALUES ("+ id_praca;
			
			if(cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\"")).length() > 0){
				id =Integer.parseInt(cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\"")));
				sql1 = sql1 + ",`id`";
				sql2 = sql2 + ","+ id;
			}
			
			if(cookie.substring(cookie.indexOf("hashMeasure\":")+14, cookie.indexOf("\",\"")).length() > 0){
				hashMeasure = cookie.substring(cookie.indexOf("hashMeasure\":")+14, cookie.indexOf("\",\""));
				sql1 = sql1 + ",`hashMeasure`";
				sql2 = sql2 + ",'"+ hashMeasure +"'";
			}
			
			cookie = cookie.substring(cookie.indexOf("\",\"")+3);
		
			if(cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\"")).length() > 0){
				ipConexao = cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\""));
				sql1 = sql1 + ",`ipConexao`";
				sql2 = sql2 + ",'"+ ipConexao +"'";
			}
			
			cookie = cookie.substring(cookie.indexOf("\",\"")+3);
				
			id_data_hora = cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\""));
			cookie = cookie.substring(cookie.indexOf("\",\"tc")+3);
				
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){
				tcpDownload = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));
				sql1 = sql1 + ",`tcpDownload`";
				sql2 = sql2 + ","+tcpDownload;
			
			}
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
			
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){
				tcpUpload = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));
				sql1 = sql1 + ",`tcpUpload`";
				sql2 = sql2 + ","+tcpUpload;			
			}
			
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
	
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){
				udpDownload = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));
				sql1 = sql1 + ",`udpDownload`";
				sql2 = sql2 + ","+udpDownload;			
			}
			
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
			
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){
				udpUpload = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));
				sql1 = sql1 + ",`udpUpload`";
				sql2 = sql2 + ","+udpUpload;	
			}
			
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
			
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){
				jitterDownload = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));
				sql1 = sql1 + ",`jitterDownload`";
				sql2 = sql2 + ","+jitterDownload;	
			}			
			
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
			
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){
				jitterUpload = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));	
				sql1 = sql1 + ",`jitterUpload`";
				sql2 = sql2 + ","+jitterUpload;	
			}
					
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
		
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){
				perdaDePacotes = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));	
				sql1 = sql1 + ",`perdaDePacotes`";
				sql2 = sql2 + ","+perdaDePacotes;	
			}
			
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
		
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){
				latencia = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));
				sql1 = sql1 + ",`latencia`";
				sql2 = sql2 + ","+latencia;		
			}
			
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
			
			if((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))).length() > 0){				
				velocidadeContratada = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+2, cookie.indexOf(",\""))));
				sql1 = sql1 + ",`velocidadeContratada`";
				sql2 = sql2 + ","+ velocidadeContratada;	
			}
			
			cookie = cookie.substring(cookie.indexOf(",\"")+2);
			
			if(cookie.substring(cookie.indexOf("cao\":\"")+6, cookie.indexOf("\"}")).length() > 0){
				ptt = cookie.substring(cookie.indexOf("cao\":\"")+6, cookie.indexOf("\"}"));				
				sql1 = sql1 + ",`ptt`";
				sql2 = sql2 + ",'"+ ptt+"'";
			}

			cookie = cookie.substring(cookie.indexOf("dataStr\":"));
		
			
			if(cookie.substring(cookie.indexOf("dataStr\":")+10, cookie.indexOf("\",\"")).length() > 0){
				id_data_hora = cookie.substring(cookie.indexOf("dataStr\":")+10, cookie.indexOf("\",\""));
				Date nova = new SimpleDateFormat("dd/MM/yy HH:mm").parse(id_data_hora);  
				id_data_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nova);  
				sql1 = sql1 + ",`id_data_hora`";
				sql2 = sql2 + ",'"+ id_data_hora+"'";
				
			}			
			
			cookie = cookie.substring(cookie.indexOf("downloadStr"));
			
			aux = null;
			aux = (cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\"")));
			
			if(!(aux.contains("u003c")||(aux.contains("-")))){
				downloadStr = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\"")-7)));
				cookie = cookie.substring(cookie.indexOf("\",\"")-7);
				downloadStrUn = cookie.substring(0, cookie.indexOf("\",\""));
				sql1 = sql1 + ",`tcpDownloadStr`,`tcpDownloadStrUn`";
				sql2 = sql2 + ","+ downloadStr +",'"+ downloadStrUn+"'";			
			}else{
				cookie = cookie.substring(cookie.indexOf("\",\""));
			}
					
			cookie = cookie.substring(cookie.indexOf("\",\"")+3);
			//System.out.println(cookie);
		//	Thread.sleep(5000);
			if((cookie.substring(cookie.indexOf("ploadStr\":\"")+11, cookie.indexOf("\",\"end"))).length() > 2){
				uploadStr = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\"")-7)));
				uploadStrUn = cookie.substring(cookie.indexOf("\",\"")-7, cookie.indexOf("\",\""));
				cookie = cookie.substring(cookie.indexOf("terDownloadStr")+3);
				sql1 = sql1 + ",`tcpUploadStr`,`tcpUploadStrUn`";
				sql2 = sql2 + ","+ uploadStr +",'"+ uploadStrUn +"'";
				
			}else{
				cookie = cookie.substring(cookie.indexOf("terDownloadStr")+3);				
			}
			
			cookie = cookie.substring(cookie.indexOf("\",\"")+3);
		
			aux = null;
			aux = cookie.substring(cookie.indexOf("\":\"")+3, cookie.indexOf("\",\""));
			
			if(!(aux.contains("u003c")||(aux.contains("-")))){
				jitterDownloadStr = Double.parseDouble((cookie.substring(cookie.indexOf("\":\"")+3, cookie.indexOf("\",\"")-3)));
				cookie = cookie.substring(cookie.indexOf("\",\"")-3);
				jitterDownloadStrUn = cookie.substring(0, cookie.indexOf("\",\""));
				sql1 = sql1 + ",`jitterDownloadStr`,`jitterDownloadStrUn`";
				sql2 = sql2 + ","+ jitterDownloadStr +",'"+ jitterDownloadStrUn +"'";				
			}else{
				cookie = cookie.substring(cookie.indexOf("\",\""));
			}

			cookie = cookie.substring(cookie.indexOf("\",\"")+3);
			
			aux = null;
			aux = cookie.substring(cookie.indexOf("\":\"")+3, cookie.indexOf("\",\""));
			if(!(aux.contains("u003c")||(aux.contains("-")))){
				perdaDePacotesStr = Double.parseDouble((cookie.substring(cookie.indexOf("\":\"")+3, cookie.indexOf("\",\"")-2)));
				sql1 = sql1 + ",`perdaDePacotesStr`";
				sql2 = sql2 + ","+ perdaDePacotesStr;	
			}

			cookie = cookie.substring(cookie.indexOf("\",\"")+3);
		
			aux = null; 
			aux = (cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\"")));
			if(!(aux.contains("u003c")||(aux.contains("-")))){
				udpDownloadStr = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\"")-7)));
				cookie = cookie.substring(cookie.indexOf("\",\"")-7);
				udpDownloadStrUn = cookie.substring(0, cookie.indexOf("\",\""));
				sql1 = sql1 + ",`udpDownloadStr`,`udpDownloadStrUn`";
				sql2 = sql2 + ","+ udpDownloadStr +",'"+ udpDownloadStrUn +"'";				
			}else{
				cookie = cookie.substring(cookie.indexOf("\",\""));
			}
	
			cookie = cookie.substring(cookie.indexOf("\",\"")+3);
			
			aux = null;
			aux = (cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\"")));
			if(!(aux.contains("u003c")||(aux.contains("-")))){
				udpUploadStr = Double.parseDouble((cookie.substring(cookie.indexOf("\":")+3, cookie.indexOf("\",\"")-7)));
				cookie = cookie.substring(cookie.indexOf("\",\"")-7);
				udpUploadStrUn = cookie.substring(0, cookie.indexOf("\",\""));
				sql1 = sql1 + ",`udpUploadStr`,`udpUploadStrUn`";
				sql2 = sql2 + ","+ udpUploadStr +",'"+ udpUploadStrUn +"'";			
			}
			
			cookie = cookie.substring(cookie.indexOf("\",\"")+3);
			if((cookie.substring(cookie.indexOf("\":\"")+3, cookie.indexOf("\",\""))).length() > 2){
				latenciaStr = Double.parseDouble((cookie.substring(cookie.indexOf("\":\"")+3, cookie.indexOf("\",\"")-3)));
				cookie = cookie.substring(cookie.indexOf("\",\"")-2);
				latenciaStrUn = cookie.substring(0, cookie.indexOf("\",\""));
				sql1 = sql1 + ",`latenciaStr`,`latenciaStrUn`";
				sql2 = sql2 + ","+ latenciaStr +",'"+ latenciaStrUn +"'";			
	
			}	
			
			cookie = cookie.substring(cookie.indexOf("},{")+3);
			
			a.setSql("SELECT Count(simetpraca.id_data_hora) FROM simetpraca WHERE simetpraca.id_data_hora =  '"+id_data_hora+"' AND simetpraca.id_praca =  "+id_praca);
	
			ResultSet resultado = a.consulta();
			resultado.next();
			if(resultado.getString(1).equals("0")){
				a.setSql(sql1+sql2+")");
				a.executa();
			}			
			
		}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(cookie);
		}
		
		//System.out.println(id +" | "+hashMeasure+" | "+ipConexao+ " | "+ id_data_hora +" | "+ tcpDownload +" | " + tcpUpload +" | "+ udpDownload +" | " + udpUpload +" | " +jitterDownload +" | " + jitterUpload +" | "+ perdaDePacotes + " | "+ latencia +" | "+ velocidadeContratada +" | "+ ptt + " | "+ downloadStr + " | "+ downloadStrUn + " | " + uploadStr + " | " + uploadStrUn + " | " + jitterDownloadStr + " | " + jitterDownloadStrUn + " | " + jitterUploadStr + " | " + jitterUploadStrUn + " | " + perdaDePacotesStr + " | "+ udpDownloadStr + " | " + udpDownloadStrUn + " | " + udpUploadStr + " | "+ udpUploadStrUn + " | "+latenciaStr+" | "+ latenciaStrUn);
		
		
	}
	
	public void cadastrarDadosTexto(String caminhoTexto){
		Arquivo arq = new Arquivo();		
		String[] nomes = new String[120];
		int tam = 0;
		arq.setCaminhoPasta(caminhoTexto.substring(0, caminhoTexto.lastIndexOf("/")+1));
		arq.setNomeArquivo(caminhoTexto.substring(caminhoTexto.lastIndexOf("/")+1));
		String arquivos = arq.ler();
		for(int j = 0; arquivos.contains("./");j++){
			nomes[j] = arquivos.substring(0, arquivos.indexOf("|"));
			arquivos = arquivos.substring(arquivos.indexOf("|")+3);
			System.out.println(nomes[j]+"|"+j);
			tam = j;
		}		
		
		
		a.abrirConexao();
		for(int i=0;i < tam; i++){
  			
  			String idcookie = nomes[i].substring(nomes[i].indexOf("ice=")+4,nomes[i].indexOf("ice=")+16);
  			a.setSql("SELECT id_praca FROM praca WHERE cookie = '"+idcookie+"'");
   			ResultSet rs = a.consulta();
  			try{
  				while(rs.next()){
  					cadastraCookieBD(a,"."+nomes[i].substring(0, nomes[i].lastIndexOf("/")+1),nomes[i].substring(nomes[i].lastIndexOf("/")+1),rs.getString(1));  					
  				}
  			}catch(Exception e){
  				e.printStackTrace();
  			}  		
		}
		a.fecharConexao();
		
		
		
		
	}
}

