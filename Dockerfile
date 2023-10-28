FROM openjdk:17
COPY ./target/pruebatecnicaalianza-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT ["java", "-jar", "java-app.jar"]
