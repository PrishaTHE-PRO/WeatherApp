/**
 * Represents weather information for a single location.
 */
public class WeatherData {
    // Instance variables - all weather properties
    private String cityName;
    private String country;
    private double temperature;      // in Fahrenheit
    private double feelsLike;        // in Fahrenheit
    private String description;      
    private String mainCondition;    
    private int humidity;            // percentage
    private double windSpeed;        // mph
    private long timestamp;          
    
    public WeatherData(String cityName, String country, double temperature, 
                       double feelsLike, String description, String mainCondition,
                       int humidity, double windSpeed) {
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.description = description;
        this.mainCondition = mainCondition;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public String getCountry() {
        return country;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public double getFeelsLike() {
        return feelsLike;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getMainCondition() {
        return mainCondition;
    }
    
    public int getHumidity() {
        return humidity;
    }
    
    public double getWindSpeed() {
        return windSpeed;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    /**
     * Gets a weather icon emoji based on the main condition
     */
    public String getWeatherIcon() {
        switch (mainCondition.toLowerCase()) {
            case "clear":
                return "☀️";
            case "clouds":
                return "☁️";
            case "rain":
            case "drizzle":
                return "🌧️";
            case "thunderstorm":
                return "⛈️";
            case "snow":
                return "❄️";
            case "mist":
            case "fog":
                return "🌫️";
            default:
                return "🌤️";
        }
    }
    
    /**
     * Formats temperature with proper rounding and degree symbol
     */
    public String getFormattedTemperature() {
        return String.format("%.1f°F", temperature);
    }
    
    /**
     * Creates a formatted string representation of the weather data
     */
    @Override
    public String toString() {
        return String.format("%s %s, %s | %s | %s | Humidity: %d%% | Wind: %.1f mph",
                getWeatherIcon(), cityName, country, getFormattedTemperature(), 
                description, humidity, windSpeed);
    }
    
    /**
     * Gets full location name (City, Country)
     */
    public String getLocation() {
        return cityName + ", " + country;
    }
}