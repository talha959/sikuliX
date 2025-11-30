package com.sikulix.testcases;

import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;

/**
 * Test Case 4: Open Paint and draw a shape
 */
public class TestCase4_PaintDrawShape {
    
    private final Screen screen;
    
    public TestCase4_PaintDrawShape() {
        this.screen = new Screen();
    }
    
    public void execute() {
        try {
            System.out.println("=== Test Case 4: Paint Draw Shape ===");
            
            // Step 1: Open Paint application
            System.out.println("Step 1: Opening Paint...");
            openPaint();
            
            // Wait for Paint to load
            Thread.sleep(3000);
            
            // Step 2: Draw rectangle using shape tool
            System.out.println("\nStep 2: Drawing rectangle...");
            selectRectangleTool();
            Thread.sleep(1000);
            drawRectangle();
            Thread.sleep(2000);
            
            // Step 3: Draw circle using shape tool
            System.out.println("\nStep 3: Drawing circle...");
            selectCircleTool();
            Thread.sleep(1000);
            drawCircle();
            Thread.sleep(2000);
            
            // Step 4: Draw triangle using shape tool
            System.out.println("\nStep 4: Drawing triangle...");
            selectTriangleTool();
            Thread.sleep(1000);
            drawTriangle();
            Thread.sleep(2000);
            
            // Wait to see the results
            Thread.sleep(2000);
            
            // Step 5: Close Paint
            System.out.println("\nStep 5: Closing Paint...");
            closePaint();
            
            System.out.println("\n✓ Test Case 4 completed successfully!");
            System.out.println("  Shapes drawn: Rectangle, Circle, and Triangle!");
            
        } catch (FindFailed e) {
            System.err.println("Error in Test Case 4: Image pattern not found - " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error in Test Case 4: Thread interrupted - " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Error in Test Case 4: " + e.getMessage());
            System.err.println("  Error type: " + e.getClass().getSimpleName());
        }
    }
    
    private void openPaint() throws FindFailed, InterruptedException {
        // Press Windows key to open Start menu
        screen.type(Key.WIN);
        Thread.sleep(1000);
        
        // Type "paint" to search
        screen.type("paint");
        Thread.sleep(1500);
        
        // Press Enter to open Paint
        screen.type(Key.ENTER);
        Thread.sleep(3000);
        
        // Wait for Paint window to fully load
        Thread.sleep(2000);
    }
    
    /**
     * Selects rectangle shape tool
     */
    private void selectRectangleTool() throws FindFailed, InterruptedException {
        // Click on canvas first to focus
        int canvasX = getSafeCenterX();
        int canvasY = getSafeCenterY();
        screen.click(canvasX, canvasY);
        Thread.sleep(500);
        
        // Click on Shapes button in toolbar
        int shapesButtonX = clampX(400);
        int shapesButtonY = clampY(100);
        screen.click(shapesButtonX, shapesButtonY);
        Thread.sleep(1000);
        
        // Select rectangle (usually first option)
        screen.type(Key.DOWN);
        Thread.sleep(300);
        screen.type(Key.ENTER);
        Thread.sleep(500);
        
        System.out.println("  - ✓ Rectangle tool selected");
    }
    
    /**
     * Selects circle/ellipse shape tool
     */
    private void selectCircleTool() throws FindFailed, InterruptedException {
        // Click on canvas first to focus
        int canvasX = getSafeCenterX();
        int canvasY = getSafeCenterY();
        screen.click(canvasX, canvasY);
        Thread.sleep(500);
        
        // Click on Shapes button in toolbar
        int shapesButtonX = clampX(400);
        int shapesButtonY = clampY(100);
        screen.click(shapesButtonX, shapesButtonY);
        Thread.sleep(1000);
        
        // Select circle/ellipse (usually second or third option)
        screen.type(Key.DOWN);
        Thread.sleep(300);
        screen.type(Key.DOWN);
        Thread.sleep(300);
        screen.type(Key.ENTER);
        Thread.sleep(500);
        
        System.out.println("  - ✓ Circle tool selected");
    }
    
    /**
     * Selects triangle shape tool
     */
    private void selectTriangleTool() throws FindFailed, InterruptedException {
        // Click on canvas first to focus
        int canvasX = getSafeCenterX();
        int canvasY = getSafeCenterY();
        screen.click(canvasX, canvasY);
        Thread.sleep(500);
        
        // Click on Shapes button in toolbar
        int shapesButtonX = clampX(400);
        int shapesButtonY = clampY(100);
        screen.click(shapesButtonX, shapesButtonY);
        Thread.sleep(1000);
        
        // Select triangle (navigate to triangle option)
        for (int i = 0; i < 5; i++) {
            screen.type(Key.DOWN);
            Thread.sleep(200);
        }
        screen.type(Key.ENTER);
        Thread.sleep(500);
        
        System.out.println("  - ✓ Triangle tool selected");
    }
    
