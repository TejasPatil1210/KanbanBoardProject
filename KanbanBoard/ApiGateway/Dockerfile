FROM openjdk:11-jre-slim
WORKDIR usr/src
ADD ./target/ApiGateway-0.0.1-SNAPSHOT.jar /usr/src/ApiGateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","ApiGateway-0.0.1-SNAPSHOT.jar"]