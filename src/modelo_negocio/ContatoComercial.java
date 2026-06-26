package modelo_negocio;

import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class ContatoComercial extends Contato {
	private String empresa;

	public ContatoComercial(){}
	public ContatoComercial(String nome, List<String> telefones, Cidade cidade, String empresa){
		super(nome, telefones, cidade);
		this.empresa = empresa;
	}
	public ContatoComercial(String nome, String empresa2, int idCidade) {
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}