FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY betting-api/pom.xml betting-api/pom.xml
COPY betting-app/pom.xml betting-app/pom.xml
RUN ./mvnw dependency:go-offline -B
COPY betting-api/src betting-api/src
COPY betting-app/src betting-app/src
RUN ./mvnw clean package -B -DskipTests

FROM eclipse-temurin:25-jre AS layers
WORKDIR /app
COPY --from=build /app/betting-app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=layers /app/dependencies/ ./
COPY --from=layers /app/spring-boot-loader/ ./
COPY --from=layers /app/snapshot-dependencies/ ./
COPY --from=layers /app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
