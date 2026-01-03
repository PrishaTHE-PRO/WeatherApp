# Weather Dashboard

A simple Java application that fetches and displays real-time weather data using the OpenWeatherMap API.

## Features

- Fetch current weather for any city worldwide
- View weather for multiple cities at once
- Automatic comparison (warmest, coldest, average temperature)
- Clean, simple text-based interface
- Lightweight - only ~950 lines of code

## Prerequisites

- Java JDK 8 or higher
- Internet connection
- OpenWeatherMap API key (free)

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/weather-dashboard.git
cd weather-dashboard
```

### 2. Download Required Library

Download `json-simple-1.1.1.jar` from:
```
https://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/json-simple/json-simple-1.1.1.jar
```

Place it in the `lib/` folder.

### 3. Get Your Free API Key

1. Sign up at [OpenWeatherMap](https://openweathermap.org/api)
2. Copy your API key from your account
3. Wait 10-15 minutes for activation

### 4. Add Your API Key

Open `config.properties` and replace `YOUR_API_KEY_HERE`:
```properties
api.key=your_actual_api_key_here
api.url=https://api.openweathermap.org/data/2.5/weather
```

### 5. Run the Application

**Using VS Code:**
1. Open the project folder
2. Install "Extension Pack for Java" extension
3. Open `src/Main.java`
4. Click "Run"

**Using Command Line:**

Windows:
```bash
javac -cp ".;lib/json-simple-1.1.1.jar" -d bin src/*.java
java -cp ".;lib/json-simple-1.1.1.jar;bin" Main
```

Mac/Linux:
```bash
javac -cp ".:lib/json-simple-1.1.1.jar" -d bin src/*.java
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
```

## License

Open source - available for educational purposes.

---

Weather data provided by [OpenWeatherMap](https://openweathermap.org/)

