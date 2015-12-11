import java.text.SimpleDateFormat;
import java.util.Date;

public class Principal {

		public static void main(String[] args) {
			Date dtn = new Date();
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
			String dataHora = dt.format(dtn);
			
			String raiz = "./";
			String caminhoLinks = raiz + "LinksCaptura/";
			String camPastaXmls = raiz + "DadosXmls/";
			
			String arqHtm = "page.htm";
			String arqXmlZiva = "getstatus.phpIformat.xml";
			
			//String linkZiva = "http://191.241.225.10/mrtg/getstatus.php?format=xml";
			String linkZiva = "http://dhcp.americanet.com.br/mrtg/getstatus.php?format=xml";
			String linkWCS = "http://187.62.212.1/prodam/xml/";	
			
			Arquivo a = new Arquivo();
			a.excluirPasta(camPastaXmls);
			
			ArquivoXML ax = new ArquivoXML();
			
			
			ax.setCaminhoPasta(camPastaXmls);
			ax.adicionarLinkXml(linkZiva);

			for(int i=0;i < 10;i++){
				LendoPaginaInternet lp = new LendoPaginaInternet(linkWCS,caminhoLinks+arqHtm);
				lp.lerPgInternet();
			}	
			
			ax.criarListaXmlsWCS(caminhoLinks, arqHtm+"l",linkWCS);
			ax.copiarLinks();
			ax.mostraAllXmls();	
			
			ax.baixarXmls();
			
			LendoXmlZiva l = new LendoXmlZiva();
			l.setCaminho(camPastaXmls+arqXmlZiva);
			l.atualizaListaDePraca();
			l.insereDadosPraca(dataHora);
		
			l.insereTotalZiva(dataHora);
			l.insereTotalUsuariosZiva(dataHora);			
			
			System.out.println("Ha\n\n");
			
			l.insereDadosAps(dataHora);		
					
			System.out.println("He\n\n");
			
			LendoXmlWCS lw = new LendoXmlWCS();
			lw.setCaminhoPasta(camPastaXmls);
			lw.setLst(ax.getArrayLinks());
			lw.cadastrarPracas();
			lw.cadastrarDadosPraca(dataHora);	
			lw.cadastrarDadosAps(dataHora);
			
			//lw.escreveLinksLst();
			//lw.escreveLinks();
			//lw.removerDeletarTodosXmls(camPastaXmls + arqXmlZiva);
		

			System.out.println(dataHora);
			
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");		
			String dataHora2 = dt2.format(dtn);
			
			Arquivo log = new Arquivo();
			log.setCaminhoPasta(raiz);
			log.setNomeArquivo("log"+dataHora2+".txt");
			log.criar();
			log.setTexto("wifiXmls -	OK");
			log.escrever();			
			
	}
}
