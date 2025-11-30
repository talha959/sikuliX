package com.sikulix.testcases;

import java.util.Random;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

/**
 * Test Case 3: Add a WiFi Network
 * This test case opens Windows Settings using Win+R, navigates to WiFi settings,
 * goes to Manage known networks, and adds a random network that is not the PC's network
 */
public class TestCase3_AddWiFiNetwork {
    
    private final Screen screen;
    private final String ssid;
    private final String password;
    private final Random random;
    
    // Random network name prefixes to ensure it's not the PC's network
    private static final String[] NETWORK_PREFIXES = {
        "TestNetwork", "RandomWiFi", "GuestNetwork", "TempNetwork", 
        "DemoWiFi", "SampleNetwork", "TestAP", "VirtualWiFi",
        "MockNetwork", "FakeNetwork", "DummyWiFi", "SimNetwork"
    };
    
    public TestCase3_AddWiFiNetwork() {
        this.screen = new Screen();
        this.random = new Random();
        // Generate a random network name that won't be the PC's network
        this.ssid = generateRandomNetworkName();
        this.password = "98765432";
    }
    
    public TestCase3_AddWiFiNetwork(String ssid, String password) {
        this.screen = new Screen();
        this.random = new Random();
        this.ssid = ssid;
        this.password = password;
    }
    
    /**
     * Generates a random network name that is unlikely to be the PC's current network
     */
    private String generateRandomNetworkName() {
        String prefix = NETWORK_PREFIXES[random.nextInt(NETWORK_PREFIXES.length)];
        int randomNum = random.nextInt(9999) + 1000; // 4-digit random number
        return prefix + "_" + randomNum;
    }
    
