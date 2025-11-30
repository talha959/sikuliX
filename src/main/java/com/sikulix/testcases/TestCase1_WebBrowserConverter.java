package com.sikulix.testcases;

import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;

public class TestCase1_WebBrowserConverter {
    private final Screen screen;
    private final String hexValue;

    public TestCase1_WebBrowserConverter() {
        this.screen = new Screen();
        this.hexValue = "FF";
    }

    public void execute() {
        try {
            System.out.println("\nTest Case 1: Web Browser Hex to Binary Converter");
            System.out.println("\nOpening Web Browser");
            screen.type(Key.WIN);
            Thread.sleep(1000);
            screen.type("chrome");
            Thread.sleep(1000);
            screen.type(Key.ENTER);

            System.out.println("\nWaiting 3 seconds for Browser to load");
            Thread.sleep(3000);
            screen.type(Key.TAB);
            Thread.sleep(3000);
            screen.type(Key.ENTER);
            Thread.sleep(3000);
            System.out.println("\nNavigating to Converter Website");
            screen.type("d", KeyModifier.ALT);
            Thread.sleep(500);
            screen.type("https://www.rapidtables.com/convert/number/hex-to-binary.html");
            Thread.sleep(1000);

            screen.type(Key.ENTER);
            System.out.println("\nWaiting 5 seconds for Page to load");
            Thread.sleep(8000);
            System.out.println("\nEntering Hex Value");
            screen.type("a", KeyModifier.CTRL);
            Thread.sleep(500);
            screen.type(hexValue);
            Thread.sleep(1000);
            System.out.println("\nPerforming Conversion");
            screen.type(Key.ENTER);
            System.out.println("\nWaiting 3 seconds for Result");
            Thread.sleep(8000);

            System.out.println("\nTest Case Completed Successfully");

            screen.type(Key.F4, KeyModifier.ALT);


        } catch (Exception e) {

            System.err.println("\nTest Case Failed: " + e.getMessage());

        }

    }

    public static void main(String[] args) {
        new TestCase1_WebBrowserConverter().execute();
    }

}