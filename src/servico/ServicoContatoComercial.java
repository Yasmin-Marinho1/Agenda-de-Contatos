package servico;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

import modelo_negocio.Cidade;
import modelo_negocio.ContatoComercial;

public class ServicoContatoComercial extends Servico {
	private ServicoContatoComercial() {
		
	}
	public static ContatoComercial localizarContatoComercial(int id) {
		ContatoComercial e = repContatoComercial.localizar(id);
		return e;
	}
	public static ContatoComercial localizarContatoComercial(String nome) {
		ContatoComercial e = repContatoComercial.localizarNome(nome);
		return e;
	}
	public static void criarContatoComercial(String nome, String empresa, int idCidade) throws Exception {
		try {
			repContatoComercial.begin();
			String nom = Normalizer.normalize(nome, Normalizer.Form.NFD);
	        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        String name = pattern.matcher(nom).replaceAll("");
			String n = name.toUpperCase().trim();
			String em = Normalizer.normalize(empresa, Normalizer.Form.NFD);
	        Pattern pat = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        String empr = pat.matcher(em).replaceAll("");
			String emp = empr.toUpperCase().trim();
			if (emp.equals("") || n.equals("")) {
				throw new Exception("nome e/ou empresa inválido(s)");
			}
			ContatoComercial e = repContatoComercial.localizarNome(n);
			if (e != null)
				throw new Exception("nome de contato já existe: " + n);
			

			e = new ContatoComercial(n, emp, idCidade);

			repContatoComercial.criar(e);
			repContatoComercial.commit();
			
		} catch (Exception e) {
			repContatoComercial.rollback();
			throw e;
		}
	}
	public static void alterarContatoComercial(int id, String nome, String empresa, int idCidade) throws Exception {
		try {
			repContatoComercial.begin();
			ContatoComercial e = repContatoComercial.localizar(id);
			if (e == null) {
				throw new Exception("alterar contato comercial - contato inexistente: " + nome);
			}
			String nom = Normalizer.normalize(nome, Normalizer.Form.NFD);
	        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        String name = pattern.matcher(nom).replaceAll("");
			String n = name.toUpperCase().trim();
			String em = Normalizer.normalize(empresa, Normalizer.Form.NFD);
	        Pattern pat = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        String empr = pat.matcher(em).replaceAll("");
			String emp = empr.toUpperCase().trim();
			if (emp.equals("") || n.equals("")) {
				throw new Exception("nome e/ou empresa inválido(s)");
			}
            Cidade cidade = repCidade.localizar(idCidade);
            if (cidade == null) {
				throw new Exception("id de cidade inválido: " + idCidade);
			}
            e.setNome(n);
            e.setEmpresa(emp);
            e.setCidade(cidade);
			repContatoComercial.atualizar(e);
			repContatoComercial.commit();
		} catch (Exception e) {
			repContatoComercial.rollback();
			throw e;
		}
	}
	public static List<ContatoComercial> listarContatosEmpresa() {
		List<ContatoComercial> result = repContatoComercial.listar();
		return result;
	}
}