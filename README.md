# SikuliX Test Cases - Windows 11 Automation

This project contains automated test cases using SikuliX for usability testing on Windows 11. The test cases demonstrate GUI automation capabilities for common Windows applications and tasks.

## 📋 Table of Contents

- [Overview](#overview)
- [Requirements](#requirements)
- [Installation](#installation)
- [Test Cases](#test-cases)
- [Running the Tests](#running-the-tests)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Troubleshooting](#troubleshooting)

## 🎯 Overview

This project implements 4 test cases for usability testing using SikuliX:

1. **Web Browser Converter** - Opens a web browser, searches for hexadecimal to binary converter, and performs conversion
2. **Excel Delete Empty Spaces** - Creates an Excel file, opens it, and deletes empty spaces from text
3. **Add WiFi Network** - Opens Windows Settings and adds a WiFi network to known networks
4. **Paint Draw Shape** - Opens Paint application and draws shapes (rectangle, circle, triangle)

## 📦 Requirements

- **Operating System**: Windows 11
- **Java**: JDK 11 or higher
- **Maven**: 3.6+ (for dependency management)
- **SikuliX**: 2.0.5 (automatically downloaded via Maven)
- **Applications** (must be installed):
  - Web Browser (Chrome/Edge)
  - Microsoft Excel
  - Microsoft Paint

## 🚀 Installation

### Step 1: Download SikuliX

Download SikuliX from: https://raiman.github.io/SikuliX1/downloads.html

### Step 2: Clone/Download the Project

```bash
git clone <repository-url>
cd sikuliX
```

### Step 3: Build the Project

```bash
mvn clean install
```

This will download all required dependencies including:
- SikuliX API (2.0.5)
- Apache POI (for Excel operations)
- JUnit (for testing)

## 🧪 Test Cases

### Test Case 1: Web Browser Converter

**Objective**: Open a web browser, search for hexadecimal to binary converter, and perform conversion.

**Features**:
- Opens Chrome/Edge browser
- Searches for "hexadecimal to binary converter"
- Opens a converter website
- Converts a hexadecimal value (default: "FF") to binary
- Closes the browser

**Run individually**:
```bash
java -cp target/classes com.sikulix.testcases.TestCase1_WebBrowserConverter
```

**Custom hex value**:
```java
TestCase1_WebBrowserConverter testCase = new TestCase1_WebBrowserConverter("A1B2");
testCase.execute();
```

### Test Case 2: Excel Delete Empty Spaces

**Objective**: Create an Excel file with half-filled data, open it, and delete empty spaces from text.

**Features**:
- Creates a sample Excel file (`test.xlsx`) with random data
- Opens the Excel file using Windows Run dialog
- Uses Find & Replace to delete all spaces from text
- Saves and closes the file

**Run individually**:
```bash
java -cp target/classes com.sikulix.testcases.TestCase2_ExcelDeleteEmptySpaces
```

**Custom file path**:
```java
TestCase2_ExcelDeleteEmptySpaces testCase = new TestCase2_ExcelDeleteEmptySpaces("path/to/file.xlsx");
testCase.execute();
```

### Test Case 3: Add WiFi Network

**Objective**: Open Windows Settings, navigate to Manage known networks, and add a random WiFi network.

**Features**:
- Opens WiFi Settings using Win+R (Run dialog)
- Navigates to "Manage known networks"
- Adds a new WiFi network with random SSID
- Enters network credentials (SSID and password)
- Saves the configuration

**Run individually**:
```bash
java -cp target/classes com.sikulix.testcases.TestCase3_AddWiFiNetwork
```

**Custom network**:
```java
TestCase3_AddWiFiNetwork testCase = new TestCase3_AddWiFiNetwork("MyNetwork", "MyPassword123");
testCase.execute();
```

**Note**: Default SSID is "Note 11" with password "98765432". The code generates random network names to avoid conflicts with existing networks.

### Test Case 4: Paint Draw Shape

**Objective**: Open Paint application and draw shapes using shape tools.

**Features**:
- Opens Microsoft Paint
- Selects rectangle tool and draws a rectangle
- Selects circle tool and draws a circle
- Selects triangle tool and draws a triangle
- Closes Paint

**Run individually**:
```bash
java -cp target/classes com.sikulix.testcases.TestCase4_PaintDrawShape
```

## ▶️ Running the Tests

### Run All Test Cases

Use the TestRunner class to execute all test cases sequentially:

```bash
java -cp target/classes com.sikulix.testcases.TestRunner
```

### Run Individual Test Cases

Each test case can be run independently:

```bash
# Test Case 1
java -cp target/classes com.sikulix.testcases.TestCase1_WebBrowserConverter

# Test Case 2
java -cp target/classes com.sikulix.testcases.TestCase2_ExcelDeleteEmptySpaces

# Test Case 3
java -cp target/classes com.sikulix.testcases.TestCase3_AddWiFiNetwork

# Test Case 4
java -cp target/classes com.sikulix.testcases.TestCase4_PaintDrawShape
```

### Using Maven

```bash
# Compile
mvn compile

# Run with Maven exec plugin (if configured)
mvn exec:java -Dexec.mainClass="com.sikulix.testcases.TestRunner"
```

## 📁 Project Structure

```
sikuliX/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── sikulix/
│                   └── testcases/
│                       ├── TestCase1_WebBrowserConverter.java
│                       ├── TestCase2_ExcelDeleteEmptySpaces.java
│                       ├── TestCase3_AddWiFiNetwork.java
│                       ├── TestCase4_PaintDrawShape.java
│                       └── TestRunner.java
├── target/
│   └── classes/          # Compiled classes
├── pom.xml               # Maven configuration
├── README.md            # This file
└── test.xlsx            # Generated Excel file (after Test Case 2)
```

## 🔧 Dependencies

The project uses the following main dependencies:

- **SikuliX API 2.0.5** - GUI automation framework
- **Apache POI 5.2.5** - Excel file operations
- **JUnit 4.13.2** - Testing framework (test scope)

All dependencies are managed via Maven and will be automatically downloaded during build.

## ⚠️ Troubleshooting

### Common Issues

1. **"Image pattern not found" errors**
   - Some test cases use image pattern matching as a fallback
   - The code includes keyboard navigation fallbacks
   - Ensure applications are in their default state

2. **Coordinates outside screen bounds**
   - Test Case 4 includes coordinate clamping to prevent this
   - If issues persist, adjust screen resolution or window size

3. **Excel file not opening**
   - Ensure Microsoft Excel is installed
   - Check file path permissions
   - Try running as administrator if needed

4. **WiFi Settings not opening**
   - Ensure you're running on Windows 11
   - Check that Settings app is accessible
   - May require administrator privileges for network changes

5. **Paint not responding**
   - Ensure Paint is installed (included with Windows)
   - Close any existing Paint windows before running
   - Wait for Paint to fully load before drawing

### Performance Tips

- Close unnecessary applications before running tests
- Ensure stable internet connection for Test Case 1
- Run tests one at a time for better reliability
- Increase wait times if your system is slow

## 📝 Notes

- All test cases are designed for **Windows 11**
- Test cases use keyboard shortcuts and mouse automation
- Image pattern matching is optional (keyboard navigation is used as fallback)
- Some test cases may require user interaction or confirmation dialogs
- Test Case 3 adds a network to "Manage known networks" - this is a real network entry

## 👤 Author

SikuliX Test Cases Project

## 📄 License

This project is for educational and testing purposes.

---

**Last Updated**: 2024

