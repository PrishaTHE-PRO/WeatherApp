import java.awt.Color;
import javax.swing.*

/**
 * Manages application theme (light/dark mode) and provides consistent colors
 */

public class ThemeManager {

    public enum Theme {
        LIGHT, DARK
    }

    private Theme currentTheme;

    // Light theme colors
    private final Color LIGHT_BACKGROUND = new Color(240, 244, 248);
    private final Color LIGHT_PANEL = Color.WHITE;
    private final Color LIGHT_TEXT = new Color(30, 41, 59);
    private final Color LIGHT_SECONDARY_TEXT = new Color(100, 116, 139);
    private final Color LIGHT_BORDER = new Color(226, 232, 240);
    private final Color LIGHT_BUTTON_BG = new Color(59, 130, 246);
    private final Color LIGHT_BUTTON_HOVER = new Color(37, 99, 235);

    // Dark theme colors
    private final Color DARK_BACKGROUND = new Color(15, 23, 42);
    private final Color DARK_PANEL = new Color(30, 41, 59);
    private final Color DARK_TEXT = new Color(248, 250, 252);
    private final Color DARK_SECONDARY_TEXT = new Color(148, 163, 184);
    private final Color DARK_BORDER = new Color(51, 65, 85);
    private final Color DARK_BUTTON_BG = new Color(59, 130, 246);
    private final Color DARK_BUTTON_HOVER = new Color(37, 99, 235);

    public ThemeManager() {
        this.currentTheme = Theme.LIGHT;
    }

    public ThemeManager(Theme theme) {
        this.currentTheme = theme;
    }

    /**
     * Toggles between light and dark theme
     */
    public Theme toggleTheme() {
        currentTheme = (currentTheme == Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;
        return currentTheme;
    }

    /**
     * Gets current theme
     */
    public Theme getCurrentTheme() {
        return currentTheme;
    }

    /**
     * Sets theme explicitly
     */
    public void setTheme(Theme theme) {
        this.currentTheme = theme;
    }

    /**
     * Checks if current theme is dark
     */
    public boolean isDarkMode() {
        return currentTheme == Theme.DARK;
    }

    /**
     * Gets background color for current theme
     */
    public Color getBackgroundColor() {
        return isDarkMode() ? DARK_BACKGROUND : LIGHT_BACKGROUND;
    }

    /**
     * Gets panel background color
     */
    public Color getPanelColor() {
        return isDarkMode() ? DARK_PANEL : LIGHT_PANEL;
    }

    /**
     * Gets primary text color
     */
    public Color getTextColor() {
        return isDarkMode() ? DARK_TEXT : LIGHT_TEXT;
    }

    /**
     * Gets secondary text color
     */
    public Color getSecondaryTextColor() {
        return isDarkMode() ? DARK_SECONDARY_TEXT : LIGHT_SECONDARY_TEXT;
    }

    /**
     * Gets border color
     */
    public Color getBorderColor() {
        return isDarkMode() ? DARK_BORDER : LIGHT_BORDER;
    }

    /**
     * Gets button background color
     */
    public Color getButtonColor() {
        return isDarkMode() ? DARK_BUTTON_BG : LIGHT_BUTTON_BG;
    }

    /**
     * Gets button hover color
     */
    public Color getButtonHoverColor() {
        return isDarkMode() ? DARK_BUTTON_HOVER : LIGHT_BUTTON_HOVER;
    }

    /**
     * Gets accent color
     */
    public Color getAccentColor() {
        return new Color(59, 130, 246);  // Blue accent
    }

    /**
     * Gets success color
     */
    public Color getSuccessColor() {
        return isDarkMode() ? new Color(74, 222, 128) : new Color(34, 197, 94);
    }

    /**
     * Gets warning color
     */
    public Color getWarningColor() {
        return isDarkMode() ? new Color(251, 191, 36) : new Color(245, 158, 11);
    }

    /**
     * Gets error color
     */
    public Color getErrorColor() {
        return isDarkMode() ? new Color(248, 113, 113) : new Color(239, 68, 68);
    }

    /**
     * Applies theme to a component and all its children
     */
    public void applyTheme(java.awt.Component component) {
        component.setBackground(getBackgroundColor());
        component.setForeground(getTextColor());
        
        if (component instanceof java.awt.Container) {
            java.awt.Container container = (java.awt.Container) component;
            
            for (java.awt.Component child : container.getComponents()) {
                applyTheme(child);
            }
        }
        
        // Special handling for specific component types
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            button.setBackground(getButtonColor());
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
        } else if (component instanceof JPanel) {
            component.setBackground(getPanelColor());
        } else if (component instanceof JTextField) {
            component.setBackground(getPanelColor());
            component.setForeground(getTextColor());
        } else if (component instanceof JTextArea) {
            component.setBackground(getPanelColor());
            component.setForeground(getTextColor());
        }
    }

    /**
     * Creates a styled button with theme colors
     */
    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(getButtonColor());
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(getButtonHoverColor());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(getButtonColor());
            }
        });
        
        return button;
    }
    
    public String getThemeName() {
        return isDarkMode() ? "Dark Mode" : "Light Mode";
    }

}
