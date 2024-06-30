FROM eclipse-temurin:17-jdk-focal

WORKDIR /opt
ENV POR 9902
EXPOSE 9902
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
