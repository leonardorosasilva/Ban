#!/bin/bash

echo "Compilando o projeto..."
javac -cp "postgresql-42.7.1.jar:src" -d out src/**/*.java

if [ $? -ne 0 ]; then
    echo "Erro na compilação!"
    exit 1
fi

echo ""
echo "Executando a aplicação..."
java -cp "postgresql-42.7.1.jar:out" Main

