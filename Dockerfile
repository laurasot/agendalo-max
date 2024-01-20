# Build stage
#
FROM eclipse-temurin:17-jdk-jammy AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package
RUN docker build -t agendalo-max-docker.


#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy 
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 8080

run -e MYSQL_URL=jdbc:mysql://localhost:3306/proyecto_agenda -e MYSQL_USER=root -e MYSQL_PASSWORD=root -d agendalo-max-docker

ENTRYPOINT java -jar /app/runner.jar