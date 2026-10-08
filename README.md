# Individual In-Class Assignment

## 1. Assignment Description
This project implements a temperature conversion solution with:
- Conversion between Fahrenheit and Celsius
- Detection of extreme temperatures (below -40C or above 50C)
- A simple JavaFX user interface for input and conversion
- Automated unit testing and code coverage reporting

Deliverables completed:
- Core converter logic (`TemperatureConverter`)
- JavaFX app (`TemperatureConverterApp`)
- JUnit 5 test suite
- JaCoCo coverage report

## 2. Technologies & Tools Used
- Java (JDK 21)
- JavaFX (javafx-controls)
- Maven
- JUnit 5
- JaCoCo
- Jenkins files included (`Jenkinsfile`, `Dockerfile`)

## 3. Design Approach & Implementation Method
The solution separates logic from presentation:
- `TemperatureConverter` contains reusable conversion and validation methods.
- `TemperatureConverterApp` handles the JavaFX GUI and user interactions.

Key implementation choices:
- Kept conversion formulas in a dedicated class for easier testing.
- Added boundary-aware extreme temperature checks.
- Added input validation in the GUI to handle invalid numeric input safely.

## 4. Testing & Quality Assurance Steps
Automated testing was done using JUnit 5.

Test cases covered:
- Fahrenheit to Celsius: freezing, boiling, and negative values
- Celsius to Fahrenheit: freezing, boiling, and negative values
- Extreme temperature checks: below range, above range, and boundary values

Results:
- Tests run: 11
- Failures: 0
- Errors: 0
- Skipped: 0

Coverage:
- JaCoCo report generated under `Jenkins/target/site/jacoco/`

## 5. How to Run
Prerequisites:
- JDK 21
- Maven
- (Optional for GUI) JavaFX SDK/runtime on Windows

From the project root:

```bash
cd Jenkins
mvn clean test
```

Run the console demo:

```bash
mvn -DskipTests compile
java -cp target/classes TemperatureConverter
```

Run the JavaFX application by launching `TemperatureConverterApp` from your IDE (with JavaFX configured).

