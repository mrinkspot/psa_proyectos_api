FROM amazoncorretto:17

WORKDIR /app

COPY target/api_proyectos-0.0.1-SNAPSHOT.war api_proyectos.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api_proyectos.war"]