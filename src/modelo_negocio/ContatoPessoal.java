package modelo_negocio;

public class ContatoPessoal extends Contato {
	private int grauProximidade;

	public ContatoPessoal(){}
	public ContatoPessoal(String nome, int grauProximidade, Cidade cidade){
		super(nome, cidade);
		this.grauProximidade = grauProximidade;
	}
	public int getGrauProximidade() {
		return grauProximidade;
	}
	public void setGrauProximidade(int grauProximidade) {
		this.grauProximidade = grauProximidade;
	}
}