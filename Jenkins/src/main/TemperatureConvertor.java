public class TemperatureConverter {

    public double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    // extreme = below -40C or above 50C
    public boolean isExtremeTemperature(double celsius) {
        return celsius < -40 || celsius > 50;
    }
}