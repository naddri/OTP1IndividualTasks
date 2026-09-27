FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY src/main/TemperatureConverter.java .

RUN javac TemperatureConverter.java

CMD ["java", "TemperatureConverter"]

