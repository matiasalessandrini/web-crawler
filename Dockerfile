FROM maven:3.8.3-openjdk-17
WORKDIR /deploy
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run