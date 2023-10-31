#!/bin/bash

# Esperar a que el servicio de base de datos esté disponible
while ! nc -z java_db 5432; do
  sleep 1
done

# Verificar si la base de datos existe
if ! psql -h java_db -U postgres -lqt | cut -d \| -f 1 | grep -qw 'postgres_desa'; then
  echo "La base de datos 'postgres_desa' no existe, creándola..."
  psql -h java_db -U postgres -c "CREATE DATABASE postgres_desa;"
fi

if ! psql -h java_db -U postgres -lqt | cut -d \| -f 1 | grep -qw 'postgres_produccion'; then
  echo "La base de datos 'postgres_produccion' no existe, creándola..."
  psql -h java_db -U postgres -c "CREATE DATABASE postgres_produccion;"
fi

# Iniciar la aplicación
java -jar java-app.jar
