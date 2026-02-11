package controlador;

import com.google.gson.Gson;
import dao.EstudianteDAO;
import modelo.Estudiante;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/estudiantes")
public class EstudianteControlador extends HttpServlet {

    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    private Gson gson = new Gson();

    // GET: Para Listar (Read)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        configurarHeaders(response);
        List<Estudiante> lista = estudianteDAO.listar();
        response.getWriter().print(gson.toJson(lista));
    }

    // POST: Para Crear (Create)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        configurarHeaders(response);
        
        Estudiante est = new Estudiante();
        est.asignarCedula(request.getParameter("cedula"));
        est.asignarNombre(request.getParameter("nombre"));
        est.asignarApellido(request.getParameter("apellido"));
        est.asignarCorreo(request.getParameter("correo"));

        int resultado = estudianteDAO.agregar(est);
        response.getWriter().print("{\"exito\":" + (resultado > 0) + "}");
    }

    // Nota: Para simplificar con Servlets sin frameworks, usaremos POST con un parámetro "accion"
    // para manejar DELETE y UPDATE, o métodos convencionales si el cliente lo soporta.

    private void configurarHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
}