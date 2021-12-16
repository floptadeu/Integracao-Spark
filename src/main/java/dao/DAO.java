package dao;

import java.sql.*;

import dao.DAO;
import model.Cars;

public class DAO {

	private Connection conexao;

	public DAO() {
		conexao = null;
	}

	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "Cars";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conex�o efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conex�o N�O efetuada com o postgres -- Driver n�o encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conex�o N�O efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;

		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	public boolean includeCar(Cars car) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO cars (year, name, manufacturer) "
					       + "VALUES ("+car.getYear()+ ", '" + car.getName() + "', '"  
					       + car.getManufacturer() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean updateCar(Cars car) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE cars SET year = '" + car.getYear() + "', name = '"  
				       + car.getName() + "', manufacturer = '" + car.getManufacturer() + "'"
					   + " WHERE code = " + car.getCode();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean deleteCar(int code) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM cars WHERE code = " + code);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public Cars[] getCar() {
		Cars[] cars = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM cars");		
	         if(rs.next()){
	             rs.last();
	             cars = new Cars[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                cars[i] = new Cars(rs.getInt("code"), rs.getInt("year"), 
	                		                  rs.getString("name"), rs.getString("manufacturer"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return cars;
	}
	
	
	public Cars get(int id) {
		for (Cars Cars : produtos) {
			if (id == Cars.getCode()) {
				return Cars;
			}
		}
		return null;
	}
}