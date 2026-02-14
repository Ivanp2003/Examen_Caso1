import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import dao.UsuarioDAO;
import dao.EstudianteDAO;
import dao.MateriaDAO;
import dao.MatriculaDAO;
import modelo.Usuario;
import modelo.Estudiante;
import modelo.Materia;
import modelo.Matricula;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
         // Obtener el puerto dinámico de Render, o usar 8080 para local
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8080;

        System.out.println("🚀 Iniciando servidor HTTP en puerto " + port + "...");

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Configurar endpoints
        server.createContext("/backend-api/api/login", new LoginHandler());
        server.createContext("/backend-api/api/estudiantes", new EstudiantesHandler());
        server.createContext("/backend-api/api/materias", new MateriasHandler());
        server.createContext("/backend-api/api/matriculas", new MatriculasHandler());
        
        // Iniciar servidor
        server.setExecutor(null); // usa el executor por defecto
        server.start();
        
        System.out.println("✅ Servidor iniciado exitosamente");
        System.out.println("🌐 Endpoints disponibles:");
        System.out.println("   POST   http://localhost:8080/backend-api/api/login");
        System.out.println("   GET    http://localhost:8080/backend-api/api/estudiantes");
        System.out.println("   POST   http://localhost:8080/backend-api/api/estudiantes");
        System.out.println("   GET    http://localhost:8080/backend-api/api/materias");
        System.out.println("   POST   http://localhost:8080/backend-api/api/materias");
        System.out.println("   GET    http://localhost:8080/backend-api/api/matriculas");
        System.out.println("   POST   http://localhost:8080/backend-api/api/matriculas");
        System.out.println("⏹️  Presiona Ctrl+C para detener el servidor");
        System.out.println();
    }
    
    static class LoginHandler implements HttpHandler {
        private UsuarioDAO usuarioDAO = new UsuarioDAO();
        private Gson gson = new Gson();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("📥 Petición recibida: " + exchange.getRequestMethod() + " " + exchange.getRequestURI());
            
            // Solo permitir POST
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                sendResponse(exchange, 405, "Método no permitido");
                return;
            }
            
            // Configurar headers CORS
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            
            // Manejar preflight OPTIONS
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            
            try {
                // Leer el cuerpo de la petición
                String body = new String(exchange.getRequestBody().readAllBytes());
                System.out.println("📄 Cuerpo de la petición: " + body);
                
                // Parsear parámetros form-data
                Map<String, String> params = parseFormData(body);
                String correo = params.get("correo");
                String clave = params.get("clave");
                
                System.out.println("🔍 Intentando login con correo: " + correo);
                
                // Validar usuario
                Usuario usuarioValidado = usuarioDAO.validar(correo, clave);
                
                Map<String, Object> respuesta = new HashMap<>();
                
                if (usuarioValidado.obtenerId() > 0) {
                    respuesta.put("estado", "exito");
                    respuesta.put("mensaje", "Bienvenido " + usuarioValidado.obtenerNombre());
                    respuesta.put("usuario", usuarioValidado);
                    System.out.println("✅ Login exitoso para: " + usuarioValidado.obtenerNombre());
                } else {
                    respuesta.put("estado", "error");
                    respuesta.put("mensaje", "Usuario o contraseña incorrectos.");
                    System.out.println("❌ Login fallido para: " + correo);
                }
                
                // Enviar respuesta
                String jsonResponse = gson.toJson(respuesta);
                sendResponse(exchange, 200, jsonResponse);
                
            } catch (Exception e) {
                System.err.println("❌ Error en el servidor: " + e.getMessage());
                e.printStackTrace();
                
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("estado", "error");
                errorResponse.put("mensaje", "Error interno del servidor");
                
                String jsonResponse = gson.toJson(errorResponse);
                sendResponse(exchange, 500, jsonResponse);
            }
        }
        
        private Map<String, String> parseFormData(String body) {
            Map<String, String> params = new HashMap<>();
            if (body != null && !body.isEmpty()) {
                String[] pairs = body.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=", 2);
                    if (keyValue.length == 2) {
                        try {
                            String key = java.net.URLDecoder.decode(keyValue[0], "UTF-8");
                            String value = java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                            params.put(key, value);
                        } catch (Exception e) {
                            // Ignorar errores de decodificación
                        }
                    }
                }
            }
            return params;
        }
        
        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    // ====================== HANDLER PARA ESTUDIANTES ======================
    static class EstudiantesHandler implements HttpHandler {
        private EstudianteDAO estudianteDAO = new EstudianteDAO();
        private Gson gson = new Gson();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("📥 Petición recibida: " + exchange.getRequestMethod() + " " + exchange.getRequestURI());
            
            // Configurar headers CORS
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            
            // Manejar preflight OPTIONS
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            
            try {
                if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    // Listar estudiantes
                    var lista = estudianteDAO.listar();
                    String response = gson.toJson(lista);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Estudiantes listados: " + lista.size());
                } else if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                    // Crear estudiante
                    String body = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> params = parseFormData(body);
                    
                    Estudiante est = new Estudiante();
                    est.asignarCedula(params.get("cedula"));
                    est.asignarNombre(params.get("nombre"));
                    est.asignarApellido(params.get("apellido"));
                    est.asignarCorreo(params.get("correo"));
                    
                    int resultado = estudianteDAO.agregar(est);
                    
                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("exito", resultado > 0);
                    respuesta.put("mensaje", resultado > 0 ? "Estudiante creado" : "Error al crear");
                    
                    String response = gson.toJson(respuesta);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Estudiante creado: " + est.obtenerNombre());
                } else if (exchange.getRequestMethod().equalsIgnoreCase("PUT")) {
                    // Actualizar estudiante
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");
                    int id = Integer.parseInt(parts[parts.length - 1]);

                    String body = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> params = parseFormData(body);

                    Estudiante est = new Estudiante();
                    est.asignarId(id);
                    est.asignarCedula(params.get("cedula"));
                    est.asignarNombre(params.get("nombre"));
                    est.asignarApellido(params.get("apellido"));
                    est.asignarCorreo(params.get("correo"));

                    int resultado = estudianteDAO.actualizar(est);

                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("exito", resultado > 0);
                    respuesta.put("mensaje", resultado > 0 ? "Estudiante actualizado" : "Error al actualizar");

                    String response = gson.toJson(respuesta);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Estudiante actualizado: ID " + id);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("DELETE")) {
                    // Eliminar estudiante
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");
                    int id = Integer.parseInt(parts[parts.length - 1]);
                    
                    int resultado = estudianteDAO.eliminar(id);
                    
                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("exito", resultado > 0);
                    respuesta.put("mensaje", resultado > 0 ? "Estudiante eliminado" : "Error al eliminar");
                    
                    String response = gson.toJson(respuesta);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Estudiante eliminado: ID " + id);
                } else {
                    sendResponse(exchange, 405, "{\"error\": \"Método no permitido\"}");
                }
            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Error interno del servidor");
                sendResponse(exchange, 500, gson.toJson(error));
            }
        }
        
        private Map<String, String> parseFormData(String body) {
            Map<String, String> params = new HashMap<>();
            if (body != null && !body.isEmpty()) {
                String[] pairs = body.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=", 2);
                    if (keyValue.length == 2) {
                        try {
                            String key = java.net.URLDecoder.decode(keyValue[0], "UTF-8");
                            String value = java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                            params.put(key, value);
                        } catch (Exception e) {
                            // Ignorar errores
                        }
                    }
                }
            }
            return params;
        }
        
        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    // ====================== HANDLER PARA MATERIAS ======================
    static class MateriasHandler implements HttpHandler {
        private MateriaDAO materiaDAO = new MateriaDAO();
        private Gson gson = new Gson();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("📥 Petición recibida: " + exchange.getRequestMethod() + " " + exchange.getRequestURI());
            
            // Configurar headers CORS
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            
            // Manejar preflight OPTIONS
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            
            try {
                if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    var lista = materiaDAO.listar();
                    String response = gson.toJson(lista);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Materias listadas: " + lista.size());
                } else if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                    String body = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> params = parseFormData(body);
                    
                    Materia mat = new Materia();
                    mat.asignarCodigo(params.get("codigo"));
                    mat.asignarNombre(params.get("nombre"));
                    mat.asignarCreditos(Integer.parseInt(params.getOrDefault("creditos", "0")));
                    
                    int resultado = materiaDAO.agregar(mat);
                    
                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("exito", resultado > 0);
                    respuesta.put("mensaje", resultado > 0 ? "Materia creada" : "Error al crear");
                    
                    String response = gson.toJson(respuesta);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Materia creada: " + mat.obtenerNombre());
                } else if (exchange.getRequestMethod().equalsIgnoreCase("DELETE")) {
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");
                    int id = Integer.parseInt(parts[parts.length - 1]);
                    
                    int resultado = materiaDAO.eliminar(id);
                    
                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("exito", resultado > 0);
                    respuesta.put("mensaje", resultado > 0 ? "Materia eliminada" : "Error al eliminar");
                    
                    String response = gson.toJson(respuesta);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Materia eliminada: ID " + id);
                } else {
                    sendResponse(exchange, 405, "{\"error\": \"Método no permitido\"}");
                }
            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
                e.printStackTrace();
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Error interno del servidor");
                sendResponse(exchange, 500, gson.toJson(error));
            }
        }
        
        private Map<String, String> parseFormData(String body) {
            Map<String, String> params = new HashMap<>();
            if (body != null && !body.isEmpty()) {
                String[] pairs = body.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=", 2);
                    if (keyValue.length == 2) {
                        try {
                            String key = java.net.URLDecoder.decode(keyValue[0], "UTF-8");
                            String value = java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                            params.put(key, value);
                        } catch (Exception e) {
                            // Ignorar errores
                        }
                    }
                }
            }
            return params;
        }
        
        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    // ====================== HANDLER PARA MATRÍCULAS ======================
    static class MatriculasHandler implements HttpHandler {
        private MatriculaDAO matriculaDAO = new MatriculaDAO();
        private Gson gson = new Gson();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("📥 Petición recibida: " + exchange.getRequestMethod() + " " + exchange.getRequestURI());
            
            // Configurar headers CORS
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            
            // Manejar preflight OPTIONS
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }
            
            try {
                if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    var lista = matriculaDAO.listarMatriculas();
                    String response = gson.toJson(lista);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Matrículas listadas: " + lista.size());
                } else if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                    String body = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> params = parseFormData(body);
                    
                    int idEstudiante = Integer.parseInt(params.getOrDefault("id_estudiante", "0"));
                    int idMateria = Integer.parseInt(params.getOrDefault("id_materia", "0"));
                    
                    int resultado = matriculaDAO.matricular(idEstudiante, idMateria);
                    
                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("exito", resultado > 0);
                    respuesta.put("mensaje", resultado > 0 ? "Matrícula creada" : "Error al crear");
                    
                    String response = gson.toJson(respuesta);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Matrícula creada");
                } else if (exchange.getRequestMethod().equalsIgnoreCase("PUT")) {
                    // Actualizar matrícula por id en la ruta
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");
                    int id = Integer.parseInt(parts[parts.length - 1]);

                    String body = new String(exchange.getRequestBody().readAllBytes());
                    Map<String, String> params = parseFormData(body);

                    int idEstudiante = Integer.parseInt(params.getOrDefault("id_estudiante", "0"));
                    int idMateria = Integer.parseInt(params.getOrDefault("id_materia", "0"));

                    int resultado = matriculaDAO.actualizar(id, idEstudiante, idMateria);

                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("exito", resultado > 0);
                    respuesta.put("mensaje", resultado > 0 ? "Matrícula actualizada" : "Error al actualizar");

                    String response = gson.toJson(respuesta);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Matrícula actualizada: ID " + id);
                } else if (exchange.getRequestMethod().equalsIgnoreCase("DELETE")) {
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");
                    int id = Integer.parseInt(parts[parts.length - 1]);
                    
                    int resultado = matriculaDAO.eliminar(id);
                    
                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("exito", resultado > 0);
                    respuesta.put("mensaje", resultado > 0 ? "Matrícula eliminada" : "Error al eliminar");
                    
                    String response = gson.toJson(respuesta);
                    sendResponse(exchange, 200, response);
                    System.out.println("✅ Matrícula eliminada: ID " + id);
                } else {
                    sendResponse(exchange, 405, "{\"error\": \"Método no permitido\"}");
                }
            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
                e.printStackTrace();
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Error interno del servidor");
                sendResponse(exchange, 500, gson.toJson(error));
            }
        }
        
        private Map<String, String> parseFormData(String body) {
            Map<String, String> params = new HashMap<>();
            if (body != null && !body.isEmpty()) {
                String[] pairs = body.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=", 2);
                    if (keyValue.length == 2) {
                        try {
                            String key = java.net.URLDecoder.decode(keyValue[0], "UTF-8");
                            String value = java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                            params.put(key, value);
                        } catch (Exception e) {
                            // Ignorar errores
                        }
                    }
                }
            }
            return params;
        }
        
        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
