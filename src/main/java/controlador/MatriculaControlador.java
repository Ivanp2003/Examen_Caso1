package controlador;

import com.google.gson.Gson;
import dao.MatriculaDAO;
import modelo.Matricula;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/matriculas")
public class MatriculaControlador extends HttpServlet {

    private MatriculaDAO matriculaDAO = new MatriculaDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        configurarHeaders(response);
        List<Matricula> lista = matriculaDAO.listarMatriculas();
        response.getWriter().print(gson.toJson(lista));
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    configurarHeaders(response);
    try {
        int idEst = Integer.parseInt(request.getParameter("id_estudiante"));
        int idMat = Integer.parseInt(request.getParameter("id_materia"));

        int resultado = matriculaDAO.matricular(idEst, idMat);
        
        if (resultado > 0) {
            response.getWriter().print("{\"estado\": \"exito\", \"mensaje\": \"Matrícula completada\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"estado\": \"error\", \"mensaje\": \"No se pudo realizar la matrícula\"}");
        }
    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().print("{\"estado\": \"error\", \"mensaje\": \"IDs inválidos\"}");
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().print("{\"estado\": \"error\", \"mensaje\": \"Error de servidor\"}");
    }
}

    private void configurarHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    @Override
protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    configurarHeaders(response);
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    response.setStatus(HttpServletResponse.SC_OK);
}
}