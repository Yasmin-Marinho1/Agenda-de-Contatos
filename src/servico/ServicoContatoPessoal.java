package servico;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

import modelo_negocio.Cidade;
import modelo_negocio.ContatoPessoal;

public class ServicoContatoPessoal extends Servico {
	private ServicoContatoPessoal() {
	}

	public static ContatoPessoal localizarContatoPessoal(int id) {
		ContatoPessoal p = repContatoPessoal.localizar(id);
		return p;
	}
	public static ContatoPessoal localizarContatoPessoal(String nome) {
		ContatoPessoal p = repContatoPessoal.localizarNome(nome);
		return p;
	}

	public static void criarContatoPessoal(String nome, int grauDeProximidade, int idCidade) throws Exception {
		try {
			repContatoPessoal.begin();
			String nom = Normalizer.normalize(nome, Normalizer.Form.NFD);
	        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        String name = pattern.matcher(nom).replaceAll("");
			String n = name.toUpperCase().trim();
			if (n.equals("")) {
				throw new Exception("nome inválido");
			}

			ContatoPessoal p = repContatoPessoal.localizarNome(n);
			if (p != null) {
				throw new Exception("usuario ja existe: " + n);
			}
			
			if (grauDeProximidade < 1 || grauDeProximidade > 3) {
				throw new Exception("grau de proximidade inválido: " + grauDeProximidade);
			}
			
			Cidade c = repCidade.localizar(idCidade);
			if (c == null) {
				throw new Exception("id de cidade inválido: " + idCidade);
			}
			
			p = new ContatoPessoal(n, grauDeProximidade, idCidade);

			repContatoPessoal.criar(p);
			repContatoPessoal.commit();
			
		} catch (Exception e) {
			repContatoPessoal.rollback();
			throw e;
		}
	}
	
	public static void alterarContatoPessoal(int id, String nome, int grauDeProximidade, int idCidade) throws Exception {
		try {
			repContatoPessoal.begin();
			ContatoPessoal p = repContatoPessoal.localizar(id);
			String nom = Normalizer.normalize(nome, Normalizer.Form.NFD);
	        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        String name = pattern.matcher(nom).replaceAll("");
			String n = name.toUpperCase().trim();
			if (n.equals("")) {
				throw new Exception("nome inválido");
			}
			if (p == null)
				throw new Exception("alterar contato pessoal - contato inexistente: " + nome);
			if (grauDeProximidade < 1 || grauDeProximidade > 3) {
				throw new Exception("grau de proximidade inválido: " + grauDeProximidade);
			}
            Cidade c = repCidade.localizar(idCidade);
            if (c == null) {
				throw new Exception("id de cidade inválido: " + idCidade);
			}
            p.setNome(n);
			p.setGrauProximidade(grauDeProximidade);
            p.setCidade(c);


			repContatoPessoal.atualizar(p);
			repContatoPessoal.commit();

		} catch (Exception e) {
			repContatoPessoal.rollback();
			throw e;
		}
	}

	public static List<ContatoPessoal> listarContatosPessoais() {
		List<ContatoPessoal> result = repContatoPessoal.listar();
		return result;
	}
}
