package repositorio.db4o;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import com.db4o.query.Query;
import modelo_negocio.Cidade;

public class RepositorioCidade extends Repositorio<Cidade> {
	
	public Cidade localizarNome(String nom) {
		Query q = manager.query();
		q.constrain(Cidade.class);
		q.descend("nome").constrain(nom);
		List<Cidade> resultados = q.execute();
		if (resultados.isEmpty())
			return null;
		else
			return resultados.getFirst();
	}
}
