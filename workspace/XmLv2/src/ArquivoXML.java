import java.util.ArrayList;


public class ArquivoXML {
	
	private ArrayList<String> links;
	private ArrayList<String> linksCopy;
	private String link;
	private String caminhoPasta;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getCaminhoPasta() {
		return caminhoPasta;
	}

	public void setCaminhoPasta(String caminhoPasta) {
		this.caminhoPasta = caminhoPasta;
	}
	
	public  ArquivoXML(){
		this.links = new ArrayList<String>();
		this.link = null;
		this.caminhoPasta = null;
		this.linksCopy = new ArrayList<String>();
	}
	
	public void criarListaXmlsWCS(String pasta,String nomeArquivo,String servidor){
		Arquivo a = new Arquivo();
		a.setCaminhoPasta(pasta);
		a.setNomeArquivo(nomeArquivo);
		String dados = a.lerArquivo();
		int ini = 0;
		int fim = 0;		
		boolean par = true;
		while(dados.indexOf("<a href=") > 0){
			ini = dados.indexOf("prodam_-_wcs");
			fim = dados.indexOf(".xml")+4;
			if(par)links.add(servidor + dados.subSequence(ini,fim).toString());
			par = !par;
			dados = dados.substring(fim);
		}
	}
	
	public void mostraAllXmls(){
		for(int i = 0; i < links.size();i++){
			System.out.println(links.get(i));
		}
	}
	
	public void baixarXmls(){
		Arquivo a = new Arquivo();
		a.setCaminhoPasta(this.caminhoPasta);
		a.criarDiretorio();
		int tentativa = 0;
		while(linksCopy.size() > 0){
			for(int i = 0; i < linksCopy.size();i++){
				a.baixarArquivoInt(linksCopy.get(i));			
			}
			linksCopy.clear();
			linksCopy.addAll(a.erro);
			a.erro.clear();
			for(int j = 0; j < linksCopy.size();j++){
				System.out.println(linksCopy.get(j));
			}
			if(tentativa > 100){
				break;
			}
			tentativa++;
		}		
		links.removeAll(linksCopy);		
	}
	
	public void adicionarLinkXml(String link){
		links.add(link);
	}
	
	public void copiarLinks(){
		linksCopy.addAll(links);
	}
	
	public ArrayList<String> getArrayLinks(){
		return(this.links);
	}
}
