# Archivos Esenciales del Proyecto

## 📁 Archivos Mantenidos

### Scripts Esenciales
- **`run-simple.bat`** - Inicia el servidor backend (método recomendado)
- **`setup-final.bat`** - Configura la base de datos MySQL

### Configuración
- **`.vscode/`** - Configuración para VS Code
  - `launch.json` - Configuración del debugger
  - `tasks.json` - Tareas automatizadas
  - `settings.json` - Configuración de Java

### Documentación
- **`README-VSCODE.md`** - Guía para ejecutar en VS Code
- **`README-THUNDERCLIENT.md`** - Guía para pruebas en Thunder Client
- **`README-ARCHIVOS.md`** - Este archivo

### Base de Datos
- **`database-setup.sql`** - Script SQL para configuración manual
- **`thunder-client-tests.json`** - Tests para importar en Thunder Client

### Proyecto
- **`pom.xml`** - Configuración Maven
- **`src/`** - Código fuente del proyecto
- **`target/`** - Archivos compilados

## 🗑️ Archivos Eliminados

Los siguientes archivos .bat fueron eliminados por ser innecesarios:
- `run-server.bat` - Duplicado de run-simple.bat
- `run.bat` - Versión más compleja innecesaria
- `setup-database-direct.bat` - Reemplazado por setup-final.bat
- `setup-database.bat` - Versión con problemas
- `start-server-final.bat` - Duplicado
- `start-server-simple.bat` - Duplicado
- `start-server.bat` - Duplicado
- `start-simple.bat` - Duplicado
- `start.bat` - Duplicado
- `start-server.ps1` - Script PowerShell innecesario

## 🚀 Formas de Ejecutar el Servidor

### Opción 1: VS Code (Recomendado)
1. Abrir VS Code
2. Ctrl+Shift+D (Debug)
3. Seleccionar "Iniciar Servidor Backend"
4. Presionar F5

### Opción 2: Script Batch
1. Doble clic en `run-simple.bat`
2. O ejecutar en terminal: `.\run-simple.bat`

### Opción 3: Configurar BD
1. Ejecutar `setup-final.bat` (solo una vez)
2. Luego iniciar el servidor con `run-simple.bat`

## 📝 Notas
- El proyecto está optimizado para desarrollo en VS Code
- Los scripts esenciales son suficientes para todas las operaciones
- La configuración de VS Code maneja automáticamente la compilación y ejecución
