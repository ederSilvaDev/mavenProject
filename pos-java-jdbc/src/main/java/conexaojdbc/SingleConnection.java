package conexaojdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	private static String url = "jdbc:postgresql://localhost:5432/posjava";
	private static String password = "supertux";
	private static String user = "postgres";
	private static Connection connection = null;

	// sempre que chamar o SingleConnection, ele vai passar pelo metodo conectar
	static {
		conectar();
	}

	// criando metodo construtor
	public SingleConnection() {
		conectar();
	}

	// criando metodo conectar
	private static void conectar() {
		try {
			if (connection == null) {// verifica se ja existe a conexão
				Class.forName("org.postgresql.Driver"); // informando driver usado
				connection = DriverManager.getConnection(url, user, password);// recebe os valores parametrizados de
																				// conexão com o banco
				connection.setAutoCommit(false);
				System.out.println("Connection Com Sucesso !");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// metodo que retorna a conexão "connection" estanciado no metodo conectar
	public static Connection getConnection() {
		return connection;

	}

}
