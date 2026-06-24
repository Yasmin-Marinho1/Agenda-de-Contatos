/**********************************
 * IFPB - Bacharelado em Eng. de Software
 * Prof. Fausto Maranhão Ayres
 **********************************/
package repositorio.db4o;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import repositorio.CRUDInterface;

public abstract class Repositorio<T> implements CRUDInterface<T> {
	protected static ObjectContainer manager;
	private static final Logger logger = LogManager.getLogger(Repositorio.class);

	public static void conectar() throws Exception{
		manager = Util.conectar();
	}

	public static void desconectar() {
		Util.desconectar();
		manager = null;
	}

	public void criar(T objeto) {
		manager.store(objeto);
		logger.info("-------- criou objeto "+objeto.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public T localizar(int id) {
		// inferindo o tipo da classe T
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Class<T> classe = (Class<T>) type.getActualTypeArguments()[0];

		Query q = manager.query();
		q.constrain(classe);
		q.descend("id").constrain(id);
		List<T> resultados = q.execute();
		if (resultados.isEmpty())
			return null;

		else
			return resultados.getFirst();
	}

	public T atualizar(T objeto) {
		manager.store(objeto);
		logger.info("-------- atualizou objeto "+objeto.getClass().getName());
		return objeto;
	}

	public void deletar(T objeto) {
		manager.delete(objeto);
		logger.info("-------- apagou objeto "+objeto.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public List<T> listar() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Class<T> classe = (Class<T>) type.getActualTypeArguments()[0];

		List<T> resultados = (List<T>) manager.query(classe);
		return resultados;

	}

	public void begin() {
		//é automatico
	}

	public void commit() {
		if (!manager.ext().isClosed()) {
			manager.commit();
			manager.ext().purge(); // limpar cache de memoria
		}
	}

	public void rollback() {
		if (!manager.ext().isClosed()) {
			manager.rollback();
			manager.ext().purge(); // limpar cache de memoria
		}
	}

}
