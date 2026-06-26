package servico;

import modelo_negocio.Contato;

public class ServicoContato extends Servico {
	private ServicoContato() {
		
	}
	public static void adicionarTelefoneContato(String numero, int id) throws Exception {
		try {
			repContato.begin();
			Contato c = repContato.localizar(id);
			String num = numero.replaceAll("[^0-9]", "");
			
			if (c == null) {
				throw new Exception("contato não exite: " + id);
			} else if (num.matches("^\\d{2}9\\d{8}$") && !c.getTelefones().contains(num)) {
				c.addTelefones(num, id);
			} else {
				throw new Exception("número de telefone inválido ou já existente: " + numero);
			}
			repContato.atualizar(c);
			repContato.commit();
		} catch (Exception e) {
			repContatoComercial.rollback();
			throw e;
		}
	}
	public static void apagarContato(int id) throws Exception {
		try {
			repContato.begin();
			Contato c = repContato.localizar(id);
			if (c == null) {
				throw new Exception("contato não exite: " + id);
			} c.setCidade(null);
			repContato.deletar(c);
			repContato.atualizar(c);
			repContato.commit();
		} catch (Exception e) {
			repContatoComercial.rollback();
			throw e;
		}
	}
}