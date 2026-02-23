# 🚀 GUÍA RÁPIDA - Despliegue en Render

## 📋 PASOS INMEDIATOS

### 1️⃣ **Subir a GitHub**
```bash
git add .
git commit -m "Backend listo para Render"
git push origin main
```

### 2️⃣ **Crear Cuenta Render**
- Ve a [render.com](https://render.com)
- Regístrate con GitHub

### 3️⃣ **Crear Base de Datos**
- Dashboard → New → PostgreSQL
- Name: `backend-db`
- Database: `matriculas`
- Plan: Free

### 4️⃣ **Crear Web Service**
- Dashboard → New → Web Service
- Conecta tu repo GitHub
- Build: `mvn clean package -DskipTests`
- Start: `java -jar target/backend-api.war`

### 5️⃣ **Configurar Variables**
```env
DB_TYPE=postgresql
DB_HOST=tu-host-de-render
DB_PORT=5432
DB_NAME=matriculas
DB_USER=admin
DB_PASSWORD=tu-contraseña
APP_ENVIRONMENT=production
```

### 6️⃣ **Probar**
```
https://tu-app.onrender.com/backend-api/api/login
```

## 🎯 **URL Final**
Tu API estará disponible en: `https://tu-nombre-de-app.onrender.com/backend-api/api/login`

## 📞 **Soporte**
- Revisa `README-RENDER.md` para detalles completos
- Los logs están en el dashboard de Render
- La base de datos se crea automáticamente

## ✅ **¡Listo en 10 minutos!**
