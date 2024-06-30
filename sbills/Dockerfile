FROM eclipse-temurin:17-jdk-focal

WORKDIR /opt
ENV POR 9901
EXPOSE 9901
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
extra_hosts:
    - "host.docker.internal:host-gateway"
