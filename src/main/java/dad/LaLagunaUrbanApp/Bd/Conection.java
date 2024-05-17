package dad.LaLagunaUrbanApp.Bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
public class Conection {

	public Connection Conection(){
		
		String username = "root";
		//String url = "jdbc:mysql://viaduct.proxy.rlwy.net:26561/railway";
        //String password = "wJAywkcwvwhbOiSwmbAIeZUulaMYljRL";
		
		String url = "jdbc:mysql://viaduct.proxy.rlwy.net:15590/railway";
        String password = "FrvldVexjBLmqdYBgyLXzNvXYJAbzYaj";
        
        
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión establecida a la base de datos MySQL en Railway");
            
          

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        
        return connection;
	}*/

public class Conection {
    public Connection obtenerConexion() {
        String username = "root";
        String url = "jdbc:mysql://viaduct.proxy.rlwy.net:15590/railway";
        String password = "FrvldVexjBLmqdYBgyLXzNvXYJAbzYaj";

        Connection connection = null;
        try {
            // Carga dinámica del controlador MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión establecida a la base de datos MySQL en Railway");
        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo cargar el controlador MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connection;
    }
}

