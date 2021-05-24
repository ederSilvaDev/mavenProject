package pos_java_jdbc.pos_java_jdbc;

import java.util.List;

import org.junit.Test;

import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

//teste executa 
public class testeBancojdbc {

	@Test
	public void initBanco() {
		// instanciando os objetos dos Insert
		UserPosDAO userposDAO = new UserPosDAO();
		Userposjava userposJava = new Userposjava();

		// o insert que é DAO recebe o objeto com os dados
		userposJava.setNome("deletar1");
		userposJava.setEmail("deletar1@ig.com.br");

		userposDAO.salvar(userposJava);

	}

	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();
		try {
			java.util.List<Userposjava> list = dao.listar();

			for (Userposjava userposJava : list) {
				System.out.println(userposJava);
				System.out.println("-----------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();

		try {
			Userposjava userposJava = dao.buscar(5L);

			System.out.println(userposJava);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Atualizando dados no banco de dados
	@Test
	public void initAtualizar() {
		try {
			UserPosDAO dao = new UserPosDAO();

			Userposjava objetoBanco = dao.buscar(7L);

			objetoBanco.setNome("Pedro Teodoro");

			dao.atualizar(objetoBanco);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//Deletando registro no banco de dados
	@Test
	public void initDeletar() {
		try {

			UserPosDAO dao = new UserPosDAO();
			dao.deletar(8L);

			System.out.println("Um  Itens " + " Foi Deletado com Sucesso !");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Realizando insert com JDBC e Java
	@Test
	public void testeinsertTelefone() {

		Telefone telefone = new Telefone();

		telefone.setTipo("celular olivia 2");
		telefone.setNumero("95645655");
		telefone.setUsuario(2L);

		UserPosDAO dao = new UserPosDAO();
		dao.salvarTelefone(telefone);
	}
	
	@Test
	public void testedeleteUserfone() {
		try {

			UserPosDAO dao = new UserPosDAO();
			dao.deletar(2L);

			System.out.println("Um  Itens " + " Foi Deletado com Sucesso !");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeCarregaFoneUser() {
		UserPosDAO dao = new UserPosDAO();
		try {
			//receber o retorno	
			List<BeanUserFone> beanUserFones = dao.listaUserFone(2L);

			for (BeanUserFone beanUserFone : beanUserFones) {
				System.out.println(beanUserFone);
				System.out.println("---------------------------------------------------");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
