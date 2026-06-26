package modelo_negocio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class Cidade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
	@OneToMany(mappedBy = "cidade", 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
			orphanRemoval = false)
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