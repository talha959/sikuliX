package com.sikulix.testcases;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;

public class TestCase2_ExcelDeleteEmptySpaces {
    private final Screen screen;

    private final String excelFilePath;
    private final String excelFolderPath;

    public TestCase2_ExcelDeleteEmptySpaces() {

        this.screen = new Screen();

        String projectDir = System.getProperty("user.dir");

        String relativePath = "src/main/java/com/sikulix/testcases/File";

        File dir = new File(projectDir, relativePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        this.excelFolderPath = dir.getAbsolutePath();
        this.excelFilePath = new File(dir, "test.xlsx").getAbsolutePath();

        createSampleExcelFile();

    }

    private void createSampleExcelFile() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            String[] data = {
                    "John  Doe", " Jane Smith", "Bob  Johnson ", "Alice Brown",
                    " Charlie  Davis", "Diana Evans ", " Frank Miller", "Grace  Wilson",
                    "Henry  Moore", " Ivy  Taylor", "Jack Anderson", " Kelly Thomas ",
                    "Liam  Jackson", "Mia  White", "Noah  Harris", " Olivia Martin",
                    "Paul  Thompson", "Quinn  Garcia", "Ryan  Martinez", " Sophia Robinson",
                    "Tom  Clark", "Uma  Rodriguez", "Victor  Lewis", "Wendy  Lee", "Xavier  Walker"
            };

            for (int i = 0; i < data.length; i++) {
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(data[i]);
            }

            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void execute() {
        try {
            System.out.println("\nTest Case 2: Excel Delete Empty Spaces (25 Records)");

            System.out.println("\nStep 1: Opening File Explorer via Start Menu");

            screen.type(Key.WIN);

            Thread.sleep(1000);

            screen.type("File Explorer");

            Thread.sleep(1000);

            screen.type(Key.ENTER);

            System.out.println("\nWaiting 5 seconds for Explorer to open");
            Thread.sleep(10000);

            System.out.println("\nTabbing 5 times to reach address bar");

            for(int i=0; i<5; i++) {
                screen.type(Key.TAB);
                Thread.sleep(300);
            }

            System.out.println("\nStep 3: Typing Folder Path");

            screen.type(excelFolderPath);

            Thread.sleep(1000);

            screen.type(Key.ENTER);

            System.out.println("\nWaiting 2 seconds for folder to load");
            Thread.sleep(6000);

            System.out.println("\nStep 4: Selecting and Opening Excel File");

            screen.type(Key.DOWN);

            Thread.sleep(500);

            screen.type(Key.ENTER);

            System.out.println("\nWaiting 15 seconds for Excel to load");

            Thread.sleep(10000);

            System.out.println("\nSelecting all cells");

            screen.type("a", KeyModifier.CTRL);

            Thread.sleep(1000);

            System.out.println("\nOpening Find & Replace dialog");

            screen.type("h", KeyModifier.CTRL);

            Thread.sleep(2000);

            System.out.println("\nSetting up replacement");

            screen.type(" ");

            Thread.sleep(500);

            screen.type(Key.TAB);

            Thread.sleep(500);

            System.out.println("\nClicking Replace All");

            screen.type("a", KeyModifier.ALT);

            Thread.sleep(2000);

            screen.type(Key.ENTER);

            Thread.sleep(1000);

            screen.type(Key.ESC);

            System.out.println("\nClosing Excel with 'Do Not Save'");

            screen.type(Key.F4, KeyModifier.ALT);

            Thread.sleep(1500);

            screen.type(Key.TAB);

            Thread.sleep(500);

            screen.type(Key.ENTER);

            System.out.println("\nTest Case Completed Successfully");

        } catch (Exception e) {

            System.err.println("\nTest Case Failed: " + e.getMessage());

        }

    }

    public static void main(String[] args) {

        new TestCase2_ExcelDeleteEmptySpaces().execute();

    }

}