package dao;

import config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Matricula;

public class MatriculaDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Matricula> listarMatriculas() {
        List<Matricula> lista = new ArrayList<>();
        String sql = "SELECT m.id, e.nombre as estudiante, mat.nombre as materia, m.fecha_registro " +
                     "FROM matriculas m " +
                     "JOIN estudiantes e ON m.id_estudiante = e.id " +
                     "JOIN materias mat ON m.id_materia = mat.id";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Matricula m = new Matricula();
                
                m.asignarId(rs.getInt("id"));
                m.asignarNombreEstudiante(rs.getString("estudiante"));
                m.asignarNombreMateria(rs.getString("materia"));
                lista.add(m);
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }

    public int matricular(int idEst, int idMat) {
        String sql = "INSERT INTO matriculas(id_estudiante, id_materia) VALUES(?,?)";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEst);
            ps.setInt(2, idMat);
            return ps.executeUpdate();
        } catch (SQLException e) { return 0; }
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM matriculas WHERE id=?";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) { System.out.println(e.getMessage()); return 0; }
    }

    // Método para actualizar una matrícula existente
    public int actualizar(int id, int idEst, int idMat) {
        String sql = "UPDATE matriculas SET id_estudiante=?, id_materia=? WHERE id=?";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEst);
            ps.setInt(2, idMat);
            ps.setInt(3, id);
            return ps.executeUpdate();
        } catch (SQLException e) { System.out.println("Error al actualizar matrícula: " + e.getMessage()); return 0; }
    }
}