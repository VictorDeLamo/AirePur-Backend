#Paso 1: Usa una imagen base con Maven y Java 17
FROM maven:3.8.4-openjdk-17 as build

#Paso 2: Copia el código fuente de tu aplicación al contenedor
WORKDIR /app
COPY src ./src
COPY pom.xml .

#Paso 3: Construye tu aplicación usando Maven
RUN mvn clean package -DskipTests

#Paso 4: Usa una imagen base de Java 17 para la imagen de ejecución
FROM openjdk:17-jdk-alpine

# Define las variables de entorno como argumentos que pueden ser pasados durante la construcción
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG APP_PASSWORD

# Establece las variables de entorno en el entorno de construcción
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV APP_PASSWORD=${APP_PASSWORD}

#Paso 5: Copia el artefacto construido desde el paso de construcción al directorio de trabajo
COPY --from=build /app/target/*.jar app.jar

#Paso 6: Expone el puerto en el que tu aplicación estará escuchando
EXPOSE 8080

#Paso 7: Ejecuta tu aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]