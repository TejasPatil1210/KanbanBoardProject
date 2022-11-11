FROM openjdk
WORKDIR usr/lib
# ENV MYSQL_DATABASE=userkanbandb
# ENV MYSQL_URL=jdbc:mysql://localhost:3306/userkanbandb?createDatabaseIfNotExist=true
ADD ./target/UserAuthenticationService-0.0.1-SNAPSHOT.jar /usr/lib/UserAuthenticationService-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","UserAuthenticationService-0.0.1-SNAPSHOT.jar"]