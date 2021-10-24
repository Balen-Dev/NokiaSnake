package com.NokiaSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; // Size of each item in this game (See draw method's for-loop for visual aid)
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS]; // Holds Body Parts on X Axis
    final int[] y = new int[GAME_UNITS]; // Holds Body Parts on Y Axis
    int bodyParts = 6;
    int applesConsumed = 0;
    int appleX; // Random apple x-coordinate spawn position
    int appleY; // Random apple y-coordinate spawn position
    char direction = 'R'; // Begins the game by the snake going right
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x1f1f1f));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

       if (running) {
           /* This for loop is only for visualizing a grid to help you understand how each possible
           position and their size of 25px, meaning it is not necessary for the game to work or run */
//           for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; ++i) {
//               g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//               g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//           }

           // The 2 following lines will draw an apple
           g.setColor(new Color(0x67C50000, true));
           g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

           // The following lines will draw the snake
           for(int i=0; i<bodyParts; ++i) {
               if (i == 0) {
                   g.setColor(new Color(0x00695C));
                   g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
               } else {
                   g.setColor(new Color(0x004D40));
                   /* Next line will make a rainbow snake, uncomment it if you want that
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))); */
                   g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
               }
           }
           g.setColor(new Color(0x59006064, true));
           g.setFont(new Font("Arial", Font.BOLD, 40));
           FontMetrics metrics = getFontMetrics(g.getFont());
           g.drawString("Score: " + applesConsumed, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesConsumed)) / 2, g.getFont().getSize());
       } else {
           gameOver(g);
       }

    }

    // Generate coordinates for a new apple when the last one is eaten by the snake
    public void newApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for(int i=bodyParts; i>0; --i) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
            break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
            break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
            break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
            break;
        }

    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesConsumed++;
            newApple();
        }
    }

    public void checkCollisions() {
        // Chekcs if head collides with body
        for(int i=bodyParts; i>0; --i) {
            if ((x[0] == x[i]) && (y[0] == y[i]) ) {
                running = false;
            }
        }

        // Checks if head touches left border
        if (x[0] < 0)
            running = false;

        // Check if head touches right border
        if (x[0] > SCREEN_WIDTH)
            running = false;

        // Check if head touches top border
        if (y[0] < 0)
            running = false;

        // Check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT)
            running = false;

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // Display The Score
        g.setColor(new Color(0x6EF44336, true));
        g.setFont(new Font("Arrus BT", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesConsumed, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesConsumed)) / 2, 400 - g.getFont().getSize());
        // Game over text
        g.setColor(new Color(0xE0F44336, true));
        g.setFont(new Font("Arrus BT", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R')
                        direction = 'L';
                break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L')
                        direction = 'R';
                break;
                case KeyEvent.VK_UP:
                    if (direction != 'D')
                        direction = 'U';
                break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U')
                        direction = 'D';
                break;
            }
        }
    }

}
