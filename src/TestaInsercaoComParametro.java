import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoComParametro {

	public static void main(String[] args) throws SQLException {
		
		String nome = "Smartphone'";
		String descricao = "Iphone 13";
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.recuperarConexao();
		connection.setAutoCommit(false);
		
		try {
			PreparedStatement stm = connection.prepareStatement ("INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)"
					, Statement.RETURN_GENERATED_KEYS);
			
			adicionarVariavel("SmartTV", "Sony", stm);
			adicionarVariavel("Fone", "Buds 2", stm);
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ROLLBACK EXECUTADO");
			connection.rollback();
		}
		
		

	}

	private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException {
		stm.setString(1, nome);
		stm.setString(2, descricao);
		/*
		if (nome.equals("Fone")) {
			throw new RuntimeException("Nao foi possível adicionar o produto");
		}*/
		
		stm.execute();
		
		ResultSet rst = stm.getGeneratedKeys();
		while(rst.next()) {
			Integer id = rst.getInt(1);
			System.out.println("O id criado foi: " + id);
		}
		rst.close();
	}

}