    /**
     * Helper method to ensure coordinates are within screen bounds
     * Uses more conservative bounds to account for window borders and toolbars
     */
    private int clampX(int x) {
        int minX = 200; // Leave more margin on left
        int maxX = screen.w - 200; // Leave more margin on right
        return Math.max(minX, Math.min(x, maxX));
    }
    
    private int clampY(int y) {
        int minY = 200; // Leave margin for toolbar at top
        int maxY = screen.h - 200; // Leave margin at bottom
        return Math.max(minY, Math.min(y, maxY));
    }
    
    /**
     * Gets safe center coordinates for drawing (accounting for toolbar and margins)
     */
    private int getSafeCenterX() {
        return clampX(screen.w / 2);
    }
    
    private int getSafeCenterY() {
        return clampY((screen.h / 2) + 100); // Offset down more to avoid toolbar
    }
    
    /**
     * Draws a rectangle by clicking and dragging
     */
    private void drawRectangle() throws FindFailed, InterruptedException {
        int centerX = getSafeCenterX();
        int centerY = getSafeCenterY();
        int width = 120;
        int height = 80;
        
        int startX = clampX(centerX - width / 2);
        int startY = clampY(centerY - height / 2);
        int endX = clampX(centerX + width / 2);
        int endY = clampY(centerY + height / 2);
        
        // Click and drag to draw
        screen.mouseMove(startX, startY);
        Thread.sleep(300);
        screen.mouseDown(Button.LEFT);
        Thread.sleep(300);
        screen.mouseMove(endX, endY);
        Thread.sleep(300);
        screen.mouseUp(Button.LEFT);
        Thread.sleep(1000);
        
        System.out.println("  - ✓ Rectangle drawn");
    }
    
    /**
     * Draws a circle by clicking and dragging
     */
    private void drawCircle() throws FindFailed, InterruptedException {
        int centerX = getSafeCenterX();
        int centerY = getSafeCenterY();
        int radius = 60;
        
        int startX = clampX(centerX - radius);
        int startY = clampY(centerY - radius);
        int endX = clampX(centerX + radius);
        int endY = clampY(centerY + radius);
        
        // Click and drag to draw circle
        screen.mouseMove(startX, startY);
        Thread.sleep(300);
        screen.mouseDown(Button.LEFT);
        Thread.sleep(300);
        screen.mouseMove(endX, endY);
        Thread.sleep(300);
        screen.mouseUp(Button.LEFT);
        Thread.sleep(1000);
        
        System.out.println("  - ✓ Circle drawn");
    }
    
    /**
     * Draws a triangle by clicking and dragging
     */
    private void drawTriangle() throws FindFailed, InterruptedException {
        int centerX = getSafeCenterX();
        int centerY = getSafeCenterY();
        int size = 60;
        
        // Triangle bounding box
        int startX = clampX(centerX - size);
        int startY = clampY(centerY - size);
        int endX = clampX(centerX + size);
        int endY = clampY(centerY + size);
        
        // Click and drag to draw triangle
        screen.mouseMove(startX, startY);
        Thread.sleep(300);
        screen.mouseDown(Button.LEFT);
        Thread.sleep(300);
        screen.mouseMove(endX, endY);
        Thread.sleep(300);
        screen.mouseUp(Button.LEFT);
        Thread.sleep(1000);
        
        System.out.println("  - ✓ Triangle drawn");
    }
    
    
    private void closePaint() throws FindFailed, InterruptedException {
        // Close Paint using Alt+F4
        screen.type(Key.F4, KeyModifier.ALT);
        Thread.sleep(1000);
        
        // If "Save changes?" dialog appears, choose Don't Save
        // Press Tab to navigate to "Don't Save" button
        screen.type(Key.TAB);
        Thread.sleep(500);
        screen.type(Key.ENTER);
        Thread.sleep(1000);
        
        // Alternative: Click close button (X) on window
        // Pattern closeButton = new Pattern("close_button.png");
        // screen.click(closeButton);
    }
    
    public static void main(String[] args) {
        TestCase4_PaintDrawShape testCase = new TestCase4_PaintDrawShape();
        testCase.execute();
    }
}

