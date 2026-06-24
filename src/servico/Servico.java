package servico;

import repositorio.db4o.Repositorio;
import repositorio.db4o.RepositorioUsuario;

public class Servico {
	protected Servico() {}

	protected static RepositorioUsuario repUsuario = new RepositorioUsuario();

	
	public static void conectar() throws Exception {
		Repositorio.conectar();
	}

	public static void desconectar()throws Exception {
		Repositorio.desconectar();
	}

}