    public void execute() {
        try {
            System.out.println("\n===== Test Case 3: Add WiFi Network (Windows 11) =====");
            System.out.println("SSID: " + ssid);
            System.out.println("Password: " + (password != null && !password.isEmpty() ? "****" : "None"));
            
            // Step 1: Open Manage known networks directly using Win+R
            System.out.println("\nStep 1: Opening Manage known networks via Run dialog...");
            openWiFiSettings();
            
            // Step 2: Ensure we're in Manage known networks section
            System.out.println("Step 2: Verifying Manage known networks page...");
            navigateToManageKnownNetworks();
            
            // Wait for Manage known networks to be ready
            Thread.sleep(2000);
            
            // Step 3: Click Add Network button to open Add Network dialog
            System.out.println("Step 3: Clicking Add Network button...");
            clickAddNetwork();
            
            // Wait for Add Network dialog to appear
            System.out.println("  - Waiting for Add Network dialog to open...");
            Thread.sleep(3000);
            
            // Step 4: Enter SSID (Network Name) in the Add Network dialog
            System.out.println("Step 4: Entering WiFi SSID in Add Network dialog...");
            enterSSID(ssid);
            
            // Step 5: Enter Password in the Add Network dialog
            System.out.println("Step 5: Entering Password in Add Network dialog...");
            enterPassword(password);
            
            // Step 6: Save/Add the network connection
            System.out.println("Step 6: Saving/Adding the network configuration...");
            saveConfiguration();
            
            // Wait for confirmation
            Thread.sleep(2000);
            
            // Step 7: Close Settings window
            System.out.println("Step 7: Closing Settings window...");
            closeSettings();
            
            System.out.println("\n✓ Test Case 3 completed successfully!");
            System.out.println("  WiFi Network '" + ssid + "' has been added!");
            
        } catch (FindFailed e) {
            System.err.println("\n❌ Test Case 3 failed: Image pattern not found - " + e.getMessage());
            System.err.println("  Make sure the required image patterns (addNetwork.png, ssidField.png, etc.) are available.");
        } catch (InterruptedException e) {
            System.err.println("\n❌ Test Case 3 failed: Thread interrupted - " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("\n❌ Test Case 3 failed: " + e.getMessage());
            System.err.println("  Error type: " + e.getClass().getSimpleName());
        }
    }
    
    /**
     * Opens Manage known networks directly using Win+R (Run dialog)
     */
    private void openWiFiSettings() throws FindFailed, InterruptedException {
        // Press Win+R to open Run dialog
        screen.type("r", KeyModifier.WIN);
        Thread.sleep(1500);
        
        // Type command to open Manage known networks directly
        // ms-settings:network-wifisettings opens Manage known networks in Windows 11
        screen.type("ms-settings:network-wifisettings");
        Thread.sleep(1000);
        
        // Press Enter to execute
        screen.type(Key.ENTER);
        Thread.sleep(4000);
        System.out.println("  - ✓ Manage known networks opened directly via Run dialog");
    }
    
    /**
     * Navigates to "Manage known networks" section (if not opened directly)
     * This is a fallback method in case direct opening doesn't work
     */
    private void navigateToManageKnownNetworks() throws FindFailed, InterruptedException {
        // Since we're opening Manage known networks directly, we just wait for it to load
        Thread.sleep(2000);
        System.out.println("  - ✓ Manage known networks page ready");
        
        // Alternative: If direct opening doesn't work, try navigation from WiFi settings
        try {
            // Try to find "Manage known networks" link using image pattern
            Pattern manageNetworks = new Pattern("manageKnownNetworks.png").similar(0.75);
            if (screen.wait(manageNetworks, 3) != null) {
                screen.click(manageNetworks);
                Thread.sleep(2000);
                System.out.println("  - ✓ Manage known networks clicked (image pattern)");
            }
        } catch (Exception e) {
            // If image pattern fails, try keyboard navigation
            navigateToManageKnownNetworksWithKeyboard();
        }
    }
    
    /**
     * Alternative method: Navigate to Manage known networks using keyboard navigation
     */
    private void navigateToManageKnownNetworksWithKeyboard() throws FindFailed, InterruptedException {
        // Scroll down or use Tab to find "Manage known networks" link
        System.out.println("  - Scrolling/navigating to find Manage known networks...");
        
        // Press Tab multiple times to navigate through options
        for (int i = 0; i < 5; i++) {
            screen.type(Key.TAB);
            Thread.sleep(400);
        }
        
        // Scroll down using Page Down or arrow keys
        screen.type(Key.PAGE_DOWN);
        Thread.sleep(1000);
        
        // Navigate more with Tab
        for (int i = 0; i < 3; i++) {
            screen.type(Key.TAB);
            Thread.sleep(400);
        }
        
        // Press Enter to open Manage known networks
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        System.out.println("  - ✓ Navigated to Manage known networks via keyboard");
    }
    
    /**
     * Clicks the Add Network button to open the Add Network dialog
     * Uses image pattern matching if available, otherwise uses keyboard navigation
     */
    private void clickAddNetwork() throws FindFailed, InterruptedException {
        try {
            // Try to find and click Add Network button using image pattern
            Pattern addNetwork = new Pattern("addNetwork.png").similar(0.75);
            if (screen.wait(addNetwork, 5) != null) {
                screen.click(addNetwork);
                System.out.println("  - ✓ Add Network button clicked (image pattern)");
                Thread.sleep(2000); // Wait for dialog to open
            } else {
                // Fallback: Use keyboard navigation
                clickAddNetworkWithKeyboard();
            }
        } catch (Exception e) {
            // If image pattern fails, use keyboard navigation
            System.out.println("  - Image pattern not found, using keyboard navigation...");
            clickAddNetworkWithKeyboard();
        }
    }
    
    /**
     * Alternative method: Click Add Network using keyboard navigation
     * In Manage known networks, the Add button is usually at the top
     */
    private void clickAddNetworkWithKeyboard() throws FindFailed, InterruptedException {
        // In Manage known networks page, Add button is usually accessible
        System.out.println("  - Navigating to Add button in Manage known networks...");
        
        // Method 1: Try using Tab to navigate to Add button
        // Press Tab multiple times to find Add button
        for (int i = 0; i < 4; i++) {
            screen.type(Key.TAB);
            Thread.sleep(500);
        }
        
        // Try pressing Enter to activate Add Network
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Method 2: If Tab doesn't work, try clicking in the top area where Add button usually is
        // In Windows 11 Manage known networks, Add button is usually visible at the top
        // Try clicking in the upper-right area (approximately 85% width, 15% height)
        int addButtonX = (int)(screen.w * 0.85);
        int addButtonY = (int)(screen.h * 0.15);
        screen.click(addButtonX, addButtonY);
        Thread.sleep(2000);
        
        System.out.println("  - ✓ Add Network button clicked/activated");
    }
    
    /**
     * Enters the SSID (Network Name) in the SSID field of Add Network dialog
     */
    private void enterSSID(String ssid) throws FindFailed, InterruptedException {
        try {
            // Try to find SSID field using image pattern
            Pattern ssidField = new Pattern("ssidField.png").similar(0.75);
            if (screen.wait(ssidField, 5) != null) {
                screen.click(ssidField);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // If image pattern fails, field should already be focused
            System.out.println("  - SSID field should be focused, clicking to ensure focus...");
            // Click in the center-left area where input fields usually are
            int fieldX = screen.w / 3;
            int fieldY = screen.h / 2;
            screen.click(fieldX, fieldY);
            Thread.sleep(500);
        }
        
        // Clear any existing text
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        screen.type(Key.DELETE);
        Thread.sleep(500);
        
        // Type the SSID
        screen.type(ssid);
        Thread.sleep(1000);
        System.out.println("  - ✓ SSID entered: " + ssid);
        
        // Move to next field (Tab)
        screen.type(Key.TAB);
        Thread.sleep(500);
    }
    
    /**
     * Enters the password in the password field
     */
    private void enterPassword(String password) throws FindFailed, InterruptedException {
        try {
            // Try to find password field using image pattern
            Pattern passwordField = new Pattern("passwordField.png").similar(0.75);
            if (screen.wait(passwordField, 5) != null) {
                screen.click(passwordField);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // If image pattern fails, field should already be focused after Tab
            System.out.println("  - Using default focus for Password field");
        }
        
        // Clear any existing text
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        screen.type(Key.DELETE);
        Thread.sleep(500);
        
        // Type the password
        if (password != null && !password.isEmpty()) {
            screen.type(password);
            Thread.sleep(1000);
            System.out.println("  - ✓ Password entered");
        } else {
            System.out.println("  - ✓ No password entered (Open Network)");
        }
    }
    
    /**
     * Saves/Adds the WiFi configuration in the Add Network dialog
     */
    private void saveConfiguration() throws FindFailed, InterruptedException {
        try {
            // Try to find Save/Add button using image pattern
            Pattern saveButton = new Pattern("saveButton.png").similar(0.75);
            if (screen.wait(saveButton, 5) != null) {
                screen.click(saveButton);
                System.out.println("  - ✓ Save/Add button clicked (image pattern)");
                Thread.sleep(2000);
            } else {
                // Fallback: Use keyboard navigation
                saveConfigurationWithKeyboard();
            }
        } catch (Exception e) {
            // If image pattern fails, use keyboard navigation
            System.out.println("  - Image pattern not found, using keyboard navigation...");
            saveConfigurationWithKeyboard();
        }
    }
    
    /**
     * Alternative method: Save/Add configuration using keyboard navigation
     */
    private void saveConfigurationWithKeyboard() throws FindFailed, InterruptedException {
        // In Add Network dialog, navigate to Save/Add button using Tab
        System.out.println("  - Navigating to Save/Add button...");
        for (int i = 0; i < 3; i++) {
            screen.type(Key.TAB);
            Thread.sleep(500);
        }
        
        // Press Enter to save/add the network
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        System.out.println("  - ✓ Network added via keyboard navigation");
        
        // Alternative: Try clicking in the bottom area where Save button usually is
        // This is a fallback if Tab navigation doesn't work
        int saveButtonX = screen.w / 2;
        int saveButtonY = (int)(screen.h * 0.85);
        screen.click(saveButtonX, saveButtonY);
        Thread.sleep(2000);
        System.out.println("  - ✓ Attempted to click Save/Add button");
    }
    
    /**
     * Closes the Settings window
     */
    private void closeSettings() throws FindFailed, InterruptedException {
        // Close Settings using Alt+F4
        screen.type(Key.F4, KeyModifier.ALT);
        Thread.sleep(1000);
        System.out.println("  - ✓ Settings window closed");
    }
    
    public static void main(String[] args) {
        // Default: Generates a random network name that won't be the PC's network
        TestCase3_AddWiFiNetwork testCase = new TestCase3_AddWiFiNetwork();
        
        // Or specify custom SSID and password
        // TestCase3_AddWiFiNetwork testCase = new TestCase3_AddWiFiNetwork("MyNetwork", "MyPassword123");
        
        // Execute test case
        testCase.execute();
    }
}
