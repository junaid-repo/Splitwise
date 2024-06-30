FROM eclipse-temurin:17-jdk-focal

WORKDIR /opt
ENV POR 8090
EXPOSE 8090
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
