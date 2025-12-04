package com.sikulix.testcases;

import org.sikuli.script.*;

public class DrawShapeNoImages {

    public static void main(String[] args) throws Exception {

        Screen s = new Screen();

        // 1. Open MS Paint
        s.keyDown(Key.WIN);
        s.type("r");
        s.keyUp(Key.WIN);
        Thread.sleep(600);

        s.type("mspaint");
        s.type(Key.ENTER);
        Thread.sleep(2000);

        // 2. Maximize
        s.keyDown(Key.WIN);
        s.type(Key.UP);
        s.keyUp(Key.WIN);
        Thread.sleep(1000);

        // 3. Navigate to the Rectangle Tool
        for (int i = 0; i < 18; i++) {
            s.type(Key.TAB);
            Thread.sleep(50); // Small delay makes it more stable
        }

        for (int i = 0; i < 3; i++) {
            s.type(Key.RIGHT);
            Thread.sleep(50);
        }

        s.type(Key.ENTER); // Select the tool
        Thread.sleep(500);

        // ---------------------------------------------------------
        // 4. DRAWING LOGIC (Fixed)
        // ---------------------------------------------------------

        // Step A: Calculate where to draw (Center of screen)
        int x1 = s.getW() / 2 - 200; // Start point X
        int y1 = s.getH() / 2 - 100; // Start point Y

        int x2 = s.getW() / 2 + 200; // End point X
        int y2 = s.getH() / 2 + 100; // End point Y

        // Step B: Move to the starting point
        s.mouseMove(new Location(x1, y1));
        Thread.sleep(500);

        // Step C: Press and HOLD the mouse button
        s.mouseDown(Button.LEFT);
        Thread.sleep(200);

        // Step D: Move to the end point (while holding the button)
        s.mouseMove(new Location(x2, y2));
        Thread.sleep(500);

        // Step E: Release the button
        s.mouseUp(Button.LEFT);

        System.out.println("✓ Rectangle tool selected and shape drawn.");
    }
}