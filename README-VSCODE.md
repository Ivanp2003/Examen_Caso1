# Ejecutar el Servidor Backend en VS Code

## 🚀 Configuración en VS Code

He configurado tu proyecto para que puedas ejecutar el servidor directamente desde VS Code.

### 📋 Pasos para Ejecutar:

#### Opción 1: Usando el Debugger (Recomendado)
1. **Abrir el proyecto en VS Code**
2. **Ir a la vista de Debug** (Ctrl+Shift+D)
3. **Seleccionar "Iniciar Servidor Backend"** en el menú desplegable
4. **Presionar F5** o hacer clic en el botón verde de play
5. **El servidor se iniciará automáticamente** en el terminal integrado

#### Opción 2: Usando Tasks
1. **Abrir la paleta de comandos** (Ctrl+Shift+P)
2. **Escribir "Tasks: Run Task"**
3. **Seleccionar "iniciar-servidor"**
4. **El servidor se iniciará** después de compilar

#### Opción 3: Terminal Integrado
1. **Abrir terminal** (Ctrl+`)
2. **Ejecutar**: `.\run-simple.bat`

### 🔧 Archivos de Configuración Creados

- `.vscode/launch.json` - Configuración del debugger
- `.vscode/tasks.json` - Tareas automatizadas
- `.vscode/settings.json` - Configuración de Java

### 🌐 Verificación

Una vez iniciado el servidor:
- **URL**: http://localhost:8080/backend-api/api/login
- **Método**: POST
- **Body**: `correo=admin@test.com&clave=1234`

### 🛠️ Si hay Problemas

1. **Extensiones necesarias en VS Code**:
   - Extension Pack for Java (Microsoft)
   - Maven for Java

2. **Verificar JAVA_HOME**:
   - Asegúrate que la variable de entorno JAVA_HOME esté configurada

3. **Recompilar si es necesario**:
   - Ctrl+Shift+P → "Tasks: Run Task" → "mvn-compile"

### 📝 Notas

- El servidor se compila automáticamente antes de iniciar
- Los logs aparecen en el terminal integrado
- Puedes detener el servidor con Ctrl+C en el terminal
- La configuración usa el classpath correcto con todas las dependencias
