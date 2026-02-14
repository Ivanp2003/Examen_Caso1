@echo off
title Backend API Server - Simple
color 0A

echo ====================================
echo    INICIANDO BACKEND API SERVER
echo ====================================
echo.

echo [1/3] Verificando MySQL...
netstat -an | findstr :3306 > nul
if %errorlevel% neq 0 (
    echo [ADVERTENCIA] MySQL no detectado en puerto 3306
    echo Asegurate de que MySQL este iniciado
    echo.
    pause
)

echo [2/3] Compilando proyecto...
mvn clean compile -q
if %errorlevel% neq 0 (
    echo [ERROR] Fallo la compilacion
    pause
    exit /b 1
)

echo [3/3] Iniciando servidor simple...
echo.
echo ====================================
echo Servidor disponible en:
echo http://localhost:8080/backend-api/api/login
echo ====================================
echo.
echo Presiona Ctrl+C para detener el servidor
echo.

REM Ejecutar servidor simple con classpath mínimo
set CLASSPATH=target/classes
set CLASSPATH=%CLASSPATH%;C:\Users\Andre\.m2\repository\com\google\code\gson\gson\2.10.1\gson-2.10.1.jar
set CLASSPATH=%CLASSPATH%;C:\Users\Andre\.m2\repository\com\mysql\mysql-connector-j\8.3.0\mysql-connector-j-8.3.0.jar

java -cp "%CLASSPATH%" SimpleServer

echo.
echo Servidor detenido
pause
