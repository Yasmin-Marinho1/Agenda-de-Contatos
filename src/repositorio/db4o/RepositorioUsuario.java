/**********************************
 * IFPB - Bacharelado em Eng. de Software
 * Prof. Fausto Maranhão Ayres
 **********************************/
package repositorio.db4o;

import java.util.List;

import com.db4o.query.Query;

import modelo_negocio.Usuario;

public class RepositorioUsuario extends Repositorio<Usuario>   {

	public Usuario localizarNome(String nom) {
		Query q = manager.query();
		q.constrain(Usuario.class);
		q.descend("nome").constrain(nom);
		List<Usuario> resultados = q.execute();
		if (resultados.isEmpty())
			return null;

		else
			return resultados.getFirst();
	}
	
}