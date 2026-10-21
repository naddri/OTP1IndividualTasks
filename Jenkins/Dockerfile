FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -Djavafx.platform=linux clean package

FROM eclipse-temurin:21-jre

WORKDIR /app

RUN apt-get update && apt-get install -y \
    libx11-6 libxext6 libxrender1 libxtst6 libxi6 libgtk-3-0 libasound2 wget unzip \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /javafx-sdk \
    && wget -O javafx.zip https://download2.gluonhq.com/openjfx/21.0.4/openjfx-21.0.4_linux-x64_bin-sdk.zip \
    && unzip javafx.zip -d /javafx-sdk \
    && rm -rf javafx.zip

COPY --from=build /build/target/JaCoCo-1.0-SNAPSHOT.jar app.jar

ENV DISPLAY=host.docker.internal:0.0

CMD ["java", \
     "--module-path", "/javafx-sdk/javafx-sdk-21.0.4/lib", \
     "--add-modules", "javafx.controls", \
     "-Dprism.order=sw", \
     "-cp", "app.jar", "TemperatureConverterApp"]
