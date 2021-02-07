/** Welcome to the drawing board! User Instructions:
 * Hold the left mouse button and drag for free hand drawing
 * CLick the left mouse button twice to draw a straight line
 * Click the right mouse button to start at a new location
 * Press space bar to clear the board
 */

/**
 * author: Chelsea Lee
 * date: 1/29/21
 * version 1.0
 */

import static org.lwjgl.opengl.GL11.*;
import java.util.Random;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
public class Main {

    public Main() {
        //List<Box> shapes = new ArrayList<Box>(16);
        try {
            Display.setDisplayMode(new DisplayMode(1000, 600));
            Display.setTitle("DrawingBoard");
            //Display.setInitialBackground(256, 256, 256);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        //OpenGL Initialization
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 1000, 600, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        // initialize two x-y coordinates
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        int switchRecording = 0;  // this variable constantly records one of the two coordinates
        boolean start = false;

        while (!Display.isCloseRequested()) { // while the drawing board is open

            if(Mouse.isButtonDown(0)) {       // if the left mouse button is pressed
                if (switchRecording == 0) {   // toggle between the two coordinates (x1,y1)(x2,y2) for recording
                    x1 = Mouse.getX();
                    y1 = Mouse.getY();

                    switchRecording = 1;
                }
                else {
                    x2 = Mouse.getX();
                    y2 = Mouse.getY();

                    switchRecording = 0;
                    start = true;   //changes to true when both non-zero coordinates are recorded to start drawing
                }
            }


            /* if the right mouse button is pressed, the
               locations of the coordinates are cleared and you can't draw yet
               - resets the location of the new sequence of recordings
             */
            if(Mouse.isButtonDown(1)) {
                start = false;
                switchRecording = 0;
            }

            // Once you have the non-zero coordinates, the lines will be drawn
            if (start == true) {
                glBegin(GL_LINES);
                    glColor3f(1.0f, 1.0f, 1.0f); // White
                    glVertex2i(x1, 600 - y1);
                    glVertex2i(x2, 600 - y2);
                glEnd();
            }


                if (Keyboard.isKeyDown(Keyboard.KEY_S)){
                    glBegin(GL_QUADS);
                        glColor3f(0.81f, 0.23f, 0.66f); // Purple
                        int random_x = (int)(Math.random()*(1000) + 0);
                        int random_y = (int)(Math.random()*(600) + 0);
                        glVertex2i(random_x,random_y); //upper left corner of square
                        glVertex2i(random_x+100, random_y); //upper right corner of square
                        glVertex2i(random_x+100, random_y+100); //bottom right corner of square
                        glVertex2i(random_x,random_y+100); //bottom left corner of square
                    glEnd();
                }

            if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
                glBegin(GL_TRIANGLES);
                    glColor3f(0.0f, 0.81f, 0.23f); // Green
                    int random_tx = (int)(Math.random()*(1000) + 0);
                    int random_ty = (int)(Math.random()*(600) + 0);
                    glVertex2i(random_tx,random_ty); //bottom left corner of triangle
                    glVertex2i(random_tx-100, random_ty); //bottom right corner of triangle
                    glVertex2i(random_tx-50, random_ty-100); //top corner of triangle
                glEnd();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
                glBegin(GL_QUADS);
                    glColor3f(0.90f, 0.90f, 0.10f); // Yellow
                    int random_rx = (int)(Math.random()*(1000) + 0);
                    int random_ry = (int)(Math.random()*(600) + 0);
                    glVertex2i(random_rx,random_ry); //upper left corner of rectangle
                    glVertex2i(random_rx+100, random_ry); //upper right corner of rectangle
                    glVertex2i(random_rx+100, random_ry-50); //bottom right corner of rectangle
                    glVertex2i(random_rx,random_ry-50); //bottom left corner of rectangle
                glEnd();
            }





            // Once you press the space bar, the screen will clear
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            }

            int dx = Mouse.getDX();
            int dy = -Mouse.getDY();
            System.out.println(dx + " " + dy);



            //Swaps the buffers and make what has been drawn visible
            Display.update();
            Display.sync(60);

        }
        Display.destroy(); //destroys the native Display and cleans up any resources used by it
        System.exit(0);
    }



    public static void main(String[] args) {
        new Main();
    }
}