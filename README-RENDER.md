# 🚀 Despliegue en Render + Base de Datos en la Nube

## 📋 Paso a Paso - Render + PostgreSQL

### 🎯 **Paso 1: Preparar el Repositorio**

1. **Subir tu código a GitHub**
   ```bash
   git init
   git add .
   git commit -m "Backend API listo para Render"
   git branch -M main
   git remote add origin https://github.com/tu-usuario/backend-api.git
   git push -u origin main
   ```

### 🎯 **Paso 2: Crear Cuenta en Render**

1. Ve a [render.com](https://render.com)
2. Regístrate con tu cuenta de GitHub
3. Conecta tu repositorio

### 🎯 **Paso 3: Crear Base de Datos PostgreSQL**

1. **Dashboard → New → PostgreSQL**
2. **Configuración:**
   - Name: `backend-db`
   - Database: `matriculas`
   - User: `admin`
   - Plan: Free
   - Region: La más cercana a ti

3. **Una vez creada, copia las credenciales:**
   - Host
   - Port
   - Database Name
   - User
   - Password

### 🎯 **Paso 4: Crear Servicio Web**

1. **Dashboard → New → Web Service**
2. **Conecta tu repositorio GitHub**
3. **Configuración:**
   - Name: `backend-api`
   - Environment: `Java`
   - Plan: Free
   - Build Command: `mvn clean package -DskipTests`
   - Start Command: `java -jar target/backend-api.war`

4. **Variables de Entorno (Environment Variables):**
   ```env
   DB_TYPE=postgresql
   DB_HOST=tu-host-de-render
   DB_PORT=5432
   DB_NAME=matriculas
   DB_USER=admin
   DB_PASSWORD=tu-contraseña
   APP_ENVIRONMENT=production
   SERVER_PORT=8080
   CORS_ALLOWED_ORIGINS=*
   ```

### 🎯 **Paso 5: Despliegue Automático**

1. **Render detectará automáticamente tu `render.yaml`**
2. **Creará la base de datos y el servicio web**
3. **El despliegue tomará 5-10 minutos**
4. **Verifica el estado en el dashboard**

### 🎯 **Paso 6: Probar la API**

Una vez desplegado, tu API estará disponible en:
```
https://tu-app-name.onrender.com/backend-api/api/login
```

**Prueba con curl:**
```bash
curl -X POST \
  -d "correo=admin@test.com&clave=1234" \
  https://tu-app-name.onrender.com/backend-api/api/login
```

## 🌐 **Alternativas de Base de Datos en la Nube**

### **Opción A: PlanetScale (MySQL)**
1. Regístrate en [planetscale.com](https://planetscale.com)
2. Crea una base de datos
3. Obtén las credenciales
4. Usa `DB_TYPE=mysql`

### **Opción B: Railway (MySQL)**
1. Regístrate en [railway.app](https://railway.app)
2. Crea un proyecto MySQL
3. Obtén las credenciales
4. Usa `DB_TYPE=mysql`

### **Opción C: Supabase (PostgreSQL)**
1. Regístrate en [supabase.com](https://supabase.com)
2. Crea un proyecto
3. Obtén las credenciales
4. Usa `DB_TYPE=postgresql`

## 🔧 **Configuración de Variables de Entorno**

### **Para Render PostgreSQL:**
```env
DB_TYPE=postgresql
DB_HOST=your-db-host.render.com
DB_PORT=5432
DB_NAME=matriculas
DB_USER=admin
DB_PASSWORD=your-password
```

### **Para PlanetScale MySQL:**
```env
DB_TYPE=mysql
DB_HOST=your-host.planetscale.com
DB_PORT=3306
DB_NAME=matriculas
DB_USER=your-user
DB_PASSWORD=your-password
```

## 🚨 **Troubleshooting**

### **Error de conexión:**
- Verifica las credenciales de la base de datos
- Confirma que el tipo de DB es correcto (mysql/postgresql)
- Revisa las reglas de firewall de la base de datos

### **Error de build:**
- Verifica que `pom.xml` tenga las dependencias correctas
- Confirma que Java 17 esté disponible en Render

### **Error 404:**
- Verifica el path del endpoint
- Confirma el contexto `/backend-api`

## 📊 **Monitoreo en Render**

1. **Dashboard → Logs** para ver errores
2. **Metrics** para monitorear rendimiento
3. **Events** para ver historial de despliegues

## 💰 **Costos**

- **Render Free Tier:** $0/mes (750 horas/mes)
- **PostgreSQL Free:** $0/mes (hasta 256MB)
- **PlanetScale Free:** $0/mes (hasta 5GB)
- **Railway Free:** $0/mes ($5 créditos/mes)

## 🎉 **¡Listo!**

Tu backend API estará desplegado y funcionando 24/7 en la nube con base de datos gestionada.
