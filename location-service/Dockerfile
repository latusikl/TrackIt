FROM adoptopenjdk/openjdk11:alpine as appBuild
WORKDIR opt
COPY . .
RUN ./gradlew build

FROM adoptopenjdk/openjdk11:latest
ARG JAR="/opt/build/libs/location-service.jar"
ARG USER="latusikl"
#Create group and user -r no home catalog -s no shell
RUN groupadd -r $USER && useradd -r -s /bin/false -g $USER $USER
COPY --from=appBuild ${JAR} /opt/
ENTRYPOINT ["java","-jar", "/opt/location-service.jar"]
EXPOSE 8090
