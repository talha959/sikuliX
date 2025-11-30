package com.sikulix.testcases;

import java.util.Random;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;

public class TestCase3_AddWiFiNetwork {
    private final Screen screen;

    private final String ssid;

    private final String password;

    private final Random random;

    private static final String[] NETWORK_PREFIXES = {
            "HiddenLab", "SecureWEP", "TestWiFi"
    };
    public TestCase3_AddWiFiNetwork() {

        this.screen = new Screen();

        this.random = new Random();

        this.ssid = generateRandomNetworkName();

        this.password = "1234567890";

    }

    private String generateRandomNetworkName() {

        String prefix = NETWORK_PREFIXES[random.nextInt(NETWORK_PREFIXES.length)];

        int randomNum = random.nextInt(9999) + 1000;

        return prefix + "_" + randomNum;

    }

    public void execute() {
        try {
            System.out.println("\nAdd WiFi Network (WEP / Hidden)");

            System.out.println("\nOpening Network & internet > Wi-Fi");

            screen.type("r", KeyModifier.WIN);

            Thread.sleep(2000);

            screen.type("ms-settings:network-wifi");

            screen.type(Key.ENTER);

            System.out.println("\nWaiting 10 seconds for Settings app");

            Thread.sleep(10000);

            System.out.println("\nNavigating inside Wi-Fi menu");

            screen.type(Key.TAB); Thread.sleep(500);

            screen.type(Key.TAB); Thread.sleep(500);

            screen.type(Key.TAB); Thread.sleep(500);

            screen.type(Key.TAB); Thread.sleep(500);

            System.out.println("\nPressing Enter to go inside 'Manage known networks'");

            screen.type(Key.ENTER);

            System.out.println("\nWaiting 8 seconds");

            Thread.sleep(4000);

            System.out.println("\nClicking 'Add network'");

            screen.type(Key.TAB);screen.type(Key.TAB);screen.type(Key.TAB);

            Thread.sleep(1000);

            screen.type(Key.ENTER);

            System.out.println("\nWaiting 8 seconds for Dialog");

            Thread.sleep(8000);

            System.out.println("\nTyping SSID");

            screen.type(ssid);

            Thread.sleep(1500);

            System.out.println("\nSelecting WEP");

            screen.type(Key.TAB);

            screen.type(Key.ENTER);

            screen.type(Key.DOWN);

            screen.type(Key.ENTER);

            Thread.sleep(1000);

            System.out.println("\nTyping Password");

            screen.type(Key.TAB);

            Thread.sleep(1000);

            screen.type(password);

            Thread.sleep(1500);

            System.out.println("\nChecking Hidden Network box");

            screen.type(Key.TAB);

            Thread.sleep(500);

            screen.type(Key.TAB);

            Thread.sleep(500);

            screen.type(Key.SPACE);

            Thread.sleep(1000);

            System.out.println("\nSaving");

            Thread.sleep(500);

            screen.type(Key.TAB);

            Thread.sleep(500);

            screen.type(Key.ENTER);

            System.out.println("\nNetwork Added.");

        } catch (Exception e) {

            System.err.println("\nTest Case Failed: " + e.getMessage());

        }

    }

    public static void main(String[] args) {

        new TestCase3_AddWiFiNetwork().execute();

    }

}