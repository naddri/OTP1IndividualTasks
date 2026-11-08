# Temperature Converter — In-class Assignment 6

A small JavaFX temperature converter localized in English, Finnish, Swedish, Japanese, and Arabic. UI labels come from UTF-8 `.properties` files. Unit names and descriptions come from an embedded H2 database.

## Database localization approach

This project uses the **translation table** approach. The `unit` table stores language-neutral unit keys and categories. `unit_translation` stores a row for each unit and language, with a foreign key to `unit`; `conversion_history` links its from/to units and stores the input, result, selected language, and timestamp. The seed data in `Jenkins/src/main/resources/init.sql` includes temperature, speed, distance, and time units in all five languages.

I chose a translation table because adding a language only requires adding translation rows, and unit keys/categories are stored once instead of being repeated for every language. The DAO uses the requested language and falls back to English if its translation is missing. This is a little more SQL than putting five name columns in one row, but it keeps the data organized and easy to extend.

## Run and test

Requires JDK 21 and Maven. From the repository root:

```text
cd Jenkins
mvn -Djavafx.platform=win clean verify
```

The JaCoCo report is generated at `Jenkins/target/site/jacoco/index.html`. Run `TemperatureConverterApp` from an IDE with JavaFX configured. The embedded H2 database is created in `Jenkins/data/` on first run.

The converter logic has 90% line coverage, and the two DAOs have 100% line coverage in the current JaCoCo report.

## Docker and Kubernetes

Build the image from `Jenkins/` with `docker build -t temperature-converter .`. The GUI container needs an X server; set `DISPLAY` to the reachable X server address before running it. The Kubernetes example is `Jenkins/k8s/temperature-converter.yaml`; update its image name and configure `DISPLAY` for your X server before applying it. H2 runs inside the application container, so no separate database pod is needed.

The Jenkins pipeline runs tests and JaCoCo, then builds and pushes Docker Hub tags. Configure Jenkins with the `dockerhub-credentials` username/password credential before running the publish stage.
