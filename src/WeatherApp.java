import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main GUI application for weather data visualization.
 */

public class WeatherApp extends JFrame {

    private WeatherFetcher fetcher;              
    private WeatherList weatherList;             
    private ThemeManager themeManager;

     private JTextField cityInput;              
    private JButton fetchButton;                 
    private JButton compareButton;               
    private JButton statsButton;                 
    private JButton clearButton;                 
    private JToggleButton themeToggle;           
    private JPanel weatherDisplayPanel;          
    private JScrollPane scrollPane;              
    private JTextArea comparisonArea;            
    private JLabel statusLabel;    
    
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CARD_WIDTH = 250;
    private static final int CARD_HEIGHT = 180;

    public WeatherApp() {
        // Initialize data structures and managers
        weatherList = new WeatherList();
        themeManager = new ThemeManager();
        
        // Try to initialize API fetcher
        try {
            fetcher = new WeatherFetcher();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading configuration: " + e.getMessage(),
                    "Configuration Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setupFrame();
        createComponents();
        applyCurrentTheme();
        setVisible(true);
    }

    /**
     * Sets up the main JFrame properties
     */
    private void setupFrame() {
        setTitle("Weather Dashboard - CS300 Project");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center on screen
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Creates all GUI components and adds them to the frame
     */
    private void createComponents() {
        // top panel with input controls
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // center panel with weather display (scrollable)
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // bottom panel with status and comparison
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the top panel with input controls
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Title label
        JLabel titleLabel = new JLabel("🌤️ Weather Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Input area
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel inputLabel = new JLabel("Enter City:");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(inputLabel);
        
        cityInput = new JTextField(20);
        cityInput.setFont(new Font("Arial", Font.PLAIN, 14));
        // Add Enter key listener to fetch weather
        cityInput.addActionListener(e -> fetchWeatherAction());
        inputPanel.add(cityInput);
        
        // Create buttons
        fetchButton = new JButton("Fetch Weather");
        fetchButton.addActionListener(e -> fetchWeatherAction());
        inputPanel.add(fetchButton);
        
        compareButton = new JButton("Compare Cities");
        compareButton.addActionListener(e -> compareAction());
        compareButton.setEnabled(false);  // Disabled until we have 2+ cities
        inputPanel.add(compareButton);
        
        statsButton = new JButton("Show Statistics");
        statsButton.addActionListener(e -> showStatistics());
        statsButton.setEnabled(false);
        inputPanel.add(statsButton);
        
        clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> clearAllData());
        clearButton.setEnabled(false);
        inputPanel.add(clearButton);
        
        // Theme toggle button
        themeToggle = new JToggleButton("🌙 Dark Mode");
        themeToggle.addActionListener(e -> toggleTheme());
        inputPanel.add(themeToggle);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     * Creates the center panel with scrollable weather display
     */
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 15, 10, 15));
        
        // Create panel to hold weather cards
        weatherDisplayPanel = new JPanel();
        weatherDisplayPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        
        // Wrap in scroll pane for scrolling functionality
        scrollPane = new JScrollPane(weatherDisplayPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);  // Smooth scrolling
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     * Creates the bottom panel with status and comparison display
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 15, 15, 15));
        
        // Status label
        statusLabel = new JLabel("Ready to fetch weather data");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        panel.add(statusLabel, BorderLayout.NORTH);
        
        // Comparison text area
        comparisonArea = new JTextArea(6, 50);
        comparisonArea.setEditable(false);
        comparisonArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        comparisonArea.setText("Add multiple cities to see comparisons here...");
        
        JScrollPane compScrollPane = new JScrollPane(comparisonArea);
        panel.add(compScrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    


}