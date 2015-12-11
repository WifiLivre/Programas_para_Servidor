import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LendoXmlWCS {
	private String nomePraca;
	private int idPraca;
	private ArrayList <String> lst;
	AcessaBanco a = new AcessaBanco();
	private ResultSet rs;
	private double latencia;
	private int usuarios;
	private double simetbox_latencia;
	private int atualizacao;
	private double entrada;
	private double saida;
	private String sql1;
	private String sql2;
	private double latenciaAp;
	private int usuariosAp;
	private double entradaAp;
	private double saidaAp;
	private String caminhoPasta;
	
	public LendoXmlWCS(){
		this.lst = new ArrayList <String>();
		this.idPraca = 0;
		this.latencia = -1;
		this.usuarios = -1;
		this.simetbox_latencia = -1;		
		this.atualizacao = -1;
		this.saida = -1;
		this.entrada = -1;
		this.sql1 = null;
		this.sql2 = null;
		this.caminhoPasta =null;
	}
	
	public void setLst(ArrayList<String> lista){
		lista.remove(0);
		for(int i = 0; i < lista.size(); i++){
			String arquivo = lista.get(i);
			
			
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
		
			
			
			arquivo = caminhoPasta + arquivo.substring(arquivo.lastIndexOf("/")+1);
			lst.add(arquivo);
			
		}
	}
	
	public void setCaminhoPasta(String camPasta){
		this.caminhoPasta = camPasta;
	}
		
	public void cadastrarPracas(){
		int idVelho = 0;
		a.abrirConexao();
		for(int i=0;i < lst.size();i++){
			String link = (lst.get(i));
					
			int ini = link.indexOf("wcs")+3;
			int inf = link.indexOf("14_");
	
			
			idVelho = Integer.parseInt(link.substring(ini,inf));
			if(idPraca != idVelho){
				idPraca = Integer.parseInt(link.substring(ini,inf));
				if(!link.contains("stacao_vila_mara_-_")){
				
					if(link.contains("latencia.xml")) nomePraca = link.substring(link.indexOf("14_-_")+5,link.indexOf("latencia.xml")-3);
					if(link.contains("simetbox_-_latencia.xml")) nomePraca = link.substring(link.indexOf("14_-_")+5,link.indexOf("simetbox_-_latencia.xml")-3);
					if(link.contains("acessos.xml")) nomePraca = link.substring(link.indexOf("14_-_")+5,link.indexOf("acessos.xml")-3);
					if(link.contains("trafego.xml")) nomePraca = link.substring(link.indexOf("14_-_")+5,link.indexOf("trafego.xml")-3);
					if(nomePraca.contains("_-_")){
						nomePraca = nomePraca.substring(0,nomePraca.indexOf("_-_"));
					}				
					nomePraca = nomePraca.replace("_"," ");
					//System.out.println(link);
					//System.out.println(nomePraca);
				}else{
					nomePraca = "praca 1 de maio";
				}
				int xid = idPraca + 1000;
				int verificaInseridas = 0;
				a.setSql("SELECT Count(nome) FROM praca WHERE nome =  '" + nomePraca + "'");
			
				rs = a.consulta();
				try{
					while(rs.next()){
						verificaInseridas = rs.getInt(1); 
					}			
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "LendoHTMLWCS\n"+e);
				}				
				if(verificaInseridas == 0){
					a.setSql("INSERT INTO `praca` (`id_praca`,`nome`,`id_empresa`) VALUES (" + xid + ",'" + nomePraca + "','2')");
					a.executa();				
				}				
							
					
			}	
		}
		a.fecharConexao();	
	}

	
	public void cadastrarDadosAps(String dataHora){
		a.abrirConexao();
		for(int i=0;i < lst.size();i++){
			String link = (lst.get(i));
			
			int inic = link.indexOf("wcs")+3;
			int infi = link.indexOf("14_");
			
			idPraca = Integer.parseInt(link.substring(inic,infi))+1000;
			
			String tipo = null;
			int ini = link.indexOf("_-_ap_")+6;
			int inf = 0;
			if(link.contains("_-_ap_")){
				if(link.contains("acessos.xml")) inf = link.indexOf("_-_acessos.xml");
				if(link.contains("latencia.xml")&&(!link.contains("simetbox"))) inf = link.indexOf("_-_latencia.xml");
				if(link.contains("trafego.xml")) inf = link.indexOf("_-_trafego.xml");
			
				int xId = Integer.parseInt(link.substring(ini,inf));
				
				System.out.println(idPraca+" | "+xId);
				
				sql1 = "INSERT INTO `praca_aps` (`id_data_hora`,`id_praca`,`id_ap`) VALUES ('" + dataHora + "'," + idPraca + "," + xId+")";
				sql2 = "SELECT Count(`id_ap`) FROM `praca_aps` WHERE `id_ap` = "+xId+" AND `id_praca` = "+idPraca+" AND `id_data_hora` = '"+ dataHora+"'";
				a.setSql(sql2);
				int verificaInseridas = 0;
			
				rs = a.consulta();
				try{
					while(rs.next()){
						verificaInseridas = rs.getInt(1); 
					}			
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Erro Aps\n"+e);
				}				
				if(verificaInseridas == 0){
					a.setSql(sql1);
					a.executa();	
					tipo = "novo";				}				
				sql1="null";
				//usuarios
				if(link.contains("acessos.xml")){
					String usAp = this.lerDadoXML(link);
					//System.out.println(link);
					if(!usAp.equals("U")){
						usuariosAp = Integer.parseInt(usAp);
						sql1 = "UPDATE `praca_aps` SET `usuarios`='" + usuariosAp + "' WHERE (`id_data_hora`='" + dataHora + "') AND (`id_praca`="+ idPraca +") AND (`id_ap`=" + xId + ")";  
						tipo = "usuarios";
					}	
				}
				//latencia
				if(link.contains("latencia.xml")&&(!link.contains("simetbox"))){
					String laAp = this.lerDadoXML(link);
					if(!laAp.equals("U")){
						latenciaAp = Double.parseDouble(laAp);
						sql1 = "UPDATE `praca_aps` SET `latencia`='" + latenciaAp + "' WHERE (`id_data_hora`='" + dataHora + "') AND (`id_praca`="+ idPraca +") AND (`id_ap`=" + xId + ")";  
						tipo = "latencia";
					}					
				}
				//trafego
				if(link.contains("trafego.xml")){
					String entradAp = null;
					String saidAp =null;
					String trAp = this.lerTrafegoDadosXML(link);
					atualizacao = Integer.parseInt(trAp.substring(0, trAp.indexOf("|")));
					entradAp = trAp.substring(trAp.indexOf("|")+1,trAp.indexOf("#"));
					saidAp = trAp.substring(trAp.indexOf("#")+1);
					
					
					sql1 = "UPDATE `praca_aps` SET `atualizacao`= " + atualizacao;   
					sql2 = " WHERE (`id_data_hora`='" + dataHora + "') AND (`id_praca`="+ idPraca +") AND (`id_ap`=" + xId + ")";  
					tipo = "atualizacao";
					
					if(!entradAp.equals("U")){
						entradaAp = Double.parseDouble(entradAp);
						sql1 = sql1+" ,`trafego_entrada`= "+entradaAp;
						tipo = tipo + "| entradaAp"; 
					}
					if(!saidAp.equals("U")){
						saidaAp = Double.parseDouble(saidAp);
						sql1 = sql1 +" ,`trafego_saida`= " +saidaAp;
						tipo = tipo + "| saidaAp";
					}	
					sql1 = sql1+sql2;
				}	
				a.setSql(sql1);
				if(sql1 != "null")	a.executa();
				
				System.out.println(idPraca+" | "+ xId +" | "+ tipo);
			}			
		}
		a.fecharConexao();
	}
	
	public void cadastrarDadosPraca(String dataHora){

		int idPracaOld = 4;

		idPraca = 1;
		a.abrirConexao();
		for(int i=0;i < lst.size();i++){
			String link = (lst.get(i));
			int ini = link.indexOf("wcs")+3;
			int inf = link.indexOf("14_");
			if(idPraca != idPracaOld){
				idPracaOld = idPraca;
				this.entradaAp = -1;
				this.saidaAp = -1;
				this.usuariosAp = -1;
				this.latenciaAp = -1;
			}
			idPraca = Integer.parseInt(link.substring(ini,inf));
			int xid = idPraca + 1000;
			a.setSql("SELECT `nome` FROM `praca` WHERE `id_praca` = " + xid);
			
			rs = a.consulta();
			try {
				while(rs.next()){					
					nomePraca = rs.getString(1);
					nomePraca = nomePraca.replace(" ", "_");
					nomePraca = nomePraca+"_-_";					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//usuarios
			String nomePracaAux = nomePraca + "acesso";
			if(link.contains(nomePracaAux)){
				String us = this.lerDadoXML(link);
				if(us.equals("U")){
					usuarios = -1;
				}else{
					usuarios = Integer.parseInt(us);
				}
			}
			//latencia
			nomePracaAux = nomePraca + "la";
			if(link.contains(nomePracaAux)){
				String la = this.lerDadoXML(link);
				if(la.equals("U")){
					latencia = -1;
				}else{
					latencia = Double.parseDouble(la);
				}				
			}	
			//simetbox
			nomePracaAux = nomePraca + "sim";	
			System.out.println(nomePraca);
			if(link.contains("stacao_vila_mara_-_")){
				String sl = this.lerDadoXML(link);
				if(sl.equals("U")){
					simetbox_latencia = -1;
				}else{
					simetbox_latencia = Double.parseDouble(sl);
				}
				nomePraca = "praca_1_de_maio_-_";
			}else{
			
				if(link.contains(nomePracaAux)){
					String sl = this.lerDadoXML(link);
					if(sl.equals("U")){
						simetbox_latencia = -1;
					}else{
						simetbox_latencia = Double.parseDouble(sl);
					}			
				}
			}
			//trafego
			nomePracaAux = nomePraca + "tr";
			if(link.contains(nomePracaAux)){
				String entrad = null;
				String said = null;
				String tr = this.lerTrafegoDadosXML(link);
				atualizacao = Integer.parseInt(tr.substring(0, tr.indexOf("|")));
				entrad = tr.substring(tr.indexOf("|")+1,tr.indexOf("#"));
				said = tr.substring(tr.indexOf("#")+1);
				if(entrad.equals("U")){
					entrad = "-1";
				}
				if(said.equals("U")){
					said = "-1";
				}
				entrada = Double.parseDouble(entrad);
				saida = Double.parseDouble(said);
			}						
			int nextId;
			if( i < (lst.size()-1)){
				String link2 = (lst.get(i+1));
				nextId = Integer.parseInt(link2.substring(ini,inf));
			}else{
				nextId = (Integer.parseInt(link.substring(ini,inf)))+ 1;
			}
			if(idPraca != nextId){
				sql1 = "INSERT INTO `dados_praca` (`id_data_hora`,`id_praca`";
				sql2 = ") VALUES ('" + dataHora + "'," + xid;
				if(entrada != -1){
					sql1 = sql1+",`entrada`";
					sql2 = sql2+","+ entrada;
				}
				if(saida != -1){
					sql1 = sql1+",`saida`";
					sql2 = sql2+","+ saida;
				}
				if(usuarios != -1){
					sql1 = sql1+",`usuarios`";
					sql2 = sql2+","+ usuarios;
				}
				if(atualizacao != -1){
					sql1 = sql1+",`atualizacao`";
					sql2 = sql2+","+ atualizacao;
				}
				if(latencia != -1){
					sql1 = sql1+",`latencia`";
					sql2 = sql2+","+ latencia;
				}
				if(simetbox_latencia != -1){
					sql1 = sql1+",`simetbox_latencia`";
					sql2 = sql2+","+ simetbox_latencia;
				}
				
			
				
				int verificaInseridas = 0;
				a.setSql("SELECT Count(id_data_hora) FROM dados_praca WHERE id_praca = '"+xid+"' AND id_data_hora =  '"+dataHora+"'");
			
				rs = a.consulta();
				try{
					while(rs.next()){
						verificaInseridas = rs.getInt(1); 
					}			
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Erro inserção dados Praça\n"+e);
				}
				
				a.setSql(sql1+sql2+")");
				if(verificaInseridas == 0){
					a.executa();
				}
				//zerando dados
				this.latencia = -1;
				this.usuarios = -1;
				this.simetbox_latencia = -1;		
				this.atualizacao = -1;
				this.saida = -1;
				this.entrada = -1;	
			}
		}
		a.fecharConexao();
	}
	
	private String lerDadoXML(String caminho){
		String last_ds = null;
		Document doc = null;
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			db = dbf.newDocumentBuilder();
		}catch(Exception e){
			System.out.println("ERRO Builder <--------------------------");
		}
		boolean again = true;
		int cont = 0;
		while(again){
			try{
				doc = db.parse(caminho);
				again = false;
			}catch(Exception e){
				System.out.println("ERRO doc <--------------------------");
				try {
					Random gerador = new Random();
					int numero = gerador.nextInt(100);
					Thread.sleep(numero);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				cont++;						
				if(cont > 5){
					return("U");											
				}
			}
		}
		Element raiz = doc.getDocumentElement();
		NodeList listaPracas = raiz.getChildNodes();
		last_ds = listaPracas.item(7).getFirstChild().getNodeValue();
		return(last_ds);
	}
	
	private String lerTrafegoDadosXML(String caminho){
		String lastupdate = null;
		String ini = null;
		String out = null;
		Document doc = null;
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			db = dbf.newDocumentBuilder();
		}catch(Exception e){
			System.out.println("ERRO Builder <--------------------------");
		}
		boolean again = true;
		int cont = 0;
		while(again){
			try{
				doc = db.parse(caminho);
				again = false;
			}catch(Exception e){
				System.out.println("ERRO doc <--------------------------");
				try {
					Random gerador = new Random();
					int numero = gerador.nextInt(100);
					Thread.sleep(numero);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				cont++;						
				if(cont > 5){
					String traf = "0"+"|"+"U"+"#"+"U";
					return(traf);				
				}
			}
		}
		Element raiz = doc.getDocumentElement();
		NodeList listaPracas = raiz.getChildNodes();
		lastupdate = listaPracas.item(1).getFirstChild().getNodeValue();
		ini =  listaPracas.item(7).getFirstChild().getNodeValue();
		out =  listaPracas.item(11).getFirstChild().getNodeValue();
		String traf = lastupdate+"|"+ini+"#"+out;
		return(traf);
	}
		
	public void escreveLinks(){
		for(int i=0;i < lst.size();i++){
			System.out.println(lst.get(i));
		}
	}		
	
	public void removerDeletarTodosXmls(String cam){
		Arquivo arq = new Arquivo();
		arq.excluir(cam);
		for(int i = 0; i < lst.size(); i++){
			arq.excluir(lst.get(i));
		}		
	}	
}
	
	
	
	
	

