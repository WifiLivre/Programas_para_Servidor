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
			//System.out.println("O diretorio CRIADO");
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
	
	public void excluir(){
		Path path = Paths.get(caminhoPasta+nomeArquivo);
		try {
			if(Files.exists(path)){
				Files.delete(path);
			}		
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
	
	public void baixarEreEscreverArquivo(String link,String pasta){
		try{
			URL site = new URL(link);
			String arquivo = link.substring(link.lastIndexOf("/"));
			Path path = Paths.get(pasta+arquivo);		
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
			if(arquivo.contains("&dia=undefined")){
				arquivo = arquivo.substring(0, arquivo.indexOf("&dia=undefined"))+".csv";
			}
			String extArqOriginal = arquivo.substring(arquivo.length()-6);
			extArqOriginal = extArqOriginal.substring(extArqOriginal.indexOf(".")+1);
			String cam = caminhoPasta + arquivo;
			int indice = 1;
			Path path = null;
			cam = cam.replace("?h", "H");
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
			if(arquivo.contains("&dia=undefined")){
				arquivo = arquivo.substring(0, arquivo.indexOf("&dia=undefined"));
			}
			String cam = caminhoPasta + arquivo;
			int indice = 1;
			Path path = null;
			cam = cam.replace("?h", "H");
			while(true){
				File f = new File(cam);
				if(f.exists()){
					if(cam.lastIndexOf(".") < 4){
						cam =cam+"."+indice+".csv";
						System.out.println("FDP"+cam+ "<+++++++++++++++++++++++++++");
					}else{
						indice++;
						cam = cam.substring(1);
						
						cam = cam.substring(1, cam.indexOf("."));
						
						cam ="."+cam+"."+indice+".csv";
						System.out.println("FDP"+cam+ "<------------------------------");
					}					
				}else{
					cam = cam.replace("?h", "H");
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
	
	public void escreveHoraModificacaoArquivo(){
		File f = new File(caminhoPasta+nomeArquivo);
		long val = f.lastModified();
	    Date date=new Date(val);
	    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    String dataTexto = df.format(date);
	    System.out.println(dataTexto);
	}

	public void escreverBaixados(ArrayList <String> baixados){
		texto = "";
		Path path = Paths.get(caminhoPasta+nomeArquivo);		
		
		
		
		
		for(int i = 0; i < baixados.size();i++){
			texto = texto+"./juliano/DadosJuliano/"+baixados.get(i).substring(2) +System.getProperty("line.separator");
		}
		
		
		
		
		
		byte[] bits = texto.getBytes();
		try {
			Files.write(path.toAbsolutePath(), bits, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Escrito no arquivo");		
	}


	public String baixarArquivoIntM(String link){
		try{
			URL site = new URL(link);
			String arquivo = link.substring(link.lastIndexOf("/"));
			String cam = caminhoPasta + arquivo;
			int indice = 1;
			Path path = null;
			cam = cam.replace("?h", "H");
			while(true){
				File f = new File(cam);
				if(f.exists()){
					if(cam.lastIndexOf(".") < 4){
						cam =cam+"."+indice;
					}else{
						indice++;
						cam = cam.substring(0, cam.lastIndexOf("."));
						cam =cam+"."+indice;
					}					
				}else{
					cam = cam.replace("?h", "H");
					path = Paths.get(cam);
					Files.copy(site.openStream(),path, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("arquivo "+path.getFileName()+" criado");
					return(path.getFileName().toString());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}