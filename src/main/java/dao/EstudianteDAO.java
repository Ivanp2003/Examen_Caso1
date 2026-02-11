package dao;

import config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;

public class EstudianteDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Estudiante> listar() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.asignarId(rs.getInt("id"));
                e.asignarCedula(rs.getString("cedula"));
                e.asignarNombre(rs.getString("nombre"));
                e.asignarApellido(rs.getString("apellido"));
                e.asignarCorreo(rs.getString("email"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    public int agregar(Estudiante est) {
        String sql = "INSERT INTO estudiantes(cedula, nombre, apellido, email) VALUES(?,?,?,?)";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, est.obtenerCedula());
            ps.setString(2, est.obtenerNombre());
            ps.setString(3, est.obtenerApellido());
            ps.setString(4, est.obtenerCorreo());
            return ps.executeUpdate(); // Retorna 1 si tuvo éxito
        } catch (SQLException e) {
            System.out.println("Error al agregar: " + e.getMessage());
            return 0;
        }
    }
}