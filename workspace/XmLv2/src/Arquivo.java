import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Arquivo{

	private String caminhoPasta;
	private String nomeArquivo;
	private String texto;
	ArrayList<String> erro; 

	public String getCaminhoPasta(){
		return caminhoPasta;
	}		
	
	public void setCaminhoPasta(String caminhoPasta){
		this.caminhoPasta = caminhoPasta;
	}
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public String getTexto(){
		return texto;
	}		

	public void setTexto(String texto){
		this.texto = texto;
	}

	public Arquivo(){
		this.caminhoPasta = null;
		this.nomeArquivo = null;
		this.texto = null;
		this.erro = new ArrayList<String>();
	}
	
	public void criarDiretorio(){
		
		Path path = Paths.get(caminhoPasta+nomeArquivo);
		if (Files.exists(path.toAbsolutePath())){
			System.out.println("O diretorio já EXISTE");
		}else{
			try {
				Files.createDirectories(path.getParent());
			} catch (IOException e) {				
				e.printStackTrace();
			}			
			System.out.println("O diretorio CRIADO");
		}				
	}
	
	public void criar(){
		
		Path path = Paths.get(caminhoPasta+nomeArquivo);
		if (Files.exists(path.toAbsolutePath())){
			System.out.println("O arquivo já EXISTE");
		}else{
			try {
				Files.createDirectories(path.getParent());
				Files.createFile(path.toAbsolutePath());
			} catch (IOException e) {				
				e.printStackTrace();
			}			
			System.out.println("O arquivo CRIADO");
		}				
	}
	
	public void escrever(){
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
		String hora = format.format(new Date(System.currentTimeMillis()));  
		
		Path path = Paths.get(caminhoPasta+nomeArquivo);		
		texto = hora+"    "+texto+System.getProperty("line.separator");
		
		byte[] bits = texto.getBytes();
		try {
			Files.write(path.toAbsolutePath(), bits, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Escrito no arquivo");		
	}
	
	public void ler(){
		Path path = Paths.get(caminhoPasta+nomeArquivo);
		byte[] retorno = null;
		try {
			retorno = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print(new String(retorno));			
	}
	
	public String lerArquivo(){
		Path path = Paths.get(caminhoPasta+nomeArquivo);
		byte[] retorno = null;
		try {
			retorno = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return(new String(retorno));			
	}
	
	public boolean deleteDir(File f){
		return(f.delete()); 
	}
	public void excluirPasta(String cam){
		String status = "Diretorio XMLs NÃO Existe";
		File dir = new File(cam);
		boolean success;
		if (dir.isDirectory()) {  
			String[] children = dir.list();  
			for (int i=0; i<children.length; i++) {   
				success = deleteDir(new File(dir, children[i]));  
			}
			success = dir.delete();		
			if (success) {  
				status = "Diretorio dos XMLs Deletado"; 
			}else{
				status = "Diretorio dos XMLs NÃO Deletado"; 
			}
		}
		System.out.println(status);
	}
	
	public void excluir(String cam){
		Path path = Paths.get(cam);
		try {
			Files.delete(path);
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}
	
	public void limpar(){
		Path path = Paths.get(caminhoPasta+nomeArquivo);		
		texto = "";
		byte[] bits = texto.getBytes();
		try {
			Files.write(path.toAbsolutePath(), bits, StandardOpenOption.DELETE_ON_CLOSE);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public void baixarEreEscreverArquivo(String link){
		try{
			URL site = new URL(link);
			String arquivo = link.substring(link.lastIndexOf("/"));
			Path path = Paths.get(caminhoPasta+arquivo);		
			Files.copy(site.openStream(),path, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("arquivo "+path.getFileName()+" criado ou re-escrito");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void baixarArquivo(String link){
		try{
			URL site = new URL(link);
			String arquivo = link.substring(link.lastIndexOf("/"));
			String extArqOriginal = arquivo.substring(arquivo.length()-6);
			extArqOriginal = extArqOriginal.substring(extArqOriginal.indexOf(".")+1);
			String cam = caminhoPasta + arquivo;
			int indice = 1;
			Path path = null;
			while(true){
				File f = new File(cam);
				if(f.exists()){
					int p1 = cam.indexOf(extArqOriginal)-7;
					String extNova = (cam.substring(p1));
					int p2 = extNova.indexOf(".")+1;
					String camNovo = (cam.substring(0,p1+p2));					
					extNova = extNova.substring(p2);
					if(extArqOriginal.equals(extNova)){
						cam =camNovo+indice+"."+extArqOriginal;
					}else{
						indice++;
						cam =camNovo+indice+"."+extArqOriginal;
					}					
				}else{
					path = Paths.get(cam);
					Files.copy(site.openStream(),path, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("arquivo "+path.getFileName()+" criado");
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void baixarArquivoInt(String link){
		try{
			URL site = new URL(link);
			String arquivo = link.substring(link.lastIndexOf("/"));
			
			
			
			arquivo = arquivo.replace("pca", "praca");
			arquivo = arquivo.replace("pq", "parque");
			arquivo = arquivo.replace("que_aclimacao", "que_da_aclimacao");
			arquivo = arquivo.replace("dom_jos_gaspar_-_ap_02", "dom_jose_gaspar_-_ap_03");
			arquivo = arquivo.replace("dom_jos_gaspar", "dom_jose_gaspar");
			arquivo = arquivo.replace("praca_cecilia_marques_araujo", "praca_cecilia_marques_de_araujo");
			arquivo = arquivo.replace("estacao_vila_mara", "praca_1_de_maio");
			arquivo = arquivo.replace("sampaio_vidal_-_ap_01_controladora_desligada_-_latencia","sampaio_vidal_-_ap_01_-_latencia");
			arquivo = arquivo.replace("praca_padre_aleixo_mafra","praca_do_forro");
			arquivo = arquivo.replace("_cisco","");	
			
			arquivo = arquivo.replace("praca_da_conquista_-_ap_01_cdp_desabilitado","praca_da_conquista_-_ap_01");
			arquivo = arquivo.replace("praca_sao_joao_vicenzotto_-_access_point","praca_sao_joao_vicenzotto_-_ap_01");
			arquivo = arquivo.replace("largo_do_paissandu_-_access_point","largo_do_paissandu_-_ap_01");
			arquivo = arquivo.replace("praca_gregorio_ramalho_-_access_point","praca_gregorio_ramalho_-_ap_01");
			arquivo = arquivo.replace("praca_brasil_-_access_point","praca_brasil_-_ap_01");
	
			arquivo = arquivo.replace("praa_torquato_plaza_-_acessos","praa_torquato_plaza_-_erro");
			arquivo = arquivo.replace("praa_torquato_plaza","praca_torquato_plaza");
			
			arquivo = arquivo.replace("praca_dom_jose_gaspar_-_cpd_biblioteca","praca_dom_jose_gaspar_-_ap_80");
			arquivo = arquivo.replace("praca_dom_jose_gaspar_-_praca","praca_dom_jose_gaspar_-_ap_81");
			
			arquivo = arquivo.replace("praca_felisberto_fernandes_-_access_point","praca_felisberto_fernandes_-_ap_01");

		
			
			String cam = caminhoPasta + arquivo;
			String ext = ".xml";
			String camFim = null;
			int indice = 1;
			Path path = null;
			cam = cam.replace("?", "I");
			cam = cam.replace("=", ".");
			while(true){
				File f = new File(cam);
				if(f.exists()){
					if(cam.lastIndexOf(".") < 4){
						cam =cam+"."+indice+ext;
					}else{
						indice++;
						camFim = cam.substring(cam.length()-6);
						cam = cam.substring(0, cam.length()-6);
						cam = cam + camFim.substring(0,camFim.indexOf("."));
						cam = cam+"."+indice+ext;
					}					
				}else{
					path = Paths.get(cam);
					Files.copy(site.openStream(),path, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("arquivo "+path.getFileName()+" criado");
					break;
				}
			}
		}catch(Exception e){
			// e.printStackTrace();
			erro.add(link);
		}
	}
	
	public void escreveHoraModificacaoArquivo(){
		File f = new File(caminhoPasta+nomeArquivo);
		long val = f.lastModified();
	    Date date=new Date(val);
	    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    String dataTexto = df.format(date);
	    System.out.println(dataTexto);
	}

}