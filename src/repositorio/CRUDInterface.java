package repositorio;
import java.util.List;

//padronizaçao dos metodos dos repositorios, para facilitar a manutencao e a evolucao do sistema
public interface CRUDInterface<T> {
	public void criar(T obj);
	public T localizar(int id);
	public T atualizar(T obj);
	public void deletar(T obj) ;
	public List<T> listar();
	public void begin();
	public void commit();
	public void rollback();
}