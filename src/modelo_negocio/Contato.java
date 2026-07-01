package modelo_negocio;

import java.util.ArrayList;
import java.util.List;


public class Contato {

	private int id;
	private String nome;
	private List<String> telefones = new ArrayList<>();
	private Cidade cidade;

	public Contato() {}
	public Contato(String nome, Cidade cidade) {
		this.nome = nome;
		this.cidade = cidade;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<String> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public void addTelefones(String numero, int id) {
		telefones.add(numero);
	}
	public void removerTelefones(String numero, int id) {
		telefones.remove(numero);
	}
}
