# Guía de Despliegue

## 🚀 Preparación para Despliegue

### 1. Variables de Entorno

Copia el archivo de entorno de ejemplo:
```bash
cp .env.example .env
```

Edita `.env` con tus configuraciones de producción:
```env
# Configuración de Base de Datos
DB_HOST=tu-host-de-produccion
DB_PORT=3306
DB_NAME=matriculas_prod
DB_USER=tu-usuario
DB_PASSWORD=tu-contraseña-segura

# Configuración de Producción
APP_ENVIRONMENT=production
SERVER_PORT=8080
LOG_LEVEL=WARN
CORS_ALLOWED_ORIGINS=https://tudominio.com
```

### 2. Compilar para Producción

```bash
mvn clean package -DskipTests
```

Esto generará `target/backend-api.war` listo para despliegue.

## 🐳 Despliegue con Docker

### Opción A: Usar Dockerfile (recomendado)

Crea un `Dockerfile`:
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/backend-api.war app.war

EXPOSE 8080

CMD ["java", "-jar", "app.war"]
```

Construir y ejecutar:
```bash
docker build -t backend-api .
docker run -p 8080:8080 --env-file .env backend-api
```

### Opción B: Docker Compose

Crea `docker-compose.yml`:
```yaml
version: '3.8'
services:
  backend:
    build: .
    ports:
      - "8080:8080"
    environment:
      - DB_HOST=mysql
      - DB_PASSWORD=${DB_PASSWORD}
    depends_on:
      - mysql
    env_file:
      - .env

  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_PASSWORD}
      MYSQL_DATABASE: matriculas
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

Ejecutar:
```bash
docker-compose up -d
```

## 🌐 Despliegue en Servidor Tomcat

### 1. Preparar el WAR
```bash
mvn clean package
```

### 2. Desplegar en Tomcat
- Copia `target/backend-api.war` a `$TOMCAT_HOME/webapps/`
- Configura las variables de entorno en `setenv.sh` o `setenv.bat`
- Reinicia Tomcat

### 3. Configurar Variables de Entorno en Tomcat

En `$TOMCAT_HOME/bin/setenv.sh` (Linux) o `setenv.bat` (Windows):
```bash
export DB_HOST=your-production-host
export DB_PASSWORD=your-secure-password
export APP_ENVIRONMENT=production
```

## ☁️ Despliegue en Cloud

### Heroku
```bash
# Instalar Heroku CLI
heroku create your-app-name

# Configurar variables de entorno
heroku config:set DB_HOST=your-db-host
heroku config:set DB_PASSWORD=your-db-password

# Desplegar
heroku deploy:war target/backend-api.war
```

### AWS Elastic Beanstalk
```bash
# Instalar EB CLI
eb init your-app-name
eb create production-env

# Configurar variables de entorno
eb setenv DB_HOST=your-db-host
eb setenv DB_PASSWORD=your-db-password

# Desplegar
eb deploy
```

## 🔧 Configuración de Producción

### Base de Datos
- Usar credenciales seguras
- Configurar SSL/TLS
- Realizar backups regulares

### Seguridad
- Cambiar contraseñas por defecto
- Configurar firewall
- Usar HTTPS
- Implementar rate limiting

### Monitoreo
- Configurar logs centralizados
- Monitorizar métricas de rendimiento
- Alertas para errores críticos

## 📋 Checklist de Despliegue

- [ ] Copiar `.env.example` a `.env`
- [ ] Configurar variables de entorno de producción
- [ ] Compilar proyecto con `mvn clean package`
- [ ] Probar WAR localmente
- [ ] Configurar base de datos de producción
- [ ] Desplegar en servidor/cloud
- [ ] Verificar endpoints críticos
- [ ] Configurar monitoreo y logs
- [ ] Documentar configuración para equipo

## 🚨 Variables Críticas para Producción

**Obligatorias:**
- `DB_HOST` - Host de base de datos
- `DB_PASSWORD` - Contraseña segura
- `APP_ENVIRONMENT=production`

**Recomendadas:**
- `LOG_LEVEL=WARN`
- `CORS_ALLOWED_ORIGINS` - Dominio específico
- `JWT_SECRET` - Clave secreta fuerte

## 🔄 Actualizaciones

Para actualizar el despliegue:
1. Compilar nueva versión: `mvn clean package`
2. Reemplazar `backend-api.war`
3. Reiniciar servidor
4. Verificar funcionamiento
