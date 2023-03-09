FROM maven:3.9.0-eclipse-temurin-17

COPY . /home
WORKDIR /home

RUN mvn install

ENTRYPOINT mvn spring-boot:run

EXPOSE 9999
