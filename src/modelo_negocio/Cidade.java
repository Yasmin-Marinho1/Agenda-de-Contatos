package modelo_negocio;

import java.util.ArrayList;
import java.util.List;

public class Cidade {
	private int id;
	private String nome;
	private List<Contato> contatos = new ArrayList<>();
	
	public Cidade() {}
	public Cidade(String nome) {
		this.nome = nome;
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
    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }
    public void adicionar(Contato c) {
		contatos.add(c);
		c.setCidade(this);
	}
	public void remover(Contato c) {
		c.setCidade(null);
		contatos.remove(c);
	}
	public List<Contato> getContatos() {
		return new ArrayList<>(contatos);
	}
}