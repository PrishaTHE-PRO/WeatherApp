import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * WeatherApp.java
 * Enhanced GUI application for weather data visualization with centered layout.
 */
public class WeatherApp extends JFrame {
    // Instance variables
    private WeatherFetcher fetcher;
    private WeatherList weatherList;
    
    // GUI Components
    private JTextField cityInput;
    private JButton fetchButton;
    private JButton clearButton;
    private JTextArea displayArea;
    private JScrollPane scrollPane;
    
    // Color scheme - modern and pleasant
    private final Color BACKGROUND_COLOR = new Color(240, 244, 248);
    private final Color PANEL_COLOR = new Color(255, 255, 255);
    private final Color ACCENT_COLOR = new Color(59, 130, 246);
    private final Color TEXT_COLOR = new Color(30, 41, 59);
    private final Color SECONDARY_TEXT = new Color(71, 85, 105);
    private final Color BORDER_COLOR = new Color(226, 232, 240);
    
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 600;
    
    /**
     * Constructor - initializes the application with enhanced styling
     */
    public WeatherApp() {
        weatherList = new WeatherList();
        
        try {
            fetcher = new WeatherFetcher();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Configuration Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        setupWindow();
        createGUI();
        setVisible(true);
    }
    
    /**
     * Sets up the main window with enhanced appearance
     */
    private void setupWindow() {
        setTitle("Weather Dashboard");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(BACKGROUND_COLOR);
    }
    
    /**
     * Creates all GUI components with modern styling and centered layout
     */
    private void createGUI() {
        // Top panel with centered content
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(PANEL_COLOR);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Title centered
        JLabel titleLabel = new JLabel("🌤️  Weather Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);
        
        topPanel.add(Box.createVerticalStrut(15));
        
        // Input panel centered
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        inputPanel.setBackground(PANEL_COLOR);
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel inputLabel = new JLabel("City:");
        inputLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        inputLabel.setForeground(SECONDARY_TEXT);
        inputPanel.add(inputLabel);
        
        // Styled input field
        cityInput = new JTextField(20);
        cityInput.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cityInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        cityInput.addActionListener(e -> fetchWeather());
        inputPanel.add(cityInput);
        
        // Styled buttons - ensure they remain visible at all times
        fetchButton = createStyledButton("Fetch Weather", ACCENT_COLOR);
        fetchButton.addActionListener(e -> fetchWeather());
        inputPanel.add(fetchButton);

        clearButton = createStyledButton("Clear All", new Color(239, 68, 68));
        clearButton.addActionListener(e -> clearAll());
        inputPanel.add(clearButton);
        
        topPanel.add(inputPanel);
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel - display area with card-like appearance
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        displayArea.setBackground(PANEL_COLOR);
        displayArea.setForeground(TEXT_COLOR);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        displayArea.setText(getWelcomeMessage());
        displayArea.setMargin(new Insets(20, 20, 20, 20));
        
        scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(0, 0, 0, 0)
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates a styled button with hover effects
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setVisible(true);  // Ensure button is always visible
        button.setEnabled(true);  // Ensure button is always enabled

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
    
    /**
     * Gets a styled welcome message
     */
    private String getWelcomeMessage() {
        return "\n\n" +
               "                  WELCOME TO WEATHER DASHBOARD\n" +
               "              ═══════════════════════════════════\n\n\n" +
               "               📍 Enter a city name above and click\n" +
               "                      'Fetch Weather'\n\n" +
               "               🌍 Add multiple cities to see\n" +
               "                   automatic comparison\n\n\n";
    }
    
    /**
     * Fetches weather data for the entered city
     */
    private void fetchWeather() {
        String cityName = cityInput.getText().trim();
        
        if (cityName.isEmpty()) {
            showStyledMessage("Please enter a city name", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (weatherList.contains(cityName)) {
            showStyledMessage(cityName + " is already in the list!", "Duplicate City", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        fetchButton.setEnabled(false);
        cityInput.setEnabled(false);
        fetchButton.setText("Loading...");
        clearButton.setVisible(true);  // Keep clear button visible during loading
        clearButton.setEnabled(true);  // Keep clear button enabled during loading

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

                } catch (Exception e) {
                    showStyledMessage("Error fetching weather:\n" + e.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }

                fetchButton.setEnabled(true);
                fetchButton.setVisible(true);  // Ensure fetch button stays visible
                cityInput.setEnabled(true);
                fetchButton.setText("Fetch Weather");
                clearButton.setVisible(true);  // Ensure clear button stays visible
                clearButton.setEnabled(true);  // Ensure clear button stays enabled
                cityInput.requestFocus();
            }
        };
        
        worker.execute();
    }
    
    /**
     * Updates the display with enhanced formatting and centered text
     */
    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        
        // Header centered
        sb.append("\n\n");
        sb.append("                 WEATHER DATA FOR ").append(weatherList.size()).append(" CITIES\n");
        sb.append("            ═══════════════════════════════════\n\n\n");
        
        // Display each city's weather centered
        for (int i = 0; i < weatherList.size(); i++) {
            WeatherData weather = weatherList.get(i);
            
            // City name centered
            String cityLine = weather.getCityName() + ", " + weather.getCountry();
            sb.append(centerText(cityLine, 60)).append("\n");
            sb.append(centerText("───────────────────────────", 15)).append("\n\n");
            
            // Weather details centered
            sb.append(centerText("🌡️ Temperature:  " + weather.getFormattedTemperature(), 60)).append("\n");
            sb.append(centerText("🤔 Feels Like:   " + String.format("%.1f°F", weather.getFeelsLike()), 60)).append("\n");
            sb.append(centerText("☁️ Conditions:  " + weather.getDescription(), 60)).append("\n");
            sb.append(centerText("💧 Humidity:    " + weather.getHumidity() + "%", 60)).append("\n");
            sb.append(centerText("💨 Wind Speed:  " + String.format("%.1f mph", weather.getWindSpeed()), 60)).append("\n");
            sb.append("\n\n");
        }
        
        // Add comparison section if we have 2+ cities
        if (weatherList.size() >= 2) {
            sb.append("\n");
            sb.append("                      COMPARISON SUMMARY\n");
            sb.append("              ═══════════════════════════════════\n\n\n");
            
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
            
            sb.append(centerText("🔥 WARMEST:  " + warmest.getLocation() + " at " + warmest.getFormattedTemperature(), 60)).append("\n\n");
            sb.append(centerText("❄️  COLDEST:  " + coldest.getLocation() + " at " + coldest.getFormattedTemperature(), 60)).append("\n\n");
            
            // Calculate average
            double sum = 0;
            for (int i = 0; i < weatherList.size(); i++) {
                sum += weatherList.get(i).getTemperature();
            }
            double average = sum / weatherList.size();
            
            sb.append(centerText("📊 AVERAGE:  " + String.format("%.1f°F", average), 60)).append("\n\n");
            
            // Temperature difference
            double diff = warmest.getTemperature() - coldest.getTemperature();
            sb.append(centerText("📏 Temperature Range:  " + String.format("%.1f°F", diff), 60)).append("\n\n");
        }
        
        displayArea.setText(sb.toString());
        displayArea.setCaretPosition(0);
    }
    
    /**
     * Centers text within a given width
     */
    private String centerText(String text, int width) {
        // Remove emoji for length calculation (they take up more visual space)
        int textLength = text.replaceAll("[^\\x00-\\x7F]", "").length();
        if (textLength >= width) {
            return text;
        }
        
        int padding = (width - textLength) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        return sb.toString();
    }
    
    /**
     * Clears all weather data with confirmation
     */
    private void clearAll() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Clear all weather data?",
                "Confirm Clear",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            weatherList.clear();
            displayArea.setText(getWelcomeMessage());
        }
    }
    
    /**
     * Shows a styled message dialog
     */
    private void showStyledMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}