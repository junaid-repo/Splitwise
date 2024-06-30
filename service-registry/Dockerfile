FROM eclipse-temurin:17-jdk-focal

WORKDIR /opt
ENV POR 8761
EXPOSE 8761
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar