import javax.swing.*;

/**
 * Entry point for the Weather Dashboard application.
 */
public class Main {
    public static void main(String[] args) {
        // Print welcome message to console
        System.out.println("=".repeat(60));
        System.out.println("Weather Dashboard Application");
        System.out.println("CS300 Object-Oriented Programming Project");
        System.out.println("=".repeat(60));
        System.out.println("\nInitializing application...");
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("✓ UI Look and Feel set successfully");
        } catch (Exception e) {
            System.out.println("⚠ Could not set system look and feel, using default");
        }
        
        // Create and show GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("✓ Creating Weather Dashboard...");
                    WeatherApp app = new WeatherApp();
                    
                    System.out.println("✓ Application started successfully!");
                    System.out.println("\nInstructions:");
                    System.out.println("1. Enter a city name in the text field");
                    System.out.println("2. Click 'Fetch Weather' or press Enter");
                    System.out.println("3. Add multiple cities to see comparison");
                    System.out.println("\nReady to fetch weather data!");
                    System.out.println("=".repeat(60));
                    
                } catch (Exception e) {
                    System.err.println("✗ Error starting application:");
                    System.err.println("  " + e.getMessage());
                    e.printStackTrace();
                    
                    JOptionPane.showMessageDialog(
                        null,
                        "Failed to start application:\n" + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    
                    System.exit(1);
                }
            }
        });
    }
}