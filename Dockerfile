FROM openjdk:17
COPY ./target/pruebatecnicaalianza-0.0.1-SNAPSHOT.jar java-app.jar
EXPOSE 5432
EXPOSE 8002

ENTRYPOINT ["java", "-jar", "java-app.jar"]
