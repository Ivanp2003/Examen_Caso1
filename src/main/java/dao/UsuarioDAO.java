package dao;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

public class UsuarioDAO {
    Connection conexion;
    PreparedStatement consulta;
    ResultSet resultado;

    // Método para validar las credenciales del usuario
    public Usuario validar(String correo, String clave) {
        Usuario usuario = new Usuario();
        // Usamos email porque así lo definimos en el script de MySQL
        String sql = "SELECT * FROM usuarios WHERE email=? AND clave=?";
        
        try {
            conexion = Conexion.obtenerConexion();
            consulta = conexion.prepareStatement(sql);
            consulta.setString(1, correo);
            consulta.setString(2, clave);
            resultado = consulta.executeQuery();

            if (resultado.next()) {
                usuario.asignarId(resultado.getInt("id"));
                usuario.asignarNombre(resultado.getString("nombre"));
                usuario.asignarCorreo(resultado.getString("email"));
                usuario.asignarClave(resultado.getString("clave"));
            }
        } catch (SQLException e) {
            System.out.println("Error en UsuarioDAO -> validar: " + e.getMessage());
        } finally {
            // Es buena práctica cerrar los recursos (opcional pero recomendado)
            try { if (conexion != null) conexion.close(); } catch (SQLException e) {}
        }
        return usuario;
    }
}