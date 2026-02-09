package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Datos de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_matriculas";
    private static final String USUARIO = "root"; 
    private static final String CLAVE = "1234"; // Tu contraseña de MySQL

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Cargar el driver de MySQL (esto es lo que Maven nos ayuda a gestionar)
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }
}
