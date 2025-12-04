import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI application for weather data visualization.
 */
public class WeatherApp extends JFrame {
    // Instance variables - core components only
    private WeatherFetcher fetcher;            
    private WeatherList weatherList;             
    
    // GUI Components 
    private JTextField cityInput;                
    private JButton fetchButton;                
    private JButton clearButton;                 
    private JTextArea displayArea;               
    private JScrollPane scrollPane;              
    
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 500;

    public WeatherApp() {
        weatherList = new WeatherList();
        
        // Initialize API fetcher
        try {
            fetcher = new WeatherFetcher();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Configuration Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        // Set up the window
        setupWindow();
        
        // Create the GUI components
        createGUI();
        
        // Make window visible
        setVisible(true);
    }
    
    /**
     * Sets up the main window properties
     */
    private void setupWindow() {
        setTitle("Weather Dashboard - CS300 Project");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center on screen
        setLayout(new BorderLayout(10, 10));
    }
    
    /**
     * Creates all GUI components
     */
    private void createGUI() {
        // Top panel - input controls
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Title and input
        JLabel titleLabel = new JLabel("Enter City:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(titleLabel);
        
        cityInput = new JTextField(20);
        cityInput.setFont(new Font("Arial", Font.PLAIN, 14));
        // Allow Enter key to fetch weather
        cityInput.addActionListener(e -> fetchWeather());
        topPanel.add(cityInput);
        
        // Buttons
        fetchButton = new JButton("Fetch Weather");
        fetchButton.addActionListener(e -> fetchWeather());
        topPanel.add(fetchButton);
        
        clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> clearAll());
        clearButton.setEnabled(false);  
        topPanel.add(clearButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel - display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setText("Enter a city name above and click 'Fetch Weather' to get started.\n\n" +
                          "Weather data will appear here...");
        displayArea.setMargin(new Insets(10, 10, 10, 10));
        
        scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Fetches weather data for the entered city
     */
    private void fetchWeather() {
        String cityName = cityInput.getText().trim();
        
        // Validate input
        if (cityName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a city name",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Check if we already have this city
        if (weatherList.contains(cityName)) {
            JOptionPane.showMessageDialog(this,
                    cityName + " is already in the list!",
                    "Duplicate City",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Disable button during fetch
        fetchButton.setEnabled(false);
        cityInput.setEnabled(false);
        
        // Use SwingWorker for background API call (prevents UI freezing)
        SwingWorker<WeatherData, Void> worker = new SwingWorker<WeatherData, Void>() {
            @Override
            protected WeatherData doInBackground() throws Exception {
                return fetcher.fetchWeather(cityName);
            }
            
            @Override
            protected void done() {
                try {
                    WeatherData weather = get();
                    
                    weatherList.add(weather);
                    
                    updateDisplay();
                    
                    cityInput.setText("");
                    clearButton.setEnabled(true);
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(WeatherApp.this,
                            "Error fetching weather: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                
                fetchButton.setEnabled(true);
                cityInput.setEnabled(true);
                cityInput.requestFocus();
            }
        };
        
        worker.execute();
    }
    
    /**
     * Updates the display with all weather data
     */
    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=".repeat(60)).append("\n");
        sb.append("            WEATHER DATA FOR ").append(weatherList.size()).append(" CITIES\n");
        sb.append("=".repeat(60)).append("\n\n");
        
        for (int i = 0; i < weatherList.size(); i++) {
            WeatherData weather = weatherList.get(i);
            
            // Format weather data nicely
            sb.append(String.format("City: %s, %s\n", 
                    weather.getCityName(), weather.getCountry()));
            sb.append(String.format("Temperature: %s (Feels like %.1f°F)\n",
                    weather.getFormattedTemperature(), weather.getFeelsLike()));
            sb.append(String.format("Conditions: %s %s\n",
                    weather.getWeatherIcon(), weather.getDescription()));
            sb.append(String.format("Humidity: %d%%\n", weather.getHumidity()));
            sb.append(String.format("Wind Speed: %.1f mph\n", weather.getWindSpeed()));
            sb.append("-".repeat(60)).append("\n\n");
        }
        
        if (weatherList.size() >= 2) {
            sb.append("\n");
            sb.append("=".repeat(60)).append("\n");
            sb.append("            SIMPLE COMPARISON\n");
            sb.append("=".repeat(60)).append("\n\n");
            
            // Find warmest and coldest
            WeatherData warmest = weatherList.get(0);
            WeatherData coldest = weatherList.get(0);
            
            for (int i = 1; i < weatherList.size(); i++) {
                WeatherData current = weatherList.get(i);
                if (current.getTemperature() > warmest.getTemperature()) {
                    warmest = current;
                }
                if (current.getTemperature() < coldest.getTemperature()) {
                    coldest = current;
                }
            }
            
            sb.append(String.format("Warmest: %s at %s\n", 
                    warmest.getLocation(), warmest.getFormattedTemperature()));
            sb.append(String.format("Coldest: %s at %s\n",
                    coldest.getLocation(), coldest.getFormattedTemperature()));
            
            // Calculate average temperature
            double sum = 0;
            for (int i = 0; i < weatherList.size(); i++) {
                sum += weatherList.get(i).getTemperature();
            }
            double average = sum / weatherList.size();
            sb.append(String.format("Average Temperature: %.1f°F\n", average));
        }
        
        displayArea.setText(sb.toString());
        displayArea.setCaretPosition(0);  // Scroll to top
    }
    
    /**
     * Clears all weather data
     */
    private void clearAll() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Clear all weather data?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            weatherList.clear();
            displayArea.setText("Enter a city name above and click 'Fetch Weather' to get started.\n\n" +
                              "Weather data will appear here...");
            clearButton.setEnabled(false);
        }
    }
}