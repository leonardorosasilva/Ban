@echo off
echo Compilando o projeto...
javac -cp "postgresql-42.7.1.jar;src" -d out src/**/*.java

if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b %errorlevel%
)

echo Compilacao concluida com sucesso!
pause

