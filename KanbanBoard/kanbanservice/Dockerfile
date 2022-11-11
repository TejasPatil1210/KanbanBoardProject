FROM openjdk
WORKDIR usr/lib
ENV MONGO_DATABASE=kanbandb
ENV MONGO_URL=mongodb://localhost:27017/kanbandb
ADD ./target/kanbanservice-0.0.1-SNAPSHOT.jar /usr/lib/kanbanservice-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","kanbanservice-0.0.1-SNAPSHOT.jar"]