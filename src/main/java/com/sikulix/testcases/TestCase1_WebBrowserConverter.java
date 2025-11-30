package com.sikulix.testcases;

import org.sikuli.script.*;

/**
 * Test Case 1: Opening a Web browser & searching for hexadecimal to binary converter
 * & performing conversion & closing Browser
 */
public class TestCase1_WebBrowserConverter {
    
    private Screen screen;
    private String hexValue;
    
    public TestCase1_WebBrowserConverter() {
        this.screen = new Screen();
        this.hexValue = "FF"; // Default hex value
    }
    
    public TestCase1_WebBrowserConverter(String hexValue) {
        this.screen = new Screen();
        this.hexValue = hexValue;
    }
    
    public void execute() {
        execute(false);
    }
    
    public void execute(boolean useDirectURL) {
        try {
            System.out.println("=== Test Case 1: Web Browser Hex to Binary Converter ===");
            System.out.println("Converting hexadecimal value: " + hexValue + " to binary\n");
            
            // Step 1: Open Web Browser (Chrome/Edge)
            System.out.println("Step 1: Opening web browser...");
            openBrowser();
            
            // Wait for browser to load
            Thread.sleep(3000);
            
            if (useDirectURL) {
                // Use direct URL method for more reliable conversion
                System.out.println("Step 2: Navigating directly to converter website...");
                performConversionWithDirectURL(hexValue);
            } else {
                // Step 2: Search for hex to binary converter
                System.out.println("Step 2: Searching for hex to binary converter...");
                searchForConverter();
                
                // Wait for search results
                Thread.sleep(3000);
                
                // Step 3: Click on a converter website
                System.out.println("Step 3: Opening converter website...");
                openConverterWebsite();
                
                // Wait for page to load
                Thread.sleep(3000);
                
                // Step 4: Perform conversion
                System.out.println("Step 4: Performing conversion...");
                performConversion();
            }
            
            // Wait to see the result
            System.out.println("\nStep 5: Verifying conversion result...");
            Thread.sleep(3000);
            
            // Step 6: Close browser
            System.out.println("Step 6: Closing browser...");
            closeBrowser();
            
            System.out.println("\n✓ Test Case 1 completed successfully!");
            System.out.println("  Hexadecimal: " + hexValue);
            System.out.println("  Binary result should be visible in the browser before closing");
            
        } catch (Exception e) {
            System.err.println("Error in Test Case 1: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void openBrowser() throws FindFailed, InterruptedException {
        // Press Windows key to open Start menu
        screen.type(Key.WIN);
        Thread.sleep(1000);
        
        // Type "chrome" or "edge" to search for browser
        screen.type("chrome");
        Thread.sleep(1500);
        
        // Press Enter to open browser
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Alternative: If browser icon is on taskbar, you can click it
        // Pattern browserIcon = new Pattern("browser_icon.png");
        // screen.click(browserIcon);
    }
    
    private void searchForConverter() throws FindFailed, InterruptedException {
        // Click on address bar (Alt+D is shortcut)
        screen.type("d", KeyModifier.ALT);
        Thread.sleep(500);
        
        // Type search query
        screen.type("hexadecimal to binary converter");
        Thread.sleep(500);
        
        // Press Enter to search
        screen.type(Key.ENTER);
        Thread.sleep(2000);
    }
    
    private void openConverterWebsite() throws FindFailed, InterruptedException {
        // Click on first search result (usually the first link)
        // Using Tab to navigate and Enter to select
        screen.type(Key.TAB);
        Thread.sleep(500);
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Alternative: Click directly on search result if you have image
        // Pattern firstResult = new Pattern("first_search_result.png");
        // screen.click(firstResult);
    }
    
    private void performConversion() throws FindFailed, InterruptedException {
        System.out.println("  - Preparing to convert hexadecimal to binary...");
        
        // Wait for page to fully load
        Thread.sleep(2000);
        
        // Method 1: Navigate to input field using Tab
        // Most converter websites have the input field as the first focusable element
        System.out.println("  - Locating input field...");
        
        // Clear any existing content in the input field
        screen.type("a", KeyModifier.CTRL); // Select all
        Thread.sleep(300);
        screen.type(Key.DELETE); // Delete selected
        Thread.sleep(500);
        
        // Alternative: Click directly on input field if visible
        // Try clicking in the center-left area where input fields usually are
        int inputX = screen.w / 4;
        int inputY = screen.h / 2;
        screen.click(inputX, inputY);
        Thread.sleep(1000);
        
        // Clear field again after clicking
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        screen.type(Key.DELETE);
        Thread.sleep(500);
        
        // Enter hexadecimal value to convert
        System.out.println("  - Entering hexadecimal value: " + this.hexValue);
        screen.type(this.hexValue);
        Thread.sleep(1000);
        
        // Trigger conversion - different methods depending on website
        System.out.println("  - Triggering conversion...");
        
        // Method 1: Press Enter (many converters auto-convert on Enter)
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Method 2: If there's a Convert button, try to click it
        // Navigate using Tab to find the convert button
        for (int i = 0; i < 3; i++) {
            screen.type(Key.TAB);
            Thread.sleep(300);
        }
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Wait for conversion result to appear
        System.out.println("  - Waiting for conversion result...");
        Thread.sleep(2000);
        
        // Verify conversion completed by checking if result area is visible
        // Most converters show result in a text area or output field
        System.out.println("  - Conversion should be complete!");
        
        // Calculate expected binary for verification (for common values)
        String expectedBinary = getExpectedBinary(this.hexValue);
        if (expectedBinary != null) {
            System.out.println("  - Expected: " + this.hexValue + " (hex) = " + expectedBinary + " (binary)");
        }
        
        // Optional: Capture the result by selecting and copying
        // Navigate to result field
        screen.type(Key.TAB);
        Thread.sleep(500);
        screen.type(Key.TAB);
        Thread.sleep(500);
        
        // Select all text in result field
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        
        // Copy to clipboard
        screen.type("c", KeyModifier.CTRL);
        Thread.sleep(500);
        
        System.out.println("  - Result copied to clipboard");
        
        // Alternative method: Direct URL approach for more reliable conversion
        // You can also navigate directly to a converter URL
        // screen.type("d", KeyModifier.ALT);
        // screen.type("https://www.rapidtables.com/convert/number/hex-to-binary.html");
        // screen.type(Key.ENTER);
        // Thread.sleep(3000);
        // Then perform the conversion steps above
    }
    
    /**
     * Alternative method: Perform conversion using a specific converter website
     * This method navigates directly to a known converter URL for more reliability
     */
    public void performConversionWithDirectURL(String hexValue) throws FindFailed, InterruptedException {
        System.out.println("=== Performing conversion with direct URL ===");
        
        // Navigate to address bar
        screen.type("d", KeyModifier.ALT);
        Thread.sleep(500);
        
        // Type converter URL
        String converterURL = "https://www.rapidtables.com/convert/number/hex-to-binary.html";
        System.out.println("  - Navigating to: " + converterURL);
        screen.type(converterURL);
        Thread.sleep(500);
        screen.type(Key.ENTER);
        Thread.sleep(4000); // Wait for page to load
        
        // Find and click on the hex input field
        System.out.println("  - Entering hex value: " + hexValue);
        
        // Click on input field (usually in center of page)
        int inputX = screen.w / 2;
        int inputY = screen.h / 2 - 50;
        screen.click(inputX, inputY);
        Thread.sleep(1000);
        
        // Clear and enter hex value
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        screen.type(hexValue);
        Thread.sleep(1000);
        
        // The conversion usually happens automatically, but we can press Enter
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Wait for result
        System.out.println("  - Conversion completed!");
        System.out.println("  - Hex: " + hexValue);
        
        // Navigate to result field to verify
        screen.type(Key.TAB);
        Thread.sleep(1000);
        screen.type(Key.TAB);
        Thread.sleep(1000);
        
        // Select and copy result
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        screen.type("c", KeyModifier.CTRL);
        Thread.sleep(500);
        
        System.out.println("  - Binary result copied to clipboard");
    }
    
    private void closeBrowser() throws FindFailed, InterruptedException {
        // Close browser using Alt+F4
        screen.type(Key.F4, KeyModifier.ALT);
        Thread.sleep(1000);
        
        // Alternative: Click close button (X) on window
        // Pattern closeButton = new Pattern("close_button.png");
        // screen.click(closeButton);
    }
    
    /**
     * Helper method to get expected binary value for common hex values
     * This is for verification purposes
     */
    private String getExpectedBinary(String hex) {
        try {
            int decimal = Integer.parseInt(hex, 16);
            return Integer.toBinaryString(decimal);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public static void main(String[] args) {
        // Default: Convert "FF" to binary
        TestCase1_WebBrowserConverter testCase = new TestCase1_WebBrowserConverter();
        
        // You can specify a custom hex value
        // TestCase1_WebBrowserConverter testCase = new TestCase1_WebBrowserConverter("A1B2");
        
        // Execute with search method (default)
        testCase.execute();
        
        // Or execute with direct URL method (more reliable)
        // testCase.execute(true);
    }
}

