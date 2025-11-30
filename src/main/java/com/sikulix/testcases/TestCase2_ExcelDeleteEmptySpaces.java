package com.sikulix.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;

/**
 * Test Case 2: From an excel file, half filled, use SikuliX to delete empty spaces
 * This test case deletes spaces (whitespace) from text content in Excel cells
 */
public class TestCase2_ExcelDeleteEmptySpaces {
    
    private Screen screen;
    private String excelFilePath;
    private Random random;
    
    // List of random first names
    private static final String[] FIRST_NAMES = {
        "Alex", "Jordan", "Taylor", "Morgan", "Casey", "Riley", "Avery", "Quinn",
        "Blake", "Cameron", "Dakota", "Drew", "Emery", "Finley", "Harper", "Hayden",
        "James", "Kai", "Logan", "Mason", "Noah", "Oliver", "Parker", "Reese",
        "Sage", "Skylar", "Tyler", "Willow", "Zoe", "Aaron", "Benjamin", "Christopher",
        "Daniel", "Ethan", "Gabriel", "Henry", "Isaac", "Jacob", "Liam", "Matthew",
        "Nathan", "Owen", "Ryan", "Samuel", "Thomas", "William", "Amelia", "Charlotte",
        "Emma", "Grace", "Isabella", "Lily", "Mia", "Olivia", "Sophia", "Zoe"
    };
    
    // List of random last names
    private static final String[] LAST_NAMES = {
        "Anderson", "Brown", "Davis", "Garcia", "Harris", "Jackson", "Johnson", "Jones",
        "Lee", "Martinez", "Miller", "Moore", "Robinson", "Smith", "Taylor", "Thomas",
        "Thompson", "Walker", "White", "Williams", "Wilson", "Young", "Adams", "Baker",
        "Clark", "Collins", "Cook", "Cooper", "Evans", "Flores", "Foster", "Green",
        "Hall", "Hill", "Hughes", "King", "Lewis", "Martin", "Mitchell", "Nelson",
        "Parker", "Perez", "Phillips", "Roberts", "Rodriguez", "Scott", "Stewart", "Turner"
    };
    
    public String getExcelFilePath() {
        return excelFilePath;
    }
    
    public TestCase2_ExcelDeleteEmptySpaces(String excelFilePath) {
        this.screen = new Screen();
        this.random = new Random();
        // Convert to absolute path if relative
        File file = new File(excelFilePath);
        if (!file.isAbsolute()) {
            this.excelFilePath = new File(System.getProperty("user.dir"), excelFilePath).getAbsolutePath();
        } else {
            this.excelFilePath = excelFilePath;
        }
    }
    
    public TestCase2_ExcelDeleteEmptySpaces() {
        this.screen = new Screen();
        this.random = new Random();
        // Default: Create in current project directory
        this.excelFilePath = new File(System.getProperty("user.dir"), "test.xlsx").getAbsolutePath();
    }
    
