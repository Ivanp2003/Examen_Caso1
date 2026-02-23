import config.DatabaseConfig;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Backend API para Render...");
        
        // Inicializar base de datos (crear tablas si no existen)
        DatabaseConfig.inicializarBaseDatos();
        
        // Iniciar el servidor HTTP simple
        try {
            SimpleServer.main(args);
        } catch (Exception e) {
            System.err.println("❌ Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
