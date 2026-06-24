package servico;
/**********************************
 * IFPB - Bacharelado em Eng. de Software
 * Prof. Fausto Maranhão Ayres
 **********************************/
import java.util.List;

import modelo_negocio.Usuario;

public class ServicoUsuario extends Servico {
	private ServicoUsuario() {
	}

	private static Usuario logado = null;	//exibido na tela principal

	public static Usuario localizarUsuario(String nome, String senha) {
		Usuario u = repUsuario.localizarNome(nome);
		if (u == null || !u.getSenha().equals(senha))
			return null;
		else
			return u;
	}

	public static void criarUsuario(String nome, String senha) throws Exception {
		try {
			repUsuario.begin();

			Usuario u = repUsuario.localizarNome(nome);
			if (u != null)
				throw new Exception("usuario ja existe:" + nome);

			u = new Usuario(nome, senha);

			repUsuario.criar(u);
			repUsuario.commit();
			
		} catch (Exception e) {
			repUsuario.rollback();
			throw e;
		}
	}

	public static List<Usuario> listarUsuarios() {
		List<Usuario> result = repUsuario.listar();
		return result;
	}

	public static Usuario getLogado() {
		return logado;
	}

	public static void setLogado(Usuario logado) {
		ServicoUsuario.logado = logado;
	}

}
