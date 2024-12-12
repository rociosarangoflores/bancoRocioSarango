ARG BANCO_NAME=bancors

FROM openjdk:21-jdk-slim AS builder
ARG BANCO_NAME


WORKDIR /app/$BANCO_NAME
COPY ./pom.xml /app
COPY ./$BANCO_NAME/.mvn ./.mvn
COPY ./$BANCO_NAME/mvnw .
COPY ./$BANCO_NAME/pom.xml .
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./$BANCO_NAME/src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim
ARG BANCO_NAME
WORKDIR /app
RUN mkdir ./logs
ARG TARGER_FOLDER=/app/$BANCO_NAME/target
COPY --from=builder $TARGER_FOLDER/bancors-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8003

ENV PORT $PORT_APP
EXPOSE $PORT

CMD ["java", "-jar", "bancors-0.0.1-SNAPSHOT.jar"]
