# Backend API - Sistema de Matrículas

## 📋 Estado del Proyecto

✅ **Proyecto limpio y optimizado**  
✅ **Servidor backend corriendo**  
✅ **Base de datos configurada**  
✅ **API funcionando correctamente**

## 🚀 Ejecutar el Servidor

### Opción 1: VS Code (Recomendado)
1. Abrir VS Code en la carpeta del proyecto
2. Ctrl+Shift+D (Debug)
3. Seleccionar "Iniciar Servidor Backend"
4. Presionar F5

### Opción 2: Script Batch
```bash
.\run-simple.bat
```

## 📡 API Endpoint

### Login
- **URL**: `http://localhost:8080/backend-api/api/login`
- **Método**: `POST`
- **Content-Type**: `application/x-www-form-urlencoded`

#### Credenciales de Prueba
- `admin@correo.com` / `123456` ✅
- `student@test.com` / `1234` ✅  
- `teacher@test.com` / `1234` ✅

#### Ejemplo de Petición
```bash
curl -X POST -d "correo=admin@correo.com&clave=123456" http://localhost:8080/backend-api/api/login
```

#### Respuesta Exitosa
```json
{
  "estado": "exito",
  "mensaje": "Bienvenido Admin Inicial",
  "usuario": {
    "id": 1,
    "nombre": "Admin Inicial",
    "correo": "admin@correo.com",
    "clave": "123456"
  }
}
```

## 📁 Estructura del Proyecto

```
backend-api/
├── .vscode/                 # Configuración VS Code
├── src/main/java/          # Código fuente
│   ├── SimpleServer.java   # Servidor HTTP principal
│   ├── config/            # Configuración de BD
│   ├── controlador/       # Controladores API
│   ├── dao/              # Data Access Objects
│   └── modelo/           # Modelos de datos
├── pom.xml                # Configuración Maven
├── run-simple.bat         # Script para iniciar servidor
├── setup-final.bat        # Script para configurar BD
├── README-THUNDERCLIENT.md # Guía para Thunder Client
├── README-VSCODE.md       # Guía para VS Code
└── thunder-client-tests.json # Tests para importar
```

## 🛠️ Configuración

### Base de Datos
- **Motor**: MySQL 9.4
- **Base**: matriculas
- **Usuario**: root
- **Contraseña**: 1234

### Dependencias Principales
- Java 17
- MySQL Connector 8.3.0
- Gson 2.10.1
- Maven 3.8.0

## ✅ Verificación

Para verificar que todo funciona correctamente:

1. **Iniciar servidor**: `.\run-simple.bat`
2. **Probar login**: 
   ```bash
   curl -X POST -d "correo=admin@correo.com&clave=123456" http://localhost:8080/backend-api/api/login
   ```
3. **Ver respuesta**: Debe retornar `{"estado":"exito",...}`

## 🧪 Pruebas en Thunder Client

Importa el archivo `thunder-client-tests.json` en Thunder Client para tener todos los casos de prueba listos para usar.

## 📝 Notas

- El servidor se ejecuta en el puerto 8080
- Los logs se muestran en tiempo real
- La API soporta CORS para peticiones desde cualquier origen
- El servidor maneja automáticamente errores de conexión a BD
