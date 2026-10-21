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

    public static void main(String[] args) {
        TemperatureConverter converter = new TemperatureConverter();
        double fahrenheit = 98.6;
        double celsius = converter.fahrenheitToCelsius(fahrenheit);
        System.out.printf("%.1fF is %.2fC%n", fahrenheit, celsius);
        System.out.printf("%.2fC is %.1fF%n", celsius, converter.celsiusToFahrenheit(celsius));
        System.out.println("Extreme temperature? " + converter.isExtremeTemperature(celsius));
    }
}