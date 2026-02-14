package dao;

import config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Materia;

public class MateriaDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Materia> listar() {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM materias";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Materia m = new Materia();
                m.asignarId(rs.getInt("id"));
                m.asignarCodigo(rs.getString("codigo"));
                m.asignarNombre(rs.getString("nombre"));
                m.asignarCreditos(rs.getInt("creditos"));
                lista.add(m);
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }

    public int agregar(Materia mat) {
        String sql = "INSERT INTO materias(codigo, nombre, creditos) VALUES(?,?,?)";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, mat.obtenerCodigo());
            ps.setString(2, mat.obtenerNombre());
            ps.setInt(3, mat.obtenerCreditos());
            return ps.executeUpdate();
        } catch (SQLException e) { return 0; }
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM materias WHERE id=?";
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) { System.out.println(e.getMessage()); return 0; }
    }
    // (Luego podemos añadir editar y eliminar de la misma forma que estudiantes)
}