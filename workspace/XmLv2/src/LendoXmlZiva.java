import java.sql.ResultSet;

//import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class LendoXmlZiva {
	
	private String caminho; 
	private String sql1;
	private String sql2;
	private String nome;
	private int atualizacao;
	private double entrada; 
	private double saida;
	private String un_entrada;
	private String un_saida;
	private int usuarios;
	private int apAtivo;
	private int apInativo;
	private int hoje;
	private int mes;
	private String status;	
	private AcessaBanco a;
	private ResultSet rs;
		
	public LendoXmlZiva(){
		this.caminho = "NAO-SETADO-CAMINHO";
		this.nome = null;
		this.atualizacao = -1;
		this.entrada = -1;
		this.saida = -1;
		this.un_entrada = null;
		this.un_saida = null;
		this.usuarios = -1;
		this.apAtivo = -1;
		this.apInativo = -1;
		this.hoje = -1;
		this.mes = -1;
		this.status = null;
		this.a = new AcessaBanco();
		this.sql1=null;
		this.sql2=null;
	}
		
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	public void lerXml(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(caminho);
			Element raiz = doc.getDocumentElement();
			System.out.println("O elemento raiz é: " + raiz.getNodeName());
			NodeList listaPracas = raiz.getChildNodes();
			for (int i=0;i < listaPracas.getLength(); i++)
			{
				this.nome = null;
				this.atualizacao = -1;
				this.entrada = -1;
				this.saida = -1;
				this.un_entrada = null;
				this.un_saida = null;
				this.usuarios = -1;
				this.apAtivo = -1;
				this.apInativo = -1;
				this.hoje = -1;
				this.mes = -1;
				this.status = null;
				//como cada elemento do NodeList é um nó, precisamos fazer o cast
				Element praca = (Element) listaPracas.item(i);				
				NodeList listaMedidas = praca.getChildNodes();				
				if(praca.getNodeName().equals("TOTAL")){
					entrada = Double.parseDouble(listaMedidas.item(0).getFirstChild().getNodeValue());
					saida = Double.parseDouble(listaMedidas.item(1).getFirstChild().getNodeValue());
					un_entrada = listaMedidas.item(2).getFirstChild().getNodeValue();
					un_saida = listaMedidas.item(3).getFirstChild().getNodeValue();
					usuarios = Integer.parseInt(listaMedidas.item(4).getFirstChild().getNodeValue());
					apAtivo = Integer.parseInt(listaMedidas.item(5).getFirstChild().getNodeValue());
					apInativo = Integer.parseInt(listaMedidas.item(6).getFirstChild().getNodeValue());
					System.out.print(entrada+"|"+ un_entrada +"|"+ saida +"|" + un_saida +"|"+ usuarios +"|"+ apAtivo +"|"+ apInativo+"|");
					System.out.println("");
				}else if(praca.getNodeName().equals("USUARIOS")){
					hoje = Integer.parseInt(listaMedidas.item(1).getFirstChild().getNodeValue());
					mes = Integer.parseInt(listaMedidas.item(2).getFirstChild().getNodeValue());
					System.out.println(hoje+"|"+mes+"|");			
				}else{
					nome = listaMedidas.item(0).getFirstChild().getNodeValue();
					atualizacao = Integer.parseInt(listaMedidas.item(1).getFirstChild().getNodeValue());
					entrada = Double.parseDouble(listaMedidas.item(2).getFirstChild().getNodeValue());
					saida = Double.parseDouble(listaMedidas.item(3).getFirstChild().getNodeValue());
					un_entrada = listaMedidas.item(4).getFirstChild().getNodeValue();
					un_saida = listaMedidas.item(5).getFirstChild().getNodeValue();
					status = listaMedidas.item(6).getFirstChild().getNodeValue();
					usuarios = Integer.parseInt(listaMedidas.item(7).getFirstChild().getNodeValue());
					apAtivo = Integer.parseInt(listaMedidas.item(9).getFirstChild().getNodeValue());
					apInativo = Integer.parseInt(listaMedidas.item(10).getFirstChild().getNodeValue());
					System.out.print(nome+"|"+ atualizacao +"|"+ entrada +"|"+ un_entrada +"|"+saida +"|"+ un_saida +"|"+ status +"|"+ usuarios +"|"+apAtivo+"|"+ apInativo+"|");
					System.out.println("");
				}
			}
			System.out.println("Ok");
		}catch(Exception e){
		//	JOptionPane.showMessageDialog(null, "LendoXMLZiva\n"+e);
		}		
	}
	
	public void atualizaListaDePraca( ){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(caminho);
			Element raiz = doc.getDocumentElement();			
			//System.out.println("O elemento raiz é: " + raiz.getNodeName());			
			NodeList listaPracas = raiz.getChildNodes();					
			for (int i=0;i < listaPracas.getLength(); i++)
			{				 
				//como cada elemento do NodeList é um nó, precisamos fazer o cast
				Element praca = (Element) listaPracas.item(i);				
				NodeList listaMedidas = praca.getChildNodes();				
				int verificaInseridas = 0;
				int id_praca = 0;
				int id_empresa = 1;
				String idPraca = praca.getNodeName();
				if(idPraca.contains("AmericaNet-Ziva")){
					id_praca = Integer.parseInt(idPraca.substring(15));
					nome = listaMedidas.item(0).getFirstChild().getNodeValue();
					a.abrirConexao();
					a.setSql("SELECT Count(nome) FROM praca WHERE nome =  '" + nome + "'");
					rs = a.consulta();
					while(rs.next()){
						verificaInseridas = rs.getInt(1); 
					}					
					if(verificaInseridas == 0){
						a.setSql("INSERT INTO `praca` (`id_praca`,`nome`,`id_empresa`) VALUES (" + id_praca + ",'" + nome + "'," + id_empresa + ")");
						a.executa();
					}					
					a.fecharConexao();											
				}	
			}
			System.out.println("Ok empresas");
			System.out.println("Ok pracas");
		}catch(Exception e){
		//	JOptionPane.showMessageDialog(null, "LendoXMLZiva\n"+e);
		}
	}	
	
	public void insereDadosPraca(String dataHora){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(caminho);
			Element raiz = doc.getDocumentElement();			
	//System.out.println("O elemento raiz é: " + raiz.getNodeName());			
			NodeList listaPracas = raiz.getChildNodes();					
			for (int i=0;i < listaPracas.getLength(); i++)
			{				 
				//como cada elemento do NodeList é um nó, precisamos fazer o cast
				Element praca = (Element) listaPracas.item(i);				
				NodeList listaMedidas = praca.getChildNodes();				
				int id_praca = 0;				
				String idPraca = praca.getNodeName();
				if(idPraca.contains("AmericaNet-Ziva")){
					id_praca = Integer.parseInt(idPraca.substring(15));	
					this.nome = null;
					this.atualizacao = -1;
					this.entrada = -1;
					this.saida = -1;
					this.un_entrada = null;
					this.un_saida = null;
					this.usuarios = -1;
					this.apAtivo = -1;
					this.apInativo = -1;
					this.hoje = -1;
					this.mes = -1;
					this.status = null;
					this.sql1 = null;
					this.sql2 = null;
					
					nome = listaMedidas.item(0).getFirstChild().getNodeValue();
					atualizacao = Integer.parseInt(listaMedidas.item(1).getFirstChild().getNodeValue());
					entrada = Double.parseDouble(listaMedidas.item(2).getFirstChild().getNodeValue());
					saida = Double.parseDouble(listaMedidas.item(3).getFirstChild().getNodeValue());
					un_entrada = listaMedidas.item(4).getFirstChild().getNodeValue();
					un_saida = listaMedidas.item(5).getFirstChild().getNodeValue();
					status = listaMedidas.item(6).getFirstChild().getNodeValue();
					usuarios = Integer.parseInt(listaMedidas.item(7).getFirstChild().getNodeValue());
				
	//				String erro = listaMedidas.item(10).getParentNode().getNodeName();
		//System.out.println(erro);
					//if(erro.contains("AmericaNet-Ziva0052")){
					//	apAtivo = Integer.parseInt(listaMedidas.item(10).getFirstChild().getNodeValue());
				//		apInativo = Integer.parseInt(listaMedidas.item(11).getFirstChild().getNodeValue());
			//		}else{
						apAtivo = Integer.parseInt(listaMedidas.item(9).getFirstChild().getNodeValue());
						apInativo = Integer.parseInt(listaMedidas.item(10).getFirstChild().getNodeValue());
				//	}
					sql1 = "INSERT INTO `dados_praca` (`id_data_hora`,`id_praca`";
					sql2 = ") VALUES ('" + dataHora + "'," + id_praca;
					if(atualizacao != -1){
						sql1 = sql1+",`atualizacao`";
						sql2 = sql2+","+ atualizacao;
					}
					if(entrada != -1){
						sql1 = sql1+",`entrada`";
						sql2 = sql2+","+ entrada;
					}
					if(un_entrada != null){
						sql1 = sql1+",`un_entrada`";
						sql2 = sql2+",'"+ un_entrada+"'";
					}
					if(saida != -1){
						sql1 = sql1+",`saida`";
						sql2 = sql2+","+ saida;
					}
					if(un_saida != null){
						sql1 = sql1+",`un_saida`";
						sql2 = sql2+",'"+ un_saida+"'";
					}
					if(usuarios != -1){
						sql1 = sql1+",`usuarios`";
						sql2 = sql2+","+ usuarios;
					}
					if(apAtivo != -1){
						sql1 = sql1+",`ap_ativos`";
						sql2 = sql2+","+ apAtivo;
					}
					if(apInativo != -1){
						sql1 = sql1+",`ap_inativos`";
						sql2 = sql2+","+ apInativo;
					}
					a.setSql(sql1+sql2+")");
					a.abrirConexao();
					a.executa();
					a.fecharConexao();	
				}			
			}
			System.out.println("Ok dados_pracas");
		}catch(Exception e){
			
		//	JOptionPane.showMessageDialog(null, "LendoXMLZiva\n"+e);
		//	e.printStackTrace();
		}	
	}
	
	public void insereDadosAps(String dataHora){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(caminho);
			Element raiz = doc.getDocumentElement();			
// System.out.println("O elemento raiz é: " + raiz.getNodeName());			
			NodeList listaPracas = raiz.getChildNodes();
			for (int i=0;i < listaPracas.getLength(); i++)
			{				 
				//como cada elemento do NodeList é um nó, precisamos fazer o cast
				Element praca = (Element) listaPracas.item(i);				
				NodeList listaMedidas = praca.getChildNodes();				
				int id_praca = 0;
				String idPraca = praca.getNodeName();
				if(idPraca.contains("AmericaNet-Ziva")){
					id_praca = Integer.parseInt(idPraca.substring(15));
					
	//				System.out.println(listaMedidas.item(11).getNodeName());
	//				String erro = listaMedidas.item(10).getParentNode().getNodeName();
			
					Element apsR;
	//				System.out.println(erro);
				//	if(erro.contains("AmericaNet-Ziva0052")){
				//		apsR = (Element) listaMedidas.item(12);
				//	}else{
						apsR = (Element) listaMedidas.item(11);
				//	}
					
					NodeList listaAps = apsR.getChildNodes();
					String id_ap = "0";
					for(int j=0;j < listaAps.getLength();j++){
						status = null;
						usuarios = -1;
						Element aps = (Element) listaAps.item(j);				
						NodeList ap = aps.getChildNodes();
						String idAp = aps.getNodeName();
						if(idAp.contains("ap")){
							id_ap = idAp.substring(2);
							if(id_ap.equals("i")) id_ap = "1";
							status = ap.item(2).getFirstChild().getNodeValue();							
							if(status.contains("ERROR")){
								status = "ER";
								j = listaAps.getLength();
							}else{						
							
								String usuar = ap.item(1).getFirstChild().getNodeValue(); 
															
								if(usuar.equals("U")){
									usuar = "-1";
									status = "ER";
								}
								usuarios = Integer.parseInt(usuar);
								sql1 = null;
								sql2 = null;
								sql1 = "INSERT INTO `praca_aps` (`id_data_hora`,`id_praca`,`id_ap`";
								sql2 = ") VALUES ('"+ dataHora + "'," + id_praca + "," + id_ap;
								if(usuarios != -1){
									sql1 = sql1+",`usuarios`";
									sql2 = sql2+","+ usuarios;
								}
								if(status != null){
									sql1 = sql1+",`status`";
									sql2 = sql2+",'"+ status+"'";
								}
								System.out.println(sql1+sql2+")");
								a.setSql("INSERT INTO `praca_aps` (`id_data_hora`,`id_praca`,`id_ap`,`usuarios`,`status`) VALUES ('"+ dataHora + "'," + id_praca + "," + id_ap + "," + usuarios + ",'" + status + "')");							
								a.setSql(sql1+sql2+")");
								a.abrirConexao();
								a.executa();
								a.fecharConexao();								
							}				
						}	
					}				
				}	
			}
			System.out.println("Ok praca_aps");
		}catch(Exception e){
		//	JOptionPane.showMessageDialog(null, "LendoXMLZiva\n"+e.getMessage());
		//	e.printStackTrace();
		}
	}	

	public void insereTotalZiva(String dataHora){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(caminho);
			Element raiz = doc.getDocumentElement();
			NodeList listaPracas = raiz.getChildNodes();
			for (int i=0;i < listaPracas.getLength(); i++)
			{
				Element praca = (Element) listaPracas.item(i);				
				NodeList listaMedidas = praca.getChildNodes();				
				if(praca.getNodeName().equals("TOTAL")){
					this.entrada =-1;
					this.saida = -1;
					this.un_entrada = "";
					this.un_saida = "";
					this.usuarios = -1;
					this.apAtivo = -1;
					this.apInativo = -1;
					entrada = Double.parseDouble(listaMedidas.item(0).getFirstChild().getNodeValue());
					saida = Double.parseDouble(listaMedidas.item(1).getFirstChild().getNodeValue());
					un_entrada = listaMedidas.item(2).getFirstChild().getNodeValue();
					un_saida = listaMedidas.item(3).getFirstChild().getNodeValue();
					usuarios = Integer.parseInt(listaMedidas.item(4).getFirstChild().getNodeValue());
					apAtivo = Integer.parseInt(listaMedidas.item(5).getFirstChild().getNodeValue());
					apInativo = Integer.parseInt(listaMedidas.item(6).getFirstChild().getNodeValue());
					sql1 = null;
					sql2 = null;
					sql1 = "INSERT INTO `total_ziva` (`id_data_hora`";
					sql2 = ") VALUES ('" + dataHora +"'";
					if(entrada != -1){
						sql1 = sql1+",`entrada`";
						sql2 = sql2+","+ entrada;
					}
					if(un_entrada != null){
						sql1 = sql1+",`un_entrada`";
						sql2 = sql2+",'"+ un_entrada+"'";
					}
					if(saida != -1){
						sql1 = sql1+",`saida`";
						sql2 = sql2+","+ saida;
					}
					if(un_saida != null){
						sql1 = sql1+",`un_saida`";
						sql2 = sql2+",'"+ un_saida+"'";
					}
					if(usuarios != -1){
						sql1 = sql1+",`usuarios`";
						sql2 = sql2+","+ usuarios;
					}
					if(apAtivo != -1){
						sql1 = sql1+",`aps_ativos`";
						sql2 = sql2+","+ apAtivo;
					}
					if(apInativo != -1){
						sql1 = sql1+",`aps_inativos`";
						sql2 = sql2+","+ apInativo;
					}
					//System.out.println(sql1+sql2+")");
					//a.setSql("INSERT INTO `total_ziva` (`id_data_hora`,`entrada`,`un_entrada`,`saida`,`un_saida`,`usuarios`,`aps_ativos`,`aps_inativos`) VALUES ('" + dataHora + "'," + entrada + ",'" + un_entrada + "'," + saida + ",'" + un_saida + "'," + usuarios + "," + apAtivo + "," + apInativo + ")");
					a.setSql(sql1+sql2+")");
					a.abrirConexao();
					a.executa();
					a.fecharConexao();
				}
			}			
			System.out.println("Ok total_ziva");
		}catch(Exception e){
		//	JOptionPane.showMessageDialog(null, "LendoXMLZiva\n"+e);
		}
	}
	
	public void insereTotalUsuariosZiva(String dataHora){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(caminho);
			Element raiz = doc.getDocumentElement();
			NodeList listaPracas = raiz.getChildNodes();
			for (int i=0;i < listaPracas.getLength(); i++)
			{
				Element praca = (Element) listaPracas.item(i);				
				NodeList listaMedidas = praca.getChildNodes();				
				if(praca.getNodeName().equals("USUARIOS")){					
					hoje = -1;
					mes = -1;
					
					hoje = Integer.parseInt(listaMedidas.item(1).getFirstChild().getNodeValue());
					mes = Integer.parseInt(listaMedidas.item(2).getFirstChild().getNodeValue());				
					sql1 = null;
					sql2 = null;
					sql1 = "INSERT INTO `usuarios_total_ziva` (`id_data_hora`";
					sql2 = ") VALUES ('" + dataHora +"'";
					if(hoje != -1){
						sql1 = sql1+",`hoje`";
						sql2 = sql2+","+ hoje;
					}
					if(mes != -1){
						sql1 = sql1+",`mes`";
						sql2 = sql2+","+ mes;
					}
					//System.out.println(sql1+sql2+")");
					//a.setSql("INSERT INTO `usuarios_total_ziva` (`id_data_hora`,`hoje`,`mes`) VALUES ('" + dataHora + "'," + hoje + "," + mes + ")");
					a.setSql(sql1+sql2+")");
					a.abrirConexao();
					a.executa();
					a.fecharConexao();
				}
			}			
			System.out.println("Ok usuarios_total_ziva");			
		}catch(Exception e){
		//	JOptionPane.showMessageDialog(null, "LendoXMLZiva\n"+e);
		}
	}
	
}