import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Principal{
  	public static void main(String[] args) throws IOException{
		
 		Date dtn = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String dataHora = dt.format(dtn);
  		

		
 		String raiz = "./juliano/DadosJuliano/";
  		String arquivoTexto = raiz+"LinksCaptura/baixados.txt";
		
  		
  		
  		// aqui eu passo o nome do arquivo um deles
  		
  		@SuppressWarnings("unused")
		String arquivo = "MedicoesNoPeriodoBox_idCookie=6466b358f40c";
  	 	
  		
  		
  		String pastaLinks = raiz+"LinksCaptura/";
 // 		String pastaCookies = raiz+"Pracas/";
  		
  		
  		String link = "http://wifilivre.sp.gov.br/pracas.csv";
 // 		String linkCookie = "http://simet-publico.ceptro.br/simet-box-history/MedicoesNoPeriodoBox?idCookie=";
				
		String nomeArquivo = link.substring(link.lastIndexOf("/")+1);
  		
  		Arquivo arq = new Arquivo();
  		arq.setCaminhoPasta(pastaLinks);
  		arq.setNomeArquivo(nomeArquivo);
  		arq.criarDiretorio();
 // 		arq.baixarEreEscreverArquivo(link, pastaLinks);
  		
  		ArquivoCookie ac = new ArquivoCookie();
  		ac.getAllCookies(pastaLinks, nomeArquivo);
  	//	ac.mostraAllCookies();
  	
  	/*	
  		arq.setCaminhoPasta(pastaPracas);
  		arq.setNomeArquivo(arquivo);
  		

  		
		ArquivoCookie ac = new ArquivoCookie();
  		*/
  		ac.cadastrarDadosTexto(arquivoTexto);
  			
  			
  		  		
  		
  		
  		/* voce deve listar os arquivos do diretorio
  		 * tambem deve renomear ou mover para outra pasta
  		 * 
  		 * 
  		 */
		SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");		
		String dataHora2 = dt2.format(dtn);	
  		
		Arquivo log = new Arquivo();
		log.setCaminhoPasta(raiz);
		log.setNomeArquivo("log"+dataHora2+".txt");
		log.criar();
		log.setTexto("SimetBoxDB -	OK");
		log.escrever();	
  	
  		System.out.println("Terminado " + dataHora);
  		//http://187.62.212.1/prodam/xml/prodam_-_wcs000114_-_mercado_municipal_-_acessos.xml
  	}  	   	
}
