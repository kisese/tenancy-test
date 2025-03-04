FROM gradle:8.8.0-jdk17 AS builder

WORKDIR /usr/src/app/

COPY ./ ./
RUN gradle bootJar

ARG SPRING_PROFILES_ACTIVE="dev"
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ARG BASE_URL="http://localhost:8080"
ENV BASE_URL=${BASE_URL}
ARG SERVER_PORT="8080"
ENV SERVER_PORT=${SERVER_PORT}


FROM gcr.io/distroless/java17-debian11

WORKDIR /usr/src/app/

EXPOSE 8080

ARG SPRING_PROFILES_ACTIVE="dev"
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ARG BASE_URL="http://localhost:8080"
ENV BASE_URL=${BASE_URL}
ARG SERVER_PORT="8080"
ENV SERVER_PORT=${SERVER_PORT}

COPY --from=builder /usr/src/app/build/libs/projectBasigo-*.jar ./app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
