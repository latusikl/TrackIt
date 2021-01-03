FROM adoptopenjdk/openjdk11:alpine as appBuild
WORKDIR opt
COPY . .
RUN ./gradlew build -xtest

FROM adoptopenjdk/openjdk11:latest
ARG JAR="/opt/build/libs/tracker-service.jar"
ARG USER="latusikl"
#Create group and user -r no home catalog -s no shell
RUN groupadd -r $USER && useradd -r -s /bin/false -g $USER $USER
COPY --from=appBuild ${JAR} /opt/tracker-service.jar
ENTRYPOINT ["java","-jar", "/opt/tracker-service.jar"]
EXPOSE 9000
