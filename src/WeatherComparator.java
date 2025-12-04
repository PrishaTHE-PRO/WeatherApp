/**
 * Handles comparison operations between weather data from different locations.
 */
public class WeatherComparator {
    /**
     * Compares temperature between two weather data points
     */
    public static String compareTemperature(WeatherData weather1, WeatherData weather2) {
        double diff = weather1.getTemperature() - weather2.getTemperature();
        String warmer, cooler;
        double absDiff = Math.abs(diff);
        
        if (diff > 0) {
            warmer = weather1.getLocation();
            cooler = weather2.getLocation();
        } else if (diff < 0) {
            warmer = weather2.getLocation();
            cooler = weather1.getLocation();
        } else {
            return String.format("%s and %s have the same temperature (%.1f°F)",
                    weather1.getLocation(), weather2.getLocation(), 
                    weather1.getTemperature());
        }
        
        return String.format("%s is %.1f°F warmer than %s", warmer, absDiff, cooler);
    }

    /**
     * Compares humidity between two locations
     */
    public static String compareHumidity(WeatherData weather1, WeatherData weather2) {
        int diff = weather1.getHumidity() - weather2.getHumidity();
        String more, less;
        int absDiff = Math.abs(diff);
        
        if (diff > 0) {
            more = weather1.getLocation();
            less = weather2.getLocation();
        } else if (diff < 0) {
            more = weather2.getLocation();
            less = weather1.getLocation();
        } else {
            return String.format("%s and %s have the same humidity (%d%%)",
                    weather1.getLocation(), weather2.getLocation(), 
                    weather1.getHumidity());
        }
        
        return String.format("%s is %d%% more humid than %s", more, absDiff, less);
    }

    /**
     * Compares wind speed between two locations
     */
    public static String compareWindSpeed(WeatherData weather1, WeatherData weather2) {
        double diff = weather1.getWindSpeed() - weather2.getWindSpeed();
        String windier, calmer;
        double absDiff = Math.abs(diff);
        
        if (diff > 0) {
            windier = weather1.getLocation();
            calmer = weather2.getLocation();
        } else if (diff < 0) {
            windier = weather2.getLocation();
            calmer = weather1.getLocation();
        } else {
            return String.format("%s and %s have the same wind speed (%.1f mph)",
                    weather1.getLocation(), weather2.getLocation(), 
                    weather1.getWindSpeed());
        }
        
        return String.format("%s is %.1f mph windier than %s", windier, absDiff, calmer);
    }

    /**
     * Generates a comprehensive comparison report
     */
    public static String getFullComparison(WeatherData weather1, WeatherData weather2) {
        StringBuilder report = new StringBuilder();
        
        report.append("=== Weather Comparison ===\n\n");
        
        // Location info
        report.append(String.format("Location 1: %s %s\n", 
                weather1.getWeatherIcon(), weather1.getLocation()));
        report.append(String.format("Location 2: %s %s\n\n", 
                weather2.getWeatherIcon(), weather2.getLocation()));
        
        // Temperature comparison
        report.append("Temperature:\n");
        report.append(String.format("  %s: %.1f°F\n", 
                weather1.getLocation(), weather1.getTemperature()));
        report.append(String.format("  %s: %.1f°F\n", 
                weather2.getLocation(), weather2.getTemperature()));
        report.append("  → ").append(compareTemperature(weather1, weather2)).append("\n\n");
        
        // Humidity comparison
        report.append("Humidity:\n");
        report.append(String.format("  %s: %d%%\n", 
                weather1.getLocation(), weather1.getHumidity()));
        report.append(String.format("  %s: %d%%\n", 
                weather2.getLocation(), weather2.getHumidity()));
        report.append("  → ").append(compareHumidity(weather1, weather2)).append("\n\n");
        
        // Wind speed comparison
        report.append("Wind Speed:\n");
        report.append(String.format("  %s: %.1f mph\n", 
                weather1.getLocation(), weather1.getWindSpeed()));
        report.append(String.format("  %s: %.1f mph\n", 
                weather2.getLocation(), weather2.getWindSpeed()));
        report.append("  → ").append(compareWindSpeed(weather1, weather2)).append("\n\n");
        
        // Weather conditions
        report.append("Conditions:\n");
        report.append(String.format("  %s: %s (%s)\n", 
                weather1.getLocation(), weather1.getDescription(), 
                weather1.getMainCondition()));
        report.append(String.format("  %s: %s (%s)\n", 
                weather2.getLocation(), weather2.getDescription(), 
                weather2.getMainCondition()));
        
        return report.toString();
    }

    /**
     * Finds the warmest city from a list
     */
    public static WeatherData findWarmest(WeatherList weatherList) {
        if (weatherList.isEmpty()) {
            return null;
        }
        
        WeatherData warmest = weatherList.get(0);
        
        for (int i = 1; i < weatherList.size(); i++) {
            WeatherData current = weatherList.get(i);
            if (current.getTemperature() > warmest.getTemperature()) {
                warmest = current;
            }
        }
        
        return warmest;
    }

    /**
     * Finds the coldest city from a list
     */
    public static WeatherData findColdest(WeatherList weatherList) {
        if (weatherList.isEmpty()) {
            return null;
        }
        
        WeatherData coldest = weatherList.get(0);
        
        for (int i = 1; i < weatherList.size(); i++) {
            WeatherData current = weatherList.get(i);
            if (current.getTemperature() < coldest.getTemperature()) {
                coldest = current;
            }
        }
        
        return coldest;
    }

    /**
     * Finds the most humid city from a list
     */
    public static WeatherData findMostHumid(WeatherList weatherList) {
        if (weatherList.isEmpty()) {
            return null;
        }
        
        WeatherData mostHumid = weatherList.get(0);
        
        for (int i = 1; i < weatherList.size(); i++) {
            WeatherData current = weatherList.get(i);
            if (current.getHumidity() > mostHumid.getHumidity()) {
                mostHumid = current;
            }
        }
        
        return mostHumid;
    }

    /**
     * Calculates average temperature across all cities
     */
    public static double calculateAverageTemperature(WeatherList weatherList) {
        if (weatherList.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        
        // Sum all temperatures
        for (int i = 0; i < weatherList.size(); i++) {
            sum += weatherList.get(i).getTemperature();
        }
        
        // Return average
        return sum / weatherList.size();
    }

    /**
     * Creates a summary statistics report for multiple cities
     */
    public static String getStatisticsSummary(WeatherList weatherList) {
        if (weatherList.isEmpty()) {
            return "No weather data available for statistics.";
        }
        
        StringBuilder summary = new StringBuilder();
        summary.append("=== Weather Statistics Summary ===\n\n");
        summary.append(String.format("Number of locations: %d\n\n", weatherList.size()));
        
        // Average temperature
        double avgTemp = calculateAverageTemperature(weatherList);
        summary.append(String.format("Average Temperature: %.1f°F\n\n", avgTemp));
        
        // Extremes
        WeatherData warmest = findWarmest(weatherList);
        WeatherData coldest = findColdest(weatherList);
        WeatherData mostHumid = findMostHumid(weatherList);
        
        summary.append(String.format("Warmest: %s at %.1f°F\n", 
                warmest.getLocation(), warmest.getTemperature()));
        summary.append(String.format("Coldest: %s at %.1f°F\n", 
                coldest.getLocation(), coldest.getTemperature()));
        summary.append(String.format("Most Humid: %s at %d%%\n", 
                mostHumid.getLocation(), mostHumid.getHumidity()));
        
        return summary.toString();
    }

}
