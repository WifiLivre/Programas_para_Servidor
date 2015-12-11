import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LeituraDeTexto {
    private String nomeArquivo;
    private String linha;
    private FileReader reader;
    private BufferedReader leitor;
    private String link;
    private ArrayList <String> lst;
    
    public LeituraDeTexto(String xNomeArquivo){
     	this.linha = "";
     	this.link = "";
    	this.nomeArquivo = xNomeArquivo;
    	this.lst = new ArrayList <String>();
    }
    
    public void lerDados(){
    	try{
	        //abrindo arquivo para leitura
	        reader = new FileReader(nomeArquivo);
	        //leitor do arquivo
	        leitor = new BufferedReader(reader);		        
	        while(true){
	        	linha=leitor.readLine();				    
	        	if(linha == null)break;		        	
	        	if(linha.contains("prodam_-_wcs000114")){		        	
		        	int i = 1;
		        	int j = 0;			        	
		        	while(i > 0){			     
			        	i = linha.indexOf("<a href=\"prodam_-_wcs0",i);
			        	if(i==-1)break;
			        	i = i + 18;
			        	j = linha.indexOf("\">", i);				        	
			        	link = "http://187.62.212.1/prodam/xml/prodam_-_"+linha.substring(i, j);
			        	//System.out.println(i);
			           	System.out.println(link);
		        	}
	        	}	         
	        	linha=leitor.readLine();
	        }
	     }
	     catch(Exception e) {
	    	  System.out.println(e);
	     }    	
    }	
    	    
    public ArrayList<String> lerNomePracas(){
    	try{
	        //abrindo arquivo para leitura
	        reader = new FileReader(nomeArquivo);
	        //leitor do arquivo
	        leitor = new BufferedReader(reader);		        
	        while(true){
	        	linha=leitor.readLine();				    
	        	if(linha == null)break;		        	
	        	if(linha.contains("prodam_-_wcs000114")){		        	
		        	int i = 1;
		        	int j = 0;			        	
		        	while(i > 0){			     
			        	i = linha.indexOf("<a href=\"prodam_-_wcs0",i);
			        	if(i==-1)break;
			        	i = i + 18;
			        	j = linha.indexOf("\">", i);				        	
			        	link = "http://187.62.212.1/prodam/xml/prodam_-_"+linha.substring(i, j);
			        	lst.add(link);
			    	}
	        	}	         
	        	linha=leitor.readLine();
	        }
    	}
	    catch(Exception e) {
	    	System.out.println(e);
	    }
		return lst;	    	
    }	
	    
}

