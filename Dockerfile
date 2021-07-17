FROM maven:3.6.1-jdk-8-alpine
ADD . /code
WORKDIR /code
RUN mvn clean package -DskipTests=true
CMD mvn spring-boot:run