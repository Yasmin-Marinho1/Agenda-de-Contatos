package modelo_negocio;

public class ContatoComercial extends Contato {
	private String empresa;
	

	public ContatoComercial(){}
	public ContatoComercial(String nome, String empresa, Cidade cidade){
		super(nome, cidade);
		this.empresa = empresa;
	}

	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}