package controlador;

import com.google.gson.Gson;
import dao.MateriaDAO;
import modelo.Materia;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/materias")
public class MateriaControlador extends HttpServlet {
    private MateriaDAO materiaDAO = new MateriaDAO();
    private Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        List<Materia> lista = materiaDAO.listar();
        response.getWriter().print(gson.toJson(lista));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        configurarHeaders(response);
        
        Materia mat = new Materia();
        mat.asignarCodigo(request.getParameter("codigo"));
        mat.asignarNombre(request.getParameter("nombre"));
        mat.asignarCreditos(Integer.parseInt(request.getParameter("creditos")));

        int resultado = materiaDAO.agregar(mat);
        response.getWriter().print("{\"exito\":" + (resultado > 0) + "}");
    }

    private void configurarHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
}