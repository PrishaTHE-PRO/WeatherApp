Weather Dashboard
A simple Java application that fetches and displays real-time weather data using the OpenWeatherMap API.
Features

🌤️ Fetch current weather for any city worldwide
📊 View weather for multiple cities at once
🔍 Automatic comparison (warmest, coldest, average temperature)
💻 Clean, simple text-based interface
⚡ Lightweight - only ~950 lines of code

Prerequisites

Java JDK 8 or higher
Internet connection
OpenWeatherMap API key (free)

Getting Started
1. Clone the Repository
bashgit clone https://github.com/yourusername/weather-dashboard.git
cd weather-dashboard
```

### 2. Download Required Library

Download `json-simple-1.1.1.jar` from:
```
https://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/json-simple/json-simple-1.1.1.jar
Place it in the lib/ folder.
3. Get Your Free API Key

Sign up at OpenWeatherMap
Copy your API key from your account
Wait 10-15 minutes for activation

4. Add Your API Key
Open config.properties and replace YOUR_API_KEY_HERE:
propertiesapi.key=your_actual_api_key_here
api.url=https://api.openweathermap.org/data/2.5/weather
5. Run the Application
Using VS Code:

Open the project folder
Install "Extension Pack for Java" extension
Open src/Main.java
Click "Run"

Using Command Line:
Windows:
bashjavac -cp ".;lib/json-simple-1.1.1.jar" -d bin src/*.java
java -cp ".;lib/json-simple-1.1.1.jar;bin" Main
Mac/Linux:
bashjavac -cp ".:lib/json-simple-1.1.1.jar" -d bin src/*.java
java -cp ".:lib/json-simple-1.1.1.jar:bin" Main
```

## Usage

1. Enter a city name (e.g., "London", "Tokyo")
2. Click "Fetch Weather" or press Enter
3. View weather information
4. Add more cities to see comparison
5. Click "Clear All" to reset

## Project Structure
```
weather-dashboard/
├── src/                       # Java source files
├── lib/                       # json-simple library
└── config.properties          # API configuration
License
Open source - available for educational purposes.
