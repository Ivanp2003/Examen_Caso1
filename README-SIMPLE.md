# Backend API - Sistema de Matrículas

## 🚀 Inicio Rápido

### 1. Configurar Base de Datos
```bash
.\setup-final.bat
```

### 2. Iniciar Servidor
```bash
.\run-simple.bat
```

### 3. Probar API
```bash
curl -X POST -d "correo=admin@correo.com&clave=123456" http://localhost:8080/backend-api/api/login
```

## 📡 Endpoint

### Login
- **URL**: `http://localhost:8080/backend-api/api/login`
- **Método**: `POST`
- **Body**: `correo=admin@correo.com&clave=123456`

### Credenciales de Prueba
- `admin@correo.com` / `123456` ✅
- `student@test.com` / `1234` ✅
- `teacher@test.com` / `1234` ✅

## 🛠️ Desarrollo en VS Code

1. Abrir la carpeta en VS Code
2. Ctrl+Shift+D (Debug)
3. Seleccionar "Iniciar Servidor Backend"
4. Presionar F5

## 📁 Estructura del Proyecto

```
backend-api/
├── src/main/java/          # Código fuente
├── pom.xml                 # Configuración Maven
├── run-simple.bat          # Iniciar servidor
├── setup-final.bat         # Configurar BD
├── .vscode/               # Configuración VS Code
├── .env                   # Variables de entorno
├── .gitignore             # Archivos ignorados
└── thunder-client-tests.json # Tests para Thunder Client
```

## 🎯 ¿Funciona sin Docker?

**SÍ!** El proyecto funciona perfectamente sin Docker:
- Servidor HTTP integrado
- Base de datos MySQL local
- Compilación con Maven
- Ejecución directa con Java

## 📝 Notas

- El servidor corre en el puerto 8080
- Base de datos MySQL en puerto 3306
- No requiere Docker ni Tomcat
- Configuración lista para desarrollo y producción
