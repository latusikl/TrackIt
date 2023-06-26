FROM sapmachine:17 AS appBuild
WORKDIR /opt
COPY . .
RUN ./gradlew build --no-daemon -x test

FROM sapmachine:17
ARG JAR="/opt/build/libs/location-service.jar"
ARG USER="latusikl"

#Create system group called $USER then add new user that do not have password (cannot login),
#has empty GECOS field (name, surname etc.), has no home direcotry, has no login shell and is assigned to created group.
RUN addgroup --system $USER && \
    adduser --disabled-password  \
            --system \
            --gecos "" \
            --no-create-home  \
            --shell /usr/sbin/nologin  \
            --ingroup $USER $USER
USER $USER
COPY --from=appBuild ${JAR} /opt/
ENTRYPOINT ["java","-jar", "/opt/location-service.jar"]
EXPOSE 8090
