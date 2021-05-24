package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

//classe responsavel por fazer um insert
public class UserPosDAO {

	private Connection connection;

	public UserPosDAO() {
		// chama o metodo que faz a conexão com o banco
		connection = SingleConnection.getConnection();
	}

	// metodo que salva os dados informados
	// construindo o sql com set informados, resalva que no ba nco o campo id foi
	// criado como Sequencial
	public void salvar(Userposjava userposJava) {
		try {
			String sql = "insert into userposjava (nome, email) values (? ,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			// recupera de dentro do objeto os dados passando para as posições corretas
			insert.setString(1, userposJava.getNome());
			insert.setString(2, userposJava.getEmail());
			insert.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void salvarTelefone(Telefone telefone) {
		try {
			String sql = "INSERT INTO telefoneuser (numero, tipo, usuariopessoa) VALUES (?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	// metodo que retorna uma Lista
	public List<Userposjava> listar() throws Exception {
		// instancia a lista
		List<Userposjava> list = new ArrayList<Userposjava>();
		// montado o sql
		String sql = "Select * from userposjava";
		// passamos o sql para o statement
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		// enquanto for true
		while (resultado.next()) {
			// cria novos objetos
			Userposjava userposJava = new Userposjava();
			// setar os objetos que desejos
			userposJava.setId(resultado.getLong("id"));
			userposJava.setNome(resultado.getString("nome"));
			userposJava.setEmail(resultado.getString("email"));

			// e adicionar em uma lista
			list.add(userposJava);

		}
		// ou retorna com resulatdo ou retorna vazia
		return list;

	}

	public Userposjava buscar(Long id) throws Exception {
		Userposjava retorno = new Userposjava();

		String sql = "Select * from userposjava where id = " + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {

			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));

		}

		return retorno;

	}

	// Consulta com Inner Join e JDBC no Java
	public List<BeanUserFone> listaUserFone(Long idUser) {

		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();

		String sql = "select nome, numero, email from telefoneuser as fone";
		sql += " inner join userposjava as userp ";
		sql += " on fone.usuariopessoa = userp.id ";
		sql += " where userp.id = " + idUser;

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();

				userFone.setEmail(resultSet.getString("nome"));
				userFone.setNome(resultSet.getString("numero"));
				userFone.setNumero(resultSet.getString("email"));

				beanUserFones.add(userFone);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return beanUserFones;
	}

	public void atualizar(Userposjava userposJava) {

		try {
			String sql = "update userposjava set nome = ? where id = " + userposJava.getId();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userposJava.getNome());

			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void deletar(long id) {
		try {
			String sql = "delete from userposjava where id = " + id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Exclusão de dados em cascata de filhos e pais
	public void deletarFonesUser(long idUser) {
		try {
			String sqlFone = "delete from telefoneuser where usuariopessoa = " + idUser;
			String sqlUser = "delete from userposjava where id = " + idUser;

			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();

			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
