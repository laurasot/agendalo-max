# Usa una imagen base de OpenJDK para Java 11
FROM openjdk:17-jre-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor (asegúrate de que el nombre del archivo coincida con el de tu aplicación)
COPY target/ProyectoAgenda-0.0.1-SNAPSHOT.war /app/

RUN -v /ruta/local:/ruta/contenedor -p 3306:3306 --name mi-contenedor-mysql -e MYSQL_ROOT_PASSWORD=contraseña -d mysql:latest
# Ejecutar maven para construir el proyecto
RUN mvn clean package

# Crear una nueva imagen basada en OpenJDK para Java 11
FROM openjdk:17-jre-slim-buster

# Expone el puerto en el que la aplicación se ejecuta (ajusta según el puerto de tu aplicación)
EXPOSE 8080

RUN mkdir -p /app/
COPY --from=build /app/indicesLucene /app/indicesLucene

COPY target/ProyectoAgenda-0.0.1-SNAPSHOT.war  /app/app.war
# Comando para ejecutar la aplicación cuando el contenedor se inicia
ENTRYPOINT  ["java", "-jar", "app.war"]