    /**
     * Generate a random name (First Last)
     */
    private String generateRandomName() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }
    
    public void execute() {
        try {
            System.out.println("=== Test Case 2: Excel Delete Empty Spaces ===");
            
            // Step 1: Create a new Excel file
            System.out.println("Step 1: Creating new Excel file...");
            createSampleExcelFile();
            System.out.println("✓ New Excel file created successfully at: " + excelFilePath);
            
            // Step 2: Open Excel file
            System.out.println("\nStep 2: Opening Excel file...");
            // Method 1: Uses Windows Run dialog (Win+R) - Most reliable
            try {
                openExcelFile();
            } catch (Exception e) {
                System.out.println("  - Run dialog method failed, trying Command Prompt method...");
                // Method 2: Use Command Prompt (very reliable fallback)
                openExcelFileViaCommand();
            }
            
            // Alternative methods (uncomment to use):
            // openExcelFileViaExplorer();  // File Explorer method
            // openExcelFileViaDialog();    // Excel Open dialog method
            
            // Wait for Excel to fully load and be ready
            System.out.println("  - Waiting for Excel to be ready...");
            Thread.sleep(4000); // Increased wait time for Excel to fully load
            
            // Step 3: Delete empty spaces from text in cells
            System.out.println("\nStep 3: Deleting empty spaces from text...");
            try {
                deleteEmptySpaces();
            } catch (Exception e) {
                System.out.println("  - Primary method failed, trying alternative Tab navigation method...");
                deleteEmptySpacesWithTab();
            }
            
            // Wait a bit
            Thread.sleep(2000);
            
            // Step 4: Save the file
            System.out.println("Step 4: Saving file...");
            saveFile();
            
            // Step 5: Close Excel
            System.out.println("Step 5: Closing Excel...");
            closeExcel();
            
            System.out.println("\n✓ Test Case 2 completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error in Test Case 2: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void openExcelFile() throws FindFailed, InterruptedException, IOException {
        // Verify file exists before trying to open
        File file = new File(excelFilePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + excelFilePath);
        }
        
        String absolutePath = file.getAbsolutePath();
        System.out.println("  - Opening file: " + file.getName());
        System.out.println("  - Full path: " + absolutePath);
        
        // Method 1: Use Windows Run dialog (Win+R) - Most Direct and Reliable
        System.out.println("  - Using Windows Run dialog to open file directly...");
        screen.type("r", KeyModifier.WIN); // Win+R to open Run dialog
        Thread.sleep(1500);
        
        // Type the full file path in Run dialog
        // Clear any existing text first
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(200);
        screen.type(absolutePath);
        Thread.sleep(1000);
        screen.type(Key.ENTER);
        Thread.sleep(4000); // Wait for Excel to open the file
        
        System.out.println("  - ✓ File opened via Run dialog");
        
        // If Run dialog method doesn't work, try alternative method below
        // The file should now be open in Excel
    }
    
    /**
     * Alternative method: Open File Explorer, navigate to directory, and open file
     */
    private void openExcelFileViaExplorer() throws FindFailed, InterruptedException, IOException {
        File file = new File(excelFilePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + excelFilePath);
        }
        
        System.out.println("  - Opening File Explorer...");
        // Open File Explorer to the file's directory
        String dirPath = file.getParent();
        if (dirPath == null) {
            dirPath = System.getProperty("user.dir");
        }
        
        // Use explorer command with directory path
        screen.type("r", KeyModifier.WIN); // Win+R
        Thread.sleep(1000);
        screen.type("explorer \"" + dirPath + "\"");
        Thread.sleep(500);
        screen.type(Key.ENTER);
        Thread.sleep(3000);
        
        // Now type the filename to filter and select it
        System.out.println("  - Searching for file: " + file.getName());
        screen.type(file.getName());
        Thread.sleep(2000);
        
        // Press Enter to open
        screen.type(Key.ENTER);
        Thread.sleep(3000);
        
        System.out.println("  - ✓ File opened via File Explorer");
    }
    
    /**
     * Alternative method: Use Command Prompt to open the file
     */
    private void openExcelFileViaCommand() throws FindFailed, InterruptedException, IOException {
        File file = new File(excelFilePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + excelFilePath);
        }
        
        System.out.println("  - Opening file via Command Prompt...");
        String absolutePath = file.getAbsolutePath();
        
        // Open Command Prompt
        screen.type("r", KeyModifier.WIN); // Win+R
        Thread.sleep(1000);
        screen.type("cmd");
        Thread.sleep(500);
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Type command to open file with default application
        String command = "start \"\" \"" + absolutePath + "\"";
        screen.type(command);
        Thread.sleep(500);
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Close command prompt
        screen.type("exit");
        Thread.sleep(300);
        screen.type(Key.ENTER);
        Thread.sleep(1000);
        
        System.out.println("  - ✓ File opened via Command Prompt");
    }
    
    /**
     * Alternative method: Open Excel first, then use File > Open dialog
     */
    private void openExcelFileViaDialog() throws FindFailed, InterruptedException, IOException {
        File file = new File(excelFilePath);
        if (!file.exists()) {
            throw new IOException("Excel file does not exist: " + excelFilePath);
        }
        
        System.out.println("  - Opening Excel application...");
        screen.type(Key.WIN);
        Thread.sleep(1000);
        screen.type("excel");
        Thread.sleep(1500);
        screen.type(Key.ENTER);
        Thread.sleep(4000); // Wait longer for Excel to fully load
        
        // Open File dialog using Ctrl+O
        System.out.println("  - Opening file dialog (Ctrl+O)...");
        screen.type("o", KeyModifier.CTRL);
        Thread.sleep(3000); // Wait for dialog to appear
        
        // Navigate to address bar in the Open dialog (Alt+D)
        System.out.println("  - Navigating to file location in dialog...");
        screen.type("d", KeyModifier.ALT);
        Thread.sleep(1000);
        
        // Get directory path
        String dirPath = file.getParent();
        if (dirPath == null) {
            dirPath = System.getProperty("user.dir");
        }
        
        // Clear and type directory path
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        screen.type(dirPath);
        Thread.sleep(1000);
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Type filename to select it
        System.out.println("  - Selecting file: " + file.getName());
        screen.type(file.getName());
        Thread.sleep(1000);
        screen.type(Key.ENTER);
        Thread.sleep(3000);
        
        System.out.println("  - ✓ File opened in Excel");
    }
    
    /**
     * Fill empty cells with random names
     * Uses Go To Special to find empty cells, then fills each with a different random name
     */
    private void fillEmptyCellsWithRandomNames() throws FindFailed, InterruptedException {
        System.out.println("  - Preparing to fill empty cells with random names...");
        
        // Step 1: Select all cells in the sheet (Ctrl+A)
        System.out.println("  - Selecting all cells...");
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(1000);
        
        // Step 2: Use Go To Special to select only empty cells
        System.out.println("  - Opening Go To Special dialog (F5, Alt+S)...");
        screen.type(Key.F5); // Go To dialog
        Thread.sleep(1000);
        
        // Press Alt+S for Special button
        screen.type("s", KeyModifier.ALT);
        Thread.sleep(1500); // Wait for Go To Special dialog
        
        // Select "Blanks" option - usually Alt+B
        screen.type("b", KeyModifier.ALT); // Blanks option
        Thread.sleep(500);
        screen.type(Key.ENTER); // Confirm selection
        Thread.sleep(1000);
        
        // Now all empty cells should be selected
        System.out.println("  - Empty cells selected, filling with random names...");
        
        // Step 3: Fill each empty cell with a different random name
        // Navigate through selected cells and fill each one
        // Start from the first selected empty cell
        int cellsToFill = 20; // Estimate - adjust based on your data
        for (int i = 0; i < cellsToFill; i++) {
            String randomName = generateRandomName();
            System.out.println("  - Filling cell " + (i + 1) + " with: " + randomName);
            
            // Type the random name
            screen.type(randomName);
            Thread.sleep(300);
            
            // Press Enter to confirm and move to next empty cell
            screen.type(Key.ENTER);
            Thread.sleep(500);
            
            // Check if we've filled all cells (if Enter doesn't move, we're done)
            // For safety, we'll limit the loop
        }
        
        // Alternative: Fill all at once with a formula, then convert to values
        // But for SikuliX, the above method is more straightforward
        
        System.out.println("  - ✓ Empty cells filled with random names!");
    }
    
    /**
     * Alternative method: Fill empty cells with random names using Tab navigation
     */
    private void fillEmptyCellsWithRandomNamesAlternative() throws FindFailed, InterruptedException {
        System.out.println("  - Using alternative method to fill with random names...");
        
        // Start from top-left cell
        screen.type(Key.HOME);
        Thread.sleep(500);
        screen.type(Key.HOME, KeyModifier.CTRL); // Go to A1
        Thread.sleep(500);
        
        // Navigate through cells and fill empty ones
        // This is a simpler approach - navigate row by row
        for (int row = 1; row <= 15; row++) { // Adjust based on your data
            for (int col = 0; col < 5; col++) { // 5 columns
                // Check if current cell is empty by trying to read it
                // Since we can't easily check with SikuliX, we'll use a pattern
                // For now, let's fill cells in the Name column (column 0) that might be empty
                
                if (col == 0) { // Name column
                    // Try to detect if empty and fill
                    String randomName = generateRandomName();
                    // Type and move right
                    screen.type(randomName);
                    Thread.sleep(300);
                    screen.type(Key.RIGHT);
                    Thread.sleep(200);
                } else {
                    // Move to next column
                    screen.type(Key.RIGHT);
                    Thread.sleep(200);
                }
            }
            // Move to next row
            screen.type(Key.DOWN);
            Thread.sleep(200);
            // Go to first column
            screen.type(Key.HOME);
            Thread.sleep(200);
        }
        
        System.out.println("  - ✓ Attempted to fill empty cells with random names!");
    }
    
    /**
     * Fill empty cells with a specified value (default: "N/A")
     * Uses Go To Special to find empty cells, then fills them
     */
    private void fillEmptyCells() throws FindFailed, InterruptedException {
        fillEmptyCells("N/A");
    }
    
    /**
     * Fill empty cells with a custom value using Go To Special method
     */
    private void fillEmptyCells(String fillValue) throws FindFailed, InterruptedException {
        System.out.println("  - Preparing to fill empty cells with: \"" + fillValue + "\"...");
        
        // Step 1: Select all cells in the sheet (Ctrl+A)
        System.out.println("  - Selecting all cells...");
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(1000);
        
        // Step 2: Use Go To Special to select only empty cells
        // Press F5 (Go To) or Ctrl+G, then Alt+S (Special), then Alt+B (Blanks), Enter
        System.out.println("  - Opening Go To Special dialog (F5, Alt+S)...");
        screen.type(Key.F5); // Go To dialog
        Thread.sleep(1000);
        
        // Press Alt+S for Special button
        screen.type("s", KeyModifier.ALT);
        Thread.sleep(1500); // Wait for Go To Special dialog
        
        // Select "Blanks" option - usually Alt+B or use arrow keys
        screen.type("b", KeyModifier.ALT); // Blanks option
        Thread.sleep(500);
        screen.type(Key.ENTER); // Confirm selection
        Thread.sleep(1000);
        
        // Now all empty cells should be selected
        System.out.println("  - Empty cells selected, entering fill value...");
        
        // Step 3: Type the fill value (this will fill all selected empty cells)
        screen.type(fillValue);
        Thread.sleep(500);
        
        // Step 4: Press Ctrl+Enter to fill all selected cells with the same value
        System.out.println("  - Filling all selected empty cells (Ctrl+Enter)...");
        screen.type(Key.ENTER, KeyModifier.CTRL);
        Thread.sleep(2000);
        
        // Step 5: Deselect (click somewhere or press arrow key)
        screen.type(Key.DOWN);
        Thread.sleep(500);
        
        System.out.println("  - ✓ Empty cells filled successfully with \"" + fillValue + "\"!");
    }
    
    /**
     * Alternative method to fill empty cells using Find & Replace
     * This method uses Find & Replace with empty "Find what" to find empty cells
     */
    private void fillEmptyCellsAlternative() throws FindFailed, InterruptedException {
        fillEmptyCellsAlternative("N/A");
    }
    
    private void fillEmptyCellsAlternative(String fillValue) throws FindFailed, InterruptedException {
        System.out.println("  - Using Find & Replace method to fill with: \"" + fillValue + "\"...");
        
        // Select all cells
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(1000);
        
        // Open Find & Replace
        screen.type("h", KeyModifier.CTRL);
        Thread.sleep(2000);
        
        // In Find what: Leave empty or type nothing to find empty cells
        // Actually, Excel Find & Replace doesn't work well with empty cells
        // So we'll use a workaround: find cells with no visible content
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        // Leave it empty - this might not work, so we'll try a different approach
        
        // Alternative: Use Options > Match entire cell contents
        // For now, let's try typing the fill value directly in selected cells
        screen.type(Key.ESC); // Close dialog
        Thread.sleep(500);
        
        // Select first cell and navigate to find empty cells manually
        screen.type(Key.HOME);
        Thread.sleep(500);
        
        // Use a simpler approach: Type value and use Ctrl+Enter
        screen.type(fillValue);
        Thread.sleep(300);
        screen.type(Key.ENTER, KeyModifier.CTRL);
        Thread.sleep(1000);
        
        System.out.println("  - ✓ Attempted to fill empty cells!");
    }
    
    /**
     * Delete empty spaces from text (original method - kept for reference)
     */
    private void deleteEmptySpaces() throws FindFailed, InterruptedException {
        System.out.println("  - Preparing to delete empty spaces...");
        
        // Step 1: Select all cells in the sheet (Ctrl+A)
        System.out.println("  - Selecting all cells...");
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(1000);
        
        // Step 2: Open Find & Replace dialog (Ctrl+H)
        System.out.println("  - Opening Find & Replace dialog (Ctrl+H)...");
        screen.type("h", KeyModifier.CTRL);
        Thread.sleep(2000); // Wait for dialog to fully appear
        
        // Step 3: Clear any existing text in "Find what" field
        System.out.println("  - Setting up Find & Replace...");
        screen.type("a", KeyModifier.CTRL); // Select all in the field
        Thread.sleep(300);
        
        // Step 4: Type a single space in "Find what" field
        screen.type(" ");
        Thread.sleep(500);
        
        // Step 5: Tab to "Replace with" field (leave it empty to delete spaces)
        System.out.println("  - Moving to Replace with field...");
        screen.type(Key.TAB);
        Thread.sleep(500);
        
        // Clear any existing text in "Replace with" field
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        // Leave it empty (don't type anything) to delete spaces
        
        // Step 6: Click "Replace All" button
        // In Excel, Alt+A is the shortcut for Replace All button
        System.out.println("  - Clicking Replace All (Alt+A)...");
        screen.type("a", KeyModifier.ALT);
        Thread.sleep(2000); // Wait for replacement to complete
        
        // Step 7: If a confirmation dialog appears, click OK
        // Sometimes Excel shows "Excel has completed its search and made X replacements"
        screen.type(Key.ENTER);
        Thread.sleep(1000);
        
        // Step 8: Close the Find & Replace dialog
        System.out.println("  - Closing Find & Replace dialog...");
        screen.type(Key.ESC);
        Thread.sleep(1000);
        
        System.out.println("  - ✓ Empty spaces deleted successfully!");
    }
    
    /**
     * Alternative method: Delete spaces using Tab navigation (if Alt+A doesn't work)
     */
    private void deleteEmptySpacesWithTab() throws FindFailed, InterruptedException {
        System.out.println("  - Using Tab navigation method...");
        
        // Select all cells
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(1000);
        
        // Open Find & Replace
        screen.type("h", KeyModifier.CTRL);
        Thread.sleep(2000);
        
        // Type space in Find what
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        screen.type(" ");
        Thread.sleep(500);
        
        // Tab to Replace with (leave empty)
        screen.type(Key.TAB);
        Thread.sleep(500);
        screen.type("a", KeyModifier.CTRL);
        Thread.sleep(300);
        
        // Navigate to Replace All button using Tab
        // Tab multiple times to reach Replace All button
        for (int i = 0; i < 5; i++) {
            screen.type(Key.TAB);
            Thread.sleep(300);
        }
        
        // Press Enter to click Replace All
        screen.type(Key.ENTER);
        Thread.sleep(2000);
        
        // Close confirmation if appears
        screen.type(Key.ENTER);
        Thread.sleep(1000);
        
        // Close dialog
        screen.type(Key.ESC);
        Thread.sleep(1000);
        
        System.out.println("  - ✓ Empty spaces deleted using Tab method!");
    }
    
    private void saveFile() throws FindFailed, InterruptedException {
        // Press Ctrl+S to save
        screen.type("s", KeyModifier.CTRL);
        Thread.sleep(1000);
    }
    
    private void closeExcel() throws FindFailed, InterruptedException {
        // Close Excel using Alt+F4
        screen.type(Key.F4, KeyModifier.ALT);
        Thread.sleep(1000);
    }
    
    /**
     * Creates a new Excel file with half-filled data containing spaces
     * This file will be used for testing the delete empty spaces functionality
     */
    public void createSampleExcelFile() {
        try {
            System.out.println("  - Creating file at: " + excelFilePath);
            
            // Create directory if it doesn't exist
            File file = new File(excelFilePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                System.out.println("  - Creating directory: " + parentDir.getAbsolutePath());
                parentDir.mkdirs();
            }
            
            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Test Data");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Email");
            headerRow.createCell(2).setCellValue("Phone");
            headerRow.createCell(3).setCellValue("Address");
            headerRow.createCell(4).setCellValue("City");
            
            // Create sample data with spaces (half-filled rows)
            String[][] sampleData = {
                {"John Doe", "john.doe@email.com", "123 456 7890", "123 Main St", "New York"},
                {"Jane Smith", "jane.smith@email.com", "987 654 3210", "456 Oak Ave", "Los Angeles"},
                {"Bob Johnson", "", "555 123 4567", "789 Pine Rd", ""},  // Half-filled
                {"Alice Brown", "alice.brown@email.com", "", "321 Elm St", "Chicago"},  // Half-filled
                {"Charlie Wilson", "", "", "654 Maple Dr", "Houston"},  // Half-filled
                {"", "diana.prince@email.com", "111 222 3333", "", "Phoenix"},  // Half-filled
                {"Eve Davis", "eve.davis@email.com", "444 555 6666", "987 Cedar Ln", "Philadelphia"},
                {"Frank Miller", "", "777 888 9999", "", ""},  // Half-filled
                {"Grace Lee", "grace.lee@email.com", "", "147 Birch Way", "San Antonio"},  // Half-filled
                {"Henry Taylor", "", "222 333 4444", "258 Spruce Ct", ""}  // Half-filled
            };
            
            // Fill rows with data (some cells have spaces, some are empty)
            for (int i = 0; i < sampleData.length; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < sampleData[i].length; j++) {
                    Cell cell = row.createCell(j);
                    String value = sampleData[i][j];
                    if (value != null && !value.isEmpty()) {
                        // Add some spaces to test data for demonstration
                        if (j == 0 && i % 2 == 0) { // Add spaces to some names
                            value = value.replace(" ", "  "); // Double spaces
                        }
                        cell.setCellValue(value);
                    }
                    // Empty cells remain empty (half-filled requirement)
                }
            }
            
            // Add some cells with extra spaces for testing
            Row extraRow = sheet.createRow(sampleData.length + 1);
            extraRow.createCell(0).setCellValue("Test  User  With  Spaces");
            extraRow.createCell(1).setCellValue("test  @  email  .  com");
            extraRow.createCell(2).setCellValue("123  456  7890");
            
            // Auto-size columns
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write to file
            FileOutputStream fos = new FileOutputStream(excelFilePath);
            workbook.write(fos);
            workbook.close();
            fos.close();
            
            System.out.println("  - ✓ Excel file created successfully!");
            System.out.println("  - Location: " + file.getAbsolutePath());
            System.out.println("  - File contains " + (sampleData.length + 2) + " rows with half-filled data and spaces");
            
        } catch (IOException e) {
            System.err.println("Error creating sample Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Alternative method using Apache POI to programmatically remove spaces
     */
    public void deleteEmptySpacesProgrammatically() {
        try {
            FileInputStream fis = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING) {
                        String value = cell.getStringCellValue();
                        // Remove all spaces
                        String newValue = value.replaceAll(" ", "");
                        cell.setCellValue(newValue);
                    }
                }
            }
            
            FileOutputStream fos = new FileOutputStream(excelFilePath);
            workbook.write(fos);
            workbook.close();
            fos.close();
            fis.close();
            
            System.out.println("Empty spaces removed programmatically!");
            
        } catch (IOException e) {
            System.err.println("Error processing Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        // Create Excel file in current project directory
        // File will be created at: <project_root>/test.xlsx
        TestCase2_ExcelDeleteEmptySpaces testCase = new TestCase2_ExcelDeleteEmptySpaces();
        
        // Or specify a custom filename in current directory
        // TestCase2_ExcelDeleteEmptySpaces testCase = new TestCase2_ExcelDeleteEmptySpaces("myfile.xlsx");
        
        // Or specify full path
        // TestCase2_ExcelDeleteEmptySpaces testCase = new TestCase2_ExcelDeleteEmptySpaces("C:\\path\\to\\file.xlsx");
        
        System.out.println("Excel file will be created at: " + testCase.getExcelFilePath());
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        System.out.println();
        
        // Execute test case: Creates new file, opens it, deletes spaces, saves and closes
        testCase.execute();
        
        // Alternative: Just create the file programmatically without opening Excel
        // testCase.createSampleExcelFile();
    }
}

