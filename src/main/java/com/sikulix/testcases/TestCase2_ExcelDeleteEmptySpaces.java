package com.sikulix.testcases;

import java.io.File;
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
    }

    public void execute() {
        try {
            File file = new File(excelFilePath);
            if (!file.exists()) {
                System.err.println("\nERROR: Excel file does not exist → " + excelFilePath);
                System.err.println("Please place test.xlsx in the folder before running.");
                return;
            }

            System.out.println("\nFound existing file: " + excelFilePath);
            System.out.println("Proceeding with Sikuli automation...");

            System.out.println("\nStep 1: Opening File Explorer via Start Menu");
            screen.type(Key.WIN);
            Thread.sleep(1000);
            screen.type("File Explorer");
            Thread.sleep(1000);
            screen.type(Key.ENTER);
            Thread.sleep(8000);

            System.out.println("\nNavigating to folder...");
            for(int i=0; i<5; i++) {
                screen.type(Key.TAB);
                Thread.sleep(300);
            }

            screen.type(excelFolderPath);
            Thread.sleep(1000);
            screen.type(Key.ENTER);
            Thread.sleep(6000);

            System.out.println("\nOpening Excel file...");
            screen.type(Key.DOWN);
            Thread.sleep(500);
            screen.type(Key.ENTER);
            Thread.sleep(10000);

            System.out.println("\nSelect all cells...");
            screen.type("a", KeyModifier.CTRL);
            Thread.sleep(1000);

            System.out.println("\nOpen Find & Replace...");
            screen.type("h", KeyModifier.CTRL);
            Thread.sleep(2000);

            System.out.println("\nReplace spaces with nothing...");
            screen.type(" ");
            Thread.sleep(500);
            screen.type(Key.TAB);
            Thread.sleep(500);

            screen.type("a", KeyModifier.ALT);
            Thread.sleep(2000);
            screen.type(Key.ENTER);
            Thread.sleep(1000);
            screen.type(Key.ESC);

            System.out.println("\nClosing Excel WITHOUT saving");
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
