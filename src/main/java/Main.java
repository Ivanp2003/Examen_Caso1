import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando servidor Tomcat embebido...");
        
        // Crear instancia de Tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setBaseDir(".");
        
        // Configurar el directorio base
        String webappDirLocation = "src/main/webapp/";
        
        // Configurar el contexto
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/backend-api", 
            new File(webappDirLocation).getAbsolutePath());
        
        // Declarar la ubicación de las clases compiladas
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
            additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        
        // Configurar para evitar problemas con anotaciones
        ctx.setJarScanner(new org.apache.tomcat.util.scan.StandardJarScanner());
        
        // Iniciar Tomcat
        tomcat.start();
        System.out.println("✅ Servidor Tomcat iniciado exitosamente");
        System.out.println("🌐 API disponible en: http://localhost:8080/backend-api/api/login");
        System.out.println("📝 Endpoint de login: POST http://localhost:8080/backend-api/api/login");
        System.out.println("⏹️  Presiona Ctrl+C para detener el servidor");
        System.out.println();
        
        // Mantener el servidor corriendo
        tomcat.getServer().await();
    }
}
