FROM maven:3.9-eclipse-temurin-21 AS construct
WORKDIR /construct
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=construct /construct/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]