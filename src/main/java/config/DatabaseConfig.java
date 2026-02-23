package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    
    // Configuración desde variables de entorno
    private static final String DB_HOST = getEnv("DB_HOST", "127.0.0.1");
    private static final String DB_PORT = getEnv("DB_PORT", "3306");
    private static final String DB_NAME = getEnv("DB_NAME", "matriculas");
    private static final String DB_USER = getEnv("DB_USER", "root");
    private static final String DB_PASSWORD = getEnv("DB_PASSWORD", "1234");
    
    // Determinar el driver según el tipo de base de datos
    private static final String DB_TYPE = getEnv("DB_TYPE", "mysql");
    
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            String url = buildJdbcUrl();
            String driver = getDriverClass();
            
            // Cargar el driver apropiado
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos (" + DB_TYPE + ")");
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Error en la conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
    
    private static String buildJdbcUrl() {
        if ("postgresql".equalsIgnoreCase(DB_TYPE)) {
            return String.format("jdbc:postgresql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
        } else {
            // MySQL por defecto
            return String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
        }
    }
    
    private static String getDriverClass() {
        if ("postgresql".equalsIgnoreCase(DB_TYPE)) {
            return "org.postgresql.Driver";
        } else {
            return "com.mysql.cj.jdbc.Driver";
        }
    }
    
    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null) ? value : defaultValue;
    }
    
    // Método para crear la tabla si no existe (para PostgreSQL)
    public static void inicializarBaseDatos() {
        try (Connection conexion = obtenerConexion()) {
            if (conexion != null) {
                String createTableSQL;
                
                if ("postgresql".equalsIgnoreCase(DB_TYPE)) {
                    createTableSQL = """
                        CREATE TABLE IF NOT EXISTS usuarios (
                            id SERIAL PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            email VARCHAR(100) UNIQUE NOT NULL,
                            clave VARCHAR(100) NOT NULL,
                            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        );
                        
                        INSERT INTO usuarios (nombre, email, clave) VALUES 
                        ('Administrador', 'admin@test.com', '1234'),
                        ('Estudiante Test', 'student@test.com', '1234'),
                        ('Profesor Test', 'teacher@test.com', '1234')
                        ON CONFLICT (email) DO UPDATE SET 
                        nombre = EXCLUDED.nombre, 
                        clave = EXCLUDED.clave;
                        """;
                } else {
                    createTableSQL = """
                        CREATE TABLE IF NOT EXISTS usuarios (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            email VARCHAR(100) UNIQUE NOT NULL,
                            clave VARCHAR(100) NOT NULL,
                            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        );
                        
                        INSERT INTO usuarios (nombre, email, clave) VALUES 
                        ('Administrador', 'admin@test.com', '1234'),
                        ('Estudiante Test', 'student@test.com', '1234'),
                        ('Profesor Test', 'teacher@test.com', '1234')
                        ON DUPLICATE KEY UPDATE 
                        nombre = VALUES(nombre), 
                        clave = VALUES(clave);
                        """;
                }
                
                try (var statement = conexion.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("✅ Base de datos inicializada correctamente");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}
