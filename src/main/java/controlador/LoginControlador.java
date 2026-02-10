package controlador;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import modelo.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/login")
public class LoginControlador extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configuraciones de respuesta (JSON y CORS para React)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); // Permite que React se conecte

        // Mapa para la respuesta final
        Map<String, Object> respuestaJson = new HashMap<>();
        
        // Obtenemos los parámetros del formulario de React
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");

        // Validamos usando el DAO
        Usuario usuarioValidado = usuarioDAO.validar(correo, clave);

        if (usuarioValidado.obtenerId() > 0) {
            respuestaJson.put("estado", "exito");
            respuestaJson.put("mensaje", "Bienvenido " + usuarioValidado.obtenerNombre());
            respuestaJson.put("usuario", usuarioValidado);
        } else {
            respuestaJson.put("estado", "error");
            respuestaJson.put("mensaje", "Usuario o contraseña incorrectos.");
        }

        // Enviamos la respuesta convertida a texto JSON
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(respuestaJson));
        out.flush();
    }
}