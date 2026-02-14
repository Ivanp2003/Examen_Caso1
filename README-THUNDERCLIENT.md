# Guía de Pruebas con Thunder Client

## Configuración del Servidor

1. **Iniciar MySQL**: Asegúrate de que MySQL esté corriendo en `localhost:3306`
2. **Configurar Base de Datos**: Ejecuta el script `database-setup.sql` en MySQL
3. **Iniciar Servidor Web**: Despliega el archivo `target/backend-api.war` en un servidor Tomcat en el puerto 8080

## Endpoints de Prueba

### Login API
- **URL**: `http://localhost:8080/backend-api/api/login`
- **Método**: POST
- **Content-Type**: application/x-www-form-urlencoded

## Casos de Prueba

### 1. Login Exitoso
```http
POST http://localhost:8080/backend-api/api/login
Content-Type: application/x-www-form-urlencoded

correo=admin@test.com&clave=1234
```

**Respuesta Esperada**:
```json
{
  "estado": "exito",
  "mensaje": "Bienvenido Administrador",
  "usuario": {
    "id": 1,
    "nombre": "Administrador",
    "correo": "admin@test.com",
    "clave": "1234"
  }
}
```

### 2. Login Fallido
```http
POST http://localhost:8080/backend-api/api/login
Content-Type: application/x-www-form-urlencoded

correo=invalid@test.com&clave=wrongpassword
```

**Respuesta Esperada**:
```json
{
  "estado": "error",
  "mensaje": "Usuario o contraseña incorrectos."
}
```

## Pasos para Probar en Thunder Client

1. **Importar Tests**: Importa el archivo `thunder-client-tests.json` en Thunder Client
2. **Configurar Variables**: Asegúrate de que las variables de entorno estén configuradas
3. **Ejecutar Tests**: Corre la colección de pruebas
4. **Verificar Resultados**: Revisa que todos los tests pasen correctamente

## Requisitos Previos

- ✅ MySQL Server corriendo en localhost:3306
- ✅ Base de datos `matriculas` creada
- ✅ Tabla `usuarios` con datos de prueba
- ✅ Servidor Tomcat corriendo en puerto 8080
- ✅ Aplicación desplegada: `backend-api.war`

## Troubleshooting

### Error de Conexión a BD
- Verifica que MySQL esté corriendo
- Confirma las credenciales en `config/Conexion.java`
- Asegúrate que la BD `matriculas` exista

### Error 404
- Verifica que el WAR esté desplegado correctamente
- Confirma el contexto: `/backend-api`
- Revisa el mapping del servlet: `/api/login`

### Error CORS
- El controlador ya incluye headers CORS
- Si persiste, verifica configuración del servidor
