import java.util.ArrayList;


public class ArquivoCookie {
	
	private ArrayList<String> cookies;
	private ArrayList<String> baixados;
	private String link;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public  ArquivoCookie(){
		this.cookies = new ArrayList<String>();
		this.baixados = new ArrayList<String>();		
		this.link = null;
	}
	
	public void getAllCookies(String pasta,String csv){
		Arquivo a = new Arquivo();
		a.setCaminhoPasta(pasta);
		a.setNomeArquivo(csv);
		String dados = a.lerArquivo();
		int ini = 0;
		int fim = 0;		

		while(dados.indexOf("idCookie=") > 0){
			ini = dados.indexOf("idCookie=")+9;
			fim = dados.indexOf("idCookie=")+21;
			cookies.add(dados.subSequence(ini,fim).toString());
			dados = dados.substring(fim);
		}
	}
	
	public void mostraAllCookies(){
		for(int i = 0; i < cookies.size();i++){
			System.out.println(cookies.get(i));
		}
	}
	
	public void criarAllFoldersPracas(String raiz,String subPasta){
		Arquivo arqu = new Arquivo();
		for(int i = 0; i < cookies.size();i++){
		
			arqu.setCaminhoPasta(raiz+cookies.get(i)+"/"+subPasta);
			arqu.criarDiretorio();			
		}
	}
	
	public void baixarCookies(String caminho,String subPasta){
		Arquivo a = new Arquivo();	
		for(int i = 0; i < cookies.size();i++){
			a.setCaminhoPasta(caminho+cookies.get(i)+"/"+subPasta);
			a.criarDiretorio();			
			baixados.add(a.getCaminhoPasta() + a.baixarArquivoIntM(link+cookies.get(i))+"|");		
			//System.out.println(link+cookies.get(i));
		}
	}
	
	public void baixarRelatorio(String caminho,String subPasta){
		Arquivo a = new Arquivo();	
		String lnk;
		for(int i = 0; i < cookies.size();i++){
			lnk = link.replace("@Dev@", cookies.get(i).toString());	
			a.setCaminhoPasta(caminho+cookies.get(i)+"/"+subPasta);
			a.criarDiretorio();			
			a.baixarArquivo(lnk);
			//System.out.println(link+cookies.get(i));
		}
	}
	
	public void escreverBaixados(String caminhoPasta){
		Arquivo a = new Arquivo();
		a.setCaminhoPasta(caminhoPasta);
		a.setNomeArquivo("baixados.txt");
		
		a.excluir();
		a.criar();
		a.escreverBaixados(baixados);		
	}
}
