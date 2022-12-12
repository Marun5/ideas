FROM openjdk:19-jdk-alpine
ADD target/ideas.jar ideas.jar
ENTRYPOINT ["java", "-jar", "ideas.jar"]
EXPOSE 8080