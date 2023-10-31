# Utilizamos una imagen OpenJDK 17 oficial como base
FROM openjdk:17

# Copiamos el archivo JAR al contenedor
COPY ./target/pruebatecnicaalianza-0.0.1-SNAPSHOT.jar /app.jar

# Exponemos el puerto en el que se ejecutará la aplicación
EXPOSE 8002
EXPOSE 8005
EXPOSE 8006

# Comando para iniciar la aplicación
CMD ["java", "-jar", "/app.jar"]
