package config;

import java.sql.Connection;
import java.sql.SQLException;

public class Conexion {
    
    public static Connection obtenerConexion() {
        // Usar la nueva configuración que soporta múltiples bases de datos
        return DatabaseConfig.obtenerConexion();
    }
}
