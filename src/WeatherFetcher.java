import java.io.*;
import java.net.*;
import java.util.Properties;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * Handles fetching weather data from the OpenWeatherMap API.
 */
public class WeatherFetcher {
    private String apiKey;      
    private String apiUrl;      
    
    public WeatherFetcher() throws IOException {
        loadConfiguration();
    }
    
    /**
     * Loads API key and URL from config.properties file
     */
    private void loadConfiguration() throws IOException {
        Properties properties = new Properties();
        
        try {
            // Try to load from current directory first
            FileInputStream fis = new FileInputStream("config.properties");
            properties.load(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            // If not found, try from classpath
            InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
            if (is != null) {
                properties.load(is);
                is.close();
            } else {
                throw new IOException("config.properties file not found!");
            }
        }
        
        apiKey = properties.getProperty("api.key");
        apiUrl = properties.getProperty("api.url");
        
        if (apiKey == null || apiKey.equals("YOUR_API_KEY_HERE")) {
            throw new IOException("Please set your API key in config.properties!");
        }
    }

    /**
     * Fetch weather data for a given city
     */
    public WeatherData fetchWeather(String cityName) throws Exception {
        String urlString = buildUrl(cityName);
        
        String jsonResponse = makeHttpRequest(urlString);
        
        return parseWeatherData(jsonResponse);
    }

    /**
     * Builds the complete API URL with query parameters
     */
    private String buildUrl(String cityName) throws UnsupportedEncodingException {
        // URL encode the city name to handle spaces and special characters
        String encodedCity = URLEncoder.encode(cityName, "UTF-8");
        
        // Build complete URL with parameters
        return String.format("%s?q=%s&appid=%s&units=imperial", 
                           apiUrl, encodedCity, apiKey);
    }

    /**
     * Makes HTTP GET request to the API
     */
    private String makeHttpRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        try {
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);  // 5 sec timeout
            connection.setReadTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("API Error: HTTP " + responseCode);
            }
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            reader.close();
            return response.toString();
            
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Parses JSON response into WeatherData object
     */
    private WeatherData parseWeatherData(String jsonString) throws Exception {
        // Create JSON parser
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString);
        
        String cityName = (String) json.get("name");
        JSONObject sys = (JSONObject) json.get("sys");
        String country = (String) sys.get("country");
        
        JSONObject main = (JSONObject) json.get("main");
        double temperature = ((Number) main.get("temp")).doubleValue();
        double feelsLike = ((Number) main.get("feels_like")).doubleValue();
        long humidity = (Long) main.get("humidity");
        
        JSONArray weatherArray = (JSONArray) json.get("weather");
        JSONObject weather = (JSONObject) weatherArray.get(0);
        String mainCondition = (String) weather.get("main");
        String description = (String) weather.get("description");
        
        JSONObject wind = (JSONObject) json.get("wind");
        double windSpeed = ((Number) wind.get("speed")).doubleValue();
        
        return new WeatherData(
            cityName,
            country,
            temperature,
            feelsLike,
            description,
            mainCondition,
            humidity.intValue(),
            windSpeed
        );
    }

    /**
     * Tests if API connection is working
     */
    public boolean testConnection() {
        try {
            fetchWeather("London");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the current API key
     */
    public String getApiKeyPreview() {
        if (apiKey == null || apiKey.length() < 8) {
            return "Not set";
        }
        return apiKey.substring(0, 4) + "..." + apiKey.substring(apiKey.length() - 4);
    }


}