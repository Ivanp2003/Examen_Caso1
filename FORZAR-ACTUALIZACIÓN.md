# 🚨 FORZAR ACTUALIZACIÓN EN RENDER

## ❌ **Problema**
Render sigue usando la configuración antigua con Java nativo en lugar del Docker actualizado.

## ✅ **Solución Implementada**

### 1. **Cambié el nombre del servicio:**
- Antes: `backend-api`
- Ahora: `backend-api-v2`
- Esto fuerza a Render a crear un nuevo servicio

### 2. **Agregué variable faltante:**
- `DB_TYPE=postgresql` (crucial para la conexión)

## 🚀 **Pasos Inmediatos:**

### **1. Subir cambios forzados:**
```bash
git add .
git commit -m "FORCE UPDATE: backend-api-v2 con Docker"
git push origin main
```

### **2. En el Dashboard de Render:**

#### **Opción A: Esperar auto-detección**
- Render debería detectar el nuevo `backend-api-v2`
- Creará un nuevo servicio con Docker
- El antiguo `backend-api` puede ser eliminado

#### **Opción B: Manual (si no funciona)**
1. **Eliminar el servicio antiguo:**
   - Dashboard → `backend-api` → Settings → Delete Service

2. **Crear nuevo servicio manualmente:**
   - New → Web Service
   - Conectar tu repo
   - Environment: Docker
   - Dockerfile path: `./Dockerfile.render`

### **3. Configurar variables manualmente (si es necesario):**
```env
DB_TYPE=postgresql
DB_HOST=tu-host-render
DB_PORT=5432
DB_NAME=matriculas
DB_USER=admin
DB_PASSWORD=tu-contraseña
APP_ENVIRONMENT=production
SERVER_PORT=8080
CORS_ALLOWED_ORIGINS=*
```

## 🎯 **Verificación:**

### **Logs del build deberían mostrar:**
```
=> Building Dockerfile.render
=> Step 1/8 : FROM openjdk:17-jdk-slim
=> Step 2/8 : RUN apt-get update && apt-get install -y maven
=> Step 3/8 : WORKDIR /app
...
=> Successfully built xxxxxxx
=> Successfully tagged backend-api-v2:latest
```

### **NO deberían mostrar:**
```
bash: line 1: mvn: command not found
```

## 🌐 **URL Final:**
Si todo funciona, tu API estará en:
```
https://backend-api-v2.onrender.com/backend-api/api/login
```

## 🔄 **Si aún falla:**

### **Plan B - Eliminar todo y empezar de cero:**
1. Eliminar el servicio `backend-api-v2`
2. Eliminar la base de datos `backend-db`
3. Crear nuevos servicios desde cero
4. Usar configuración manual

### **Plan C - Alternativa sin render.yaml:**
1. Eliminar `render.yaml`
2. Crear servicio web manualmente
3. Configurar todo en el dashboard de Render

## 📞 **Contacto si persiste el problema:**
- Revisa que el `Dockerfile.render` esté en el repo
- Verifica que no haya errores de sintaxis en `render.yaml`
- Confirma que el repo esté actualizado

**Sube los cambios y Render debería crear el nuevo servicio con Docker correctamente!** 🚀
