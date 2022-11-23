FROM openjdk:19-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
ADD target/ideas.jar ideas.jar
ENTRYPOINT ["java", "-jar", "ideas.jar"]
EXPOSE 8080