package com.sikulix.testcases;

/**
 * Main test runner to execute all test cases
 */
public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("SikuliX Test Cases - Windows 11");
        System.out.println("==========================================\n");
        
        // Run all test cases
        try {
            // Test Case 1: Web Browser Converter
            System.out.println("\n>>> Starting Test Case 1 <<<\n");
            TestCase1_WebBrowserConverter testCase1 = new TestCase1_WebBrowserConverter();
            testCase1.execute();
            
            Thread.sleep(2000);
            
            // Test Case 2: Excel Delete Empty Spaces
            System.out.println("\n>>> Starting Test Case 2 <<<\n");
            // Creates Excel file in current project directory (sikuliX/test.xlsx)
            TestCase2_ExcelDeleteEmptySpaces testCase2 = new TestCase2_ExcelDeleteEmptySpaces();
            System.out.println("File will be created at: " + testCase2.getExcelFilePath());
            testCase2.execute();
            
            Thread.sleep(2000);
            
            // Test Case 3: Add WiFi Network
            System.out.println("\n>>> Starting Test Case 3 <<<\n");
//            TestCase3_AddWiFiNetwork testCase3 = new TestCase3_AddWiFiNetwork();
//            testCase3.execute();
            
            Thread.sleep(2000);
            
            // Test Case 4: Paint Draw Shape
            System.out.println("\n>>> Starting Test Case 4 <<<\n");
            TestCase4_PaintDrawShape testCase4 = new TestCase4_PaintDrawShape();
            testCase4.execute();
            
            System.out.println("\n==========================================");
            System.out.println("All test cases completed!");
            System.out.println("==========================================");
            
        } catch (Exception e) {
            System.err.println("Error running test cases: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

