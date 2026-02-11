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
}