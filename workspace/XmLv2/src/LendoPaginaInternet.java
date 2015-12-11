import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class LendoPaginaInternet {
	private URL url;
	private String file2;
	private Path path;
	private Path path2;
	public LendoPaginaInternet(){
		this.url = null;
		this.path = null;
	}
	public LendoPaginaInternet(String url,String file){
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
		this.file2 = file+"l";
		this.path = Paths.get(file);
		if (Files.exists(path.toAbsolutePath())){
			try {
				Files.delete(path);
				Files.createDirectories(path.getParent());
				Files.createFile(path.toAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}	 
			//	System.out.println("O arquivo já EXISTE");			
		}else{
			try {
				Files.createDirectories(path.getParent());
				Files.createFile(path.toAbsolutePath());
				//System.out.println("O arquivo CRIADO");
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}
		this.costroePagina2();
	}

	public void setFile(String file) {
		this.file2 = file+"l";
		this.path = Paths.get(file);
		if (Files.exists(path.toAbsolutePath())){
			System.out.println("O arquivo já EXISTE");			
		}else{
			try {
				Files.createDirectories(path.getParent());
				Files.createFile(path.toAbsolutePath());
				System.out.println("O arquivo CRIADO");
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}
		this.costroePagina2();
	}

	public void setUrl(String url){
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void lerPgInternet(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        String texto;
	        while ((texto = in.readLine()) != null) {
	        	byte[] bits = texto.getBytes();	        	
	        	Files.write(path.toAbsolutePath(), bits, StandardOpenOption.APPEND);
	    	}	        
	        in.close();
	        
	        Random gerador = new Random();	        
	        int numero = gerador.nextInt(250);	        
	        Thread.sleep(numero);
	        if(Files.size(path) <= Files.size(path2)){
	        	Files.delete(path);	        	
	        }else{
	        	Files.delete(path2);
	        	Files.createLink(path2, path);
	        	Files.delete(path);	
	        	//System.out.println("Atualizado arquivo");	        	
	        }	        
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private void costroePagina2(){
		this.path2 = Paths.get(file2);
		if (!Files.exists(path2.toAbsolutePath())){
			try {
				Files.createDirectories(path2.getParent());
				Files.createFile(path2.toAbsolutePath());
				System.out.println("O arquivo CRIADO");
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}
	}
	
	public void LendoPaginaXml(String url1,String caminho){
		try {
			this.url = new URL(url1);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		this.path = Paths.get(caminho);
		if (Files.exists(path.toAbsolutePath())){
			try {
				Files.delete(path);
				Files.createDirectories(path.getParent());
				Files.createFile(path.toAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}	 
			//	System.out.println("O arquivo já EXISTE");			
		}else{
			try {
				Files.createDirectories(path.getParent());
				Files.createFile(path.toAbsolutePath());
				//System.out.println("O arquivo CRIADO");
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        String texto;
	        while ((texto = in.readLine()) != null) {
	        	byte[] bits = texto.getBytes();	        	
	        	Files.write(path.toAbsolutePath(), bits, StandardOpenOption.APPEND);
	    	}	        
	       // System.out.println("Escrito no arquivo");	        
	        in.close();
	        Random gerador = new Random();	        
	        int numero = gerador.nextInt(250);
	        Thread.sleep(numero);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public static File gravaArquivoDeURL(String stringUrl, String pathLocal) {  
	    try {  
	        //Encapsula a URL num objeto java.net.URL  
	        URL url = new URL(stringUrl);  
	        //Queremos o arquivo local com o mesmo nome descrito na URL  
	        //Lembrando que o URL.getPath() ira retornar a estrutura   
	        //completa de diretorios e voce deve tratar esta String  
	        //caso nao deseje preservar esta estrutura no seu disco local.  
	        String nomeArquivoLocal = url.getPath();  
	        //Cria streams de leitura (este metodo ja faz a conexao)...  
	        InputStream is = url.openStream();  
	        //... e de escrita.  
	        FileOutputStream fos = new FileOutputStream(pathLocal+nomeArquivoLocal);  
	        //Le e grava byte a byte. Voce pode (e deve) usar buffers para  
	        //melhor performance (BufferedReader).  
	        int umByte = 0;  
	        while ((umByte = is.read()) != -1){  
	            fos.write(umByte);  
	        }  
	        //Nao se esqueca de sempre fechar as streams apos seu uso!  
	        is.close();  
	        fos.close();  
	        //apos criar o arquivo fisico, retorna referencia para o mesmo  
	        return new File(pathLocal+nomeArquivoLocal);  
	    } catch (Exception e) {  
	        //Lembre-se de tratar bem suas excecoes, ou elas tambem lhe tratarão mal!  
	        //Aqui so vamos mostrar o stack no stderr.  
	        e.printStackTrace();  
	    }  
	    return null;  
	}  
	
}


