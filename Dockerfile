FROM ubuntu:latest
MAINTAINER SQAD_10
COPY target/api_proyectos-0.0.1-SNAPSHOT.war psa_proyectos_api.war
ENTRYPOINT ["java", "-war", "/psa_proyectos_api.war"]