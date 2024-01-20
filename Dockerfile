# Usa una imagen base de OpenJDK para Java 11
FROM adoptopenjdk:17.0.4_101-hotspot
VOLUME /tmp
# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor (asegúrate de que el nombre del archivo coincida con el de tu aplicación)
COPY target/ProyectoAgenda-0.0.1-SNAPSHOT.war /app/

# Ejecutar maven para construir el proyecto
RUN mvn clean package

# Expone el puerto en el que la aplicación se ejecuta (ajusta según el puerto de tu aplicación)

# Comando para ejecutar la aplicación cuando el contenedor se inicia
ENTRYPOINT  ["java", "-jar", "app.war"]
EXPOSE 8080