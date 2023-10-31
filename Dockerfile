# Etapa 1: Construir y empaquetar la aplicación Java
FROM openjdk:17-jdk-slim AS builder

# Copiar el archivo JAR al contenedor
COPY ./target/pruebatecnicaalianza-0.0.1-SNAPSHOT.jar java-app.jar

# Etapa 2: Preparar la imagen final
FROM ubuntu:latest

# Instalar Java
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Copiar el JAR de la etapa anterior
COPY --from=builder java-app.jar /java-app.jar

# Copiar scripts y configuraciones
COPY init-db.sh /init-db.sh
COPY init.sh /init.sh
COPY nginx.conf /etc/nginx/nginx.conf

# Conceder permisos de ejecución a los scripts
RUN chmod +x /init-db.sh
RUN chmod +x /init.sh

# Instalar Nginx y Supervisor
RUN apt-get install -y nginx supervisor

# Exponer puertos
EXPOSE 5432
EXPOSE 8002
EXPOSE 80

# Definir el comando de inicio
CMD ["/init.sh"]
