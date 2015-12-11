import java.text.SimpleDateFormat;
import java.util.Date;

public class Principal{
  	public static void main(String[] args){
  		
  		Date dtn = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String dataHora = dt.format(dtn);
  		  		
  		String raiz = "./";
  		String pastaLinks = raiz+"LinksCaptura/";
  		String pastaPracas = raiz+"Pracas/";
  		String subPastaTexto = "TextoMedida/";
  		String subPastaRelatorio = "RelatorioCSV/";
  		
  		
  		String link = "http://wifilivre.sp.gov.br/pracas.csv";
  		
  
  //		long lastDay = dtn.getTime(); 
  //		long firstDay = dtn.getTime() - 11036800;
  	
  
  		long lastDay = Long.parseLong("1422863762000"); 
  	   long firstDay = Long.parseLong("1419037600000");
  
  	   /*
  	   //fev - mar - abr
  		lastDay = Long.parseLong("1440964298000"); 
   	    firstDay = Long.parseLong("1400079194888");
   
  		//maio - jun - jul
  	 firstDay = Long.parseLong("1400079194888");
  	   	lastDay = Long.parseLong("1440979801000"); 
	//    firstDay = Long.parseLong("1440298800000");
   	    
  	1438190160800

  		//ago set out*/
  	   
  	 firstDay = Long.parseLong("1438190160800");
  	   	lastDay = Long.parseLong("1446559019000"); 
	   	

  		//11036800
  		//http://simet-publico.ceptro.br/simet-box-history/ReportBoxCsvServlet?hashDevice=6466b3590108&dia=undefined&firstDay=1422851718933&lastDay=1422862755733&limit=false
  		//http://simet-publico.ceptro.br/simet-box-history/ReportBoxCsvServlet?hashDevice=6466b3590108&dia=undefined&firstDay=1422851718933&lastDay=1422862755733&limit=false
  		//http://simet-publico.ceptro.br/simet-box-history/ReportBoxCsvServlet?hashDevice=6466b358ed10&dia=undefined&firstDay=1422237600000&lastDay=1422863762000&limit=true
  	 
  	 	String linkRelatorio = "http://simet-publico.ceptro.br/simet-box-history/ReportBoxCsvServlet?hashDevice=@Dev@&dia=undefined&firstDay="+firstDay+"&lastDay="+lastDay+"&limit=true";
  	  	
  		
  		
  //		String linkCookie = "http://simet-publico.ceptro.br/simet-box-history/MedicoesNoPeriodoBox?hashDevice=";
  	//	String linkRelatorio = "http://simet-publico.ceptro.br/simet-box-history/ReportBoxCsvServlet?hashDevice=@Dev@&dia=undefined&firstDay="+firstDay+"&lastDay="+lastDay+"&limit=false";
  
  		
  		String nomeArquivo = link.substring(link.lastIndexOf("/")+1);
  		
  		Arquivo arq = new Arquivo();
  		
  		 		
  		arq.setCaminhoPasta(pastaLinks);
  		arq.setNomeArquivo(nomeArquivo);
  		arq.criarDiretorio();
  		arq.baixarEreEscreverArquivo(link, pastaLinks);
	
  		ArquivoCookie ac = new ArquivoCookie();
  		ac.getAllCookies(pastaLinks, nomeArquivo);
  		
  		ac.mostraAllCookies();
  		ac.criarAllFoldersPracas(pastaPracas,subPastaTexto);
  		ac.criarAllFoldersPracas(pastaPracas,subPastaRelatorio);  		
  		
  //		ac.setLink(linkCookie);  
  //		ac.baixarCookies(pastaPracas, subPastaTexto);  		
  		
 // 		ac.escreverBaixados(pastaLinks);
  		
  		ac.setLink(linkRelatorio);  
  		ac.baixarRelatorio(pastaPracas, subPastaRelatorio);  	
  		
  		
  		System.out.println(dataHora);
		
		SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");		
		String dataHora2 = dt2.format(dtn);	
		
		Arquivo log = new Arquivo();
		log.setCaminhoPasta(raiz);
		log.setNomeArquivo("log"+dataHora2+".txt");
		log.criar();
		log.setTexto("SimetBox -	OK");
		log.escrever();			
		
  	}  	   	
}
